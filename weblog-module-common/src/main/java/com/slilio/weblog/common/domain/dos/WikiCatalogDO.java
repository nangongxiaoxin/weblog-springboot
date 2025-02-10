package com.slilio.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_wiki_catalog")
public class WikiCatalogDO {
  @TableId(type = IdType.AUTO)
  private Long id;

  private Long wikiId;
  private Long articleId;
  private String title;
  private Integer level;
  private Long parentId;
  private Integer sort;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Boolean isDeleted;
}
