package com.wly.clickgood.model.vo;

import java.util.Date;

import lombok.Data;

//VO 通常是 View Object 的缩写。
//“博客展示对象”
@Data
public class BlogVo {
    private String id;
    private String title;
    private String content;
    private String coverImg;
    private Date createTime;
    // 改之后
    private Integer thumbCount;
    private Boolean hasClickGood;

    
}
