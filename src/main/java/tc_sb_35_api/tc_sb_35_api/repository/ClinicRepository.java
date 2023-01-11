package tc_sb_35_api.tc_sb_35_api.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import tc_sb_35_api.tc_sb_35_api.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long>{

    public ArrayList<Clinic> findByIsActive(int activeStatus, Sort sort);

    public ArrayList<Clinic> findAllByOrderByIdClinicAsc();

    // @Modifying used to specify that query includes DML
    @Modifying
    @Query("UPDATE Clinic c SET c.isActive = ?2, c.lastUpdateDate = ?3, c.lastUpdateAction = ?4 WHERE c.idClinic = ?1")
    public void updateActiveStatus(Long idClinic, int newStatus, LocalDateTime now, String updateAction);

}
