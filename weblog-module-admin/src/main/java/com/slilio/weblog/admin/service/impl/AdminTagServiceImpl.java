package com.slilio.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slilio.weblog.admin.model.vo.tag.*;
import com.slilio.weblog.admin.service.AdminTagService;
import com.slilio.weblog.common.domain.dos.TagDO;
import com.slilio.weblog.common.domain.mapper.TagMapper;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.model.vo.SelectRspVO;
import com.slilio.weblog.common.utils.PageResponse;
import com.slilio.weblog.common.utils.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {
  @Autowired private TagMapper tagMapper;

  /**
   * 添加标签集合
   *
   * @param addTagReqVO
   * @return
   */
  @Override
  public Response addTag(AddTagReqVO addTagReqVO) {
    // vo转换为do
    List<TagDO> tagDOS =
        addTagReqVO.getTags().stream()
            .map(
                tagName ->
                    TagDO.builder()
                        .name(tagName.trim())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build())
            .collect(Collectors.toList());
    // 批量插入
    try {
      saveBatch(tagDOS);
    } catch (Exception e) {
      log.warn("该标签已存在", e);
    }
    return Response.success();
  }

  /**
   * 标签分页数据获取
   *
   * @param findTagPageListReqVO
   * @return
   */
  @Override
  public PageResponse findTagList(FindTagPageListReqVO findTagPageListReqVO) {
    // 分页参数，条件参数
    Long current = findTagPageListReqVO.getCurrent();
    Long size = findTagPageListReqVO.getSize();
    String name = findTagPageListReqVO.getName();
    LocalDate startDate = findTagPageListReqVO.getStartDate();
    LocalDate endDate = findTagPageListReqVO.getEndDate();
    // 分页查询
    Page<TagDO> page = tagMapper.selectPageList(current, size, name, startDate, endDate);
    List<TagDO> records = page.getRecords();
    // vo转do
    List<FindTagPageListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(records)) {
      vos =
          records.stream()
              .map(
                  tagDO ->
                      FindTagPageListRspVO.builder()
                          .id(tagDO.getId())
                          .name(tagDO.getName())
                          .createTime(tagDO.getCreateTime())
                          .build())
              .collect(Collectors.toList());
    }
    return PageResponse.success(page, vos);
  }

  /**
   * 删除标签
   *
   * @param deleteTagReqVO
   * @return
   */
  @Override
  public Response deleteTag(DeleteTagReqVO deleteTagReqVO) {
    // 标签ID
    Long tagId = deleteTagReqVO.getId();
    // 根据标签ID删除语句
    int count = tagMapper.deleteById(tagId);
    return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.TAG_NOT_EXISTED);
  }

  @Override
  public Response searchTag(SearchTagReqVO searchTagReqVO) {
    String key = searchTagReqVO.getKey();

    // 执行模糊查询
    List<TagDO> tagDOS = tagMapper.selectByKey(key);
    // do转vo
    List<SelectRspVO> vos = null;
    if (!CollectionUtils.isEmpty(tagDOS)) {
      vos =
          tagDOS.stream()
              .map(
                  tagDO ->
                      SelectRspVO.builder().label(tagDO.getName()).value(tagDO.getId()).build())
              .collect(Collectors.toList());
    }
    return Response.success(vos);
  }

  /**
   * 查询标签 Select 列表数据
   *
   * @return
   */
  @Override
  public Response findTagSelectList() {
    // 查询所有标签，Wrappers.emptyWrapper() 表示查询为空
    List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());

    // DO转VO
    List<SelectRspVO> vos = null;
    if (!CollectionUtils.isEmpty(tagDOS)) {
      vos =
          tagDOS.stream()
              .map(
                  tagDO ->
                      SelectRspVO.builder().label(tagDO.getName()).value(tagDO.getId()).build())
              .collect(Collectors.toList());
    }
    return Response.success(vos);
  }
}
