package com.slilio.weblog.common.model;

import lombok.Data;

@Data
public class BasePageQuery {
    /**
     * 当前页
     */
    private Long current = 1L;
    /**
     * 每页展示的数据条数，目前默认为10条
     */
    private Long size = 10L;
}
