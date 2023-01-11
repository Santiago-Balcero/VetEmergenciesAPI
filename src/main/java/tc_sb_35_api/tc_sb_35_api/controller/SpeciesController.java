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
import tc_sb_35_api.tc_sb_35_api.model.Species;
import tc_sb_35_api.tc_sb_35_api.service.SpeciesService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/species")
public class SpeciesController {

   @Autowired
   SpeciesService speciesService;

   // As business may include new species, frontend gets list of species from here
   @GetMapping
   public ResponseEntity<Object> getSpecies() {
      log.info("HTTP REQUEST: get species");
      List<Species> speciesList = speciesService.getSpecies();
      log.info("HTTP RESPONSE: list of species");
      return new ResponseEntity<>(speciesList, HttpStatus.OK);
   }
    
}
