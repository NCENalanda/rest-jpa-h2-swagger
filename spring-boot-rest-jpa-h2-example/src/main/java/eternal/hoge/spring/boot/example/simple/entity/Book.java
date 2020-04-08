package eternal.hoge.spring.boot.example.simple.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Data
@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public  String name;
    public  String author;
    public  Double price;
}
