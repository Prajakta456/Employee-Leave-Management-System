package employeeManagement.repository;

import org.springframework.data.repository.CrudRepository;

import employeeManagement.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}