package com.slilio.weblog.admin.model.vo.wiki;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindWikiPageListRspVO {
  private Long id; // 知识库ID
  private String title; // 知识库标题
  private String cover; // 知识库封面
  private String summary; // 摘要
  private LocalDateTime createTime; // 发布时间
  private Boolean isTop; // 是否置顶
  private Boolean isPublish; // 是否发布
}
