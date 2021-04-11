package com.wsz.mywiki.controller;

import com.wsz.mywiki.domain.Ebook;
import com.wsz.mywiki.resp.CommonResp;
import com.wsz.mywiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//返回字符串
@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list1")
    public CommonResp list() {
        CommonResp<List<Ebook>> resp = new CommonResp<>();
        List<Ebook> list=ebookService.list();
        resp.setContent(list);
        return resp;
    }
}
