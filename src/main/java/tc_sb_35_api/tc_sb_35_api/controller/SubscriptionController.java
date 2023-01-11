package tc_sb_35_api.tc_sb_35_api.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import tc_sb_35_api.tc_sb_35_api.dto.EmergencySearchDTO;
import tc_sb_35_api.tc_sb_35_api.dto.EmergencyConverter;
import tc_sb_35_api.tc_sb_35_api.dto.SubscriptionWithEmergenciesDTO;
import tc_sb_35_api.tc_sb_35_api.dto.SubscriptionConverter;
import tc_sb_35_api.tc_sb_35_api.dto.SubscriptionEmergencyFormDTO;
import tc_sb_35_api.tc_sb_35_api.model.Emergency;
import tc_sb_35_api.tc_sb_35_api.model.Subscription;
import tc_sb_35_api.tc_sb_35_api.service.EmergencyService;
import tc_sb_35_api.tc_sb_35_api.service.SubscriptionService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    EmergencyService emergencyService;
    
    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    SubscriptionConverter subscriptionConverter;

    @Autowired
    EmergencyConverter emergencyConverter;

    // Subscription entity does not have DELETE method because for report and business purposes data is needed
    // Also because a client may renew subscription with same id and emergency records
    // For future versions of this project PUT method could be added for when client renews subscription
    
    @PutMapping("/renew")
    public ResponseEntity<Object> renewSubscription(@RequestBody Long idSubscription) {
        log.info("HTTP REQUEST: renew subscription, idSubscription: " + idSubscription);
        if(subscriptionService.renewSubscription(idSubscription)) {
            log.info("HTTP RESPONSE: renew subscription confirmation message, idSubscription: " + idSubscription);
            return new ResponseEntity<>("Subscripción activada exitosamente.", HttpStatus.OK);
        }
        log.info("HTTP RESPONSE: renew subscription error message, idSubscription: " + idSubscription);
        return new ResponseEntity<>("Error activando la suscripción.", HttpStatus.OK);
        
    }
    
    @PostMapping
    public ResponseEntity<Object> createSubscription(@RequestBody Long idClient) {
        log.info("HTTP REQUEST: create subscription for client: " + idClient);
        Long idSubscription = subscriptionService.createSubscription(idClient);
        log.info("HTTP RESPONSE: confirmation message, idSubscription: " + idSubscription);
        return new ResponseEntity<>("Subscripción registrada exitosamente con el id: " + idSubscription + ".", HttpStatus.CREATED);
    }

    @GetMapping 
    public ResponseEntity<Object> checkForExpiredSubscriptions() {
        log.info("HTTP REQUEST: manual execution of batch job for expired subscriptions");
        subscriptionService.checkForExpiredSubscriptions();
        log.info("HTTP RESPONSE: manual execution of batch job confirmation message");
        return new ResponseEntity<>("Las suscripciones vencidas fueron desactivadas exitosamente.", HttpStatus.OK);
    }
    
    @GetMapping(path = "/client/{idClient}")
    public ResponseEntity<Object> getSubscriptionByIdClient(@PathVariable("idClient") Long idClient) {
        log.info("HTTP REQUEST: get subscription for emergency form by idClient: " + idClient);
        Subscription subscription = subscriptionService.getSubscriptionByIdClient(idClient);
        SubscriptionEmergencyFormDTO subscriptionEmergengyFormDto = subscriptionConverter.convertToEmergengyFormDto(subscription);
        log.info("HTTP REQUEST: SubscriptionEmergencyFormDTO for emergency form, idClient: " + idClient);
        return new ResponseEntity<>(subscriptionEmergengyFormDto, HttpStatus.OK);
    }

    @GetMapping("/clientdocument/emergencies/{clientDocument}")
    public ResponseEntity<Object> getSubscriptionWithEmergenciesByClientDocument(@PathVariable("clientDocument") String clientDocument) {
        log.info("HTTP REQUEST: get subscription with emergencies by clientdocument: " + clientDocument);
        Subscription subscription = subscriptionService.getSubscriptionByClientDocument(clientDocument);
        SubscriptionWithEmergenciesDTO subscriptionDto = getEmergenciesFromSubscription(subscription);
        log.info("HTTP RESPONSE: SubscriptionWithEmergenciesDTO with list of EmergencySearchDTO by clientdocument: " + clientDocument);
        return new ResponseEntity<>(subscriptionDto, HttpStatus.OK);
    }

    @GetMapping("/emergencies/{idSubscription}")
    public ResponseEntity<Object> getSubscriptionWithEmergenciesByIdSubscription(@PathVariable("idSubscription") Long idSubscription) {
        log.info("HTTP REQUEST: get subscription with emergencies by idSubscription: " + idSubscription);
        Subscription subscription = subscriptionService.getSubscriptionById(idSubscription);
        SubscriptionWithEmergenciesDTO subscriptionDto = getEmergenciesFromSubscription(subscription);
        log.info("HTTP REQUEST: SubscriptionWithEmergenciesDTO with list of EmergencySearchDTO by idSubscription: " + idSubscription);
        return new ResponseEntity<>(subscriptionDto, HttpStatus.OK);
    }

    @GetMapping("/idclient/emergencies/{idClient}")
    public ResponseEntity<Object> getSubscriptionWithEmergenciesByIdClient(@PathVariable("idClient") Long idClient) {
        log.info("HTTP REQUEST: get subscription with emergencies by idClient: " + idClient);
        Subscription subscription = subscriptionService.getSubscriptionByIdClient(idClient);
        SubscriptionWithEmergenciesDTO subscriptionDto = getEmergenciesFromSubscription(subscription);
        log.info("HTTP RESPONSE: SubscriptionWithEmergenciesDTO with list of EmergencySearchDTO by idClient: " + idClient);
        return new ResponseEntity<>(subscriptionDto, HttpStatus.OK);
    }

    // Because this controller recieves multiple requests for subscription with list of emergencies based on different parameters,
    // multiple requests are handlend by different endpoints and they get subscriptions in different ways
    // They all use this private method to get emergencies for that subscription
    private SubscriptionWithEmergenciesDTO getEmergenciesFromSubscription(Subscription subscription) {
        log.info("INTERNAL PROCESS REQUEST: get list of emergencies for idSubscription " + subscription.getIdSubscription());
        List<Emergency> emergencies = emergencyService.getEmergenciesBySubscription(subscription.getIdSubscription());
        List<EmergencySearchDTO> emergenciesDto = emergencies.stream()
            .map(emergency -> emergencyConverter.convertToEmergenciesSearchDto(emergency)).collect(Collectors.toList());
        SubscriptionWithEmergenciesDTO subscriptionDto = subscriptionConverter.convertToSubscriptionByClientDto(subscription);
        subscriptionDto.setEmergencies(emergenciesDto);
        log.info("INTERNAL PROCESS RESPONSE: SubscriptionWithEmergenciesDTO with list of EmergencySearchDTO, idSubscription " + subscription.getIdSubscription());
        return subscriptionDto;
    }
    
}
