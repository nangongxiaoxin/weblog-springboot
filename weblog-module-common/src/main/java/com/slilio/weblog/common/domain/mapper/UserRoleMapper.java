package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.slilio.weblog.common.domain.dos.UserDO;
import com.slilio.weblog.common.domain.dos.UserRoleDO;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    default List<UserRoleDO> selectByUsername(String username){
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        //构建sql查询条件
        wrapper.eq(UserRoleDO::getUsername,username);

        return selectList(wrapper);
    }

    /**
     * 根据用户名修改密码
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
