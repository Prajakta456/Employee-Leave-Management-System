package employeeManagement.repository;

import employeeManagement.entity.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Override
    @EntityGraph(attributePaths = {"roles"})
    Optional<Employee> findById(Integer employeeId);
}