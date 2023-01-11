package tc_sb_35_api.tc_sb_35_api.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Report {
    
    private Long idReport;
    private int reportYear;
    private int reportMonth;
    private String species;
    private String mainSymptom;
    private Long numberEmergencies;
    private LocalDateTime reportExecutionDate;

}
