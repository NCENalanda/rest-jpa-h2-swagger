package eternal.hoge.spring.boot.example.simple.repository;

import eternal.hoge.spring.boot.example.simple.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookRepository extends JpaRepository<Book,Long> {

   List findByName(String name);
   List findByAuthor(String author);
   List findByPrice(Double price);
}
