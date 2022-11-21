package com.employee.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.employee.application.exception.EmployeeException;
import com.employee.application.model.Employee;
import com.employee.application.service.EmployeeServiceJdbc;
import com.employee.application.service.impl.EmployeeServiceJbcImpl;

/**
 * Hello world!
 *
 */
public class App {

	private static EmployeeServiceJdbc employeeServiceJdbc;

	public static void main(String[] args) {

		try {

			employeeServiceJdbc = new EmployeeServiceJbcImpl();
			ExecutorService executor = Executors.newCachedThreadPool();
			FastReader in = new FastReader();
			FastWriter out = new FastWriter();
			System.out.print("Welcome to Employee Management App!");

			while (true) {

				System.out.println("\n");
				System.out.println("1. Add Employee");
				System.out.println("2. View Employee");
				System.out.println("3. Update Employee");
				System.out.println("4. Delete Employee");
				System.out.println("5. View All Employees");
				System.out.println("6. Print Statistics");
				System.out.println("7. Import");
				System.out.println("8. Export");
				System.out.println("9. Exit");

				System.out.print("Enter the option: ");
				int option = 0;
				// Get option from user
				try {
					option = Integer.parseInt(in.next());
				} catch (NumberFormatException e) {
					System.out.println("Invalid option. Please enter valid option.");
					continue;
				}
				int empId;
				try {
					switch (option) {
					case 1:
						addEmployee(in);
						System.out.println("Employee has been added successfully!");
						break;
					case 2:
						System.out.print("Please enter employee id: ");
						empId = in.nextInt();
						Employee emp = null;
						try {
							emp = employeeServiceJdbc.findEmployeeById(empId);
						} catch (EmployeeException e) {
							System.out.println(e.getMessage());
							break;
						}
						printHeader();
						printDetail(emp);
						break;
					case 3:
						System.out.print("Please enter employee id: ");
						empId = in.nextInt();
						Employee empForUpdate;
						try {
							empForUpdate = employeeServiceJdbc.findEmployeeById(empId);
						} catch (EmployeeException e) {
							System.out.println(e.getMessage());
							break;
						}
						captureEmpDetail(in,empForUpdate);
						employeeServiceJdbc.update(empForUpdate);
						System.out.println("Employee has been updated successfully!");
						break;
					case 4:
						System.out.print("Please enter employee id: ");
						empId = in.nextInt();
						employeeServiceJdbc.delete(empId);
						System.out.println("Employee has been deleted successfully!");
						break;
					case 5:
						List<Employee> employees = employeeServiceJdbc.getAll();
						printHeader();
						for (Employee employee : employees) {
							printDetail(employee);
						}
						break;
					case 6:
						printStatistics();
						break;
					case 7:
						Callable<Boolean> importThread = new Callable<Boolean>() {
							@Override
							public Boolean call() throws Exception {
								System.out.println(Thread.currentThread() + " Waiting for 5s to start import process.");
//								Thread.sleep(5000);
								employeeServiceJdbc.bulkImport();
								return true;
							}
						};

						Future<Boolean> importFuture = executor.submit(importThread);
						System.out.println(Thread.currentThread().getName() + " Import process triggered");

						break;
					case 8:

						Callable<Boolean> exportThread = new Callable<Boolean>() {
							@Override
							public Boolean call() throws Exception {
								System.out.println(Thread.currentThread() + " Waiting for 5s to start export process.");
								Thread.sleep(5000);
								employeeServiceJdbc.bulkExport();
								return true;
							}
						};

						Future<Boolean> exportFuture = executor.submit(exportThread);
						System.out.println(Thread.currentThread().getName() + " Export process triggered");

						break;
					case 9:
						System.out.println("Thank you!!!");
						executor.shutdown();
						out.close();
						System.exit(0);
						break;

					default:
						break;
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter valid input.");
				}
			}
		} catch (Exception e) {
		}
	}

	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		public String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public String nextLine() {
			String str = "";
			try {
				str = br.readLine().trim();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	static class FastWriter {
		private final BufferedWriter bw;

		public FastWriter() {
			bw = new BufferedWriter(new OutputStreamWriter(System.out));
		}

		public void print(Object object) throws IOException {
			bw.append("" + object);
		}

		public void println(Object object) throws IOException {
			print(object);
			bw.append("\n");
		}

		public void close() throws IOException {
			bw.close();
		}
	}

	private static void printStatistics() {

		System.out.println(
				"No of employees older than thirty years: " + employeeServiceJdbc.getEmployeeCountAgeGreaterThan(30));
		System.out.println(
				"List employee IDs older than thirty years: " + employeeServiceJdbc.getEmployeeIdsAgeGreaterThan(30));
		System.out.println("Employee count by Department: " + employeeServiceJdbc.getEmployeeCountByDepartment());
		System.out.println(
				"Employee count by Department ordered: " + employeeServiceJdbc.getEmployeeCountByDepartmentOdered());
	}

	private static void printHeader() {
		System.out.format("\n%5s %15s %5s %15s %15s %15s", "EmpID", "Name", "Age", "Designation", "Department",
				"Country");
	}

	private static void printDetail(Employee emp) {
		if (emp == null) {
			return;
		}

		System.out.format("\n%5d %15s %5d %15s %15s %15s", emp.getEmpId(), emp.getName(), emp.getAge(),
				emp.getDesignation(), emp.getDepartment(), emp.getCountry());
	}

	private static void addEmployee(FastReader in) throws NumberFormatException {
		Employee employee = new Employee();

		captureEmpDetail(in, employee);

		employeeServiceJdbc.create(employee);
	}

	private static void captureEmpDetail(FastReader in, Employee employee) throws NumberFormatException {
		System.out.print("Enter employee Name: ");
		employee.setName(in.nextLine());

		try {
			boolean val = true;
			do {
				System.out.print("Enter employee Age: ");
				String errorMsg = "Invalid Age. Age should be between 18 to 60.";
				employee.setAge(in.nextInt());
				val = employeeServiceJdbc.validate(employee, errorMsg, e -> e.getAge() >= 18 && e.getAge() <= 60, m -> {
					System.out.println(m);
					return false;
				});
			} while (!val);
		} catch (NumberFormatException e) {
			throw e;
		}

		System.out.print("Enter employee Designation: ");
		employee.setDesignation(in.nextLine());

		System.out.print("Enter employee Department: ");
		employee.setDepartment(in.nextLine());

		System.out.print("Enter employee Country: ");
		employee.setCountry(in.nextLine());
	}

}
