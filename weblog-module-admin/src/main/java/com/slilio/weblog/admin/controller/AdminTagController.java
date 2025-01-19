package com.slilio.weblog.admin.controller;

import com.slilio.weblog.admin.model.vo.tag.AddTagReqVO;
import com.slilio.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.slilio.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.slilio.weblog.admin.model.vo.tag.SearchTagReqVO;
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

/** 标签 */
@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 标签模块")
public class AdminTagController {

  @Autowired private AdminTagService adminTagService;

  /**
   * 添加分类
   *
   * @param addTagReqVO
   * @return
   */
  @PostMapping("/tag/add")
  @ApiOperation(value = "添加标签")
  @ApiOperationLog(description = "添加标签")
  public Response addCategory(@RequestBody @Validated AddTagReqVO addTagReqVO) {
    return adminTagService.addTag(addTagReqVO);
  }

  /**
   * 标签分页数据获取
   *
   * @param findTagPageListReqVO
   * @return
   */
  @PostMapping("/tag/list")
  @ApiOperation(value = "标签分页数据获取")
  @ApiOperationLog(description = "标签分页数据获取")
  public PageResponse findTagPageList(
      @RequestBody @Validated FindTagPageListReqVO findTagPageListReqVO) {
    return adminTagService.findTagList(findTagPageListReqVO);
  }

  /**
   * 删除标签
   *
   * @param deleteTagReqVO
   * @return
   */
  @PostMapping("/tag/delete")
  @ApiOperation(value = "删除标签")
  @ApiOperationLog(description = "删除标签")
  public Response deleteTag(@RequestBody @Validated DeleteTagReqVO deleteTagReqVO) {
    return adminTagService.deleteTag(deleteTagReqVO);
  }

  /**
   * 标签模糊查询
   *
   * @return
   */
  @PostMapping("/tag/search")
  @ApiOperation(value = "标签模糊查询")
  @ApiOperationLog(description = "标签模糊查询")
  public Response searchTag(@RequestBody @Validated SearchTagReqVO searchTagReqVO) {
    return adminTagService.searchTag(searchTagReqVO);
  }

  /**
   * 查询标签 Select 列表数据
   *
   * @return
   */
  @PostMapping("/tag/select/list")
  @ApiOperation(value = "查询标签 Select 列表数据")
  @ApiOperationLog(description = "查询标签 Select 列表数据")
  public Response findTagSelectList() {
    return adminTagService.findTagSelectList();
  }
}
