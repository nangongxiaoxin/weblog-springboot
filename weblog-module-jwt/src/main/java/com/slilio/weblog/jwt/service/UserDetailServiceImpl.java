package com.slilio.weblog.jwt.service;

import com.slilio.weblog.common.domain.dos.UserDO;
import com.slilio.weblog.common.domain.dos.UserRoleDO;
import com.slilio.weblog.common.domain.mapper.UserMapper;
import com.slilio.weblog.common.domain.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 用户数据查询 包含账号、密码、角色等信息
     * todo 用户角色待完善
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
        //从数据库查询
        UserDO userDO = userMapper.findByUsername(username);
        //判断用户是否存在
        if(Objects.isNull(userDO)){
            throw new UsernameNotFoundException("该用户不存在");
        }

        //用户角色 调用mapper从数据库查询
        List<UserRoleDO> roleDOS = userRoleMapper.selectByUsername(username);

        //转数组
        String[] roleArr = null;
        if (!CollectionUtils.isEmpty(roleDOS)) {
            List<String> roles = roleDOS.stream().map(UserRoleDO::getRole).collect(Collectors.toList());
            roleArr = roles.toArray(new String[roles.size()]);
        }

        //authorities用于指定角色
        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities(roleArr)
                .build();
    }
}
