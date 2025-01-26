package com.slilio.weblog.search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LuceneHelper {
  public void createIndex(String indexDir, List<Document> documents) {
    try {
      File dir = new File(indexDir);
      // 判断索引目录是否存在
      if (!dir.exists()) {
        // 删除目录里的内容
        FileUtils.cleanDirectory(dir);
      } else {
        // 如不存在就创建目录
        FileUtils.forceMkdir(dir);
      }

      // 读取索引目录
      Directory directory = FSDirectory.open(Paths.get(indexDir));

      // 中文分析器
      Analyzer analyzer = new SmartChineseAnalyzer();
      IndexWriterConfig config = new IndexWriterConfig(analyzer);
      // 创建索引
      IndexWriter writer = new IndexWriter(directory, config);

      // 添加文档
      documents.forEach(
          document -> {
            try {
              writer.addDocument(document);
            } catch (IOException e) {
              log.error("添加 Lucene 文档错误: ", e);
            }
          });

      // 提交
      writer.commit();
      writer.close();
    } catch (Exception e) {
      log.error("创建 Lucene 索引失败: ", e);
    }
  }
}
