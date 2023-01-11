package tc_sb_35_api.tc_sb_35_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to display info of clinics for clinics map in frontend
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ClinicMapDTO {
    
    private Long idClinic;
    private String clinicName;
    private String clinicAddress;
    private String clinicTelephone;
    private Double clinicLatitude;
    private Double clinicLongitude;
    
}
