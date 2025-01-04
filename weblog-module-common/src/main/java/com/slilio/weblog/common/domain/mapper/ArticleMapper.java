package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slilio.weblog.common.domain.dos.ArticleDO;
import java.time.LocalDate;
import java.util.Objects;

public interface ArticleMapper extends BaseMapper<ArticleDO> {

  default Page<ArticleDO> selectPageList(
      Long current, Long size, String title, LocalDate startDate, LocalDate endDate) {
    // 分页插叙（查询第几页，每页多少数据
    Page<ArticleDO> page = new Page<>(current, size);

    // 构建查询条件
    LambdaQueryWrapper<ArticleDO> wrapper =
        Wrappers.<ArticleDO>lambdaQuery()
            .like(StringUtils.isNotBlank(title), ArticleDO::getTitle, title)
            .ge(Objects.nonNull(startDate), ArticleDO::getCreateTime, startDate)
            .le(Objects.nonNull(endDate), ArticleDO::getCreateTime, endDate)
            .orderByDesc(ArticleDO::getCreateTime);

    return selectPage(page, wrapper);
  }
}
