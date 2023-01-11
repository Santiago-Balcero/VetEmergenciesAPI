package tc_sb_35_api.tc_sb_35_api.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import tc_sb_35_api.tc_sb_35_api.model.Subscription;

@Configuration
public class SubscriptionConverter {
    
    @Autowired
    ModelMapper modelMapper;

    public SubscriptionEmergencyFormDTO convertToEmergengyFormDto(Subscription subscription) {
        SubscriptionEmergencyFormDTO subscriptionEmergencyFormDto = modelMapper.map(subscription, SubscriptionEmergencyFormDTO.class);
        return subscriptionEmergencyFormDto;
    }

    public SubscriptionWithEmergenciesDTO convertToSubscriptionByClientDto(Subscription subscription) {
        SubscriptionWithEmergenciesDTO subscriptionDto = modelMapper.map(subscription, SubscriptionWithEmergenciesDTO.class);
        return subscriptionDto;
    }

}
