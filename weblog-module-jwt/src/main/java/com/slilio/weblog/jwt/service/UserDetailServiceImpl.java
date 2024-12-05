package com.slilio.weblog.jwt.service;

import com.slilio.weblog.common.domain.dos.UserDO;
import com.slilio.weblog.common.domain.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
        //从数据库查询
        UserDO userDO = userMapper.findByUsername(username);
        //判断用户是否存在
        if(Objects.isNull(userDO)){
            throw new UsernameNotFoundException("该用户不存在");
        }
        //authorities用于指定角色，这里写死为ADMIN管理员
        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities("ADMIN")
                .build();
    }
}
