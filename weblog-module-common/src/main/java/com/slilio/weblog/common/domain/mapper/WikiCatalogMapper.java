package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.slilio.weblog.common.config.InsertBatchMapper;
import com.slilio.weblog.common.domain.dos.WikiCatalogDO;
import com.slilio.weblog.common.enums.WikiCatalogLevelEnum;
import java.util.List;

public interface WikiCatalogMapper extends InsertBatchMapper<WikiCatalogDO> {

  /**
   * 根据id查询知识库下的所有目录
   *
   * @param wikiId
   * @return
   */
  default List<WikiCatalogDO> selectByWikiId(Long wikiId) {
    return selectList(Wrappers.<WikiCatalogDO>lambdaQuery().eq(WikiCatalogDO::getWikiId, wikiId));
  }

  /**
   * 根据ID删除知识库
   *
   * @param wikiId
   * @return
   */
  default int deleteByWikiId(Long wikiId) {
    return delete(Wrappers.<WikiCatalogDO>lambdaQuery().eq(WikiCatalogDO::getWikiId, wikiId));
  }

  /**
   * 查询知识库中的第一篇文章
   *
   * @param wikiId
   * @return
   */
  default WikiCatalogDO selectFirstArticleId(Long wikiId) {
    return selectOne(
        Wrappers.<WikiCatalogDO>lambdaQuery()
            .eq(WikiCatalogDO::getWikiId, wikiId) // 指定知识库ID
            .eq(WikiCatalogDO::getLevel, WikiCatalogLevelEnum.TWO.getValue()) // 查询二级目录
            .isNotNull(WikiCatalogDO::getArticleId) // article_id不能为空
            .orderByAsc(WikiCatalogDO::getId) // 按照id升序排列
            .last("limit 1"));
  }

  /**
   * 根据知识库Id和文章Id查询对应的目录
   *
   * @param wikiId
   * @param articleId
   * @return
   */
  default WikiCatalogDO selectByWikiIdAndArticleId(Long wikiId, Long articleId) {
    return selectOne(
        Wrappers.<WikiCatalogDO>lambdaQuery()
            .eq(WikiCatalogDO::getWikiId, wikiId)
            .eq(WikiCatalogDO::getArticleId, articleId));
  }

  /**
   * 查询下一篇文章
   *
   * @param wikiId
   * @param catalogId
   * @return
   */
  default WikiCatalogDO selectNextArticle(Long wikiId, Long catalogId) {
    return selectOne(
        Wrappers.<WikiCatalogDO>lambdaQuery()
            .eq(WikiCatalogDO::getWikiId, wikiId)
            .isNotNull(WikiCatalogDO::getArticleId) // articleId不为空
            .orderByAsc(WikiCatalogDO::getId) // 按目录Id顺序排列
            .gt(WikiCatalogDO::getId, catalogId) // 查询比当前文章Id大的
            .last("limit 1") // 第一条记录即为下一篇文章
        );
  }

  /**
   * 查询上一篇文章
   *
   * @param wikiId
   * @param catalogId
   * @return
   */
  default WikiCatalogDO selectPreArticle(Long wikiId, Long catalogId) {
    return selectOne(
        Wrappers.<WikiCatalogDO>lambdaQuery()
            .eq(WikiCatalogDO::getWikiId, wikiId)
            .isNotNull(WikiCatalogDO::getArticleId) // articleId不为空
            .orderByDesc(WikiCatalogDO::getId) // 按目录Id倒序排列
            .lt(WikiCatalogDO::getId, catalogId) // 查询比当前文章Id大的
            .last("limit 1") // 第一条记录即为上一篇文章
        );
  }
}
