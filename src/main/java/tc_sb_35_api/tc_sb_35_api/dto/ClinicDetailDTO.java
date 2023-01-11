package tc_sb_35_api.tc_sb_35_api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tc_sb_35_api.tc_sb_35_api.model.Department;
import tc_sb_35_api.tc_sb_35_api.model.ServiceOfClinic;

// DTO to display info of clinics for detail of clinics in frontend
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ClinicDetailDTO {
    
    private Long idClinic;
    private String clinicName;
    private String clinicNit;
    private Department clinicDepartment;
    private String clinicCity;
    private String clinicAddress;
    private String clinicTelephone;
    private String clinicEmail;
    private List<ServiceOfClinic> clinicServices;
    private int isActive;
    private String lastUpdateDate;
    private String lastUpdateAction;

}
