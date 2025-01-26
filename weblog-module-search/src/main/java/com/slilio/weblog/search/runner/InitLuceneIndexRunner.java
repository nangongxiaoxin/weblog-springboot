package com.slilio.weblog.search.runner;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.slilio.weblog.common.constant.Constants;
import com.slilio.weblog.common.domain.dos.ArticleContentDO;
import com.slilio.weblog.common.domain.dos.ArticleDO;
import com.slilio.weblog.common.domain.mapper.ArticleContentMapper;
import com.slilio.weblog.common.domain.mapper.ArticleMapper;
import com.slilio.weblog.search.LuceneHelper;
import com.slilio.weblog.search.config.LuceneProperties;
import com.slilio.weblog.search.index.ArticleIndex;
import java.io.File;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitLuceneIndexRunner implements CommandLineRunner {
  @Autowired private LuceneProperties luceneProperties;
  @Autowired private LuceneHelper luceneHelper;
  @Autowired private ArticleMapper articleMapper;
  @Autowired private ArticleContentMapper articleContentMapper;

  public InitLuceneIndexRunner(
      ArticleMapper articleMapper,
      LuceneProperties luceneProperties,
      ArticleContentMapper articleContentMapper) {
    this.articleMapper = articleMapper;
    this.luceneProperties = luceneProperties;
    this.articleContentMapper = articleContentMapper;
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("==> 开始初始化 Lucene 索引...");

    // 查询所有文章
    List<ArticleDO> articleDOS = articleMapper.selectList(Wrappers.emptyWrapper());

    // 未发布文章，则不创建lucene
    if (articleDOS.isEmpty()) {
      log.info("==> 未发布任何文章，暂不创建 Lucene 索引...");
      return;
    }

    // 若配置文件中未配置索引存放目录，日志提示错误信息
    if (StringUtils.isBlank(luceneProperties.getIndexDir())) {
      log.error("==> 未指定 Lucene 索引存放位置，需在 application.yml 文件中添加路径配置...");
      return;
    }

    // 文章索引存放目录，如 /app/weblog/lucene-index/article
    String articleIndexDir = luceneProperties.getIndexDir() + File.separator + ArticleIndex.NAME;

    List<Document> documents = Lists.newArrayList();
    articleDOS.forEach(
        articleDO -> {
          Long articleId = articleDO.getId();

          // 查询正文
          ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);
          // 构建文档
          Document document = new Document();
          // 设置文档字段Field
          document.add(
              new TextField(ArticleIndex.COLUMN_ID, String.valueOf(articleId), Field.Store.YES));
          document.add(
              new TextField(ArticleIndex.COLUMN_TITLE, articleDO.getTitle(), Field.Store.YES));
          document.add(
              new TextField(ArticleIndex.COLUMN_COVER, articleDO.getCover(), Field.Store.YES));
          document.add(
              new TextField(ArticleIndex.COLUMN_SUMMARY, articleDO.getSummary(), Field.Store.YES));
          document.add(
              new TextField(
                  ArticleIndex.COLUMN_CONTENT, articleContentDO.getContent(), Field.Store.YES));
          document.add(
              new TextField(
                  ArticleIndex.COLUMN_CREATE_TIME,
                  Constants.DATE_TIME_FORMATTER.format(articleDO.getCreateTime()),
                  Field.Store.YES));
          documents.add(document);
        });

    // 创建索引
    luceneHelper.createIndex(articleIndexDir, documents);

    log.info("==> 结束初始化 Lucene 索引...");
  }
}
