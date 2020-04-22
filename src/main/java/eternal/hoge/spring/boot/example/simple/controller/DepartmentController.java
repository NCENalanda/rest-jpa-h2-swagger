package eternal.hoge.spring.boot.example.simple.controller;


import eternal.hoge.spring.boot.example.simple.entity.Book;
import eternal.hoge.spring.boot.example.simple.entity.Department;
import eternal.hoge.spring.boot.example.simple.repository.DepartmentRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@Slf4j
@Api
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("getDepartment")
    public List<Department> getDepartment(){

        return departmentRepository.findAll();
    }

    @GetMapping("getDepartment/{id}")
    public Department getDepartment(@PathVariable("id") Long id){

        return departmentRepository.findById(id).get();
    }

    @GetMapping("getDepartmentByName/{name}")
    public Department getDepartment(@PathVariable("name") String name){

        log.info("name : "+name);
        return departmentRepository.findByName(name);
    }

    @PostMapping("/createDepartment")
    public Department createDepartment(@RequestBody Department department){

        return departmentRepository.save(department);
    }


}
