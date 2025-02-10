package com.slilio.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_wiki")
public class WikiDo {

  @TableId(type = IdType.AUTO)
  private Long id;

  private String title;
  private String cover;
  private String summary;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Boolean isDeleted;
  private Integer weight;
  private Boolean isPublish;
}
