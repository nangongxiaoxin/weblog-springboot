package com.slilio.weblog.admin.model.vo.article;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "查询文章详情入参 VO")
public class FindArticleDetailReqVO {
  @NotNull(message = "文章 ID 不能为空")
  private Long id; // 文章ID
}
