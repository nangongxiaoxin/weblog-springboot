package com.slilio.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 文章标签关联DO */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("t_article_tag_rel")
public class ArticleTagRelDO {

  @TableId(type = IdType.AUTO)
  private Long id;

  private Long articleId;
  private Long tagId;
}
