package com.slilio.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 分类DO */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_category")
public class CategoryDO {
  @TableId(type = IdType.AUTO)
  private Long id;

  private String name;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

  private Boolean isDeleted;

  private Integer articlesTotal;
}
