package eternal.hoge.spring.boot.example.simple.repository;

import eternal.hoge.spring.boot.example.simple.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository  extends JpaRepository<Department,Long> {
    Department findByName(String name);
}
