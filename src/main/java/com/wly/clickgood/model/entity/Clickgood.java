package com.wly.clickgood.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName clickgood
 */
@TableName(value ="clickgood")
@Data
public class Clickgood implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 
     */
    @TableField("userId")
    private Long userId;

    /**
     * 
     */
    @TableField("blogId")
    private Long blogId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
