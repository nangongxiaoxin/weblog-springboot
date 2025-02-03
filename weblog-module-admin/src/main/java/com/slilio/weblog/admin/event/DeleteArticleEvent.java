package com.slilio.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DeleteArticleEvent extends ApplicationEvent {

  private Long articleId; // 文章ID

  public DeleteArticleEvent(Object source, Long articleId) {
    super(source);
    this.articleId = articleId;
  }
}
