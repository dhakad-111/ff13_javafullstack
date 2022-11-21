package com.employee.application.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.employee.application.exception.EmployeeException;
import com.employee.application.model.Employee;



public interface EmployeeServiceJdbc {
	
	public boolean create(Employee emp);

	public boolean update(Employee emp);

	public boolean delete(int empId);

	public Employee findEmployeeById(int empId) throws EmployeeException;

	public List<Employee> getAll();

	public void bulkImport();

	public void bulkExport();

	public boolean validate(Employee emp, String msg, Predicate<Employee> condition,
			Function<String, Boolean> operation);

	public long getEmployeeCountAgeGreaterThan(int age);

	public List<Integer> getEmployeeIdsAgeGreaterThan(int age);

	public Map<String, Long> getEmployeeCountByDepartment();

	public Map<String, Long> getEmployeeCountByDepartmentOdered();

}
