package com.howtodoinjava.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HeaderController {
	
	@GetMapping("/print-all-headers")
    public  Map<String,String> getAllheaders(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            System.out.println("Header Name: "+key+" Header Value: "+value);
        });
        
         System.out.println("---------------------------------------------------");
        return  headers;
    }
}
