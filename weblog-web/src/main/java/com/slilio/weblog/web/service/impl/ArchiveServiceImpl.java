package com.slilio.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.slilio.weblog.common.domain.dos.ArticleDO;
import com.slilio.weblog.common.domain.mapper.ArticleMapper;
import com.slilio.weblog.common.utils.PageResponse;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.convert.ArticleConvert;
import com.slilio.weblog.web.model.vo.archive.FindArchiveArticlePageListReqVO;
import com.slilio.weblog.web.model.vo.archive.FindArchiveArticlePageListRspVO;
import com.slilio.weblog.web.model.vo.archive.FindArchiveArticleRspVO;
import com.slilio.weblog.web.service.ArchiveService;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class ArchiveServiceImpl implements ArchiveService {

  @Autowired private ArticleMapper articleMapper;

  /**
   * 获取文章归档分页数据
   *
   * @param findArchiveArticlePageListReqVO
   * @return
   */
  @Override
  public Response findArchivePageList(
      FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO) {
    Long current = findArchiveArticlePageListReqVO.getCurrent();
    Long size = findArchiveArticlePageListReqVO.getSize();

    // 分页查询
    IPage<ArticleDO> page = articleMapper.selectPageList(current, size, null, null, null);
    List<ArticleDO> articleDOS = page.getRecords();

    List<FindArchiveArticlePageListRspVO> vos = Lists.newArrayList();
    if (!CollectionUtils.isEmpty(articleDOS)) {
      // do转vo
      List<FindArchiveArticleRspVO> archiveArticleRspVOS =
          articleDOS.stream()
              .map(articleDO -> ArticleConvert.INSTANCE.convertDO2ArchiveArticleVO(articleDO))
              .collect(Collectors.toList());
      // 按创建月份分组
      Map<YearMonth, List<FindArchiveArticleRspVO>> map =
          archiveArticleRspVOS.stream()
              .collect(Collectors.groupingBy(FindArchiveArticleRspVO::getCreateMonth));
      // 使用Treemap 按月份排序
      Map<YearMonth, List<FindArchiveArticleRspVO>> sortedMap =
          new TreeMap<>(Collections.reverseOrder());
      sortedMap.putAll(map);
      // 便遍历排序后的Map，将其转换为归档VO
      sortedMap.forEach(
          (k, v) ->
              vos.add(FindArchiveArticlePageListRspVO.builder().month(k).articles(v).build()));
    }

    return PageResponse.success(page, vos);
  }
}
