package com.employee.application.dao;

import java.util.List;
import java.util.Map;

import com.employee.application.model.Employee;

public interface EmployeeDAO {
	
	public boolean create(Employee employee); 
	public Employee findById(int id);
	public List<Employee> getAll();
	public boolean update(Employee employee);
	public boolean deleteById(int id);
	public long getEmployeeCountAgeGreaterThan(int age);
	public List<Integer> getEmployeeIdsAgeGreaterThan(int age);
	public Map<String, Long> getEmployeeCountByDepartment();
	public Map<String, Long> getEmployeeCountByDepartmentOdered();

}
