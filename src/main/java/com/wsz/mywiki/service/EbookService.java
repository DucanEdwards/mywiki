package com.wsz.mywiki.service;

import com.wsz.mywiki.domain.Ebook;
import com.wsz.mywiki.domain.EbookExample;
import com.wsz.mywiki.mapper.EbookMapper;
import com.wsz.mywiki.req.EbookReq;
import com.wsz.mywiki.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%"+req.getName()+"%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> ebookRespList=new ArrayList<>();
        for (Ebook ebook : ebookList) {
            EbookResp ebookResp=new EbookResp();
            BeanUtils.copyProperties(ebook,ebookResp);
            ebookRespList.add(ebookResp);
        }
        return ebookRespList;
    }

}
