package com.slilio.weblog.admin.convert;

import com.slilio.weblog.admin.model.vo.article.FindArticleDetailRspVO;
import com.slilio.weblog.common.domain.dos.ArticleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleDetailConvert {

  // 初始化convert实例
  ArticleDetailConvert INSTANCE = Mappers.getMapper(ArticleDetailConvert.class);

  // 将DO转化为VO
  FindArticleDetailRspVO convertDO2VO(ArticleDO bean);
}
