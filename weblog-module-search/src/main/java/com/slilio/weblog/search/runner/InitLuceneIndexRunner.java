package com.slilio.weblog.search.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitLuceneIndexRunner implements CommandLineRunner {
  @Override
  public void run(String... args) throws Exception {
    log.info("==> 开始初始化 Lucene 索引...");
  }
}
