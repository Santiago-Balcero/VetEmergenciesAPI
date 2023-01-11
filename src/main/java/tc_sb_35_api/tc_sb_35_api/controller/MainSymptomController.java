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
import tc_sb_35_api.tc_sb_35_api.model.MainSymptom;
import tc_sb_35_api.tc_sb_35_api.service.MainSymptomService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/mainsymptom")
public class MainSymptomController {

    @Autowired
    MainSymptomService mainSymptomservice;

    // As business may include new symptoms, frontend gets list of symptoms from here
    @GetMapping
    public ResponseEntity<Object> getMainSymptoms() {
        log.info("HTTP REQUEST: get symptoms");
        List<MainSymptom> symptomsList = mainSymptomservice.getMainSymptoms();
        log.info("HTTP RESPONSE: list of symptoms");
        return new ResponseEntity<>(symptomsList, HttpStatus.OK);
    }
    
}
