package eternal.hoge.spring.boot.example.simple;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
 
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = "eternal.hoge.spring.boot.example.simple")
public class Application {
 
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
     
}