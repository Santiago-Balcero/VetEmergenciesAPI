package tc_sb_35_api.tc_sb_35_api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tc_sb_35_api.tc_sb_35_api.dto.EmergencyConverter;
import tc_sb_35_api.tc_sb_35_api.dto.EmergencyDetailDTO;
import tc_sb_35_api.tc_sb_35_api.dto.EmergencyFormDTO;
import tc_sb_35_api.tc_sb_35_api.dto.EmergencyTableDTO;
import tc_sb_35_api.tc_sb_35_api.dto.SubscriptionConverter;
import tc_sb_35_api.tc_sb_35_api.model.Emergency;
import tc_sb_35_api.tc_sb_35_api.service.EmergencyService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/emergency")
public class EmergencyController {

    @Autowired
    EmergencyService emergencyService;

    @Autowired
    EmergencyConverter emergencyConverter;

    @Autowired
    SubscriptionConverter subscriptionConverter;

    // Emergency entity does not have DELETE methods because for report and business purposes data is needed

    // This endpoint updates status of emergencies from Pending to either Aprroved or Rejected following business rules
    // Rules: maximum 2 approved emergencies during the subscription year
    // List<Long> has two numbers, first one is idEmergency and second one is idSubscription
    @PutMapping
    public ResponseEntity<Object> approveEmergency(@RequestBody List<Long> emergencySubscriptionIds) {
        log.info("HTTP REQUEST: approve emergency, idEmergency: " + emergencySubscriptionIds.get(0));
        String newStatus = emergencyService.approveEmergency(emergencySubscriptionIds);
        if(newStatus.equals("Approved") || newStatus.equals("Rejected")) {
            log.info("HTTP RESPONSE: confimation message, emergency was " + newStatus + ", idEmergency: " + emergencySubscriptionIds.get(0));
            return new ResponseEntity<>("La emergencia con id " + emergencySubscriptionIds.get(0) + 
                " fue " + (newStatus.equals("Approved") ? "APROBADA" : "RECHAZADA") + ". Suscripción # " + emergencySubscriptionIds.get(1) 
                + (newStatus.equals("Approved") ? "." : " ha superado el límite de cobertura o se encuentra inactiva."), HttpStatus.OK);
        }
        log.info("HTTP RESPONSE: unable to process emergency message: idEmergency: " + emergencySubscriptionIds.get(0));
        return new ResponseEntity<>("Error al procesar la emergencia.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // List<Long> has two numbers, first one is idEmergency and second one is idSubscription
    @PutMapping("/confirm")
    public ResponseEntity<Object> confirmEmergency(@RequestBody List<Long> emergencySubscriptionIds) {
        log.info("HTTP REQUEST: confirm emergency, idEmergency: " + emergencySubscriptionIds.get(0));
        String newStatus = emergencyService.confirmEmergency(emergencySubscriptionIds);
        if(newStatus.equals("Confirmed")) {
            log.info("HTTP RESPONSE: confimation message, emergency was CONFIRMED, idEmergency: " + emergencySubscriptionIds.get(0));
            return new ResponseEntity<>("La emergencia con id " + emergencySubscriptionIds.get(0) + 
            " fue CONFIRMADA exitosamente. El cliente con subscripción # " + emergencySubscriptionIds.get(1) + " ha sido atendido.",
            HttpStatus.OK);
        }
        log.info("HTTP RESPONSE: unable to process emergency message: idEmergency: " + emergencySubscriptionIds.get(0));
        return new ResponseEntity<>("Error al procesar la emergencia.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping
    public ResponseEntity<Object> createEmergency(@Valid @RequestBody EmergencyFormDTO emergencyDto) {
        log.info("HTTP REQUEST: create new emergency");
        Emergency emergency = emergencyConverter.convertToEmergency(emergencyDto);
        Long idEmergency = emergencyService.createEmergency(emergency);
        if (!idEmergency.equals(0L)) {
            log.info("HTTP RESPONSE: confirmation message, idEmergency: " + idEmergency);
            return new ResponseEntity<>("Emergencia registrada exitosamente con el id: " + idEmergency, HttpStatus.CREATED);
        }
        log.info("HTTP RESPONSE: error message");
        return new ResponseEntity<>("Error al registrar la emergencia.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{idEmergency}")
    public ResponseEntity<Object> getEmergencyById(@PathVariable Long idEmergency) {
        log.info("HTTP REQUEST: get emergency by id, idEmergency: " + idEmergency);
        Emergency emergency = emergencyService.getEmergencyById(idEmergency);
        EmergencyDetailDTO emergencyDetailDto = emergencyConverter.convertToEmergencyDetailDto(emergency);
        log.info("HTTP RESPONSE: EmergencyDetailDTO, idEmergency: " + idEmergency);
        return new ResponseEntity<>(emergencyDetailDto, HttpStatus.OK);     
    }

    // For MVP this method is set to get emergencies with "Pending" status
    // Future iterations of this project may include searching for status according to frontend request
    // For that purpose this method will receive as parameter a String with emergencyStatus
    @GetMapping
    public ResponseEntity<Object> getEmergenciesByEmergencyStatus() {
        log.info("HTTP REQUEST: get emergencies by status Pending (for MVP)");
        List<Emergency> emergencies = emergencyService.getEmergenciesByEmergencyStatus();
        List<EmergencyTableDTO> emergenciesDto = emergencies.stream()
            .map(emergency -> emergencyConverter.convertToEmergencyTableDto(emergency)).collect(Collectors.toList());
        log.info("HTTP RESPONSE: list of EmergencyTableDTO");
        return new ResponseEntity<>(emergenciesDto, HttpStatus.OK);
    }

}
