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
@Table(name = "MAIN_SYMPTOM")
public class MainSymptom {

    @Id
    @GeneratedValue(generator = "IdMainSymptom")
    @SequenceGenerator(name = "IdMainSymptom", sequenceName = "SEQ_MAIN_SYMPTOM", allocationSize = 1)
    @Column(name = "id_main_symptom")
    private Integer idMainSymptom;

    @Column(name = "main_symptom")
    private String mainSymptom;

}
