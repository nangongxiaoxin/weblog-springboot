package com.slilio.weblog.admin.model.vo.wiki;

import com.slilio.weblog.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询知识库分页数据入参 VO")
public class FindWikiPageListReqVO extends BasePageQuery {
  private String title; // 知识库标题
  private LocalDate startDate; // 发布的起始日期
  private LocalDate endDate; // 发布的结束日期
}
