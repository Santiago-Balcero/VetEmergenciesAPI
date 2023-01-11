package tc_sb_35_api.tc_sb_35_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tc_sb_35_api.tc_sb_35_api.exceptions.ClinicNotFoundException;
import tc_sb_35_api.tc_sb_35_api.exceptions.ExpiredSubscriptionException;
import tc_sb_35_api.tc_sb_35_api.model.Emergency;
import tc_sb_35_api.tc_sb_35_api.repository.ClinicRepository;
import tc_sb_35_api.tc_sb_35_api.repository.EmergencyRepository;
import tc_sb_35_api.tc_sb_35_api.repository.SubscriptionRepository;

@Service
public class EmergencyService {

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    EmergencyRepository emergencyRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    ClinicRepository clinicRepository;

    @Transactional
    public String approveEmergency(List<Long> emergencySubscriptionIds) {
        LocalDateTime updateDate = LocalDateTime.now();
        // Check if subscription is active if subscription is not active emergency status is set to Rejected
        if(subscriptionRepository.getStatus(emergencySubscriptionIds.get(1)) == 0) {
            emergencyRepository.updateEmergencyStatus(emergencySubscriptionIds.get(0), "Rejected", updateDate);
            return "Rejected";
        }
        int emergencies = emergencyRepository.countEmergencies(emergencySubscriptionIds.get(1));
        // In future versions of this project set the number of emergencies as a constant, MVP uses a limit of two emergencies
        if(emergencies >= 2){
            // update rejected emergency
            emergencyRepository.updateEmergencyStatus(emergencySubscriptionIds.get(0), "Rejected", updateDate);
            return "Rejected";
        }
        // update approved emergency
        emergencyRepository.updateEmergencyStatus(emergencySubscriptionIds.get(0), "Approved", updateDate);
        return "Approved";
    }

    @Transactional
    public String confirmEmergency(List<Long> emergencySubscriptionIds) {
        LocalDateTime updateDate = LocalDateTime.now();
        emergencyRepository.updateEmergencyStatus(emergencySubscriptionIds.get(0), "Confirmed", updateDate);
        return "Confirmed";
    }

    public Long createEmergency(Emergency emergency) {
        if(!clinicRepository.existsById(emergency.getEmergencyClinic().getIdClinic())) {
            throw new ClinicNotFoundException();
        }
        if(subscriptionRepository.getStatus(emergency.getEmergencySubscription().getIdSubscription()) == 0) {
            throw new ExpiredSubscriptionException(emergency.getEmergencySubscription().getIdSubscription());
        }
        emergency.setEmergencyDate(LocalDateTime.now());
        emergency.setEmergencyStatus("Pending");
        emergency.setLastStatusUpdateDate(LocalDateTime.now());
        emergency = emergencyRepository.save(emergency);
        // Return emergency id for user experience, for routing in Angular and getting full emergency info in Emergency detail view
        return emergency.getIdEmergency();  
    }

    public Emergency getEmergencyById(Long idEmergency) {
        return emergencyRepository.findById(idEmergency).get();
    }

    // For MVP purposes this method only gets emergencies with status "Pending"
    // Future versions of this project may include a String status as parameter
    public List<Emergency> getEmergenciesByEmergencyStatus() {
        return emergencyRepository.findByEmergencyStatus("Pending", Sort.by(Sort.Direction.ASC, "idEmergency"));
    }

    public List<Emergency> getEmergenciesBySubscription(Long idSubscription) {
        List<Emergency> emergencies = emergencyRepository.findAllByEmergencySubscriptionIdSubscription(idSubscription, Sort.by(Sort.Direction.ASC, "idEmergency"));
        return emergencies;
    }
    
}
