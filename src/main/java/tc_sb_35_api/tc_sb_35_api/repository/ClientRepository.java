package tc_sb_35_api.tc_sb_35_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tc_sb_35_api.tc_sb_35_api.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

    public boolean existsClientByClientDocument(String clientDocument);

    @Query("SELECT clientPassword FROM Client WHERE clientDocument = ?1")
    public String getPassword(String clientDocument);

    @Query("SELECT idClient FROM Client WHERE clientDocument = ?1 AND clientPassword = ?2")
    public Long getId(String clientDocument, String clientPassword);

}
