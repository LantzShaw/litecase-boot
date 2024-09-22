package com.litecase.boot.web.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("product")
public class Product {
    @TableId(type = IdType.ASSIGN_ID)
    private Long product_id;
    private String name;
    private String description;
    private String image;
    private double price;
    private int quantity;
    private int category_id;
    private int brand_id;
    /**
     * 0: deleted; 1: active
     */
    private int status;
}
