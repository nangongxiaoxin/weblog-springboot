package com.slilio.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slilio.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.slilio.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.slilio.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.slilio.weblog.admin.model.vo.category.FindCategoryPageListRspVO;
import com.slilio.weblog.admin.service.AdminCategoryService;
import com.slilio.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.slilio.weblog.common.domain.dos.CategoryDO;
import com.slilio.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.slilio.weblog.common.domain.mapper.CategoryMapper;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.exception.BizException;
import com.slilio.weblog.common.model.vo.SelectRspVO;
import com.slilio.weblog.common.utils.PageResponse;
import com.slilio.weblog.common.utils.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {
  @Autowired private CategoryMapper categoryMapper;
  @Autowired private ArticleCategoryRelMapper articleCategoryRelMapper;

  /**
   * 添加分类
   *
   * @param addCategoryReqVO
   * @return
   */
  @Override
  public Response addCategory(AddCategoryReqVO addCategoryReqVO) {
    String categoryName = addCategoryReqVO.getName();

    // 先判断分类是否存在
    CategoryDO categoryDO = categoryMapper.selectByName(categoryName);

    if (Objects.nonNull(categoryDO)) {
      log.warn("分类名称： {}, 此分类已存在", categoryName);
      throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);
    }

    // 构建DO类
    CategoryDO insertCategoryDO =
        CategoryDO.builder().name(addCategoryReqVO.getName().trim()).build();
    // 执行insert
    categoryMapper.insert(insertCategoryDO);

    return Response.success();
  }

  /**
   * 查询分类列表
   *
   * @param findCategoryPageListReqVO
   * @return
   */
  @Override
  public PageResponse findCategoryPageList(FindCategoryPageListReqVO findCategoryPageListReqVO) {
    // 获取当前页、以及每页需要展示的数据数量
    Long current = findCategoryPageListReqVO.getCurrent();
    Long size = findCategoryPageListReqVO.getSize();
    String name = findCategoryPageListReqVO.getName();
    LocalDate startDate = findCategoryPageListReqVO.getStartDate();
    LocalDate endDate = findCategoryPageListReqVO.getEndDate();

    // 执行分页查询
    Page<CategoryDO> categoryDOPage =
        categoryMapper.selectPageList(current, size, name, startDate, endDate);

    List<CategoryDO> categoryDOS = categoryDOPage.getRecords();

    // DO 转 VO
    List<FindCategoryPageListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(categoryDOS)) {
      vos =
          categoryDOS.stream()
              .map(
                  categoryDO ->
                      FindCategoryPageListRspVO.builder()
                          .id(categoryDO.getId())
                          .name(categoryDO.getName())
                          .createTime(categoryDO.getCreateTime())
                          .articlesTotal(categoryDO.getArticlesTotal())
                          .build())
              .collect(Collectors.toList());
    }

    return PageResponse.success(categoryDOPage, vos);
  }

  /**
   * 删除分类
   *
   * @param deleteCategoryReqVO
   * @return
   */
  @Override
  public Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO) {
    // 分类ID
    Long categoryId = deleteCategoryReqVO.getId();

    // 校验该分类下是否已经有文章，若有，则提示需要先删除分类下所有文章，才能删除
    ArticleCategoryRelDO articleCategoryRelDO =
        articleCategoryRelMapper.selectOneByCategoryId(categoryId);

    if (Objects.nonNull(articleCategoryRelDO)) {
      log.warn("==> 此分类下包含文章，无法删除，categoryId: {}", categoryId);
      throw new BizException(ResponseCodeEnum.CATEGORY_CAN_NOT_DELETE);
    }

    // 删除分类
    categoryMapper.deleteById(categoryId);
    return Response.success();
  }

  @Override
  public Response findCategorySelectList() {
    // 查询所有分类
    List<CategoryDO> categoryDOS = categoryMapper.selectList(null);

    // DO转VO
    List<SelectRspVO> selectRspVOS = null;
    // 如果分类数据不为空
    if (!CollectionUtils.isEmpty(categoryDOS)) {
      // 将分类 ID 作为 Value 值，将分类名称作为 label 展示
      selectRspVOS =
          categoryDOS.stream()
              .map(
                  categoryDO ->
                      SelectRspVO.builder()
                          .label(categoryDO.getName())
                          .value(categoryDO.getId())
                          .build())
              .collect(Collectors.toList());
    }

    return Response.success(selectRspVOS);
  }
}
