package tc_sb_35_api.tc_sb_35_api.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import tc_sb_35_api.tc_sb_35_api.dto.VeterinarianConverter;
import tc_sb_35_api.tc_sb_35_api.dto.VeterinarianDetailDTO;
import tc_sb_35_api.tc_sb_35_api.dto.VeterinarianFormDTO;
import tc_sb_35_api.tc_sb_35_api.model.Veterinarian;
import tc_sb_35_api.tc_sb_35_api.service.VeterinarianService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/veterinarian")
public class VeterinarianController {

     @Autowired
    VeterinarianService veterinarianService;

    @Autowired
    VeterinarianConverter veterinarianConverter;
    
    @PutMapping
    public ResponseEntity<Object> updateVeterinarian(@RequestBody VeterinarianDetailDTO veterinarianDetailDto) {
        log.info("HTTP REQUEST: update veterinarian, idVeterinarian: " + veterinarianDetailDto.getIdVeterinarian());
        Veterinarian veterinarian = veterinarianConverter.convertToVeterinarian(veterinarianDetailDto);
        // Check if update was successful
        if (veterinarianService.updateVeterinarian(veterinarian)) {
            log.info("HTTP RESPONSE: confirmation message, idVeterinarian: " + veterinarian.getIdVeterinarian());
            return new ResponseEntity<>("Veterinario actualizado exitosamente.", HttpStatus.OK);
        }
        log.info("HTTP RESPONSE: error message, idVeterinarian: " + veterinarian.getIdVeterinarian());
        return new ResponseEntity<>("Error al actualizar el veterinario.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Activate and deactivate veterinarians instead of deleting them as agreed with PO
    @PutMapping("/activestatus/{newStatus}")
    public ResponseEntity<Object> updateActiveStatus(@RequestBody Long idVeterinarian, @PathVariable("newStatus") int newStatus) {
        log.info("HTTP REQUEST: deactivate veterinarian, idVeterinarian: " + idVeterinarian);
        // Check if update was successful
        if (veterinarianService.updateActiveStatus(idVeterinarian, newStatus)) {
            log.info("HTTP RESPONSE: confirmation message, idVeterinarian: " + idVeterinarian);
            return new ResponseEntity<>("Veterinario " + (newStatus == 1 ? "activado" : "desactivado") + " exitosamente.", HttpStatus.OK);
        }
        log.info("HTTP RESPONSE: error message, idVeterinarian: " + idVeterinarian);
        return new ResponseEntity<>("Error al " + (newStatus == 1 ? "activar" : "desactivar") + " el veterinario.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping
    public ResponseEntity<Object> createVeterinarian(@RequestBody VeterinarianFormDTO veterinarianFormDto) {
        log.info("HTTP REQUEST: create new veterinarian");
        Veterinarian veterinarian = veterinarianConverter.convertToVeterinarian(veterinarianFormDto);
        Long idVeterinarian = veterinarianService.createVeterinarian(veterinarian);
        log.info("HTTP RESPONSE: confirmation message, idVeterinarian: " + idVeterinarian);
        return new ResponseEntity<>("Veterinario registrado exitosamente con el id: " + idVeterinarian + ".", HttpStatus.CREATED);
    }

    @GetMapping(path = "/{idVeterinarian}")
    public ResponseEntity<Object> getVeterinarianById(@PathVariable("idVeterinarian") Long idVeterinarian) {
        log.info("HTTP REQUEST: get veterinarian, idVeterinarian: " + idVeterinarian);
        Veterinarian veterinarian = veterinarianService.getVeterinarianById(idVeterinarian);
        VeterinarianDetailDTO veterinarianDetailDto = veterinarianConverter.convertToDetailDto(veterinarian);
        log.info("HTTP RESPONSE: veterinarianDetailDto, idVeterinarian: " + idVeterinarian);
        return new ResponseEntity<>(veterinarianDetailDto, HttpStatus.OK);
    }

    // Gets all active veterinarians related to a specific clinic for client view
    // Client needs to see only active veterinarians
    @GetMapping(path = "/clinic/{idClinic}")
    public ResponseEntity<Object> getActiveVeterinariansByIdClinic(@PathVariable("idClinic") Long idClinic) {
        log.info("HTTP REQUEST: get active veterinarians by clinic, idClinic: " + idClinic);
        List<Veterinarian> veterinarians = veterinarianService.getActiveVeterinariansByIdClinic(idClinic);
        if (veterinarians.size() == 0) {
            log.info("HTTP RESPONSE: no veterinarians found, idClinic: " + idClinic);
            return new ResponseEntity<>("No hay veterinarios activos en esta clínica.", HttpStatus.NOT_FOUND);
        }
        List<VeterinarianDetailDTO> veterinariansDto = veterinarians.stream()
            .map(veterinarian -> veterinarianConverter.convertToDetailDto(veterinarian)).collect(Collectors.toList());
        log.info("HTTP RESPONSE: list fo veterinarianDetailDto, idClinic: " + idClinic);
        return new ResponseEntity<>(veterinariansDto, HttpStatus.OK);
    }

    // Gets all veterinarians related to a specific clinic for admin view
    // Admin needs to see al veterinarians from a clinic
    @GetMapping(path = "/clinic/all/{idClinic}")
    public ResponseEntity<Object> getAllVeterinariansByIdClinic(@PathVariable("idClinic") Long idClinic) {
        log.info("HTTP REQUEST: get all veterinarians by clinic, idClinic: " + idClinic);
        List<Veterinarian> veterinarians = veterinarianService.getAllVeterinariansByIdClinic(idClinic);
        if (veterinarians.size() == 0) {
            log.info("HTTP RESPONSE: no veterinarians found, idClinic: " + idClinic);
            return new ResponseEntity<>("No hay veterinarios registrados en esta clínica.", HttpStatus.NOT_FOUND);
        }
        List<VeterinarianDetailDTO> veterinariansDto = veterinarians.stream()
            .map(veterinarian -> veterinarianConverter.convertToDetailDto(veterinarian)).collect(Collectors.toList());
        log.info("HTTP RESPONSE: list fo veterinarianDetailDto, idClinic: " + idClinic);    
        return new ResponseEntity<>(veterinariansDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{idVeterinarian}")
    public ResponseEntity<Object> deleteVeterinarian(@PathVariable("idVeterinarian") Long idVeterinarian) {
        log.info("HTTP REQUEST: delete veterinarian, idVeterinarian: " + idVeterinarian);
        if (veterinarianService.deleteVeterinarian(idVeterinarian)) {
            log.info("HTTP RESPONSE: confirmation message, idVeterinarian: " + idVeterinarian);
            return new ResponseEntity<>("Veterinario borrado exitosamente.", HttpStatus.OK);
        }
        log.info("HTTP RESPONSE: error message, idVeterinarian: " + idVeterinarian);
        return new ResponseEntity<>("Error al borrar el veterinario.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
