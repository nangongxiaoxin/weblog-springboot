package com.slilio.weblog.web.model.vo.search;

import com.slilio.weblog.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "文章搜索 VO")
public class SearchArticlePageListReqVO extends BasePageQuery {
  /** 搜索关键词 */
  @NotBlank(message = "搜索关键词不能为空")
  private String word;
}
