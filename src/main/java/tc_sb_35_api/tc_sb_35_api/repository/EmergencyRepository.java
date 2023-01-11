package tc_sb_35_api.tc_sb_35_api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import tc_sb_35_api.tc_sb_35_api.model.Emergency;

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {

    public List<Emergency> findByEmergencyStatus(String emergencyStatus, Sort sort);

    // JPQL query that given an idSubscription returns number of emergencies of that subscription with status "Confirmed"
    // and lastStatusUpdateDate between startDate and finishDate
    // This query returns the number that will be used as business rule to approve or not emergencies for that subscription
    @Query("SELECT COUNT(1) FROM Emergency e INNER JOIN Subscription s " +
        "ON e.emergencySubscription = s.idSubscription " +
        "WHERE e.emergencySubscription.idSubscription = ?1 AND e.emergencyStatus = 'Confirmed' " +
        "AND e.lastStatusUpdateDate > s.startDate AND e.lastStatusUpdateDate < s.finishDate")
    public int countEmergencies(Long idSubscription);

    @Modifying
    @Query("UPDATE Emergency e SET e.emergencyStatus = ?2, e.lastStatusUpdateDate = ?3 WHERE e.idEmergency = ?1")
    public void updateEmergencyStatus(Long idEmergency, String newStatus, LocalDateTime updateDate);
    
    public List<Emergency> findAllByEmergencySubscriptionIdSubscription(Long emergencySubscription, Sort sort);
    
}
