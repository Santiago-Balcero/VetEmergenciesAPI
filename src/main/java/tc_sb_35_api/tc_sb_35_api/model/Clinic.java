package tc_sb_35_api.tc_sb_35_api.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "CLINIC")
public class Clinic {
    
    @Id
    @GeneratedValue(generator = "IdClinic")
    @SequenceGenerator(name = "IdClinic", sequenceName = "SEQ_CLINIC", allocationSize = 1)
    @Column(name = "id_clinic")
    private Long idClinic;

    @Column(name = "clinic_name")
    private String clinicName;

    @Column(name = "clinic_nit")
    private String clinicNit;

    @ManyToOne
    @JoinColumn(name = "id_department")
    private Department clinicDepartment;

    @Column(name = "clinic_city")
    private String clinicCity;

    @Column(name = "clinic_address")
    private String clinicAddress;

    @Column(name = "clinic_latitude")
    private double clinicLatitude;

    @Column(name = "clinic_longitude")
    private double clinicLongitude;

    @Column(name = "clinic_telephone")
    private String clinicTelephone;

    @Column(name = "clinic_email")
    private String clinicEmail;

    @ManyToMany
    // Specifies the previously created join table (between clinic and service) and it's columns in DB
    @JoinTable(name = "clinic_service",
    joinColumns = @JoinColumn(name = "mix_id_clinic"),
    inverseJoinColumns = @JoinColumn(name = "mix_id_service"))
    List<ServiceOfClinic> clinicServices;

    @Column(name = "is_active")
    private int isActive;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "last_update_action")
    private String lastUpdateAction;

}
