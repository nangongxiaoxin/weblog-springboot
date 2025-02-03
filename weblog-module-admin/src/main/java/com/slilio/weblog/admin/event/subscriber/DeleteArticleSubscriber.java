package com.slilio.weblog.admin.event.subscriber;

import com.slilio.weblog.admin.event.DeleteArticleEvent;
import com.slilio.weblog.search.LuceneHelper;
import com.slilio.weblog.search.index.ArticleIndex;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeleteArticleSubscriber implements ApplicationListener<DeleteArticleEvent> {
  @Autowired private LuceneHelper luceneHelper;

  @Override
  @Async("threadPoolTaskExecutor")
  public void onApplicationEvent(DeleteArticleEvent event) {
    // 在这里处理收到的事件，可以是任何逻辑操作
    Long articleId = event.getArticleId();

    // 获取线程名字
    String threadName = Thread.currentThread().getName();

    log.info("==> threadName: {}", threadName);
    log.info("==> 文章删除事件消费成功，articleId: {}", articleId);

    // 删除事件
    Term condition = new Term(ArticleIndex.COLUMN_ID, String.valueOf(articleId));

    long count = luceneHelper.deleteDocument(ArticleIndex.NAME, condition);

    log.info("==> 删除文章对应 Lucene 文档结束，articleId: {}，受影响行数: {}", articleId, count);
  }
}
