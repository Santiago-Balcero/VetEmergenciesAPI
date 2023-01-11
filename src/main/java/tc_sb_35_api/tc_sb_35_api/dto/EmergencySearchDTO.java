package tc_sb_35_api.tc_sb_35_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to show info of emergencies in frontend when searching by clientDocument or by idSubscription
// Also used when a client wants to see his emergencies
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EmergencySearchDTO {
    
    private Long idEmergency;
    private String clinicNit;
    private String clinicName;
    private String clinicDepartment;
    private String clinicCity;
    private String clinicAddress;
    private String emergencyDate;
    private String emergencyStatus;
    private String emergencySpecies;
    private String emergencyMainSymptom;
    private String emergencyDescription;
    private String lastStatusUpdateDate;

}
