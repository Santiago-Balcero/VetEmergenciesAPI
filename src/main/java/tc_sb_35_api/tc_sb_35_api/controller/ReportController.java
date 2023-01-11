package tc_sb_35_api.tc_sb_35_api.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.ICsvBeanWriter;

import lombok.extern.slf4j.Slf4j;
import tc_sb_35_api.tc_sb_35_api.dto.ReportInfoDTO;
import tc_sb_35_api.tc_sb_35_api.service.ReportService;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/report")
public class ReportController {
    
    @Autowired
    ReportService reportService;

    // This path variables are the information admin wants in frontend
    @GetMapping("/{year}/{month}/{species}/{symptom}")
    public ResponseEntity<Object> getReports(@PathVariable("year") int year, 
        @PathVariable("month") int month, 
        @PathVariable("species") Long idSpecies, 
        @PathVariable("symptom") Long idMainSymptom) {
        log.info("HTTP REQUEST: generate report for year " + year + " month " + month 
            + " idSpecies " + idSpecies + " idMainSymptom " + idMainSymptom);
        ReportInfoDTO reportsDto = reportService.getReports(year, month, idSpecies, idMainSymptom);
        log.info("HTTP RESPONSE: report according parameters");
        return new ResponseEntity<>(reportsDto, HttpStatus.OK);
    }

    // This path variables are the information admin wants in frontend
    @GetMapping("download/{year}/{month}/{species}/{symptom}")
    public void downloadReport(HttpServletResponse response, @PathVariable("year") int year, 
    @PathVariable("month") int month, 
    @PathVariable("species") Long idSpecies, 
    @PathVariable("symptom") Long idMainSymptom) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=emergenciesReport-" + LocalDateTime.now() + ".csv");
        ICsvBeanWriter writer = reportService.getCsv(response.getWriter(), year, month, idSpecies, idMainSymptom);
        writer.close();
    }

}
