package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.slilio.weblog.common.config.InsertBatchMapper;
import com.slilio.weblog.common.domain.dos.WikiCatalogDO;
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
}
