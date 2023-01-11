package tc_sb_35_api.tc_sb_35_api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tc_sb_35_api.tc_sb_35_api.dto.ClinicConverter;
import tc_sb_35_api.tc_sb_35_api.dto.ClinicDetailDTO;
import tc_sb_35_api.tc_sb_35_api.dto.ClinicEmergencyFormDTO;
import tc_sb_35_api.tc_sb_35_api.dto.ClinicFormDTO;
import tc_sb_35_api.tc_sb_35_api.dto.ClinicMapDTO;
import tc_sb_35_api.tc_sb_35_api.dto.ClinicTableDTO;
import tc_sb_35_api.tc_sb_35_api.model.Clinic;
import tc_sb_35_api.tc_sb_35_api.service.ClinicService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/clinic")
public class ClinicController {
    
    @Autowired
    ClinicService clinicService;

    @Autowired
    ClinicConverter clinicConverter;

    @PutMapping
    public ResponseEntity<Object> updateClinic(@RequestBody ClinicDetailDTO clinicDetailDto) {
        log.info("HTTP REQUEST: update clinic, idClinic: " + clinicDetailDto.getIdClinic());
        Clinic clinic = clinicConverter.convertToClinicDetail(clinicDetailDto);
        // Check if update was successful
        if (clinicService.updateClinic(clinic)) {
            log.info("HTTP RESPONSE: confirmation message, idClinic: " + clinic.getIdClinic());
            return new ResponseEntity<>("Clínica actualizada exitosamente.", HttpStatus.OK);
        }
        log.info("HTTP RESPONSE: error message, idClinic: " + clinic.getIdClinic());
        return new ResponseEntity<>("Error al actualizar la clínica.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Activate and deactivate clinics instead of deleting them as agreed with PO
    @PutMapping("/activestatus/{newStatus}")
    public ResponseEntity<Object> updateActiveStatus(@RequestBody Long idClinic, @PathVariable("newStatus") int newStatus) {
        log.info("HTTP REQUEST: deactivate clinic, idClinic: " + idClinic);
        // Check if update was successful
        if (clinicService.updateActiveStatus(idClinic, newStatus)) {
            log.info("HTTP RESPONSE: confirmation message, idClinic: " + idClinic);
            return new ResponseEntity<>("Clínica " + (newStatus == 1 ? "activada" : "desactivada") + " exitosamente.", HttpStatus.OK);
        }
        log.info("HTTP RESPONSE: error message, idClinic: " + idClinic);
        return new ResponseEntity<>("Error al " + (newStatus == 1 ? "activar" : "desactivar") + " la clínica.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping
    public ResponseEntity<Object> createClinic(@Valid @RequestBody ClinicFormDTO clinicFormDto) {
        log.info("HTTP REQUEST: create new clinic");
        Clinic clinic = clinicConverter.convertToClinicForm(clinicFormDto);
        Long idClinic = clinicService.createClinic(clinic);
        log.info("HTTP RESPONSE: confirmation message, idClinic: " + idClinic);
        return new ResponseEntity<>("Clínica registrada exitosamente con el id: " + idClinic + ".", HttpStatus.CREATED);
    }

    // Returns list of active clinics to be shown in clinics table in frontend for client role
    // For client role only active clinics must be shown
    @GetMapping
    public ResponseEntity<Object> getActiveClinics() {
        log.info("HTTP REQUEST: get active clinics for table");
        List<Clinic> clinics = clinicService.getActiveClinics();
        if (clinics.size() == 0) {
            log.info("HTTP RESPONSE: no clinics found message");
            return new ResponseEntity<>("No hay clínicas activas en el sistema.", HttpStatus.NOT_FOUND);
        }
        List<ClinicTableDTO> clinicsTable = clinics.stream()
            .map(clinic -> clinicConverter.convertToTableDto(clinic)).collect(Collectors.toList());
        log.info("HTTP RESPONSE: list of ClinicTableDto");
        return new ResponseEntity<>(clinicsTable, HttpStatus.OK);
    }

    // Returns list of active clinics with data to be used in Google map
    @GetMapping(path = "/map")
    public ResponseEntity<Object> getActiveClinicsMap() {
        log.info("HTTP REQUEST: get active clinics for map");
        List<Clinic> clinics = clinicService.getActiveClinics();
        if (clinics.size() == 0) {
            log.info("HTTP RESPONSE: no clinics found message");
            return new ResponseEntity<>("No hay clínicas activas en el sistema.", HttpStatus.NOT_FOUND);
        }
        List<ClinicMapDTO> clinicsMap = clinics.stream()
            .map(clinic -> clinicConverter.convertToMapDto(clinic)).collect(Collectors.toList());
        log.info("HTTP RESPONSE: list of ClinicMapDto");
        return new ResponseEntity<>(clinicsMap, HttpStatus.OK);
    }

    // Returns list of all clinics to be shown in clinics table in frontend for admin role
    // For admin role all clinics must be shown independent of clinic status
    @GetMapping(path = "/all")
    public ResponseEntity<Object> getAllClinics() {
        log.info("HTTP REQUEST: get all clinics for table");
        List<Clinic> clinics = clinicService.getAllClinics();
        if (clinics.size() == 0) {
            log.info("HTTP RESPONSE: no clinics found message");
            return new ResponseEntity<>("No hay clínicas registradas en el sistema.", HttpStatus.NOT_FOUND);
        }
        List<ClinicTableDTO> clinicsTable = clinics.stream()
            .map(clinic -> clinicConverter.convertToTableDto(clinic)).collect(Collectors.toList());
        log.info("HTTP RESPONSE: list of ClinicTableDto");      
        return new ResponseEntity<>(clinicsTable, HttpStatus.OK);
    }

    @GetMapping(path = "/{idClinic}")
    public ResponseEntity<Object> getClinicById(@PathVariable("idClinic") Long idClinic) {
        log.info("HTTP REQUEST: get clinic, idClinic: " + idClinic);
        Clinic clinic = clinicService.getClinicById(idClinic);
        ClinicDetailDTO clinicDetail = clinicConverter.convertToDetailDto(clinic);
        log.info("HTTP RESPONSE: clinicDetailDto, idClinic: " + idClinic);
        return new ResponseEntity<>(clinicDetail, HttpStatus.OK); 
    }

    @GetMapping("/emergency/{idClinic}")
    public ResponseEntity<Object> getClinicByIdForEmergency(@PathVariable("idClinic") Long idClinic) {
        log.info("HTTP REQUEST: get clinic by id for emergency, idClinic: " + idClinic);
        Clinic clinic = clinicService.getClinicById(idClinic);
        ClinicEmergencyFormDTO clinicDto = clinicConverter.convertToClinicEmergencyDto(clinic);
        log.info("HTTP RESPONSE: ClinicEmergencyFormDTO, idClinic: " + idClinic);
        return new ResponseEntity<>(clinicDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{idClinic}")
    public ResponseEntity<Object> deleteClinic(@PathVariable("idClinic") Long idClinic) {
        log.info("HTTP REQUEST: delete clinic, idClinic: " + idClinic);
        if (clinicService.deleteClinic(idClinic)) {
            log.info("HTTP RESPONSE: confirmation message, idClinic: " + idClinic);
            return new ResponseEntity<>("Clínica " + idClinic + " borrada exitosamente.", HttpStatus.OK);
        }
        log.info("HTTP RESPONSE: error message, idClinic: " + idClinic);
        return new ResponseEntity<>("Error al borrar la clínica.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
