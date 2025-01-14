package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slilio.weblog.common.domain.dos.ArticleDO;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface ArticleMapper extends BaseMapper<ArticleDO> {

  /**
   * 文章分页查询
   *
   * @param current
   * @param size
   * @param title
   * @param startDate
   * @param endDate
   * @return
   */
  default Page<ArticleDO> selectPageList(
      Long current, Long size, String title, LocalDate startDate, LocalDate endDate) {
    // 分页插叙（查询第几页，每页多少数据
    Page<ArticleDO> page = new Page<>(current, size);

    // 构建查询条件
    LambdaQueryWrapper<ArticleDO> wrapper =
        Wrappers.<ArticleDO>lambdaQuery()
            .like(StringUtils.isNotBlank(title), ArticleDO::getTitle, title)
            .ge(Objects.nonNull(startDate), ArticleDO::getCreateTime, startDate)
            .le(Objects.nonNull(endDate), ArticleDO::getCreateTime, endDate)
            .orderByDesc(ArticleDO::getCreateTime);

    return selectPage(page, wrapper);
  }

  /**
   * 根据文章id分页查询
   *
   * @param current
   * @param size
   * @param articleIds
   * @return
   */
  default Page<ArticleDO> selectPageListByArticleIds(
      Long current, Long size, List<Long> articleIds) {
    // 分页查询
    Page<ArticleDO> page = new Page<>(current, size);

    // 构建查询条件
    LambdaQueryWrapper<ArticleDO> wrapper =
        Wrappers.<ArticleDO>lambdaQuery()
            .in(ArticleDO::getId, articleIds)
            .orderByDesc(ArticleDO::getCreateTime);

    return selectPage(page, wrapper);
  }

  /**
   * 查询上一篇文章
   *
   * @param articleId
   * @return
   */
  default ArticleDO selectPreArticle(Long articleId) {
    return selectOne(
        Wrappers.<ArticleDO>lambdaQuery()
            .orderByAsc(ArticleDO::getId) // 按文章ID升序排列
            .gt(ArticleDO::getId, articleId) // 查询比当前文章ID大的
            .last("limit 1")); // 第一条记录即为上一篇文章
  }

  /**
   * 查询下一篇文章
   *
   * @param articleId
   * @return
   */
  default ArticleDO selectNextArticle(Long articleId) {
    return selectOne(
        Wrappers.<ArticleDO>lambdaQuery()
            .orderByDesc(ArticleDO::getId) // 按文章 ID 倒序排列
            .lt(ArticleDO::getId, articleId) // 查询比当前文章 ID 小的
            .last("limit 1")); // 第一条记录即为下一篇文章
  }
}
