package eternal.hoge.spring.boot.example.simple.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Data
@Entity
@Table
@ToString
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private  String name;
    private  Integer totalEmployees;
    private  String location;

}
