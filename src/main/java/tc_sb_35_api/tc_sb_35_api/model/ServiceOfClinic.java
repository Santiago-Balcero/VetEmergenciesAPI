package tc_sb_35_api.tc_sb_35_api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "SERVICE")
public class ServiceOfClinic {

    @Id
    @GeneratedValue(generator = "IdService")
    @SequenceGenerator(name = "IdService", sequenceName = "SEQ_SERVICE", allocationSize = 1)
    @Column(name = "id_service")
    private Long idService;

    @Column(name = "service")
    private String service;

    @ManyToMany
    // This annotation is to avoid inifinite recursion calling when using get http requests between clinics and services
    // Documentation: https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion#managed-back-reference
    @JsonIgnore
    Set<Clinic> clinics;

}
