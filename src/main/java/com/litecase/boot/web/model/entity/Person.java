package com.litecase.boot.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litecase.boot.web.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
@TableName("person")
public class Person extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Size(min = 0, max = 10, message = "姓名不能超过10个字符")
    private String name;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能为空")
    private Integer age;

    /**
     * 性别
     */
//    @ApiModelProperty(value = "性别: 0 男, 1 女"
    @Schema(title = "性别")
    private String gender;

    @Size(min = 0, max = 11, message = "电话号码不能超过11个字符")
    private String phoneNumber;

    @Email(message = "邮箱格式不正确")
    private String email;


    @Schema(title = "附件")
    @TableField(exist = false)
    @NotEmpty(message = "附件不能为空")
    private List<PersonAttachment> files;
}
