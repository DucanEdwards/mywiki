package com.wsz.mywiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsz.mywiki.domain.Category;
import com.wsz.mywiki.domain.CategoryExample;
import com.wsz.mywiki.mapper.CategoryMapper;
import com.wsz.mywiki.req.CategoryQueryReq;
import com.wsz.mywiki.req.CategorySaveReq;
import com.wsz.mywiki.resp.CategoryQueryResp;
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
public class CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
//        List<CategoryResp> categoryRespList=new ArrayList<>();
//        for (Category category : categoryList) {
////            CategoryResp categoryResp=new CategoryResp();
////            BeanUtils.copyProperties(category,categoryResp);
//            // 复制一个对象
//            CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
//            categoryRespList.add(categoryResp);
//        }

        List<CategoryQueryResp> categoryRespList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(categoryRespList);
        return pageResp;
    }

    /**
     * 保存数据
     */
    public void save(CategorySaveReq req){
        Category category=CopyUtil.copy(req,Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        }
        else {
            // 更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }


}
