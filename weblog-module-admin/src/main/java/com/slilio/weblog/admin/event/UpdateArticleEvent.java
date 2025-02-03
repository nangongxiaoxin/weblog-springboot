package com.slilio.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UpdateArticleEvent extends ApplicationEvent {
  private Long articleId; // 文章ID

  // 更新事件
  public UpdateArticleEvent(Object source, Long articleId) {
    super(source);
    this.articleId = articleId;
  }
}
