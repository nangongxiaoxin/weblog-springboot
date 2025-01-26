package com.slilio.weblog.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "lucene")
@Component
@Data
public class LuceneProperties {
  // 存放索引的文件夹
  private String indexDir;
}
