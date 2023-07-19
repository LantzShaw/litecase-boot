package com.litecase.boot.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litecase.boot.web.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@TableName("person_attachment")
@Data
public class PersonAttachment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(title = "文件名")
    private String fileName;

    @Schema(title = "源文件名")
    private String originName;
}
