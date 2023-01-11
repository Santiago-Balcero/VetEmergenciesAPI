package tc_sb_35_api.tc_sb_35_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tc_sb_35_api.tc_sb_35_api.model.Client;
import tc_sb_35_api.tc_sb_35_api.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

    public boolean existsBySubscriptionClient(Client client);
    
    // JPQL query to gett all active subscriptions for status update
    @Query("SELECT s FROM Subscription s WHERE s.isActive = 1")
    public List<Subscription> findByActiveStatus();

    // JPQL query to find the subscription related to a client
    @Query("SELECT s FROM Subscription s WHERE s.subscriptionClient = ?1")
    public Subscription findByIdClient(Client client);

    // JPQL query to get isActive status
    @Query("SELECT s.isActive FROM Subscription s WHERE s.idSubscription = ?1")
    public int getStatus(Long idSubscription);

    // JPQL query to get Subscription by clientDocument
    @Query("SELECT s FROM Subscription s INNER JOIN Client c " + 
    "ON c.idClient = s.subscriptionClient WHERE c.clientDocument = ?1")
    public Subscription findByClientDocument(String clientDocument);

}
