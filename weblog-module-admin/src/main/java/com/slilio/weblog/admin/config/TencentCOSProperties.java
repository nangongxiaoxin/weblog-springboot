package com.slilio.weblog.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "tencentcos")
public class TencentCOSProperties {
  private String region;
  private String SecretId;
  private String SecretKey;
  private String bucketName;
  private String uploadFolder;
}
