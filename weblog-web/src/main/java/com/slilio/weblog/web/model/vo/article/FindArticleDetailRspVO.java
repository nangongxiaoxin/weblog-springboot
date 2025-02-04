package com.slilio.weblog.web.model.vo.article;

import com.slilio.weblog.web.model.vo.tag.FindTagListRspVO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 文章返参 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArticleDetailRspVO {
  private String title; // 文章标题
  private String content; // 文章正文（HTML）
  private LocalDateTime createTime; // 发布时间
  private Long categoryId; // 分类ID
  private String categoryName; // 分类名称
  private Long readNum; // 阅读量
  private List<FindTagListRspVO> tags; // 标签集合
  private FindPreNextArticleRspVO preArticle; // 上一篇文章
  private FindPreNextArticleRspVO nextArticle; // 下一篇文章
  private Integer totalWords; // 总字数
  private String readTime; // 总时长
}
