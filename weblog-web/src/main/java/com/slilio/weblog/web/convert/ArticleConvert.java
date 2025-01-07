package com.slilio.weblog.web.convert;

import com.slilio.weblog.common.domain.dos.ArticleDO;
import com.slilio.weblog.web.model.vo.article.FindIndexArticlePageListRspVO;
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
}
