package tc_sb_35_api.tc_sb_35_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import tc_sb_35_api.tc_sb_35_api.dto.ClientProcessingDTO;
import tc_sb_35_api.tc_sb_35_api.service.ClientService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    // This endpoints do not include all CRUD operations as defined for this MVP
    // Could create all CRUD methods in next versions of this project

    // This endpoint receives an uploaded file from frontend as MultipartFile
    // File is CSV with headers corresponding to attributes required for creating new clients
    @PostMapping
    public ResponseEntity<Object> createClients(@RequestParam("clients") MultipartFile file) {
        log.info("HTTP REQUEST: create list of clients from MultiPart file " + file.getOriginalFilename());
        ClientProcessingDTO processInfo = clientService.createClients(file);
        log.info("HTTP RESPONSE: number of new clients that were saved in data base");
        return new ResponseEntity<>(processInfo, HttpStatus.OK);
    }

    @GetMapping(path = "/{clientDocument}/{clientPassword}")
    public ResponseEntity<Object> clientLogin(@PathVariable("clientDocument") String clientDocument, 
        @PathVariable("clientPassword") String clientPassword) {
        log.info("HTTP REQUEST: login client, clientDocument: " + clientDocument);
        Long idClient = clientService.clientLogin(clientDocument, clientPassword);
        log.info("HTTP RESPONSE: client logged in, idClient: " + idClient);
        return new ResponseEntity<>(idClient, HttpStatus.OK);
    }

}
