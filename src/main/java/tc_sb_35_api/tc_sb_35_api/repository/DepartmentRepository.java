package tc_sb_35_api.tc_sb_35_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tc_sb_35_api.tc_sb_35_api.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    
}
