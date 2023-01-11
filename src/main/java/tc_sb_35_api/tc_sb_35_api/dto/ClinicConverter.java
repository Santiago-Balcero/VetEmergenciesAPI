package tc_sb_35_api.tc_sb_35_api.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import tc_sb_35_api.tc_sb_35_api.model.Clinic;
import tc_sb_35_api.tc_sb_35_api.model.Department;
import tc_sb_35_api.tc_sb_35_api.util.DateFormat;
import tc_sb_35_api.tc_sb_35_api.util.LastActionToSpanish;

@Configuration
public class ClinicConverter {

    @Autowired
    ModelMapper modelMapper;
    // ModelMapper had some problems mapping in some cases, for those moments,
    // manual mapping was used to help

    @Autowired
    LastActionToSpanish lastActionToSpanish;

    @Autowired
    DateFormat dateFormat;
    
    public ClinicTableDTO convertToTableDto(Clinic clinic) {
        ClinicTableDTO clinicTableDTO = modelMapper.map(clinic, ClinicTableDTO.class);
        return clinicTableDTO;
    }

    public ClinicMapDTO convertToMapDto(Clinic clinic) {
        ClinicMapDTO clinicMapDto = modelMapper.map(clinic, ClinicMapDTO.class);
        return clinicMapDto;
    }

    public ClinicDetailDTO convertToDetailDto(Clinic clinic) {
        ClinicDetailDTO clinicDetailDTO = modelMapper.map(clinic, ClinicDetailDTO.class);
        clinicDetailDTO.setLastUpdateAction(lastActionToSpanish.lastActionToSpanish(clinic.getLastUpdateAction()));
        clinicDetailDTO.setLastUpdateDate(dateFormat.dateFormat(clinic.getLastUpdateDate()));
        return clinicDetailDTO;
    }

    public Clinic convertToClinicForm(ClinicFormDTO clinicFormDto) {
        Clinic clinic = modelMapper.map(clinicFormDto, Clinic.class);
        Department department = new Department();
        department.setIdDepartment(clinicFormDto.getClinicDepartment());
        clinic.setClinicDepartment(department);
        return clinic;
    }

    public Clinic convertToClinicDetail(ClinicDetailDTO clinicDetailDto) {
        Clinic clinic = modelMapper.map(clinicDetailDto, Clinic.class);
        clinic.setClinicDepartment(clinicDetailDto.getClinicDepartment());
        clinic.setClinicServices(clinicDetailDto.getClinicServices());
        return clinic;
    }

    public ClinicEmergencyFormDTO convertToClinicEmergencyDto(Clinic clinic) {
        ClinicEmergencyFormDTO clinicDto = modelMapper.map(clinic, ClinicEmergencyFormDTO.class);
        return clinicDto;
    }

}
