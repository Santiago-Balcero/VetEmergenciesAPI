package tc_sb_35_api.tc_sb_35_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tc_sb_35_api.tc_sb_35_api.model.Species;
import tc_sb_35_api.tc_sb_35_api.repository.SpeciesRepository;

@Service
public class SpeciesService {
    
    @Autowired
    SpeciesRepository speciesRepository;

    public List<Species> getSpecies() {
        return speciesRepository.findAll();
    }

}
