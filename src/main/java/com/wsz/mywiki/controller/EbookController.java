package com.wsz.mywiki.controller;

import com.wsz.mywiki.req.EbookReq;
import com.wsz.mywiki.resp.CommonResp;
import com.wsz.mywiki.resp.EbookResp;
import com.wsz.mywiki.resp.PageResp;
import com.wsz.mywiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//返回字符串
@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req) {
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        PageResp<EbookResp> list=ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
