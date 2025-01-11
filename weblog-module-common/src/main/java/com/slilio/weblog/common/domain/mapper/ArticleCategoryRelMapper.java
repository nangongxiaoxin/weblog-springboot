package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.slilio.weblog.common.domain.dos.ArticleCategoryRelDO;
import java.util.List;

public interface ArticleCategoryRelMapper extends BaseMapper<ArticleCategoryRelDO> {

  /** 根据文章ID删除关联记录 */
  default int deleteByArticleId(Long articleId) {
    return delete(
        Wrappers.<ArticleCategoryRelDO>lambdaQuery()
            .eq(ArticleCategoryRelDO::getArticleId, articleId));
  }

  /**
   * 根据文章ID查询分类
   *
   * @param articleId
   * @return
   */
  default ArticleCategoryRelDO selectByArticleId(Long articleId) {
    return selectOne(
        Wrappers.<ArticleCategoryRelDO>lambdaQuery()
            .eq(ArticleCategoryRelDO::getArticleId, articleId));
  }

  /**
   * 根据分类ID查询
   *
   * @param categoryId
   * @return
   */
  default ArticleCategoryRelDO selectOneByCategoryId(Long categoryId) {
    return selectOne(
        Wrappers.<ArticleCategoryRelDO>lambdaQuery()
            .eq(ArticleCategoryRelDO::getCategoryId, categoryId)
            .last("limit 1"));
  }

  /**
   * 根据文章ID集合批量查询
   *
   * @param articleIds
   * @return
   */
  default List<ArticleCategoryRelDO> selectByArticleIds(List<Long> articleIds) {
    return selectList(
        Wrappers.<ArticleCategoryRelDO>lambdaQuery()
            .in(ArticleCategoryRelDO::getArticleId, articleIds));
  }

  /**
   * 根据分类 ID 查询所有的关联记录
   *
   * @param categoryId
   * @return
   */
  default List<ArticleCategoryRelDO> selectListByCategoryId(Long categoryId) {
    return selectList(
        Wrappers.<ArticleCategoryRelDO>lambdaQuery()
            .eq(ArticleCategoryRelDO::getCategoryId, categoryId));
  }
}
