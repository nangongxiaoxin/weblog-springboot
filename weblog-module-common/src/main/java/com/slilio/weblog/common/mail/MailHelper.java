package com.slilio.weblog.common.mail;

import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailHelper {
  @Autowired private MailProperties mailProperties;
  @Autowired private JavaMailSender javaMailSender;

  public boolean sendHtml(String to, String title, String html) {
    log.info("=====》开始发送邮件...");
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    MimeMessageHelper mimeMessageHelper = null;
    try {
      mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
      // 邮件发送来源
      mimeMessageHelper.setFrom(mailProperties.getUsername());
      // 邮件发送目标
      mimeMessageHelper.setTo(to);
      // 设置标题
      mimeMessageHelper.setSubject(title);
      // 设置内容，内容是否为html，值为true
      mimeMessageHelper.setText(html, true);

      javaMailSender.send(mimeMessage);
      log.info("=====》邮件发送成功，to：{}，title:{}，content:{}", to, title, html);

    } catch (Exception e) {
      log.error("=====》邮件发送异常，e");
      return false;
    }
    return true;
  }
}
