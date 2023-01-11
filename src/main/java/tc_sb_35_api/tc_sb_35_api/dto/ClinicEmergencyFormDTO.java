package tc_sb_35_api.tc_sb_35_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to display clinic info in emergency form in frontend
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ClinicEmergencyFormDTO {
    
    private String clinicName;
    private String clinicTelephone;
    private String clinicEmail;
    private String clinicAddress;
    private String clinicCity;
    private String clinicDepartment;

}
