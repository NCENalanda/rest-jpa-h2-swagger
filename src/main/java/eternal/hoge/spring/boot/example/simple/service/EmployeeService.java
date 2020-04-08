package eternal.hoge.spring.boot.example.simple.service;

import java.util.List;

import eternal.hoge.spring.boot.example.simple.entity.Employee;


public interface EmployeeService {
 public List<Employee> retrieveEmployees();
 
 public Employee getEmployee(Long employeeId);
 
 public void saveEmployee(Employee employee);
 
 public void deleteEmployee(Long employeeId);
 
 public void updateEmployee(Employee employee);
}