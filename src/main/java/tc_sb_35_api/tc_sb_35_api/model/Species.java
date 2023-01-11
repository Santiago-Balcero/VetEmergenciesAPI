package tc_sb_35_api.tc_sb_35_api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "SPECIES")
public class Species {
    
    @Id
    @GeneratedValue(generator = "IdSpecies")
    @SequenceGenerator(name = "IdSpecies", sequenceName = "SEQ_SPECIES", allocationSize = 1)
    @Column(name = "id_species")
    private Integer idSpecies;

    @Column(name = "species")
    private String species;

}
