package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.slilio.weblog.common.domain.dos.ArticleCategoryRelDO;

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
}
