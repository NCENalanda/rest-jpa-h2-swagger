package eternal.hoge.spring.boot.example.simple.controller;


import eternal.hoge.spring.boot.example.simple.entity.Company;
import eternal.hoge.spring.boot.example.simple.entity.Department;
import eternal.hoge.spring.boot.example.simple.repository.CompanyRepository;
import eternal.hoge.spring.boot.example.simple.repository.DepartmentRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@Slf4j
@Api
public class CompanyController {


    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("getDepartment")
    public List<Company> getDepartment(){

        return companyRepository.findAll();
    }

    @GetMapping("getDepartment/{id}")
    public  Company getDepartment(@PathVariable("id") Long id){

        return companyRepository.findById(id).get();
    }

    @GetMapping("getDepartmentByName/{name}")
    public Company getCompany(@PathVariable("name") String name){

        log.info("name : "+name);
        return companyRepository.findByName(name);
    }

    @PostMapping("/createDepartment")
    public Company createDepartment(@RequestBody Company company){

        return companyRepository.save(company);
    }
}
