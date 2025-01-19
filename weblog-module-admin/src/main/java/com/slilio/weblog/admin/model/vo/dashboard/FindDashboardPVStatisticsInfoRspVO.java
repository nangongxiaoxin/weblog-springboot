package com.slilio.weblog.admin.model.vo.dashboard;

import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "查询仪表盘文章 PV 访问量信息入参 VO")
public class FindDashboardPVStatisticsInfoRspVO {
  private List<String> pvDates; // 日期集合
  private List<Long> pvCounts; // pv浏览量集合
}
