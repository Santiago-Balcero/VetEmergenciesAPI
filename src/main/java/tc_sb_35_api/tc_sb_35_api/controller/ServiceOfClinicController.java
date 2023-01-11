package tc_sb_35_api.tc_sb_35_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tc_sb_35_api.tc_sb_35_api.model.ServiceOfClinic;
import tc_sb_35_api.tc_sb_35_api.service.ServiceOfClinicService;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/service")
public class ServiceOfClinicController {

    @Autowired
    ServiceOfClinicService serviceOfClinicService;

    // As business may include new clinic services, frontend gets list of clinic services from here
    @GetMapping
    public ResponseEntity<Object> getServices() {
        log.info("HTTP REQUEST: get clinic services");
        List<ServiceOfClinic> services = serviceOfClinicService.getServices();
        log.info("HTTP RESPONSE: list of clinic services");
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

}
