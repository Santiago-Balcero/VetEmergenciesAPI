package tc_sb_35_api.tc_sb_35_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tc_sb_35_api.tc_sb_35_api.batch.BatchConfiguration;
import tc_sb_35_api.tc_sb_35_api.exceptions.ClientWithSubscriptionException;
import tc_sb_35_api.tc_sb_35_api.model.Client;
import tc_sb_35_api.tc_sb_35_api.model.Subscription;
import tc_sb_35_api.tc_sb_35_api.repository.EmergencyRepository;
import tc_sb_35_api.tc_sb_35_api.repository.SubscriptionRepository;

@Slf4j
@Service
@EnableScheduling
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    EmergencyRepository emergencyRepository;

    @Autowired
    BatchConfiguration batchJob;

    // In future versions of this project This method will include payment confirmations and methods
    // For MVP purposes this method renews subscription
    public boolean renewSubscription(Long idSubscription) {
        Subscription subscription = subscriptionRepository.findById(idSubscription).get();
        subscription.setStartDate(LocalDate.now());
        activateSubscription(subscription);
        subscriptionRepository.save(subscription);
        return true;
    }
    
    // Future iterations of this project may include creating multiple subscriptions for a set of clients
    // For example: a company sponsors subscriptions for its employees
    public Long createSubscription(Long idClient) {
        Subscription subscription = new Subscription();
        Client client = new Client();
        if(subscriptionRepository.existsBySubscriptionClient(client)) {
            throw new ClientWithSubscriptionException(idClient);
        }
        client.setIdClient(idClient);
        subscription.setSubscriptionClient(client);
        subscription.setStartDate(LocalDate.now());
        subscription = activateSubscription(subscription);
        subscription = subscriptionRepository.save(subscription);
        return subscription.getIdSubscription();
    }

    public Subscription getSubscriptionByIdClient(Long idClient) {
        Client client = new Client();
        client.setIdClient(idClient);
        return subscriptionRepository.findByIdClient(client);
    }

    public Subscription getSubscriptionByClientDocument(String clientDocument) {
        if(subscriptionRepository.findByClientDocument(clientDocument) == null) {
            throw new IllegalArgumentException();
        }
        return subscriptionRepository.findByClientDocument(clientDocument);
    }

    public Subscription getSubscriptionById(Long idSubscription) {
        return subscriptionRepository.findById(idSubscription).get();
    }

    // Activates a subscription, new ones or renewed ones
    // Sets finishDate as startDate + one year
    // Sets subscription status as active (1) 
    private Subscription activateSubscription(Subscription subscription) {
        subscription.setFinishDate(subscription.getStartDate().plusYears(1));
        subscription.setIsActive(1);
        subscription.setLastUpdateDate(LocalDateTime.now());
        subscription.setLastUpdateAction("Activate");
        log.info("INTERNAL PROCESS: subscription with id " + subscription.getIdSubscription() + "was successfully activated");
        return subscription;
    }

    // This calls the execution of batch job when manually executed in frontend by admin
    public void checkForExpiredSubscriptions() {
        try {
            batchJob.launchJob();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
