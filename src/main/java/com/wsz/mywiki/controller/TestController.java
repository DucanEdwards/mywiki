package com.wsz.mywiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//返回字符串
@RestController
public class TestController {
    @Value("${test.one:one}")
    private String one;

    @GetMapping("/hello")
    public String hello() {
        return "hello"+one;
    }

    @PostMapping("/hello/post")
    public String hellopost(String name) {
        return "hello post "+name;
    }
}
