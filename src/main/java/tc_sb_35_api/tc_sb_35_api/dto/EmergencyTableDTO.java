package tc_sb_35_api.tc_sb_35_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to show info of emergency in emergencies table in frontend
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EmergencyTableDTO {

    private Long idEmergency;
    private Long emergencySubscriptionId;
    private String emergencyClientDocument;
    private String emergencyClinicName;
    private String emergencyDate;
    private String emergencyStatus;
    private String emergencySpecies;
    private String emergencyMainSymptom;
    private String emergencyDescription;
    
}
