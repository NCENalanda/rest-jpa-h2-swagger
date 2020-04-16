package eternal.hoge.spring.boot.example.simple.configuration;

import eternal.hoge.spring.boot.example.simple.entity.Book;
import eternal.hoge.spring.boot.example.simple.entity.Document;
import eternal.hoge.spring.boot.example.simple.entity.Employee;
import eternal.hoge.spring.boot.example.simple.repository.EmployeeRepository;
import eternal.hoge.spring.boot.example.simple.repository.IBookRepository;
import eternal.hoge.spring.boot.example.simple.repository.IDocumentRepository;
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
    @Bean(initMethod = "init")
    public   void  exceuteSql(){
        log.info("exceuteSql()");
        this.addBook().addEmployee().addDocument();

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
        Employee employee2 = new Employee();
        employee2.setDepartment("Construction");employee2.setName("emp2");employee2.setSalary(200000);
        Employee employee3 = new Employee();
        employee3.setDepartment("Management");employee3.setName("emp3");employee3.setSalary(300000);
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
}
