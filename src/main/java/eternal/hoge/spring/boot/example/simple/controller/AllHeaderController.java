package eternal.hoge.spring.boot.example.simple.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
//@ApiIgnore
@Slf4j
public class AllHeaderController {

    @GetMapping("/print-all-headers")
    public  Map<String,String> getAllheaders(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            System.out.println("Header Name: "+key+" Header Value: "+value);
        });
        return  headers;
    }

    @PostMapping("/")
    public  String defaultget(@RequestBody Object o){
        log.info("  defaultget() post ");
        log.info("Object is : "+o.toString());

        return  "working";
    }

    @GetMapping("/")
    public  String defaultget(){
        log.info("  defaultget() get ");
        return  "working";
    }
}
