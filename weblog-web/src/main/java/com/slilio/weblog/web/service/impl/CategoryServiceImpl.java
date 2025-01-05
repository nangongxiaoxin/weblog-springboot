package com.slilio.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.slilio.weblog.common.domain.dos.CategoryDO;
import com.slilio.weblog.common.domain.mapper.CategoryMapper;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.model.vo.category.FindCategoryListRspVO;
import com.slilio.weblog.web.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  @Autowired private CategoryMapper categoryMapper;

  /**
   * 获取分类列表
   *
   * @return
   */
  @Override
  public Response findCategoryList() {
    // 查询所有分类
    List<CategoryDO> categoryDOS = categoryMapper.selectList(Wrappers.emptyWrapper());
    // VO转DO
    List<FindCategoryListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(categoryDOS)) {
      vos =
          categoryDOS.stream()
              .map(
                  categoryDO ->
                      FindCategoryListRspVO.builder()
                          .id(categoryDO.getId())
                          .name(categoryDO.getName())
                          .build())
              .collect(Collectors.toList());
    }
    return Response.success(vos);
  }
}
