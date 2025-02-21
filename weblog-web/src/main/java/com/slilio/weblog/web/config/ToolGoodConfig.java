package com.slilio.weblog.web.config;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import toolgood.words.IllegalWordsSearch;

@Configuration
@Slf4j
public class ToolGoodConfig {

  @Bean
  public IllegalWordsSearch illegalWordsSearch(ResourceLoader resourceLoader) throws IOException {
    log.info("==> 开始初初始化敏感词工具类 ...");
    IllegalWordsSearch illegalWordsSearch = new IllegalWordsSearch();

    log.info("==> 加载敏感词文件 ...");
    // 读取resource文件夹下的敏感词txt文件
    List<String> sensitiveWords = Lists.newArrayList();
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(
                resourceLoader.getResource("classpath:word/sensi_words.txt").getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (StringUtils.isNotBlank(line.trim())) {
          sensitiveWords.add(line.trim());
        }
      }
    }

    // 设置敏感词
    illegalWordsSearch.SetKeywords(sensitiveWords);
    log.info("==> 初始化敏感词工具类成功 ...");
    return illegalWordsSearch;
  }
}
