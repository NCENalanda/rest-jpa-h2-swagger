package eternal.hoge.spring.boot.example.simple.controller;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import eternal.hoge.spring.boot.example.simple.entity.Employee;
import eternal.hoge.spring.boot.example.simple.service.EmployeeService;
 

@RestController
@RequestMapping("api")
public class EmployeeRestController {
  
 @Autowired
 private EmployeeService employeeService;
  
 public void setEmployeeService(EmployeeService employeeService) {
  this.employeeService = employeeService;
 }
 
 @GetMapping("employees")
 @ApiOperation(value = "Reterive  All employees ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
         @Authorization(value = "OAuth2Security", scopes = {
                 @AuthorizationScope(scope = "viewEmployee", description = "View API")
         })
 }, tags={ "AWS Lambda (Individual)",  })
 public List<Employee> getEmployees() {
  List<Employee> employees = employeeService.retrieveEmployees();
  return employees;
 }
  
 @GetMapping("employees/{employeeId}")
 @ApiOperation(value = "Reterive  employee by  id ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
         @Authorization(value = "OAuth2Security", scopes = {
                 @AuthorizationScope(scope = "viewEmployee", description = "View API")
         })
 }, tags={ "AWS Lambda (Individual)",  })
 public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) {
  return employeeService.getEmployee(employeeId);
 }
  
 @PostMapping("employees")
 @ApiOperation(value = "create employees ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
         @Authorization(value = "OAuth2Security", scopes = {
                 @AuthorizationScope(scope = "createEmployee", description = "View API")
         })
 }, tags={ "AWS Lambda (Individual)",  })
 public void saveEmployee(Employee employee){
  employeeService.saveEmployee(employee);
  System.out.println("Employee Saved Successfully");
 }
  
 @DeleteMapping("employees/{employeeId}")
 @ApiOperation(value = "delete  by id ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
         @Authorization(value = "OAuth2Security", scopes = {
                 @AuthorizationScope(scope = "deleteEmployee", description = "View API")
         })
 }, tags={ "AWS Lambda (Individual)",  })
 public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
  employeeService.deleteEmployee(employeeId);
  System.out.println("Employee Deleted Successfully");
 }
  
 @PutMapping("employees/{employeeId}")
 @ApiOperation(value = "update employees ", notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ", response = String.class, authorizations = {
         @Authorization(value = "OAuth2Security", scopes = {
                 @AuthorizationScope(scope = "upateEmployee", description = "View API")
         })
 }, tags={ "AWS Lambda (Individual)",  })
 public void updateEmployee(@RequestBody Employee employee,
   @PathVariable(name="employeeId")Long employeeId){
  Employee emp = employeeService.getEmployee(employeeId);
  if(emp != null){
   employeeService.updateEmployee(employee);
  }
   
 }
 
}