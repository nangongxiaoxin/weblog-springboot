package com.slilio.weblog.admin.controller;

import com.slilio.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.slilio.weblog.admin.service.AdminUserService;
import com.slilio.weblog.common.aspect.ApiOperationLog;
import com.slilio.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 用户模块")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 修改用户密码
     * @param updateAdminUserPasswordReqVO
     * @return
     */
    @PostMapping("/password/update")
    @ApiOperation(value = "修改用户密码")
    @ApiOperationLog(description = "修改用户密码")
    public Response updatePassword(@RequestBody @Validated UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO){
        return adminUserService.updatePassword(updateAdminUserPasswordReqVO);

    }

    @PostMapping("/admin/findUserInfo")
    @ApiOperation(value = "获取用户信息")
    @ApiOperationLog(description = "获取用户信息")
    public Response findUserInfo(){
        return adminUserService.findUserInfo();
    }
}
