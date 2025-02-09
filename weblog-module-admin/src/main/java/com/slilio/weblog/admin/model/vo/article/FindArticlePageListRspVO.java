package com.slilio.weblog.admin.model.vo.article;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 查询文章分页数据出参VO */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArticlePageListRspVO {
  private Long id; // 文章ID
  private String title; // 文章标题
  private String cover; // 文章封面
  private LocalDateTime createTime; // 发布日期
  private Boolean isTop; // 是否置顶
}
