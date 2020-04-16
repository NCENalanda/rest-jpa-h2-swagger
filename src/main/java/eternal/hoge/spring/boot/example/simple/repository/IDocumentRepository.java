package eternal.hoge.spring.boot.example.simple.repository;

import eternal.hoge.spring.boot.example.simple.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDocumentRepository extends JpaRepository<Document,Long> {
    List<Document> findByUserId(Long userId);
}
