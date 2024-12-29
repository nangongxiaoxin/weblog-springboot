package com.slilio.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 文章分类关联表 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName("t_article_category_rel")
public class ArticleCategoryRelDO {
  @TableId(type = IdType.AUTO)
  private Long id;

  private Long articleId;
  private Long categoryId;
}
