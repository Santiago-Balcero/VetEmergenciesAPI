package tc_sb_35_api.tc_sb_35_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tc_sb_35_api.tc_sb_35_api.model.ServiceOfClinic;
import tc_sb_35_api.tc_sb_35_api.repository.ServiceOfClinicRepository;


@Service
public class ServiceOfClinicService {
    
    @Autowired
    ServiceOfClinicRepository clinicServiceRepository;

    public List<ServiceOfClinic> getServices() {
        return clinicServiceRepository.findAll();
    }

}
