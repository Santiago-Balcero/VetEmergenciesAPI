package tc_sb_35_api.tc_sb_35_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tc_sb_35_api.tc_sb_35_api.model.MainSymptom;
import tc_sb_35_api.tc_sb_35_api.repository.MainSymptomRepository;

@Service
public class MainSymptomService {

    @Autowired
    MainSymptomRepository mainSymptomRepository;

    public List<MainSymptom> getMainSymptoms() {
        return mainSymptomRepository.findAll();
    }
    
}
