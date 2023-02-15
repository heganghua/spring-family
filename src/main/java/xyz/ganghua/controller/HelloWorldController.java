package xyz.ganghua.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.ganghua.entity.Users;

@RestController
public class HelloWorldController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/hello")
    public String hello(String name) {
        System.out.println("-------------");
        Users attribute = (Users)request.getAttribute("traceLog");
        System.out.println(attribute.toString());
        return "Hello " + attribute + "!";
    }
}