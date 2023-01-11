package tc_sb_35_api.tc_sb_35_api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to show detailed info of subscription, client and emergencies in frontend when searching by clientDocument or by idsubscription
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class SubscriptionWithEmergenciesDTO {

    private Long idSubscription;
    private int isActive;
    private String startDate;
    private String finishDate;
    private String clientDocument;
    private String clientName;
    private String clientLastname;
    private String clientTelephone;
    private String clientEmail;
    private List<EmergencySearchDTO> emergencies;
    
}
