package com.slilio.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slilio.weblog.admin.convert.WikiConvert;
import com.slilio.weblog.admin.model.vo.wiki.AddWikiReqVO;
import com.slilio.weblog.admin.model.vo.wiki.DeleteWikiReqVO;
import com.slilio.weblog.admin.model.vo.wiki.FindWikiPageListReqVO;
import com.slilio.weblog.admin.model.vo.wiki.FindWikiPageListRspVO;
import com.slilio.weblog.admin.service.AdminWikiService;
import com.slilio.weblog.common.domain.dos.ArticleDO;
import com.slilio.weblog.common.domain.dos.WikiCatalogDO;
import com.slilio.weblog.common.domain.dos.WikiDO;
import com.slilio.weblog.common.domain.mapper.ArticleMapper;
import com.slilio.weblog.common.domain.mapper.WikiCatalogMapper;
import com.slilio.weblog.common.domain.mapper.WikiMapper;
import com.slilio.weblog.common.enums.ArticleTypeEnum;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.enums.WikiCatalogLevelEnum;
import com.slilio.weblog.common.exception.BizException;
import com.slilio.weblog.common.utils.PageResponse;
import com.slilio.weblog.common.utils.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class AdminWikiServiceImpl implements AdminWikiService {
  @Autowired private WikiMapper wikiMapper;
  @Autowired private WikiCatalogMapper wikiCatalogMapper;
  @Autowired private ArticleMapper articleMapper;

  /**
   * 新增知识库
   *
   * @param addWikiReqVO
   * @return
   */
  @Override
  public Response addWiki(AddWikiReqVO addWikiReqVO) {
    // VO转DO
    WikiDO wikiDo =
        WikiDO.builder()
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

  /**
   * 根据ID删除知识库
   *
   * @param deleteWikiReqVO
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Response deleteWiki(DeleteWikiReqVO deleteWikiReqVO) {
    Long wikiId = deleteWikiReqVO.getId();

    // 删除知识库
    int count = wikiMapper.deleteById(wikiId);

    // 若知识库不存在
    if (count == 0) {
      log.warn("该知识库不存在，wikiId：{}", wikiId);
      throw new BizException(ResponseCodeEnum.WIKI_NOT_FOUND);
    }

    // 查询此知识库下所有目录
    List<WikiCatalogDO> wikiCatalogDOS = wikiCatalogMapper.selectByWikiId(wikiId);
    // 过滤目录中所有文章的ID
    List<Long> articleIds =
        wikiCatalogDOS.stream()
            .filter(
                wikiCatalogDO ->
                    Objects.nonNull(wikiCatalogDO.getArticleId()) // 文章ID不能为空
                        && Objects.equals(
                            wikiCatalogDO.getLevel(), WikiCatalogLevelEnum.TWO.getValue())) // 二级目录
            .map(WikiCatalogDO::getArticleId) // 提取文章ID
            .collect(Collectors.toList());
    // 更新文章类型为普通
    if (!CollectionUtils.isEmpty(articleIds)) {
      articleMapper.updateByIds(
          ArticleDO.builder().type(ArticleTypeEnum.NORMAL.getValue()).build(), articleIds);
    }

    // 删除知识库目录
    wikiCatalogMapper.deleteByWikiId(wikiId);
    return Response.success();
  }

  /**
   * 知识库分页查询
   *
   * @param findWikiPageListReqVO
   * @return
   */
  @Override
  public Response findWikiPageList(FindWikiPageListReqVO findWikiPageListReqVO) {
    // 获取当前页、以及每页需要展示的数据数量
    Long current = findWikiPageListReqVO.getCurrent();
    Long size = findWikiPageListReqVO.getSize();
    // 查询条件
    String title = findWikiPageListReqVO.getTitle();
    LocalDate startDate = findWikiPageListReqVO.getStartDate();
    LocalDate endDate = findWikiPageListReqVO.getEndDate();

    // 执行分页查询
    Page<WikiDO> wikiDOPage =
        wikiMapper.selectPageList(current, size, title, startDate, endDate, null);

    // 获取查询记录
    List<WikiDO> wikiDOS = wikiDOPage.getRecords();
    // DO转VO
    List<FindWikiPageListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(wikiDOS)) {
      vos =
          wikiDOS.stream()
              .map(wikiDO -> WikiConvert.INSTANCE.convertDO2VO(wikiDO))
              .collect(Collectors.toList());
    }
    return PageResponse.success(wikiDOPage, vos);
  }
}
