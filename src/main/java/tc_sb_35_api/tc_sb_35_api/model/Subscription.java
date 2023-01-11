package tc_sb_35_api.tc_sb_35_api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "SUBSCRIPTION")
public class Subscription {

    @Id
    @GeneratedValue(generator = "IdSubscription")
    @SequenceGenerator(name = "IdSubscription", sequenceName = "SEQ_SUBSCRIPTION", allocationSize = 1)
    @Column(name = "id_subscription")
    private Long idSubscription;

    @OneToOne
    @JoinColumn(name = "id_client")
    private Client subscriptionClient;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @Column(name= "is_active")
    private int isActive;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "last_update_action")
    private String lastUpdateAction;

}
