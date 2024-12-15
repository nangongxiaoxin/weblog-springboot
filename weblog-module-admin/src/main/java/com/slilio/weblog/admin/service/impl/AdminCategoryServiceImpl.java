package com.slilio.weblog.admin.service.impl;

import com.slilio.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.slilio.weblog.admin.service.AdminCategoryService;
import com.slilio.weblog.common.domain.dos.CategoryDO;
import com.slilio.weblog.common.domain.mapper.CategoryMapper;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.exception.BizException;
import com.slilio.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    @Override
    public Response addCategory(AddCategoryReqVO addCategoryReqVO){
        String categoryName = addCategoryReqVO.getName();

        //先判断分类是否存在
        CategoryDO categoryDO = categoryMapper.selectByName(categoryName);

        if (Objects.nonNull(categoryDO)){
            log.warn("分类名称： {}, 此分类已存在", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);
        }

        //构建DO类
        CategoryDO insertCategoryDO = CategoryDO.builder()
                .name(addCategoryReqVO.getName().trim())
                .build();
        //执行insert
        categoryMapper.insert(insertCategoryDO);

        return Response.success();
    }
}
