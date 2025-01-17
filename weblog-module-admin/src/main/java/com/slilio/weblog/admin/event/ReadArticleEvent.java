package com.slilio.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

// 自定义文章被阅读事件
@Getter
public class ReadArticleEvent extends ApplicationEvent {
  /** 文章ID */
  private Long articleId;

  public ReadArticleEvent(Object source, Long articleId) {
    super(source);
    this.articleId = articleId;
  }
}
