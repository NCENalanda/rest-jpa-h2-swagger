package eternal.hoge.spring.boot.example.simple.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;
    private  String ceo;
    private  String director;
    private  String headquarters;
    private  String departments;
    private  Integer totalBranch;
    private  Double currentRevenue;
    private  Integer totalStaff;



}
