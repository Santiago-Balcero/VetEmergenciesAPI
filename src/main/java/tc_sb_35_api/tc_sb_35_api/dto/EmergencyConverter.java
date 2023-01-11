package tc_sb_35_api.tc_sb_35_api.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import tc_sb_35_api.tc_sb_35_api.util.DateFormat;
import tc_sb_35_api.tc_sb_35_api.util.StatusToSpanish;
import tc_sb_35_api.tc_sb_35_api.model.Clinic;
import tc_sb_35_api.tc_sb_35_api.model.Emergency;
import tc_sb_35_api.tc_sb_35_api.model.MainSymptom;
import tc_sb_35_api.tc_sb_35_api.model.Species;
import tc_sb_35_api.tc_sb_35_api.model.Subscription;

@Configuration
public class EmergencyConverter {

    @Autowired
    ModelMapper modelMapper;
    // ModelMapper had some problems mapping in some cases, for those moments,
    // manual mapping was used to help

    @Autowired
    DateFormat dateFormat;

    @Autowired
    StatusToSpanish statusToSpanish;

    @Autowired
    SubscriptionConverter subscriptionConverter;

    public EmergencyDetailDTO convertToEmergencyDetailDto(Emergency emergency) {
        EmergencyDetailDTO emergencyDto = modelMapper.map(emergency, EmergencyDetailDTO.class);
        emergencyDto.setEmergencySpecies(emergency.getEmergencySpecies().getSpecies());
        emergencyDto.setEmergencyDate(dateFormat.dateFormat(emergency.getEmergencyDate()));
        emergencyDto.setLastStatusUpdateDate(dateFormat.dateFormat(emergency.getLastStatusUpdateDate()));
        emergencyDto.setEmergencyStatus(statusToSpanish.statusToSpanish(emergency.getEmergencyStatus()));
        emergencyDto.setClientDocument(emergency.getEmergencySubscription().getSubscriptionClient().getClientDocument());
        emergencyDto.setClientName(emergency.getEmergencySubscription().getSubscriptionClient().getClientName());
        emergencyDto.setClientLastname(emergency.getEmergencySubscription().getSubscriptionClient().getClientLastname());
        emergencyDto.setClientTelephone(emergency.getEmergencySubscription().getSubscriptionClient().getClientTelephone());
        emergencyDto.setClientEmail(emergency.getEmergencySubscription().getSubscriptionClient().getClientEmail());
        emergencyDto.setEmergencyClinicId(emergency.getEmergencyClinic().getIdClinic());
        emergencyDto.setClinicDepartment(emergency.getEmergencyClinic().getClinicDepartment().getDepartment());
        emergencyDto.setClinicCity(emergency.getEmergencyClinic().getClinicCity());
        emergencyDto.setClinicAddress(emergency.getEmergencyClinic().getClinicAddress());
        emergencyDto.setClinicTelephone(emergency.getEmergencyClinic().getClinicTelephone());
        emergencyDto.setClinicEmail(emergency.getEmergencyClinic().getClinicEmail());
        return emergencyDto;
    }

    public EmergencyTableDTO convertToEmergencyTableDto(Emergency emergency) {
        EmergencyTableDTO emergencyDto = modelMapper.map(emergency, EmergencyTableDTO.class);
        emergencyDto.setEmergencySpecies(emergency.getEmergencySpecies().getSpecies());
        emergencyDto.setEmergencyDate(dateFormat.dateFormat(emergency.getEmergencyDate()));
        emergencyDto.setEmergencyStatus(statusToSpanish.statusToSpanish(emergency.getEmergencyStatus()));
        emergencyDto.setEmergencySubscriptionId(emergency.getEmergencySubscription().getIdSubscription());
        emergencyDto.setEmergencyClientDocument(emergency.getEmergencySubscription().getSubscriptionClient().getClientDocument());
        emergencyDto.setEmergencyClinicName(emergency.getEmergencyClinic().getClinicName());
        return emergencyDto;
    }

    public EmergencySearchDTO convertToEmergenciesSearchDto(Emergency emergency) {
        EmergencySearchDTO emergencyDto = modelMapper.map(emergency, EmergencySearchDTO.class);
        emergencyDto.setEmergencyDate(dateFormat.dateFormat(emergency.getEmergencyDate()));
        emergencyDto.setLastStatusUpdateDate(dateFormat.dateFormat(emergency.getLastStatusUpdateDate()));
        emergencyDto.setEmergencyStatus(statusToSpanish.statusToSpanish(emergency.getEmergencyStatus()));
        return emergencyDto;
    }

    // Manual mapping because need to create instances of emergency attributes from attributes of DTO
    public Emergency convertToEmergency(EmergencyFormDTO emergencyDto) {
        Emergency emergency = new Emergency();
        emergency.setEmergencyDescription(emergencyDto.getEmergencyDescription());
        Clinic clinic = new Clinic();
        clinic.setIdClinic(emergencyDto.getEmergencyClinic());
        emergency.setEmergencyClinic(clinic);
        Subscription subscription = new Subscription();
        subscription.setIdSubscription(emergencyDto.getEmergencySubscription());
        emergency.setEmergencySubscription(subscription);
        Species species = new Species();
        species.setIdSpecies(emergencyDto.getEmergencySpecies());
        emergency.setEmergencySpecies(species);
        MainSymptom symptom = new MainSymptom();
        symptom.setIdMainSymptom(emergencyDto.getEmergencyMainSymptom());
        emergency.setEmergencyMainSymptom(symptom);
        return emergency;
    }
    
}
