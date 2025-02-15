package com.slilio.weblog.admin.model.vo.article;

import com.slilio.weblog.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 查询文章分页数据入参VO */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询文章分页数据入参 VO")
public class FindArticlePageListReqVO extends BasePageQuery {
  private String title; // 文章标题
  private LocalDate startDate; // 发布的起始日期
  private LocalDate endDate; // 发布的结束日期
  private Integer type; //文章类型
}
