package tc_sb_35_api.tc_sb_35_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tc_sb_35_api.tc_sb_35_api.exceptions.ClinicNotFoundException;
import tc_sb_35_api.tc_sb_35_api.exceptions.InvalidAddressException;
import tc_sb_35_api.tc_sb_35_api.exceptions.UnableToSaveObjectException;
import tc_sb_35_api.tc_sb_35_api.model.Clinic;
import tc_sb_35_api.tc_sb_35_api.repository.ClinicRepository;
import tc_sb_35_api.tc_sb_35_api.repository.DepartmentRepository;

@Service
public class ClinicService {
    
    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    VeterinarianService veterinarianService;

    @Autowired
    GeocodingService geocodingService;

    public boolean updateClinic(Clinic clinic) {
        if (clinic.getClinicAddress().split(" ").length < 3) {
            return false;
        }
        if (clinicRepository.existsById(clinic.getIdClinic())) {
            clinic = geocodingService.getCoordenates(clinic);
            clinic.setLastUpdateDate(LocalDateTime.now());
            clinic.setLastUpdateAction("Data update");
            // Repository method is the same for post and createClinic
            // Object with Id is assumed to be updated by JPA
            clinicRepository.save(clinic);
            return true;
        }
        throw new ClinicNotFoundException();
    }

    @Transactional
    // int newStatus is either 1 or 0 according to activate or deactivate request
    public boolean updateActiveStatus(Long idClinic, int newStatus) {
        if(newStatus != 0 && newStatus != 1 ) {
            throw new UnableToSaveObjectException();
        }
        if (clinicRepository.existsById(idClinic)) {
            clinicRepository.updateActiveStatus(idClinic, newStatus, LocalDateTime.now(), newStatus == 1 ? "Activate" : "Deactivate");
            return true;
        }
        throw new ClinicNotFoundException();
    }

    public Long createClinic(Clinic clinic) {
        // If received address is less than 3 words it means address is not correct
        // For example proper address will be something like "Cl 1 2-3"
        if (clinic.getClinicAddress().split(" ").length < 3) {
            throw new InvalidAddressException();
        }
        clinic = geocodingService.getCoordenates(clinic);
        clinic.setIsActive(1);
        clinic.setLastUpdateDate(LocalDateTime.now());
        clinic.setLastUpdateAction("Creation");
        clinic = clinicRepository.save(clinic);
        // If clinic was not saved
        if(clinic.getIdClinic() == null) {
            throw new UnableToSaveObjectException();
        }
        return clinic.getIdClinic();
    }

    // Uses sorting as argument
    public List<Clinic> getActiveClinics() {
        List<Clinic> clinics = clinicRepository.findByIsActive(1, Sort.by(Sort.Direction.ASC, "idClinic"));
        return clinics;
    }

    // Uses sorting as method name
    public List<Clinic> getAllClinics() {
        List<Clinic> clinics = clinicRepository.findAllByOrderByIdClinicAsc();
        return clinics;
    }

    public Clinic getClinicById(Long idClinic) {
        return clinicRepository.findById(idClinic).get();
    }

    public boolean deleteClinic(Long idClinic) {
        clinicRepository.deleteById(idClinic);
        return true;
    }

}
