package tc_sb_35_api.tc_sb_35_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tc_sb_35_api.tc_sb_35_api.exceptions.ClinicNotFoundException;
import tc_sb_35_api.tc_sb_35_api.exceptions.UnableToSaveObjectException;
import tc_sb_35_api.tc_sb_35_api.exceptions.VeterinarianNotFoundException;
import tc_sb_35_api.tc_sb_35_api.model.Clinic;
import tc_sb_35_api.tc_sb_35_api.model.Veterinarian;
import tc_sb_35_api.tc_sb_35_api.repository.ClinicRepository;
import tc_sb_35_api.tc_sb_35_api.repository.VeterinarianRepository;

@Service
public class VeterinarianService {

    @Autowired
    VeterinarianRepository veterinarianRepository;

    @Autowired
    ClinicRepository clinicRepository;
    
    public boolean updateVeterinarian(Veterinarian veterinarian) {
        if (veterinarianRepository.existsById(veterinarian.getIdVeterinarian())) {
            veterinarian.setLastUpdateDate(LocalDateTime.now());
            veterinarian.setLastUpdateAction("Data update");
            // Repository method is the same for post and createVeterinarian
            // Object with Id is assumed to be updated by JPA
            veterinarianRepository.save(veterinarian);
            return true;
        }
        throw new VeterinarianNotFoundException();
    }

    @Transactional
    public boolean updateActiveStatus(Long idVeterinarian, int newStatus) {
        if(newStatus != 0 && newStatus != 1 ) {
            throw new UnableToSaveObjectException();
        }
        if (veterinarianRepository.existsById(idVeterinarian)) {
            veterinarianRepository.updateActiveStatus(idVeterinarian, newStatus, LocalDateTime.now(), newStatus == 1 ? "Activate" : "Deactivate");
            return true;
        }
        throw new VeterinarianNotFoundException();
    }

    public Long createVeterinarian(Veterinarian veterinarian) {
        // Clinic must exist in database
        if(!clinicRepository.existsById(veterinarian.getVetClinic().getIdClinic())) {
            throw new ClinicNotFoundException();
        }
        veterinarian.setIsActive(1);
        veterinarian.setLastUpdateDate(LocalDateTime.now());
        veterinarian.setLastUpdateAction("Creation");
        veterinarian = veterinarianRepository.save(veterinarian);
        // If veterinarian was not saved
        if(veterinarian.getIdVeterinarian() == null) {
            throw new UnableToSaveObjectException();
        }
        return veterinarian.getIdVeterinarian();

    }

    public Veterinarian getVeterinarianById(Long idVeterinarian) {
        return veterinarianRepository.findById(idVeterinarian).get();
    }
    
    // Gets active veterinarians related to a specific clinic
    public List<Veterinarian> getActiveVeterinariansByIdClinic(Long idClinic) {
        Clinic vetClinic = new Clinic();
        vetClinic.setIdClinic(idClinic);
        return veterinarianRepository.findByVetClinicAndIsActive(vetClinic, 1);
    }

    // Gets all veterinarians related to a specific clinic
    public List<Veterinarian> getAllVeterinariansByIdClinic(Long idClinic) {
        Clinic vetClinic = new Clinic();
        vetClinic.setIdClinic(idClinic);
        return veterinarianRepository.findByVetClinic(vetClinic);
    }

    public boolean deleteVeterinarian(Long idVeterinarian) {
        veterinarianRepository.deleteById(idVeterinarian);
        return true;
    }

}
