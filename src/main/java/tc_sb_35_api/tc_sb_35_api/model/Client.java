package tc_sb_35_api.tc_sb_35_api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "CLIENT")
public class Client {
    
    @Id
    @GeneratedValue(generator = "IdClient")
    @SequenceGenerator(name = "IdClient", sequenceName = "SEQ_CLIENT", allocationSize = 1)
    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "client_document")
    private String clientDocument;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_lastname")
    private String clientLastname;

    @Column(name = "client_telephone")
    private String clientTelephone;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "client_password")
    private String clientPassword;

    @Column(name = "is_active")
    private int isActive;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "last_update_action")
    private String lastUpdateAction;

    @OneToOne(mappedBy = "subscriptionClient")
    // This annotation is to avoid inifinite recursion calling when using get http requests between clinics and services
    // Documentation: https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion#managed-back-reference
    @JsonIgnore
    private Subscription clientSubscription;

}
