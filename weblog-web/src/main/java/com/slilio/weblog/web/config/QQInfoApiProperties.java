package com.slilio.weblog.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "qqinfoapi")
public class QQInfoApiProperties {
  private String apiUrl;
  private String apiKey;
}
