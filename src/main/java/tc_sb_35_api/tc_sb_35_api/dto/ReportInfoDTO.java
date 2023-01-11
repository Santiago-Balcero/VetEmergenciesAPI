package tc_sb_35_api.tc_sb_35_api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Dto to send to frontend list of reportsDto and total number of emergencies
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReportInfoDTO {

    List<ReportTableDTO> reportsDto;
    int numberOfEmergencies;

}
