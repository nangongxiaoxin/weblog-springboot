package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.slilio.weblog.common.domain.dos.UserRoleDO;


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

}
