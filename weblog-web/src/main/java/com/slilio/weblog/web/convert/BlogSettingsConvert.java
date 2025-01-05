package com.slilio.weblog.web.convert;

import com.slilio.weblog.common.domain.dos.BlogSettingsDO;
import com.slilio.weblog.web.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {
  /** 初始化convert实例 */
  BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

  /**
   * 将DO转化为VO
   *
   * @param bean
   * @return
   */
  FindBlogSettingsDetailRspVO convertDO2VO(BlogSettingsDO bean);
}
