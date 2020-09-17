package eternal.hoge.spring.boot.example.simple.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.ws.rs.Produces;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Api
@Slf4j
public class AllHeaderController {

    @ApiIgnore
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

        return  "working : "+o.toString();
    }

    @GetMapping("/")
    public  String defaultget(){
        log.info("  defaultget() get ");
        return  "working";
    }





    @GetMapping("/csv")
    @Produces("text/plain")
    public String csvResponse(){
        log.info("  defaultget() post ");

        SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aa");
        //Setting the time zone
        dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
        List list = Arrays.asList("Content-Type: text/html;charset=ISO-8859-1",dateTimeInGMT.format(new Date()));


        String str  =  list.toString();
        str = str.replace("[","");
        str = str.replace("]","");

        List l1  = Arrays.asList("1","2","3");
        List l2  = Arrays.asList("a","b","c");
        String str1  =  l1.toString();
       str1 =  str1.replace("[","");
       str1 =  str1.replace("]","");

        str = str+"<br>";
        str = str+str1.toString();

         str1  =  l2.toString();
        str1 =  str1.replace("[","");
        str1 =  str1.replace("]","");

       str = str+"<br>";
        str = str+str1.toString();
        return str;



    }
}
