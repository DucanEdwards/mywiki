package com.wsz.mywiki.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//返回字符串
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
