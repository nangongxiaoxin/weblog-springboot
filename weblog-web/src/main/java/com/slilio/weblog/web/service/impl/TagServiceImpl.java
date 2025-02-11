package com.slilio.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slilio.weblog.common.domain.dos.ArticleDO;
import com.slilio.weblog.common.domain.dos.ArticleTagRelDO;
import com.slilio.weblog.common.domain.dos.TagDO;
import com.slilio.weblog.common.domain.mapper.ArticleMapper;
import com.slilio.weblog.common.domain.mapper.ArticleTagRelMapper;
import com.slilio.weblog.common.domain.mapper.TagMapper;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.exception.BizException;
import com.slilio.weblog.common.utils.PageResponse;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.convert.ArticleConvert;
import com.slilio.weblog.web.model.vo.tag.FindTagArticlePageListReqVO;
import com.slilio.weblog.web.model.vo.tag.FindTagArticlePageListRspVO;
import com.slilio.weblog.web.model.vo.tag.FindTagListReqVO;
import com.slilio.weblog.web.model.vo.tag.FindTagListRspVO;
import com.slilio.weblog.web.service.TagService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
  @Autowired private TagMapper tagMapper;
  @Autowired private ArticleTagRelMapper articleTagRelMapper;
  @Autowired private ArticleMapper articleMapper;

  /**
   * 获取所有标签
   *
   * @return
   */
  @Override
  public Response findTagList(FindTagListReqVO findTagListReqVO) {
    Long size = findTagListReqVO.getSize();

    List<TagDO> tagDOS = null;
    if (Objects.isNull(size) || size == 0) {
      // 查询所有标签
      tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());

    } else {
      // 否则查询指定的数量
      tagDOS = tagMapper.selectByLimit(size);
    }

    // DO转VO
    List<FindTagListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(tagDOS)) {
      vos =
          tagDOS.stream()
              .map(
                  tagDO ->
                      FindTagListRspVO.builder().id(tagDO.getId()).name(tagDO.getName()).build())
              .collect(Collectors.toList());
    }
    return Response.success(vos);
  }

  /**
   * 获取标签下文章分页列表
   *
   * @param findTagArticlePageListReqVO
   * @return
   */
  @Override
  public Response findTagPageList(FindTagArticlePageListReqVO findTagArticlePageListReqVO) {
    Long current = findTagArticlePageListReqVO.getCurrent();
    Long size = findTagArticlePageListReqVO.getSize();
    Long tagId = findTagArticlePageListReqVO.getId(); // 标签ID

    // 判断该标签是否存在
    TagDO tagDO = tagMapper.selectById(tagId);
    if (Objects.isNull(tagDO)) {
      log.warn("==> 该标签不存在, tagId: {}", tagId);
      throw new BizException(ResponseCodeEnum.TAG_NOT_EXISTED);
    }

    // 查询该标签关联的文章id
    List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByTagId(tagId);

    // 提取所有文章 ID
    List<Long> ArticleIds =
        articleTagRelDOS.stream().map(ArticleTagRelDO::getArticleId).collect(Collectors.toList());

    // 根据文章ID集合查询文章分页数据
    Page<ArticleDO> page = articleMapper.selectPageListByArticleIds(current, size, ArticleIds);
    List<ArticleDO> articleDOS = page.getRecords();

    // DO转VO
    List<FindTagArticlePageListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(articleDOS)) {
      vos =
          articleDOS.stream()
              .map(articleDO -> ArticleConvert.INSTANCE.convertDO2TagArticleVO(articleDO))
              .collect(Collectors.toList());
    }
    return PageResponse.success(page, vos);
  }
}
