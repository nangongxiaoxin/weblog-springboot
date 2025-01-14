package com.slilio.weblog.web.model.vo.article;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiOperation(value = "查询文章详情 VO")
public class FindArticleDetailReqVO {
  /** 文章ID */
  private Long articleId;
}
