package com.wsz.mywiki.controller;

import com.wsz.mywiki.domain.Demo;
import com.wsz.mywiki.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//返回字符串
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    @GetMapping("/list1")
    public List<Demo> list() {
        return demoService.list();
    }
}
