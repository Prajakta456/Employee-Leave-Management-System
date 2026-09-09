package employeeManagement.repository;

import org.springframework.data.repository.CrudRepository;

import employeeManagement.entity.LeaveInformation;

public interface LeaveInformationRepo
        extends CrudRepository<LeaveInformation, Integer> {

}