package com.slilio.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slilio.weblog.admin.convert.BlogSettingsConvert;
import com.slilio.weblog.admin.model.vo.blogsettings.FindBlogSettingsRspVO;
import com.slilio.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.slilio.weblog.admin.service.AdminBlogSettingsService;
import com.slilio.weblog.common.domain.dos.BlogSettingsDO;
import com.slilio.weblog.common.domain.mapper.BlogSettingsMapper;
import com.slilio.weblog.common.utils.Response;
import org.springframework.stereotype.Service;

@Service
public class AdminBlogSettingsServiceImpl extends ServiceImpl<BlogSettingsMapper, BlogSettingsDO>
    implements AdminBlogSettingsService {

  private final BlogSettingsMapper blogSettingsMapper;

  public AdminBlogSettingsServiceImpl(BlogSettingsMapper blogSettingsMapper) {
    this.blogSettingsMapper = blogSettingsMapper;
  }

  /**
   * 更新博客设置信息
   *
   * @param updateBlogSettingsReqVO
   * @return
   */
  @Override
  public Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO) {
    // VO 转 DO
    BlogSettingsDO blogSettingsDO =
        BlogSettingsConvert.INSTANCE.convertVO2DO(updateBlogSettingsReqVO);
    blogSettingsDO.setId(1L);

    // 保存或者更新（当数据库中存在ID为1的记录时，则执行更新操作，否则执行插入操作
    saveOrUpdate(blogSettingsDO);
    return Response.success();
  }

  /**
   * 获取博客设置详情
   *
   * @return
   */
  @Override
  public Response findDetail() {
    // 查询ID为1的记录
    BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);

    // DO转VO
    FindBlogSettingsRspVO vo = BlogSettingsConvert.INSTANCE.convertDO2VO(blogSettingsDO);

    return Response.success(vo);
  }
}
