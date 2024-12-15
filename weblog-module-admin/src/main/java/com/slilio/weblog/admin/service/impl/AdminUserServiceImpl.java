package com.slilio.weblog.admin.service.impl;

import com.slilio.weblog.admin.model.vo.user.FindUserInfoRspVO;
import com.slilio.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.slilio.weblog.admin.service.AdminUserService;
import com.slilio.weblog.common.domain.mapper.UserMapper;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 修改密码
     * @param updateAdminUserPasswordReqVO
     * @return
     */
    @Override
    public Response updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO){
        //获取用户名和密码
        String username = updateAdminUserPasswordReqVO.getUsername();
        String password = updateAdminUserPasswordReqVO.getPassword();

        //加密密码
        String encodePassword = passwordEncoder.encode(password);

        //更新到数据库
        int count = userMapper.updatePasswordByUsername(username, encodePassword);
        //如果受影响条数为1，证明成功，否则就是失败，用户不存在
        return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.USERNAME_NOT_FOUNT);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @Override
    public Response findUserInfo() {
        //获取存储在ThreadLocal中的用户信息 TokenAuthenticationFilter实现
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //拿到用户名
        String username = authentication.getName();

        return Response.success(FindUserInfoRspVO.builder().username(username).build());
    }
}
