package com.slilio.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.slilio.weblog.admin.model.vo.dashboard.FindDashboardStatisticsInfoRspVO;
import com.slilio.weblog.admin.service.AdminDashboardService;
import com.slilio.weblog.common.domain.dos.ArticleDO;
import com.slilio.weblog.common.domain.dos.ArticlePublishCountDO;
import com.slilio.weblog.common.domain.mapper.ArticleMapper;
import com.slilio.weblog.common.domain.mapper.CategoryMapper;
import com.slilio.weblog.common.domain.mapper.TagMapper;
import com.slilio.weblog.common.utils.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminDashboardServiceImpl implements AdminDashboardService {
  @Autowired private ArticleMapper articleMapper;
  @Autowired private CategoryMapper categoryMapper;
  @Autowired private TagMapper tagMapper;

  /**
   * 获取仪表盘基础信息
   *
   * @return
   */
  @Override
  public Response findDashboardStatistics() {
    // 查询文章总数
    Long articleTotalCount = articleMapper.selectCount(Wrappers.lambdaQuery());
    // 查询分类总数
    Long categoryTotalCount = categoryMapper.selectCount(Wrappers.lambdaQuery());
    // 查询标签总数
    Long tagTotalCount = tagMapper.selectCount(Wrappers.lambdaQuery());
    // 总浏览数
    List<ArticleDO> articleDOS = articleMapper.selectAllReadNum();
    Long pvTotalCount = 0L;

    if (!CollectionUtils.isEmpty(articleDOS)) {
      // 所有read_num
      pvTotalCount = articleDOS.stream().mapToLong(ArticleDO::getReadNum).sum();
    }

    // 组装vo类
    FindDashboardStatisticsInfoRspVO vo =
        FindDashboardStatisticsInfoRspVO.builder()
            .articleTotalCount(articleTotalCount)
            .categoryTotalCount(categoryTotalCount)
            .tagTotalCount(tagTotalCount)
            .pvtTotalCount(pvTotalCount)
            .build();

    return Response.success(vo);
  }

  @Override
  public Response findDashBoardPublishArticleStatistics() {
    // 当前日期
    LocalDate currDate = LocalDate.now();
    // 当前日期倒退一年的日期
    LocalDate startDate = currDate.minusYears(1);

    // 查询这一年内，每天发布文章的数量
    List<ArticlePublishCountDO> articlePublishCountDOS =
        articleMapper.selectDateArticlePublishCount(startDate, currDate.plusDays(1));

    Map<LocalDate, Long> map = null;
    if (!CollectionUtils.isEmpty(articlePublishCountDOS)) {
      // do转map
      Map<LocalDate, Long> dateArticleCountMap =
          articlePublishCountDOS.stream()
              .collect(
                  Collectors.toMap(
                      ArticlePublishCountDO::getDate, ArticlePublishCountDO::getCount));
      // 有序map 以日期升序
      map = Maps.newLinkedHashMap();
      // 从上年的今天循环到今天
      for (;
          startDate.isBefore(currDate) || startDate.isEqual(currDate);
          startDate = startDate.plusDays(1)) {
        // 以日期作为key从dateArticleCountMap中获取文章发布数量
        Long count = dateArticleCountMap.get(startDate);
        // 设置返参到map
        map.put(startDate, Objects.isNull(count) ? 0 : count);
      }
    }

    return Response.success(map);
  }
}
