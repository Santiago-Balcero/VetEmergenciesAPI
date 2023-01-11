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
@Table(name = "DEPARTMENT")
public class Department {
    
    @Id
    @GeneratedValue(generator = "IdDepartment")
    @SequenceGenerator(name = "IdDepartment", sequenceName = "SEQ_DEPARTMENT", allocationSize = 1)
    @Column(name = "id_department")
    private Long idDepartment;

    @Column(name = "department")
    private String department;
    
}
