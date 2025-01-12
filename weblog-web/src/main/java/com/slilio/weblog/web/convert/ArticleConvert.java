package com.slilio.weblog.web.convert;

import com.slilio.weblog.common.domain.dos.ArticleDO;
import com.slilio.weblog.web.model.vo.archive.FindArchiveArticleRspVO;
import com.slilio.weblog.web.model.vo.article.FindIndexArticlePageListRspVO;
import com.slilio.weblog.web.model.vo.category.FindCategoryArticlePageListRspVO;
import com.slilio.weblog.web.model.vo.tag.FindTagArticlePageListRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleConvert {
  /** 初始化convert实例 */
  ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

  /**
   * 将DO转化为VO
   *
   * @param bean
   * @return
   */
  @Mapping(
      target = "createDate",
      expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
  FindIndexArticlePageListRspVO convertDO2VO(ArticleDO bean);

  /**
   * 将DO转化为归档文章VO
   *
   * @param bean
   * @return
   */
  @Mapping(
      target = "createDate",
      expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
  @Mapping(
      target = "createMonth",
      expression = "java(java.time.YearMonth.from(bean.getCreateTime()))")
  FindArchiveArticleRspVO convertDO2ArchiveArticleVO(ArticleDO bean);

  /**
   * ArticleDO -> FindTagArticlePageListRspVO
   *
   * @param bean
   * @return
   */
  @Mapping(
      target = "createDate",
      expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
  FindTagArticlePageListRspVO convertDO2TagArticleVO(ArticleDO bean);

  /**
   * 将DO转换成分类文章VO
   *
   * @param bean
   * @return
   */
  @Mapping(
      target = "createDate",
      expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
  FindCategoryArticlePageListRspVO convertDO2CategoryArticleVO(ArticleDO bean);
}
