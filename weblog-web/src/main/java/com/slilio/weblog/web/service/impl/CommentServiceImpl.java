package com.slilio.weblog.web.service.impl;

import com.slilio.weblog.common.domain.dos.BlogSettingsDO;
import com.slilio.weblog.common.domain.dos.CommentDO;
import com.slilio.weblog.common.domain.mapper.BlogSettingsMapper;
import com.slilio.weblog.common.domain.mapper.CommentMapper;
import com.slilio.weblog.common.enums.CommentStatusEnum;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.exception.BizException;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.config.QQInfoApiProperties;
import com.slilio.weblog.web.model.vo.comment.FindQQUserInfoReqVO;
import com.slilio.weblog.web.model.vo.comment.FindQQUserInfoRspVO;
import com.slilio.weblog.web.model.vo.comment.PublishCommentReqVO;
import com.slilio.weblog.web.service.CommentService;
import com.slilio.weblog.web.utils.StringUtil;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
  @Autowired RestTemplate restTemplate;
  @Autowired QQInfoApiProperties qqInfoApiProperties;
  @Autowired private BlogSettingsMapper blogSettingsMapper;
  @Autowired private CommentMapper commentMapper;

  /**
   * 获取QQ信息
   *
   * @param findQQUserInfoReqVO
   * @return
   */
  @Override
  public Response findQQUserInfo(FindQQUserInfoReqVO findQQUserInfoReqVO) {
    String qq = findQQUserInfoReqVO.getQq();

    // 校验QQ号
    if (!StringUtil.isPureNumber(qq)) {
      log.warn("昵称输入的格式不是 QQ 号: {}", qq);
      throw new BizException(ResponseCodeEnum.NOT_QQ_NUMBER);
    }

    // 请求第三方接口

    //    String url = String.format("https://api.nsmao.net/api/qqinfo?qq=%s", qq);
    //    String result = restTemplate.getForObject(url, String.class);
    //
    //    log.info("通过 QQ 号获取用户信息: {}", result);
    //    // 解析响应参数
    //    ObjectMapper objectMapper = new ObjectMapper();
    //
    //    try {
    //      Map<String, Object> map = objectMapper.readValue(result, Map.class);
    //      if (Objects.equals(map.get("code"), HttpStatus.OK.value())) {
    //        // 获取用户头像、昵称、邮箱
    //        return Response.success(
    //            FindQQUserInfoRspVO.builder()
    //                .avatar(String.valueOf(map.get("imgurl")))
    //                .nickname(String.valueOf(map.get("name")))
    //                .mail(String.valueOf(map.get("mail")))
    //                .build());
    //      }
    //      return Response.fail();
    //    } catch (JsonProcessingException e) {
    //      throw new RuntimeException(e);
    //    }

    String url =
        String.format(
            qqInfoApiProperties.getApiUrl() + "?key=%s&qq=%s", qqInfoApiProperties.getApiKey(), qq);
    Map<String, Object> result = restTemplate.getForObject(url, Map.class);

    log.info("通过 QQ 号获取用户信息: {}", result);

    try {
      if (Objects.equals(result.get("code"), HttpStatus.OK.value())) {
        // 提取 data 部分
        Map<String, Object> data = (Map<String, Object>) result.get("data");
        // 获取用户头像、昵称、邮箱
        return Response.success(
            FindQQUserInfoRspVO.builder()
                .avatar(String.valueOf(data.get("avatar")))
                .nickname(String.valueOf(data.get("nick")))
                .mail(String.valueOf(data.get("email")))
                .build());
      }
      return Response.fail();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 发布评论
   *
   * @param publishCommentReqVO
   * @return
   */
  @Override
  public Response publishComment(PublishCommentReqVO publishCommentReqVO) {
    // 回复的评论ID
    Long replyCommentId = publishCommentReqVO.getReplyCommentId();
    // 评论内容
    String content = publishCommentReqVO.getContent();
    // 昵称
    String nickname = publishCommentReqVO.getNickname();

    // 查询博客设置相关信息（约定ID为1）
    BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
    // 是否开启了敏感词过滤
    boolean isCommentSensiWordOpen = blogSettingsDO.getIsCommentSensiWordOpen();
    // 是否开启了审核
    boolean isCommentExamineOpen = blogSettingsDO.getIsCommentExamineOpen();

    // 设置默认状态（正常）
    Integer status = CommentStatusEnum.NORMAL.getCode();
    // 审核不通过原因
    String reason = "";

    // 如果开启了审核，设置为待审核，等待博主后台审核通过
    if (isCommentExamineOpen) {
      status = CommentStatusEnum.WAIT_EXAMINE.getCode();
    }

    // 评论内容开启了敏感词过滤
    if (isCommentSensiWordOpen) {
      // todo 敏感词过滤逻辑
    }

    // 构建DO对象
    CommentDO commentDO =
        CommentDO.builder()
            .avatar(publishCommentReqVO.getAvatar())
            .content(content)
            .mail(publishCommentReqVO.getMail())
            .createTime(LocalDateTime.now())
            .nickname(nickname)
            .routerUrl(publishCommentReqVO.getRouterUrl())
            .website(publishCommentReqVO.getWebsite())
            .replyCommentId(replyCommentId)
            .parentCommentId(publishCommentReqVO.getParentCommentId())
            .status(status)
            .reason(reason)
            .build();
    commentMapper.insert(commentDO);
    return Response.success();
  }
}
