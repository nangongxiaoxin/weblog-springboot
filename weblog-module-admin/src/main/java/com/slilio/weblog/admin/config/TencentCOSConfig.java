package com.slilio.weblog.admin.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TencentCOSConfig {
  @Autowired TencentCOSProperties tencentCOSProperties;

  @Bean
  public COSClient cosClient() {
    // 用户信息
    COSCredentials credentials =
        new BasicCOSCredentials(
            tencentCOSProperties.getSecretId(), tencentCOSProperties.getSecretKey());
    // 桶信息
    ClientConfig clientConfig = new ClientConfig(new Region(tencentCOSProperties.getRegion()));
    // 生成COS客户端
    return new COSClient(credentials, clientConfig);
  }
}
