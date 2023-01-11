package tc_sb_35_api.tc_sb_35_api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "EMERGENCY")
public class Emergency {

    @Id
    @GeneratedValue(generator = "IdEmergency")
    @SequenceGenerator(name = "IdEmergency", sequenceName = "SEQ_EMERGENCY", allocationSize = 1)
    @Column(name = "id_emergency")
    private Long idEmergency;

    @ManyToOne
    @JoinColumn(name = "id_subscription")
    private Subscription emergencySubscription;

    @ManyToOne
    @JoinColumn(name = "id_clinic")
    private Clinic emergencyClinic;

    @Column(name = "emergency_date")
    private LocalDateTime emergencyDate;

    @Column(name = "emergency_status")
    private String emergencyStatus;

    @ManyToOne
    @JoinColumn(name = "id_species")
    private Species emergencySpecies;

    @ManyToOne
    @JoinColumn(name = "id_main_symptom")
    private MainSymptom emergencyMainSymptom;
    
    @Column(name = "emergency_description")
    private String emergencyDescription;

    @Column(name = "last_status_update_date")
    private LocalDateTime lastStatusUpdateDate;

}
