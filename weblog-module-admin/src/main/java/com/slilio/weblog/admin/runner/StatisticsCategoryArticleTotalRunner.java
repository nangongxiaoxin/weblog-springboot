package com.slilio.weblog.admin.runner;

import com.slilio.weblog.admin.service.AdminStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StatisticsCategoryArticleTotalRunner implements CommandLineRunner {
  @Autowired private AdminStatisticsService StatisticsService;

  @Override
  @Async("threadPoolTaskExecutor")
  public void run(String... args) throws Exception {
    log.info("==> 开始统计各分类下文章数量...");
    StatisticsService.statisticsCategoryArticleTotal();
    log.info("==> 结束统计各分类下文章数量...");
  }
}
