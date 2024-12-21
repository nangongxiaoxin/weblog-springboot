package com.slilio.weblog.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 下拉列表值
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectRspVO {
    //下拉显示的值
    private String label;
    /**
     * select 下拉列表的value，如ID等
     */
    private Object value;
}
