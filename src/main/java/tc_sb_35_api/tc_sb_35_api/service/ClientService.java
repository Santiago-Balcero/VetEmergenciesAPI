package tc_sb_35_api.tc_sb_35_api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import tc_sb_35_api.tc_sb_35_api.dto.ClientProcessingDTO;
import tc_sb_35_api.tc_sb_35_api.exceptions.InvalidCredentialsException;
import tc_sb_35_api.tc_sb_35_api.model.Client;
import tc_sb_35_api.tc_sb_35_api.repository.ClientRepository;
import tc_sb_35_api.tc_sb_35_api.util.PasswordHash;

@Slf4j
@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PasswordHash passwordHash;

    // Documentation: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html
    // Reads the MultipartFile and reads CSV line by line to get data
    // Data from each line is manually mapped to create Client instances and push them into a Clients list
    // Clients list is then saved in DB
    public ClientProcessingDTO createClients(MultipartFile file) {
        ClientProcessingDTO processInfo = new ClientProcessingDTO();
        int newClientsCount = 0;
        // invalidDocuments will be given to front end for information purposes
        ArrayList<String> invalidDocuments = new ArrayList<String>();
        ArrayList<String> validDocuments = new ArrayList<String>();
        ArrayList<Client> toSaveClients = new ArrayList<Client>();
        try {
            // Reads as text content from the file received as argument
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            // Console check for file name
            log.info("INTERNAL PROCESS: processing file, file name: " + file.getOriginalFilename());
            // Console check for file headers read and prepare reader to get data
            log.info("INTERNAL PROCESS: processing file, file headers: " + reader.readLine());
            // This variable is going to have each line records as one string
            String row;
            while ((row = reader.readLine()) != null && !row.isEmpty()) {
                // Splits row string into different values
                String[] clientData = row.split(",");
                // Manually mapping into a Client instance
                Client newClient = new Client();
                newClient.setClientDocument(clientData[0].strip());
                log.info("INTERNAL PROCESS: creating new client, clientDocument: " + newClient.getClientDocument());
                // Checks if clientDocument exists in db or if it is repeated in this csv file (does that by seeing if clientdocument exists in validDocuments list)
                if (clientRepository.existsClientByClientDocument(newClient.getClientDocument()) || validDocuments.contains(newClient.getClientDocument())) {
                    // If client document is already in database or processed in this csv file
                    invalidDocuments.add(newClient.getClientDocument());
                    log.info("INTERNAL PROCESS: invalid client document, clientDocument: " + newClient.getClientDocument());
                }
                else {
                    validDocuments.add(newClient.getClientDocument());
                    newClient.setClientName(clientData[1].strip());
                    newClient.setClientLastname(clientData[2].strip());
                    newClient.setClientTelephone(clientData[3].strip());
                    newClient.setClientEmail(clientData[4].strip());
                    // Hashes password with sha256 algorithm
                    newClient.setClientPassword(passwordHash.passwordHash(clientData[5].strip()));
                    newClient.setIsActive(1);
                    newClient.setLastUpdateDate(LocalDateTime.now());
                    newClient.setLastUpdateAction("Creation");
                    toSaveClients.add(newClient);
                    log.info("INTERNAL PROCESS: client added to clients list to save, clientDocument: " + newClient.getClientDocument());
                }
            }
            // Number of saved clients
            newClientsCount = clientRepository.saveAll(toSaveClients).size();
            processInfo.setCreatedClients(newClientsCount);
            processInfo.setInvalidDocuments(invalidDocuments);
            log.info("INTERNAL PROCESS: " + newClientsCount + " new clients were saved");
            log.info("INTERNAL PROCESS: " + invalidDocuments.size() + " invalid documents found");
            return processInfo;
        } catch (IOException ex) {
            ex.printStackTrace();
            return processInfo;
        }
    }
    
    public Long clientLogin(String clientDocument, String clientPassword) {
        clientPassword = passwordHash.passwordHash(clientPassword);
        Long idClient = clientRepository.getId(clientDocument, clientPassword);
        if(idClient == null) {
            throw new InvalidCredentialsException();
        }
        return idClient;
    }

}
