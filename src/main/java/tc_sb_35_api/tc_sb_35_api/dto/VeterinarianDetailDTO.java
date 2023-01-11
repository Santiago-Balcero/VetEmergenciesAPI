package tc_sb_35_api.tc_sb_35_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to show veterinarian info in clinic's detail cards
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class VeterinarianDetailDTO {
    
    private Long idVeterinarian;
    private Long vetClinicId;
    private String vetDocument;
    private String vetName;
    private String vetLastname;
    private String vetTelephone;
    private String vetEmail;
    private int isActive;
    private String lastUpdateDate;
    private String lastUpdateAction;

}
