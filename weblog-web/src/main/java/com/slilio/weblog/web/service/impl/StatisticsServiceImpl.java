package com.slilio.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.slilio.weblog.common.domain.dos.ArticleDO;
import com.slilio.weblog.common.domain.mapper.ArticleMapper;
import com.slilio.weblog.common.domain.mapper.CategoryMapper;
import com.slilio.weblog.common.domain.mapper.TagMapper;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.model.vo.statistics.FindStatisticsInfoRspVO;
import com.slilio.weblog.web.service.StatisticsService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {
  @Autowired ArticleMapper articleMapper;
  @Autowired CategoryMapper categoryMapper;
  @Autowired TagMapper tagMapper;

  /**
   * 获取文章总数、分类总数、标签总数、总访问量统计信息
   *
   * @return
   */
  @Override
  public Response findInfo() {
    // 查询文章总数
    Long articleTotalCount = articleMapper.selectCount(Wrappers.emptyWrapper());
    // 查询分类总数
    Long categoryTotalCount = categoryMapper.selectCount(Wrappers.emptyWrapper());
    // 查询标签总数
    Long tagTotalCount = tagMapper.selectCount(Wrappers.emptyWrapper());
    // 总浏览量
    List<ArticleDO> articleDOS = articleMapper.selectAllReadNum();
    Long pvTotalCount = 0L;
    if (!CollectionUtils.isEmpty(articleDOS)) {
      pvTotalCount = articleDOS.stream().mapToLong(ArticleDO::getReadNum).sum();
    }

    // 组装VO
    FindStatisticsInfoRspVO vo =
        FindStatisticsInfoRspVO.builder()
            .articleTotalCount(articleTotalCount)
            .categoryTotalCount(categoryTotalCount)
            .tagTotalCount(tagTotalCount)
            .pvTotalCount(pvTotalCount)
            .build();

    return Response.success(vo);
  }
}
