package tc_sb_35_api.tc_sb_35_api.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import tc_sb_35_api.tc_sb_35_api.model.Clinic;
import tc_sb_35_api.tc_sb_35_api.model.Veterinarian;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long>{
    
    // Gets active veterinarians related to a specific clinic
    public ArrayList<Veterinarian> findByVetClinicAndIsActive(Clinic vetClinic, int activeStatus);

    // Gets all veterinarians related to a specific clinic
    public ArrayList<Veterinarian> findByVetClinic(Clinic vetClinic);

    // @Modifying used to specify that query includes DML
    @Modifying
    @Query("UPDATE Veterinarian v SET v.isActive = ?2, v.lastUpdateDate = ?3, v.lastUpdateAction = ?4 WHERE v.idVeterinarian = ?1")
    public void updateActiveStatus(Long idVeterinarian, int newStatus, LocalDateTime now, String updateAction);

}
