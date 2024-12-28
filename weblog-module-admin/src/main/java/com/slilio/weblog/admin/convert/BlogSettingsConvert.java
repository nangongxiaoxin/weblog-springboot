package com.slilio.weblog.admin.convert;

import com.slilio.weblog.admin.model.vo.blogsettings.FindBlogSettingsRspVO;
import com.slilio.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.slilio.weblog.common.domain.dos.BlogSettingsDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {
  /** 初始化convert实例 */
  BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

  /**
   * 将VO转换为DO
   *
   * @param bean
   * @return
   */
  BlogSettingsDO convertVO2DO(UpdateBlogSettingsReqVO bean);

  /**
   * 将DO转换为VO
   *
   * @param bean
   * @return
   */
  FindBlogSettingsRspVO convertDO2VO(BlogSettingsDO bean);
}
