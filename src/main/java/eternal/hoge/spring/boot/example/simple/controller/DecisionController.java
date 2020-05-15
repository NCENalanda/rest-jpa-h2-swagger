package eternal.hoge.spring.boot.example.simple.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("decision")
public class DecisionController {

    @GetMapping
    public  String    getDecision(@RequestParam ("id") String id){
        return   id;
    }

    @PostMapping
    public  String    getDecision1(@RequestParam ("id") String id){
        return   id;
    }
}
