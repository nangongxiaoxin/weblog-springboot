package com.slilio.weblog.web.model.vo.blogsettings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindBlogSettingsDetailRspVO {
  private String logo;
  private String name;
  private String author;
  private String introduction;
  private String avatar;
  private String githubHomepage;
  private String csdnHomepage;
  private String giteeHomepage;
  private String zhihuHomepage;
}
