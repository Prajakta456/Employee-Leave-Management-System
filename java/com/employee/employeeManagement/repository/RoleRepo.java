package com.employee.employeeManagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.employee.employeeManagement.entity.Role;
/*CrudRepository implements basic CRUD operations, including count, delete, deleteById, save, saveAll, findById, and findAll.*/

@Repository
public interface RoleRepo extends CrudRepository<Role,Integer>{

	
}
