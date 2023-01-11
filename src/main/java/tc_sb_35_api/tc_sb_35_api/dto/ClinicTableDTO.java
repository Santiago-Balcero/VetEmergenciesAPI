package tc_sb_35_api.tc_sb_35_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to display info of clinics for table of clinics in frontend
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ClinicTableDTO {
    
    private Long idClinic;
    private String clinicName;
    private String clinicNit;
    private int isActive;
    private String clinicDepartment;
    private String clinicCity;
    private String clinicAddress;
    private String clinicTelephone;
    private String clinicEmail;

}
