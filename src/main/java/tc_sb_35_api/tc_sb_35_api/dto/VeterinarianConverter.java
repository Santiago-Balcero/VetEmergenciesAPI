package tc_sb_35_api.tc_sb_35_api.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import tc_sb_35_api.tc_sb_35_api.model.Clinic;
import tc_sb_35_api.tc_sb_35_api.model.Veterinarian;
import tc_sb_35_api.tc_sb_35_api.util.DateFormat;
import tc_sb_35_api.tc_sb_35_api.util.LastActionToSpanish;

@Configuration
public class VeterinarianConverter {

    @Autowired
    private ModelMapper modelMapper;
    // ModelMapper had some problems mapping in some cases, for those moments,
    // manual mapping was used to help

    @Autowired
    LastActionToSpanish lastActionToSpanish;

    @Autowired
    DateFormat dateFormat;

    public VeterinarianDetailDTO convertToDetailDto(Veterinarian veterinarian) {
        VeterinarianDetailDTO veterinarianDetailDto = modelMapper.map(veterinarian, VeterinarianDetailDTO.class);
        veterinarianDetailDto.setLastUpdateAction(lastActionToSpanish.lastActionToSpanish(veterinarian.getLastUpdateAction()));
        veterinarianDetailDto.setLastUpdateDate(dateFormat.dateFormat(veterinarian.getLastUpdateDate()));
        return veterinarianDetailDto;
    }

    public Veterinarian convertToVeterinarian(VeterinarianFormDTO veterinarianFormDto) {
        // ModelMapper sets idClinic as idVeterinarian making method to misbehave
        // Manual mapping from DTO to Entity
        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setVetDocument(veterinarianFormDto.getVetDocument());
        veterinarian.setVetName(veterinarianFormDto.getVetName());
        veterinarian.setVetLastname(veterinarianFormDto.getVetLastname());
        veterinarian.setVetTelephone(veterinarianFormDto.getVetTelephone());
        veterinarian.setVetEmail(veterinarianFormDto.getVetEmail());
        Clinic vetClinic = new Clinic();
        vetClinic.setIdClinic(veterinarianFormDto.getVetClinicId());
        veterinarian.setVetClinic(vetClinic);
        return veterinarian;
    }

    public Veterinarian convertToVeterinarian(VeterinarianDetailDTO veterinariandetailDto) {
        Veterinarian veterinarian = modelMapper.map(veterinariandetailDto, Veterinarian.class);
        Clinic vetClinic = new Clinic();
        vetClinic.setIdClinic(veterinariandetailDto.getVetClinicId());
        return veterinarian;
    }
    
}
