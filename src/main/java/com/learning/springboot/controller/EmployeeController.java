package com.learning.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springboot.dao.IEmployeeDao;
import com.learning.springboot.model.Employee;

@RestController
public class EmployeeController {

	@Autowired
	private IEmployeeDao iEmployeeDao;

	@GetMapping(path = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> fetchEmployees() {
		List<Employee> employees = (List<Employee>) iEmployeeDao.findAll();
		if (null == employees || employees.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(employees);
	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") String id) {
		iEmployeeDao.deleteById(id);
		return ResponseEntity.ok().body("Employee deleted");
	}

	@PutMapping("/employee/{id}")
	public Employee updateEmployee(@PathVariable("id") String id) {
		Optional<Employee> optional = this.iEmployeeDao.findById(id);	
		Employee employee = null;
		if(optional.isPresent()) {
			employee = optional.get();
			employee.setLocation("Bangaluru");
			employee.setProjectName("Aviva");
		} else {
			employee = new Employee();
			employee.setEmpId(id);
			employee.setEmpName("Sukhman");
			employee.setLocation("Bangalore");
			employee.setExperience(3);
			employee.setProjectName("IMS");
			this.iEmployeeDao.save(employee);
		}	

		return employee;
	}
}
