package tc_sb_35_api.tc_sb_35_api.exceptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class ExpiredSubscriptionException extends RuntimeException {
    
    private Long idSubscription;
    
}
