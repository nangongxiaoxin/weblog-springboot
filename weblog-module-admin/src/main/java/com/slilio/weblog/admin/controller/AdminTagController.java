package com.slilio.weblog.admin.controller;

import com.slilio.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.slilio.weblog.admin.model.vo.tag.AddTagReqVO;
import com.slilio.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.slilio.weblog.admin.service.AdminTagService;
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


/**
 * 标签
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 标签模块")
public class AdminTagController {

    @Autowired
    private AdminTagService adminTagService;

    /**
     * 添加分类
     * @param addTagReqVO
     * @return
     */
    @PostMapping("/tag/add")
    @ApiOperation(value = "添加标签")
    @ApiOperationLog(description = "添加标签")
    public Response addCategory(@RequestBody @Validated AddTagReqVO addTagReqVO){
        return adminTagService.addTag(addTagReqVO);
    }


    /**
     * 标签分页数据获取
     * @param findTagPageListReqVO
     * @return
     */
    @PostMapping("/tag/list")
    @ApiOperation(value = "标签分页数据获取")
    @ApiOperationLog(description = "标签分页数据获取")
    public PageResponse findTagPageList(@RequestBody @Validated FindTagPageListReqVO findTagPageListReqVO) {
        return adminTagService.findTagList(findTagPageListReqVO);
    }
//
//    /**
//     * 删除分类
//     * @param deleteCategoryReqVO
//     * @return
//     */
//    @PostMapping("/category/delete")
//    @ApiOperation(value = "删除分类")
//    @ApiOperationLog(description = "删除分类")
//    public Response deleteCategory(@RequestBody @Validated DeleteCategoryReqVO deleteCategoryReqVO){
//        return adminCategoryService.deleteCategory(deleteCategoryReqVO);
//    }
//
//    /**
//     * 分类 Select 下拉列表数据获取
//     * @return
//     */
//    @PostMapping("/category/select/list")
//    @ApiOperation(value = "分类 Select 下拉列表数据获取")
//    @ApiOperationLog(description = "分类 Select 下拉列表数据获取")
//    public Response findCategorySelectList(){
//       return adminCategoryService.findCategorySelectList();
//    }


}
