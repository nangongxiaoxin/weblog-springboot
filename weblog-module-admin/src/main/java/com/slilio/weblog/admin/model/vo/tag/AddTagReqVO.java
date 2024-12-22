package com.slilio.weblog.admin.model.vo.tag;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 标签新增入参实体VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "添加标签VO")
public class AddTagReqVO {

    @NotEmpty(message = "标签集合不能为空")
    private List<String> tags;
}
