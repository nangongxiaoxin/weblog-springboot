package com.slilio.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_statistics_article_pv")
public class StatisticsArticlePVDO {

  @TableId(type = IdType.AUTO)
  private Long id;

  private LocalDate pvDate;

  private Long pvCount;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;
}
