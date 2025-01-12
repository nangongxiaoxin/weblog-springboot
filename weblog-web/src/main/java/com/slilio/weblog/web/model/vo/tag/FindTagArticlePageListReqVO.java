package com.slilio.weblog.web.model.vo.tag;

import com.slilio.weblog.common.model.BasePageQuery;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindTagArticlePageListReqVO extends BasePageQuery {
  /** 标签ID */
  @NotNull(message = "标签ID不能为空")
  private Long id;
}
