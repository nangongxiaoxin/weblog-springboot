package com.slilio.weblog.admin.schedule;

import com.slilio.weblog.common.domain.dos.StatisticsArticlePVDO;
import com.slilio.weblog.common.domain.mapper.StatisticsArticlePVMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitPVRecordScheduledTask {
  @Autowired private StatisticsArticlePVMapper articlePVMapper;

  @Scheduled(cron = "0 0 23 * * ?")
  public void execute() {
    // 定时执行任务的业务逻辑
    log.info("==> 开始执行初始化明日 PV 访问量记录定时任务");

    // 当日日期
    LocalDate currDate = LocalDate.now();

    // 明日日期
    LocalDate tomorrowDate = currDate.plusDays(1);

    // 组装插入的记录
    StatisticsArticlePVDO articlePVDO =
        StatisticsArticlePVDO.builder()
            .pvDate(tomorrowDate) // 明日的文章pv访问数
            .pvCount(0L) // 默认阅读数为0
            .createTime(LocalDateTime.now())
            .updateTime(LocalDateTime.now())
            .build();
    articlePVMapper.insert(articlePVDO);
    log.info("==> 结束执行初始化明日 PV 访问量记录定时任务");
  }
}
