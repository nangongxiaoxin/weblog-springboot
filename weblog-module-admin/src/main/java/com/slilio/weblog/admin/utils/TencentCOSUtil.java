package com.slilio.weblog.admin.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.slilio.weblog.admin.config.TencentCOSProperties;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class TencentCOSUtil {
  @Autowired private TencentCOSProperties tencentCOSProperties;
  @Autowired private COSClient cosClient;

  //  // 用户信息
  //  COSCredentials credentials =
  //      new BasicCOSCredentials(
  //          tencentCOSProperties.getSecretId(), tencentCOSProperties.getSecretKey());
  //  // 桶信息
  //  ClientConfig clientConfig = new ClientConfig(new Region(tencentCOSProperties.getRegion()));
  //  // 生成COS客户端
  //  COSClient cosClient = new COSClient(credentials, clientConfig);
  public String uploadFile(MultipartFile file) throws Exception {
    // 判断文件是否为空
    if (file == null || file.getSize() <= 0) {
      log.error("===>异常文件异常，文件大小为...");
      throw new RuntimeException("文件大小不能为空");
    }
    // 文件的原始名字
    String originalFilename = file.getOriginalFilename();
    // 目标目录
    String uploadFolder = tencentCOSProperties.getUploadFolder();
    // 生成文件的名称（将 UUID 字符串中的 - 替换成空字符串）
    String key = UUID.randomUUID().toString().replace("-", "");
    // 获取文件的后缀
    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
    // 拼接上文件后缀，即为要存储的文件名
    String objectName = String.format("%s%s%s", uploadFolder, key, suffix);

    log.info("==> 开始上传文件至 TencentCOS, ObjectName: {}", objectName);

    // 上传至TencentCOS
    PutObjectResult putObjectResult =
        cosClient.putObject(
            new PutObjectRequest(
                tencentCOSProperties.getBucketName(), objectName, file.getInputStream(), null));
    // 返回文件的访问链接
    String url =
        String.format(
            "https://%s.cos.%s.myqcloud.com/%s",
            tencentCOSProperties.getBucketName(), tencentCOSProperties.getRegion(), objectName);
    log.info("==> 上传文件至 TencentCOS 成功，访问路径: {}", url);
    file.getInputStream().close();
    return url;
  }
}
