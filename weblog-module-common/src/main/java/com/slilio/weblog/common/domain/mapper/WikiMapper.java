package com.slilio.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slilio.weblog.common.domain.dos.WikiDO;
import java.time.LocalDate;
import java.util.Objects;

public interface WikiMapper extends BaseMapper<WikiDO> {

  /**
   * 分页查询
   *
   * @param current
   * @param size
   * @param title
   * @param startDate
   * @param endDate
   * @param isPublish
   * @return
   */
  default Page<WikiDO> selectPageList(
      Long current,
      Long size,
      String title,
      LocalDate startDate,
      LocalDate endDate,
      Boolean isPublish) {
    // 分页对象
    Page<WikiDO> page = new Page<>(current, size);

    // 构建查询条件
    LambdaQueryWrapper<WikiDO> wrapper =
        Wrappers.<WikiDO>lambdaQuery()
            .like(StringUtils.isNotBlank(title), WikiDO::getTitle, title) // 模糊查询
            .ge(Objects.nonNull(startDate), WikiDO::getCreateTime, startDate) // 大于等于startDate
            .le(Objects.nonNull(endDate), WikiDO::getCreateTime, endDate) // 小于等于endDate
            .eq(Objects.nonNull(isPublish), WikiDO::getIsPublish, isPublish) // 发布状态
            .orderByDesc(WikiDO::getWeight) // 按权重倒序
            .orderByDesc(WikiDO::getCreateTime); // 按创建时间倒序
    return selectPage(page, wrapper);
  }

  /**
   * 查询最大权重
   *
   * @return
   */
  default WikiDO selectMaxWeight() {
    return selectOne(
        Wrappers.<WikiDO>lambdaQuery()
            .orderByDesc(WikiDO::getWeight) // 按权重降序排列
            .last("limit 1")); // 仅查询出一条
  }
}
