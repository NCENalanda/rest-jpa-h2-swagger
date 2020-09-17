package eternal.hoge.spring.boot.example.simple.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("time")
@Slf4j
public class TimeController {

    @GetMapping("{id}")
    @ApiOperation(
            value = "Get By Pass Security ",
            notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ",
            response = String.class,
            extensions = {@Extension(properties = {@ExtensionProperty(name = "x-auth-type",value = "None")})},
            tags={ "AWS Lambda (Individual)",  })
    public  String timeout(@PathVariable("id") Long id ){
        log.info("  timeout() ");
        try {
            Thread.sleep(id);
        }catch (Exception e){e.printStackTrace();}
        log.info("  time "+id);
        return  "working   : "+id;
    }

    @PostMapping("{id}")
    @ApiOperation(
            value = "Post By Pass Security ",
            notes = "This operation can be use to retrieve ARNs of AWS Lambda function for a given AWS credentials. ",
            response = String.class,
            extensions = {@Extension(properties = {@ExtensionProperty(name = "x-auth-type",value = "None")})},
            tags={ "AWS Lambda (Individual)",  })
    public  String posttimeout(@PathVariable("id") Long id ){
        log.info("  posttimeout ");
        try {
            Thread.sleep(id);
        }catch (Exception e){e.printStackTrace();}
        log.info("  time "+id);
        return  "working   : "+id;
    }
}
