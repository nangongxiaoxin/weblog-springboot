package com.slilio.weblog.admin.convert;

import com.slilio.weblog.admin.model.vo.wiki.FindWikiPageListRspVO;
import com.slilio.weblog.common.domain.dos.WikiDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WikiConvert {
  WikiConvert INSTANCE = Mappers.getMapper(WikiConvert.class);

  /**
   * 将WikiDO.getWeight()>0时的boolean结果映射给FindWikiPageListRspVO.setIsTop()
   *
   * @param bean
   * @return
   */
  @Mapping(target = "isTop", expression = "java(bean.getWeight() > 0)")
  FindWikiPageListRspVO convertDO2VO(WikiDO bean);
}
