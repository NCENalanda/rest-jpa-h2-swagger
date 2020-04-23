package eternal.hoge.spring.boot.example.simple.repository;

import eternal.hoge.spring.boot.example.simple.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    Company findByName(String name);
}
