package employeeManagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import employeeManagement.entity.ApplyLeave;

public interface ApplyLeaveRepo extends CrudRepository<ApplyLeave, Integer> {

    List<ApplyLeave> findByEmployeeId(int employeeId);
}
