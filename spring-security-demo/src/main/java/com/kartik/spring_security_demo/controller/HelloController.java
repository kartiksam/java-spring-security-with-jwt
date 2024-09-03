package com.kartik.spring_security_demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")

    public String greet(HttpServletRequest request)
    {
    return "Hello World";

}
    @GetMapping("/about")

    public String greets(HttpServletRequest request){

        return "Kartik sharma" + request.getSession().getId();
    }
}
