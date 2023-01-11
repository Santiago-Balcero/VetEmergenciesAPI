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
@Table(name = "VETERINARIAN")
public class Veterinarian {
    
    @Id
    @GeneratedValue(generator = "IdVeterinarian")
    @SequenceGenerator(name = "IdVeterinarian", sequenceName = "SEQ_VETERINARIAN", allocationSize = 1)
    @Column(name = "id_veterinarian")
    private Long idVeterinarian;

    @Column(name = "vet_document")
    private String vetDocument;

    @ManyToOne
    @JoinColumn(name = "id_clinic")
    private Clinic vetClinic;

    @Column(name = "vet_name")
    private String vetName;

    @Column(name = "vet_lastname")
    private String vetLastname;

    @Column(name = "vet_telephone")
    private String vetTelephone;

    @Column(name = "vet_email")
    private String vetEmail;

    @Column(name = "is_active")
    private int isActive;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "last_update_action")
    private String lastUpdateAction;

}
