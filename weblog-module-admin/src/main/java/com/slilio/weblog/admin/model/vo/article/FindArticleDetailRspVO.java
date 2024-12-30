package com.slilio.weblog.admin.model.vo.article;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindArticleDetailRspVO {
  private Long id; // 文章ID
  private String title; // 文章标题
  private String cover; // 文章封面
  private String content; // 文章内容
  private Long categoryId; // 分类ID
  private List<Long> tagIds; // 标签id集合
  private String summary; // 摘要
}
