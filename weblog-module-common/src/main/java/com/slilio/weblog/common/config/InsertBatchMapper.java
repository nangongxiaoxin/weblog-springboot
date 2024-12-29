package com.slilio.weblog.common.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InsertBatchMapper<T> extends BaseMapper<T> {
  // 批量插入
  int insertBatchSomeColumn(@Param("list") List<T> batchList);
}
