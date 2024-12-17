package com.slilio.weblog.admin.controller;

import com.slilio.weblog.admin.model.FindCategoryPageListReqVO;
import com.slilio.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.slilio.weblog.admin.service.AdminCategoryService;
import com.slilio.weblog.common.aspect.ApiOperationLog;
import com.slilio.weblog.common.utils.PageResponse;
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
@Api(tags = "Admin 分类模块")
public class AdminCategoryController {

    @Autowired
    private AdminCategoryService adminCategoryService;

    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    @PostMapping("/category/add")
    @ApiOperation(value = "添加分类")
    @ApiOperationLog(description = "添加分类")
    public Response addCategory(@RequestBody @Validated AddCategoryReqVO addCategoryReqVO){
        return adminCategoryService.addCategory(addCategoryReqVO);
    }

    /**
     * 分类分页数据获取
     * @param findCategoryPageListReqVO
     * @return
     */
    @PostMapping("/category/list")
    @ApiOperation(value = "分类分页数据获取")
    @ApiOperationLog(description = "分类分页数据获取")
    public PageResponse findCategoryList(@RequestBody @Validated FindCategoryPageListReqVO findCategoryPageListReqVO) {
        return adminCategoryService.findCategoryList(findCategoryPageListReqVO);
    }

}
