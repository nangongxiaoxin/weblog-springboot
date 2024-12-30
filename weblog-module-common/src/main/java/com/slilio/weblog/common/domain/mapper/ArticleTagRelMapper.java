package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.slilio.weblog.common.config.InsertBatchMapper;
import com.slilio.weblog.common.domain.dos.ArticleTagRelDO;
import java.util.List;

public interface ArticleTagRelMapper extends InsertBatchMapper<ArticleTagRelDO> {
  /**
   * 根据文章ID删除关联记录
   *
   * @param articleId
   * @return
   */
  default int deleteByArticleId(Long articleId) {
    return delete(
        Wrappers.<ArticleTagRelDO>lambdaQuery().eq(ArticleTagRelDO::getArticleId, articleId));
  }

  /**
   * 根据文章ID查询标签集合
   *
   * @param articleId
   * @return
   */
  default List<ArticleTagRelDO> selectByArticleId(Long articleId) {
    return selectList(
        Wrappers.<ArticleTagRelDO>lambdaQuery().eq(ArticleTagRelDO::getArticleId, articleId));
  }
}
