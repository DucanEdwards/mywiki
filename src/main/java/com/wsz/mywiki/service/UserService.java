package com.wsz.mywiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsz.mywiki.domain.User;
import com.wsz.mywiki.domain.UserExample;
import com.wsz.mywiki.exception.BusinessException;
import com.wsz.mywiki.exception.BusinessExceptionCode;
import com.wsz.mywiki.mapper.UserMapper;
import com.wsz.mywiki.req.UserLoginReq;
import com.wsz.mywiki.req.UserQueryReq;
import com.wsz.mywiki.req.UserResetPasswordReq;
import com.wsz.mywiki.req.UserSaveReq;
import com.wsz.mywiki.resp.PageResp;
import com.wsz.mywiki.resp.UserLoginResp;
import com.wsz.mywiki.resp.UserQueryResp;
import com.wsz.mywiki.util.CopyUtil;
import com.wsz.mywiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    public PageResp<UserQueryResp> list(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameEqualTo(req.getLoginName());
        }
        PageHelper.startPage(req.getPage(),req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
//        List<UserResp> userRespList=new ArrayList<>();
//        for (User user : userList) {
////            UserResp userResp=new UserResp();
////            BeanUtils.copyProperties(user,userResp);
//            // 复制一个对象
//            UserResp userResp = CopyUtil.copy(user, UserResp.class);
//            userRespList.add(userResp);
//        }

        List<UserQueryResp> userRespList = CopyUtil.copyList(userList, UserQueryResp.class);

        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(userRespList);
        return pageResp;
    }

    /**
     * 保存数据
     */
    public void save(UserSaveReq req){
        User user=CopyUtil.copy(req,User.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            User userDB = selectByLoginName(req.getLoginName());
            if (ObjectUtils.isEmpty(userDB)) {
                // 新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            }
            else {
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        }
        else {
            // 更新
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public User selectByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        else {
            return userList.get(0);
        }
    }

    /**
     * 修改密码
     */
    public void resetPassword(UserResetPasswordReq req){
        User user=CopyUtil.copy(req,User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 登录
     */
    public UserLoginResp login(UserLoginReq req){
        User userDb = selectByLoginName(req.getLoginName());
        if (ObjectUtils.isEmpty(userDb)) {
            //用户名不存在
            LOG.info("用户名不存在,{}",req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }
        else{
            if (userDb.getPassword().equals(req.getPassword())) {
                // 登录成功
                UserLoginResp userLoginResp=CopyUtil.copy(userDb,UserLoginResp.class);
                return userLoginResp;
            }
            else{
                // 密码不对
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", req.getPassword(), userDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }
}
