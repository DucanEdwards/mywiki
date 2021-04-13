package com.wsz.mywiki.controller;

import com.wsz.mywiki.req.EbookQueryReq;
import com.wsz.mywiki.req.EbookSaveReq;
import com.wsz.mywiki.resp.CommonResp;
import com.wsz.mywiki.resp.EbookQueryResp;
import com.wsz.mywiki.resp.PageResp;
import com.wsz.mywiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

//返回字符串
@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list=ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp list(@RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }
}
