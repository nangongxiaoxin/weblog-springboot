package com.slilio.weblog.web.controller;

import com.slilio.weblog.common.aspect.ApiOperationLog;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.model.vo.search.SearchArticlePageListReqVO;
import com.slilio.weblog.web.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@Api(tags = "搜索")
public class SearchController {
  @Autowired private SearchService searchService;

  @PostMapping("/search")
  @ApiOperation(value = "文章搜索")
  @ApiOperationLog(description = "文章搜索")
  public Response searchArticlePageList(
      @RequestBody @Validated SearchArticlePageListReqVO searchArticlePageListReqVO) {
    return searchService.searchArticlePageList(searchArticlePageListReqVO);
  }
}
