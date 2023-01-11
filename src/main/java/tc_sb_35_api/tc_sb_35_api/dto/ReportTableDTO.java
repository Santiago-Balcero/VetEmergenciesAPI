package tc_sb_35_api.tc_sb_35_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to display info in frontend and create CSV file
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ReportTableDTO {
    
    private Long idReport;
    private int reportYear;
    private String reportMonth;
    private String species;
    private String mainSymptom;
    private Long numberEmergencies;
    private String reportExecutionDate;

}
