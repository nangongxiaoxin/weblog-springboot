package com.slilio.weblog.web.model.vo.wiki;

import com.slilio.weblog.web.model.vo.article.FindPreNextArticleRspVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindWikiArticlePreNextRspVO {
  private FindPreNextArticleRspVO preArticle; // 上一篇文章
  private FindPreNextArticleRspVO nextArticle; // 下一篇文章
}
