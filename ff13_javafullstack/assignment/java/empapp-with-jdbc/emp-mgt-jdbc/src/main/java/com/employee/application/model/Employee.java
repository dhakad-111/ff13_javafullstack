package com.employee.application.model;

import java.io.Serializable;

public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int empId;
	private String name;
	private int age;
	private String department;
	private String designation;
	private String country;

	public Employee() {

	}

	public Employee(int empId, String name, int age, String designation, String department, String country) {
		this.empId = empId;
		this.name = name;
		this.age = age;
		this.designation = designation;
		this.department = department;
		this.country = country;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", name=" + name + ", age=" + age + ", department=" + department
				+ ", designation=" + designation + ", country=" + country + "]";
	}

}