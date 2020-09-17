package eternal.hoge.spring.boot.example.simple.controller;

import eternal.hoge.spring.boot.example.simple.entity.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bypass")
@Api
public class UNAuth {


    @GetMapping("/")
    @ApiOperation(
            value = "Get By Pass Security ",
            notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ",
            response = String.class,
            extensions = {@Extension(properties = {@ExtensionProperty(name = "x-auth-type",value = "None")})},
            tags={ "AWS Lambda (Individual)",  })
    public String getMessage(){
        return "this is bypass get message";
    }

    @PostMapping("/")
    @ApiOperation(
            value = "Post By Pass Security ",
            notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ",
            response = String.class,
            extensions = {@Extension(properties = {@ExtensionProperty(name = "x-auth-type",value = "None")})},
            tags={ "AWS Lambda (Individual)",  })
    public String postMessage(){
        return "this is bypass post message";
    }

}
