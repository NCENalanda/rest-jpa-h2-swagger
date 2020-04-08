package eternal.hoge.spring.boot.example.simple.repository;

import eternal.hoge.spring.boot.example.simple.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book,Long> {
}
