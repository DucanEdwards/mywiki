package com.wsz.mywiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsz.mywiki.domain.Content;
import com.wsz.mywiki.domain.Doc;
import com.wsz.mywiki.domain.DocExample;
import com.wsz.mywiki.mapper.ContentMapper;
import com.wsz.mywiki.mapper.DocMapper;
import com.wsz.mywiki.req.DocQueryReq;
import com.wsz.mywiki.req.DocSaveReq;
import com.wsz.mywiki.resp.DocQueryResp;
import com.wsz.mywiki.resp.PageResp;
import com.wsz.mywiki.util.CopyUtil;
import com.wsz.mywiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {
    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private ContentMapper contentMapper;

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    public List<DocQueryResp> all() {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        return list;
    }

    public PageResp<DocQueryResp> list(DocQueryReq req) {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
//        List<DocResp> docRespList=new ArrayList<>();
//        for (Doc doc : docList) {
////            DocResp docResp=new DocResp();
////            BeanUtils.copyProperties(doc,docResp);
//            // 复制一个对象
//            DocResp docResp = CopyUtil.copy(doc, DocResp.class);
//            docRespList.add(docResp);
//        }

        List<DocQueryResp> docRespList = CopyUtil.copyList(docList, DocQueryResp.class);

        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(docRespList);
        return pageResp;
    }

    /**
     * 保存数据
     */
    public void save(DocSaveReq req){
        Doc doc=CopyUtil.copy(req,Doc.class);
        Content content=CopyUtil.copy(req,Content.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);

            content.setId(doc.getId());
            contentMapper.insert(content);
        }
        else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
            int count=contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count==0) {
                contentMapper.insert(content);
            }
        }
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<String> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }

    public String findContent(Long id) {
        Content content=contentMapper.selectByPrimaryKey(id);
        return content.getContent();
    }



}
