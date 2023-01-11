package tc_sb_35_api.tc_sb_35_api.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to return info of created clients and invalid client documents
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ClientProcessingDTO {

    private int createdClients;
    private ArrayList<String> invalidDocuments;
    
}
