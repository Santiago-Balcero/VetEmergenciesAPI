package tc_sb_35_api.tc_sb_35_api.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import tc_sb_35_api.tc_sb_35_api.util.DateFormat;
import tc_sb_35_api.tc_sb_35_api.util.MonthToString;
import tc_sb_35_api.tc_sb_35_api.model.Report;

@Configuration
public class ReportConverter {

    @Autowired
    ModelMapper modelMapper;
    // ModelMapper had some problems mapping in some cases, for those moments,
    // manual mapping was used to help

    @Autowired
    DateFormat dateFormat;

    @Autowired
    MonthToString monthToString;

    public ReportTableDTO convertToReportTableDto(Report report) {
        ReportTableDTO reportDto = modelMapper.map(report, ReportTableDTO.class);
        reportDto.setReportExecutionDate(dateFormat.dateFormat(report.getReportExecutionDate()));
        reportDto.setReportMonth(monthToString.monthToString(report.getReportMonth()));
        return reportDto;
    }
    
}
