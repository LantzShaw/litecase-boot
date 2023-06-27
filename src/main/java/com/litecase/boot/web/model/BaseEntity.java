package com.litecase.boot.web.model;

import java.io.Serializable;

public class BaseEntity implements Serializable {
    //    序列化版本号
    private static final long serialVersionUID = 1L;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createAt;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private String updateAt;
}
