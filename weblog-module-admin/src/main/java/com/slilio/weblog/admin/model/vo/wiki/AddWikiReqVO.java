package com.slilio.weblog.admin.model.vo.wiki;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "新增知识库VO")
public class AddWikiReqVO {
  @NotBlank(message = "知识库标题不能为空")
  @Length(min = 1, max = 20, message = "知识库标题字数需要大于1小于20")
  private String title;

  @NotBlank(message = "知识库摘要不能为空")
  private String summary;

  @NotBlank(message = "知识库封面不能为空")
  private String cover;
}
