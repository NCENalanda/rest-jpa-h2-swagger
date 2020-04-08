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
 @ApiOperation(value = "employees", nickname = "employees", notes = "", authorizations = {
         @Authorization(value = "default", scopes = {
                 @AuthorizationScope(scope = "TELE", description = "for telestra")
         })    }, tags={  })
 public List<Employee> getEmployees() {
  List<Employee> employees = employeeService.retrieveEmployees();
  return employees;
 }
  
 @GetMapping("employees/{employeeId}")
 @ApiOperation(value = "employees/{employeeId}", nickname = "employees/{employeeId}", notes = "", authorizations = {
         @Authorization(value = "default", scopes = {
                 @AuthorizationScope(scope = "TELE", description = "for telestra")
         })    }, tags={  })
 public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) {
  return employeeService.getEmployee(employeeId);
 }
  
 @PostMapping("employees")
 @ApiOperation(value = "employees", nickname = "employees", notes = "", authorizations = {
         @Authorization(value = "default", scopes = {
                 @AuthorizationScope(scope = "TELE", description = "for telestra")
         })    }, tags={  })
 public void saveEmployee(Employee employee){
  employeeService.saveEmployee(employee);
  System.out.println("Employee Saved Successfully");
 }
  
 @DeleteMapping("employees/{employeeId}")
 @ApiOperation(value = "employees/{employeeId}", nickname = "employees/{employeeId}", notes = "", authorizations = {
         @Authorization(value = "default", scopes = {
                 @AuthorizationScope(scope = "TELE", description = "for telestra")
         })    }, tags={  })
 public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
  employeeService.deleteEmployee(employeeId);
  System.out.println("Employee Deleted Successfully");
 }
  
 @PutMapping("employees/{employeeId}")
 @ApiOperation(value = "employees/{employeeId}", nickname = "employees/{employeeId}", notes = "", authorizations = {
         @Authorization(value = "default", scopes = {
                 @AuthorizationScope(scope = "TELE", description = "for telestra")
         })    }, tags={  })
 public void updateEmployee(@RequestBody Employee employee,
   @PathVariable(name="employeeId")Long employeeId){
  Employee emp = employeeService.getEmployee(employeeId);
  if(emp != null){
   employeeService.updateEmployee(employee);
  }
   
 }
 
}