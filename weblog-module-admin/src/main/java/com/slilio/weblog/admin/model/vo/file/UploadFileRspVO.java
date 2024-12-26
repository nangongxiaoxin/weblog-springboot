package com.slilio.weblog.admin.model.vo.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileRspVO {
    /**
     *  文件访问链接
     */
    private String url;
}
