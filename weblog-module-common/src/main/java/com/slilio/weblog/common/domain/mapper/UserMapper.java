package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.slilio.weblog.common.domain.dos.UserDO;

public interface UserMapper extends BaseMapper<UserDO> {
    default UserDO findByUsername(String username){
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername,username);
        return selectOne(wrapper);
    }
}
