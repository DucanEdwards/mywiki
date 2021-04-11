package com.wsz.mywiki.controller;

import com.wsz.mywiki.domain.Test;
import com.wsz.mywiki.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//返回字符串
@RestController
public class TestController {
    @Value("${test.one:one}")
    private String one;

    @Resource
    private TestService testService;

    @GetMapping("/hello")
    public String hello() {
        return "hello"+one;
    }

    @PostMapping("/hello/post")
    public String hellopost(String name) {
        return "hello post "+name;
    }

    @GetMapping("/test/list")
    public List<Test> list() {
        return testService.list();
    }
}
