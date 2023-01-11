package tc_sb_35_api.tc_sb_35_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to show detailed info of emergency in frontend
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EmergencyDetailDTO {
    
    private Long idEmergency;
    private Long emergencySubscription;
    private String clientDocument;
    private String clientName;
    private String clientLastname;
    private String clientTelephone;
    private String clientEmail;
    private Long emergencyClinicId;
    private String clinicName;
    private String clinicDepartment;
    private String clinicCity;
    private String clinicAddress;
    private String clinicTelephone;
    private String clinicEmail;
    private String emergencyDate;
    private String emergencyStatus;
    private String emergencySpecies;
    private String emergencyMainSymptom;
    private String emergencyDescription;
    private String lastStatusUpdateDate;

}
