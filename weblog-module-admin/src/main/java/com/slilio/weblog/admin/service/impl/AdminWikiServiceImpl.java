package com.slilio.weblog.admin.service.impl;

import com.slilio.weblog.admin.model.vo.wiki.AddWikiReqVO;
import com.slilio.weblog.admin.service.AdminWikiService;
import com.slilio.weblog.common.domain.dos.WikiCatalogDO;
import com.slilio.weblog.common.domain.dos.WikiDo;
import com.slilio.weblog.common.domain.mapper.WikiCatalogMapper;
import com.slilio.weblog.common.domain.mapper.WikiMapper;
import com.slilio.weblog.common.utils.Response;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminWikiServiceImpl implements AdminWikiService {
  @Autowired private WikiMapper wikiMapper;
  @Autowired private WikiCatalogMapper wikiCatalogMapper;

  /**
   * 新增知识库
   *
   * @param addWikiReqVO
   * @return
   */
  @Override
  public Response addWiki(AddWikiReqVO addWikiReqVO) {
    // VO转DO
    WikiDo wikiDo =
        WikiDo.builder()
            .cover(addWikiReqVO.getCover())
            .title(addWikiReqVO.getTitle())
            .summary(addWikiReqVO.getSummary())
            .createTime(LocalDateTime.now())
            .updateTime(LocalDateTime.now())
            .build();
    // 新增知识库
    wikiMapper.insert(wikiDo);
    // 获取新增记录的主键ID
    Long wikiId = wikiDo.getId();

    // 初始化默认目录
    // >概述
    // >基础
    wikiCatalogMapper.insert(WikiCatalogDO.builder().wikiId(wikiId).title("概述").sort(1).build());
    wikiCatalogMapper.insert(WikiCatalogDO.builder().wikiId(wikiId).title("基础").sort(2).build());

    return Response.success();
  }
}
