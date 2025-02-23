package com.slilio.weblog.web.event.subscriber;

import com.slilio.weblog.common.domain.dos.BlogSettingsDO;
import com.slilio.weblog.common.domain.dos.CommentDO;
import com.slilio.weblog.common.domain.mapper.BlogSettingsMapper;
import com.slilio.weblog.common.domain.mapper.CommentMapper;
import com.slilio.weblog.common.enums.CommentStatusEnum;
import com.slilio.weblog.common.mail.MailHelper;
import com.slilio.weblog.web.event.PublishCommentEvent;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PublishCommentSubscriber implements ApplicationListener<PublishCommentEvent> {
  @Autowired private CommentMapper commentMapper;
  @Autowired private BlogSettingsMapper blogSettingsMapper;
  @Autowired private MailHelper mailHelper;

  @Override
  public void onApplicationEvent(PublishCommentEvent event) {
    // 在这里处理收到的事件，可以是任何操作逻辑
    Long commentId = event.getCommentId();

    // 获取当前线程的名称
    String threadName = Thread.currentThread().getName();

    log.info("===》 threadName:{}", threadName);
    log.info("===》 评论发布事件消费成功，commentId：{}", commentId);

    // 获取评论的相关信息
    CommentDO commentDO = commentMapper.selectById(commentId);
    Long replyCommentId = commentDO.getReplyCommentId();
    String nickname = commentDO.getNickname();
    String content = commentDO.getContent();
    Integer status = commentDO.getStatus();

    // 获取博客设置的相关信息
    BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
    // 博客名称
    String blogName = blogSettingsDO.getName();
    // 博主邮箱
    String authorMail = blogSettingsDO.getMail();
    // 是否需要审核
    Boolean isCommentExamineOpen = blogSettingsDO.getIsCommentExamineOpen();
    // 是否开启了敏感词过滤
    boolean isSensiWordOpen = blogSettingsDO.getIsCommentSensiWordOpen();
    // 博客访问地址
    String domain = blogSettingsDO.getDomain();

    // 二级评论，并且状态为‘正常’，邮件通知被评论的用户
    if (Objects.nonNull(replyCommentId)
        && Objects.equals(status, CommentStatusEnum.NORMAL.getCode())) {
      CommentDO replyCommentDO = commentMapper.selectById(replyCommentId);

      String to = replyCommentDO.getMail();
      String routerUrl = replyCommentDO.getRouterUrl();
      String title = String.format("你在%s的评论收到了回复", blogName);

      // 构建Html
      String html =
          String.format(
              "<html><body>"
                  + "<h2>你的评论:</h2><p>%s</p>"
                  + "<h2>%s 回复了你:</h2><p>%s</p>"
                  + "<p><a href='%s%s' target='_blank'>查看详情</a></p>"
                  + "</body></html>",
              replyCommentDO.getContent(), nickname, content, domain, routerUrl);
      // 发送邮件
      mailHelper.sendHtml(to, title, html);
    } else if (Objects.isNull(replyCommentId)
        && StringUtils.isNotBlank(authorMail)) { // 一级评论，需要通知到博主
      String routerUrl = commentDO.getRouterUrl();
      String title = String.format("%s收到了评论", blogName);

      // 如果开启了评论审核，当前的状态为 待审核，标题后面加上 待审核 标识
      if (isCommentExamineOpen
          && Objects.equals(status, CommentStatusEnum.WAIT_EXAMINE.getCode())) {
        title = title + "【待审核】";
      }

      // 如果开启了敏感词过滤，并且当前评论状态为 ‘审核不通过’，标题后缀加上【系统已拦截】标识
      if (isSensiWordOpen && Objects.equals(status, CommentStatusEnum.EXAMINE_FAILED.getCode())) {
        title = title + "【系统已拦截】";
      }

      // 构建Html
      String html =
          String.format(
              "<html><body>"
                  + "<h2>路由:</h2><p>%s</p>"
                  + "<h2>%s 评论了你:</h2><p>%s</p>"
                  + "<p><a href='%s%s' target='_blank'>查看详情</a></p>"
                  + "</body></html>",
              routerUrl, nickname, commentDO.getContent(), domain, routerUrl);
      // 发送邮件
      mailHelper.sendHtml(authorMail, title, html);
      log.info("邮件已发送");
    }
  }
}
