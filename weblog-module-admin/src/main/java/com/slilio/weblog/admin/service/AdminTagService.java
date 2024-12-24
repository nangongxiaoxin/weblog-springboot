package com.slilio.weblog.admin.service;

import com.slilio.weblog.admin.model.vo.tag.AddTagReqVO;
import com.slilio.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.slilio.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.slilio.weblog.admin.model.vo.tag.SearchTagReqVO;
import com.slilio.weblog.common.utils.PageResponse;
import com.slilio.weblog.common.utils.Response;

public interface AdminTagService {

    /**
     * 添加标签集合
     * @param addTagReqVO
     * @return
     */
    Response addTag(AddTagReqVO addTagReqVO);

    /**
     * 标签分页数据获取
     * @param findTagPageListReqVO
     * @return
     */
    PageResponse findTagList(FindTagPageListReqVO findTagPageListReqVO);

    /**
     * 删除标签
     * @param deleteTagReqVO
     * @return
     */
    Response deleteTag(DeleteTagReqVO deleteTagReqVO);

    /**
     * 标签模糊查询
     * @return
     */
    Response searchTag(SearchTagReqVO searchTagReqVO);
}
