package tc_sb_35_api.tc_sb_35_api.batch;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;

import lombok.extern.slf4j.Slf4j;
import tc_sb_35_api.tc_sb_35_api.model.Subscription;

@Slf4j
public class ExpiredSubscriptionProcessor implements ItemProcessor<Subscription, Subscription>{
    
    @Override
    public Subscription process(@NonNull Subscription subscription) throws Exception {
        LocalDate today = LocalDate.now();
        // Deactivates the subscription if finishDay was yesterday or before
        if (subscription.getFinishDate().isBefore(today)) {
            subscription.setIsActive(0);
            subscription.setLastUpdateDate(LocalDateTime.now());
            subscription.setLastUpdateAction("Deactivate");
            log.info("INTERNAL PROCESS: subscription with id " + subscription.getIdSubscription() + " expired and was deactivated");
        }
        return subscription;
    }

}
