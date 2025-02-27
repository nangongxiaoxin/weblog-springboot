package com.slilio.weblog.admin.service.impl;

import com.slilio.weblog.admin.model.vo.file.UploadFileRspVO;
import com.slilio.weblog.admin.service.AdminFileService;
import com.slilio.weblog.admin.utils.MinioUtil;
import com.slilio.weblog.admin.utils.TencentCOSUtil;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.exception.BizException;
import com.slilio.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class AdminFileServiceImpl implements AdminFileService {
  @Autowired private MinioUtil minioUtil;
  @Autowired private TencentCOSUtil tencentCOSUtil;

  @Override
  public Response uploadFile(MultipartFile file) {
    try {
      // 上传文件
      // String url = minioUtil.uploadFile(file);
      String url = tencentCOSUtil.uploadFile(file);
      // 构建成功返参，将图片url返回
      return Response.success(UploadFileRspVO.builder().url(url).build());
    } catch (Exception e) {
      log.error("==> 上传文件至 Minio 错误: ", e);
      // 手动抛出业务异常，提示 “文件上传失败”
      throw new BizException(ResponseCodeEnum.FILE_UPLOAD_FAILED);
    }
  }
}
