package com.wsz.mywiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//返回字符串
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/hello/post")
    public String hellopost(String name) {
        return "hello post "+name;
    }
}
