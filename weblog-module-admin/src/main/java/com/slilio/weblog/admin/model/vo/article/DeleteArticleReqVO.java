package com.slilio.weblog.admin.model.vo.article;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "删除文章 VO")
public class DeleteArticleReqVO {
  @NotNull(message = "文章ID不能为空")
  private Long id;
}
