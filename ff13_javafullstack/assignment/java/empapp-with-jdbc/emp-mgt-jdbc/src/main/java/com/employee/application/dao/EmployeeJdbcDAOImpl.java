package com.employee.application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.employee.application.model.Employee;
import com.employee.application.util.JdbcUtils;

public class EmployeeJdbcDAOImpl implements EmployeeDAO {

	static Connection connection = JdbcUtils.getConnection();

	public boolean create(Employee employee) {
		String insert = "INSERT INTO employee (name, age, department, designation, country) VALUES(?, ?, ?, ?, ?)";
		int result = 0;
		try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setInt(2, employee.getAge());
			preparedStatement.setString(3, employee.getDepartment());
			preparedStatement.setString(4, employee.getDesignation());
			preparedStatement.setString(5, employee.getCountry());
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result > 0;

	}

	public Employee findById(int id) {
		String select = "SELECT *FROM employee where empID=?";
		Employee employee = new Employee();
		try (PreparedStatement prepareStatement = connection.prepareStatement(select)) {
			prepareStatement.setInt(1, id);
			ResultSet query = prepareStatement.executeQuery();
			while (query.next()) {
				employee.setEmpId(query.getInt("empID"));
				employee.setAge(query.getInt("age"));
				employee.setName(query.getString("name"));
				employee.setDepartment(query.getString("department"));
				employee.setDesignation(query.getString("designation"));
				employee.setCountry(query.getString("country"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	public List<Employee> getAll() {
		String selectAll = "SELECT *FROM employee";
		List<Employee> employeeList = new ArrayList<>();
		try (PreparedStatement prepareStatement = connection.prepareStatement(selectAll)) {
			ResultSet result = prepareStatement.executeQuery();
			while (result.next()) {
				Employee employee = new Employee();
				employee.setEmpId(result.getInt("empId"));
				employee.setName(result.getString("name"));
				employee.setAge(result.getInt("age"));
				employee.setDesignation(result.getString("designation"));
				employee.setDepartment(result.getString("department"));
				employee.setCountry(result.getString("country"));
				employeeList.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeeList;

	}

	public boolean update(Employee employee) {
		String update = "UPDATE employee SET name=?, age=?, department=?, designation=?,country=?  where empID=?";
		int result = 0;
		try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
			prepareStatement.setString(1, employee.getName());
			prepareStatement.setInt(2, employee.getAge());
			prepareStatement.setString(3, employee.getDepartment());
			prepareStatement.setString(4, employee.getDesignation());
			prepareStatement.setString(5, employee.getCountry());
			prepareStatement.setInt(6, employee.getEmpId());
			result = prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result > 0;
	}

	public boolean deleteById(int id) {
		String delete = "DELETE FROM employee WHERE empID=?";
		int result = 0;
		try (PreparedStatement prepareStatement = connection.prepareStatement(delete)) {
			prepareStatement.setInt(1, id);
			result = prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result > 0;
	}

	@Override
	public long getEmployeeCountAgeGreaterThan(int age) {
		String select = "SELECT COUNT(*) as cnt FROM employee WHERE age > 30";
		long cnt = 0;
		try (PreparedStatement prepareStatement = connection.prepareStatement(select)) {
			ResultSet result = prepareStatement.executeQuery();
			cnt = result.getLong("cnt");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<Integer> getEmployeeIdsAgeGreaterThan(int age) {
		String select = "SELECT empId FROM employee WHERE age > 30";
		List<Integer> empIds = new ArrayList<>();
		try (PreparedStatement prepareStatement = connection.prepareStatement(select)) {
			ResultSet result = prepareStatement.executeQuery();
			while (result.next()) {
				empIds.add(result.getInt("empId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empIds;
	}

	@Override
	public Map<String, Long> getEmployeeCountByDepartment() {
		String select = "SELECT  count(*) as cnt , department from employee group by department";
		Map<String, Long> empCountByDept = new HashMap<>();
		try (PreparedStatement prepareStatement = connection.prepareStatement(select)) {
			ResultSet result = prepareStatement.executeQuery();
			while (result.next()) {
				empCountByDept.put(result.getString("department"), result.getLong("cnt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empCountByDept;
	}

	@Override
	public Map<String, Long> getEmployeeCountByDepartmentOdered() {
		String select = "SELECT  COUNT(*) as cnt , department from employee GROUP BY department ORDER BY department";
		Map<String, Long> empCountByDept = new TreeMap<>();
		try (PreparedStatement prepareStatement = connection.prepareStatement(select)) {
			ResultSet result = prepareStatement.executeQuery();
			while (result.next()) {
				empCountByDept.put(result.getString("department"), result.getLong("cnt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empCountByDept;
	}

}
