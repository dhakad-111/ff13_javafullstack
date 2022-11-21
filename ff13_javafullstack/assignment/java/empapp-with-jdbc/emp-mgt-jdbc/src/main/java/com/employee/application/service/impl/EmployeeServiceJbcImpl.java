package com.employee.application.service.impl;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;

import com.employee.application.dao.EmployeeDAO;
import com.employee.application.dao.EmployeeJdbcDAOImpl;
import com.employee.application.exception.EmployeeException;
import com.employee.application.model.Employee;
import com.employee.application.service.EmployeeServiceJdbc;

public class EmployeeServiceJbcImpl implements EmployeeServiceJdbc {

	private static EmployeeServiceJdbc employeeServiceJdbc;

	private EmployeeDAO daoEmployeeDAO;

	public boolean create(Employee employee) {
		daoEmployeeDAO = new EmployeeJdbcDAOImpl();
		return daoEmployeeDAO.create(employee);

	}

	public Employee findEmployeeById(int id) throws EmployeeException {
		daoEmployeeDAO = new EmployeeJdbcDAOImpl();
		return daoEmployeeDAO.findById(id);
		
	}

	public List<Employee> getAll() {
		daoEmployeeDAO = new EmployeeJdbcDAOImpl();
		return daoEmployeeDAO.getAll();
		

	}

	public boolean update(Employee employee) {
		daoEmployeeDAO = new EmployeeJdbcDAOImpl();
		return daoEmployeeDAO.update(employee);

	}

	public boolean delete(int id) {
		daoEmployeeDAO = new EmployeeJdbcDAOImpl();
		return daoEmployeeDAO.deleteById(id);

	}

	public boolean validate(Employee emp, String msg, Predicate<Employee> condition,
			Function<String, Boolean> operation) {
		if (!condition.test(emp)) {
			return operation.apply(msg);
		}
		return true;
	}

	// Get Employee count greater than given age
	public long getEmployeeCountAgeGreaterThan(int age) {
		daoEmployeeDAO = new EmployeeJdbcDAOImpl();
		return daoEmployeeDAO.getEmployeeCountAgeGreaterThan(age);

	}

	// Get list of Employee IDs whose age is greater than given age
	public List<Integer> getEmployeeIdsAgeGreaterThan(int age) {
		daoEmployeeDAO = new EmployeeJdbcDAOImpl();
		return daoEmployeeDAO.getEmployeeIdsAgeGreaterThan(age);
		
	}

	// Get Department wise Employee count
	public Map<String, Long> getEmployeeCountByDepartment() {
		daoEmployeeDAO = new EmployeeJdbcDAOImpl();
		return daoEmployeeDAO.getEmployeeCountByDepartment();
		
	}

	// Get Department wise Employee count ordered by Department name
	public Map<String, Long> getEmployeeCountByDepartmentOdered() {
		daoEmployeeDAO = new EmployeeJdbcDAOImpl();
		return daoEmployeeDAO.getEmployeeCountByDepartmentOdered();
		
	}

	public synchronized void bulkImport() {
		System.out.format("%n%s - Import started %n", Thread.currentThread().getName());
		int counter = 0;
		// windows path - .\\input\\employee-input.txt
		// mac/linux path - /input/employee-input.txt
		try (Scanner in = new Scanner(new FileReader(".\\input\\employee-input.txt"))) {
			System.out.println("Implorting file...");
			while (in.hasNextLine()) {
				String emp = in.nextLine();
				System.out.println("Importing employee - " + emp);
				Employee employee = new Employee();
				StringTokenizer tokenizer = new StringTokenizer(emp, ",");

				// Emp ID
//				employee.setEmpId(Integer.parseInt(tokenizer.nextToken()));
				// Name
				employee.setName(tokenizer.nextToken());
				// Age
				employee.setAge(Integer.parseInt(tokenizer.nextToken()));
				// Designation
				employee.setDesignation(tokenizer.nextToken());
				// Department
				employee.setDepartment(tokenizer.nextToken());
				// Country
				employee.setCountry(tokenizer.nextToken());

//				employees.put(employee.getEmpId(), employee);
				System.out.println("employee" + employee);
				this.create(employee);
				counter++;
			}
			System.out.format("%s - %d Employees are imported successfully.", Thread.currentThread().getName(),
					counter);
		} catch (Exception e) {
			System.out.println("Error occured while importing employee data. " + e.getMessage());
		}
	}

	public void bulkExport() {
		System.out.format("%n%s - Export started %n", Thread.currentThread().getName());
		String path = ".\\output\\employee-output.txt";
		List<Employee> empList = getAll();
		String temp = "";
		for (Employee x : empList) {
			temp += x.getEmpId() + "," + x.getName() + "," + x.getAge() + "," + x.getDesignation() + ","
					+ x.getDepartment() + "," + x.getCountry() + "\n";
		}
		try (FileOutputStream fileOutputStream = new FileOutputStream(path, true)) {
			byte[] bytes = temp.getBytes();
			fileOutputStream.write(bytes);
			fileOutputStream.flush();
			fileOutputStream.close();
			System.out.format("%d Employees are exported successfully.", bytes.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}