package com.learning.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.springboot.model.Employee;

@Repository
//@RepositoryRestResource(path = "users", collectionResourceRel = "users")
public interface IEmployeeDao extends JpaRepository<Employee, String>{

}
