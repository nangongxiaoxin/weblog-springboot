package com.slilio.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.slilio.weblog.common.domain.dos.TagDO;
import com.slilio.weblog.common.domain.mapper.TagMapper;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.model.vo.tag.FindTagListRspVO;
import com.slilio.weblog.web.service.TagService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
  @Autowired private TagMapper tagMapper;

  /**
   * 获取所有标签
   *
   * @return
   */
  @Override
  public Response findTagList() {
    // 查询所有标签
    List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());

    // DO转VO
    List<FindTagListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(tagDOS)) {
      vos =
          tagDOS.stream()
              .map(
                  tagDO ->
                      FindTagListRspVO.builder().id(tagDO.getId()).name(tagDO.getName()).build())
              .collect(Collectors.toList());
    }
    return Response.success(vos);
  }
}
