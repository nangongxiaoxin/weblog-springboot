package com.slilio.weblog.admin.model.vo.comment;

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
@ApiModel(value = "查询评论分页数据入参VO")
public class FindCommentPageListReqVO extends BasePageQuery {
  private String routerUrl; // 路由地址
  private LocalDate startDate; // 发布的起始日期
  private LocalDate endDate; // 发布的结束日期
  private Integer status; // 状态
}
