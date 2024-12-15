package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.slilio.weblog.common.domain.dos.UserDO;

import java.time.LocalDateTime;

/**
 * 用户信息查询
 */
public interface UserMapper extends BaseMapper<UserDO> {
    default UserDO findByUsername(String username){
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        //构建sql查询
        wrapper.eq(UserDO::getUsername,username);
        return selectOne(wrapper);
    }

    /**
     * 更新用户密码
     * @param username
     * @param password
     * @return
     */
    default int updatePasswordByUsername(String username, String password){
        LambdaUpdateWrapper<UserDO> wrapper = new LambdaUpdateWrapper<>();
//        设置需要更新的字段
        wrapper.set(UserDO::getPassword, password);
        wrapper.set(UserDO::getUpdateTime, LocalDateTime.now());
//        更新条件
        wrapper.eq(UserDO::getUsername, username);

        return update(null, wrapper);
    }
}
