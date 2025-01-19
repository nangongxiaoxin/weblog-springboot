package com.slilio.weblog.admin.event.subscriber;

import com.slilio.weblog.admin.event.ReadArticleEvent;
import com.slilio.weblog.common.domain.mapper.ArticleMapper;
import com.slilio.weblog.common.domain.mapper.StatisticsArticlePVMapper;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/** 订阅者 消费者 */
@Component
@Slf4j
public class ReadArticleSubscriber implements ApplicationListener<ReadArticleEvent> {
  @Autowired private ArticleMapper articleMapper;
  @Autowired private StatisticsArticlePVMapper articlePVMapper;

  @Override
  @Async("threadPoolTaskExecutor")
  public void onApplicationEvent(ReadArticleEvent event) {
    // 在此处理收到的事件，可以是任何逻辑
    Long articleId = event.getArticleId();

    // 当前线程的名字
    String threadName = Thread.currentThread().getName();

    log.info("==> threadName: {}", threadName);
    log.info("==> 文章阅读事件消费成功，articleId: {}", articleId);

    // 执行文章阅读量+1
    articleMapper.increaseReadNum(articleId);
    log.info("==> 文章阅读量 +1 操作成功，articleId: {}", articleId);

    // 执行当日文章PV访问量+1
    LocalDate currDate = LocalDate.now();
    articlePVMapper.increasePVCount(currDate);
    log.info("==> 当日文章 PV 访问量 +1 操作成功，date: {}", currDate);
  }
}
