package com.slilio.weblog.admin.service;

import com.slilio.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.slilio.weblog.common.utils.Response;

public interface AdminCategoryService {
    Response addCategory(AddCategoryReqVO addCategoryReqVO);
}
