package eternal.hoge.spring.boot.example.simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eternal.hoge.spring.boot.example.simple.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{

}