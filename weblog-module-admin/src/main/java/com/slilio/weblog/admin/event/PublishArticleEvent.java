package com.slilio.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PublishArticleEvent extends ApplicationEvent {
  private Long articleId; // 文章ID

  public PublishArticleEvent(Object source, Long articleId) {
    super(source);
    this.articleId = articleId;
  }
}
