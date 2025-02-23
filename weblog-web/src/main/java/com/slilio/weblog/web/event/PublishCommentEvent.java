package com.slilio.weblog.web.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PublishCommentEvent extends ApplicationEvent {
  private Long commentId; // 评论ID

  // 评论发布事件
  public PublishCommentEvent(Object source, Long commentId) {
    super(source);
    this.commentId = commentId;
  }
}
