package tc_sb_35_api.tc_sb_35_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to show info of subscription to emergency form in frontend
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class SubscriptionEmergencyFormDTO {
    
    private Long idSubscription;
    private int isActive;
    private String finishDate;

}
