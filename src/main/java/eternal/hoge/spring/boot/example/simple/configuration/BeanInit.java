package eternal.hoge.spring.boot.example.simple.configuration;

import eternal.hoge.spring.boot.example.simple.entity.*;
import eternal.hoge.spring.boot.example.simple.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Configuration
@Slf4j
public class BeanInit {
    @Autowired
    private IBookRepository  bookRepository;
    @Autowired
    private EmployeeRepository  employeeRepository;
    @Autowired
    private IDocumentRepository documentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CompanyRepository companyRepository;


    @Bean(initMethod = "init")
    public   void  exceuteSql(){
        log.info("exceuteSql()");
        this.addBook()
                .addEmployee()
                .addDocument()
                .addDepartment()
                .addCompany()
        ;

    }

    private void addCompany() {
        log.info("addCompany()");
        Company company = new Company();
        company.setCeo("ceo-1");company.setCurrentRevenue(111111111.00);
        company.setDirector("director-1");company.setHeadquarters("Mumbai");
        company.setName("comp-1");company.setTotalStaff(2790);
        Company company1 = company;

        company = new Company();
        company.setCeo("ceo-2");company.setCurrentRevenue(341111111.00);
        company.setDirector("director-2");company.setHeadquarters("NEW-DELHI");
        company.setName("comp-2");company.setTotalStaff(7690);
        Company company2 = company;

        companyRepository.saveAll(Arrays.asList(company1,company2));
    }

    public  BeanInit addBook(){
        log.info("addBook()");
        Book  book1 = new Book();
        book1.setAuthor("author1");book1.setName("book1");book1.setPrice(1000.00);

        Book  book2 = new Book();
        book2.setAuthor("author1");book2.setName("book2");book2.setPrice(2000.00);

        Book  book3 = new Book();
        book3.setAuthor("author1");book3.setName("book3");book3.setPrice(3000.00);

        bookRepository.saveAll(Arrays.asList(book1,book2,book3));
        return this;

    }
    public BeanInit  addEmployee(){
        log.info("addEmployee()");
        Employee employee1 = new Employee();
        employee1.setDepartment("IT");employee1.setName("emp1");employee1.setSalary(100000);
        employee1.setCompany("comp-1");employee1.setProject("proj-1");
        Employee employee2 = new Employee();
        employee2.setDepartment("Construction");employee2.setName("emp2");employee2.setSalary(200000);
        employee2.setCompany("comp-1");employee2.setProject("proj-1");
        Employee employee3 = new Employee();
        employee3.setDepartment("Management");employee3.setName("emp3");employee3.setSalary(300000);
        employee3.setCompany("comp-1");employee3.setProject("proj-1");
        employeeRepository.saveAll(Arrays.asList(employee1,employee2,employee3));
        return this;
    }
    public BeanInit  addDocument(){
        log.info("addDocument()");
        Document   document1 = new Document();
        document1.setTitle("document1");document1.setUserId(1L);

        Document   document2 = new Document();
        document2.setTitle("document2");document2.setUserId(1L);

        Document   document3 = new Document();
        document3.setTitle("document3");document3.setUserId(2L);

        Document   document4 = new Document();
        document4.setTitle("document4");document4.setUserId(2L);

        Document   document5 = new Document();
        document5.setTitle("document5");document5.setUserId(2L);

        Document   document6 = new Document();
        document6.setTitle("document6");document6.setUserId(3L);
        documentRepository.saveAll(Arrays.asList(document1,document2,document3,document4,document5,document6));
        return this;
    }

    public BeanInit  addDepartment(){
        log.info("addDepartment()");

        Department department = new Department();
        department.setName("IT");department.setLocation("HYD");department.setTotalEmployees(1000);
        Department department1 = department;

        department = new Department();
        department.setName("Construction");department.setLocation("DELHI");department.setTotalEmployees(4000);
        Department department2 = department;

        department = new Department();
        department.setName("Management");department.setLocation("MUMBAI");department.setTotalEmployees(500);
        Department department3 = department;

        department = new Department();
        department.setName("Finance");department.setLocation("MUMBAI");department.setTotalEmployees(700);
        Department department4 = department;

        departmentRepository.saveAll(Arrays.asList(department1,department2,department3,department4));

        return this;
    }
}
