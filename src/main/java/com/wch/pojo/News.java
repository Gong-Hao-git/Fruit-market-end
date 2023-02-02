package com.wch.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告管理
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News implements Serializable {
    private Integer id;
    /**
     * 标题
     */
    private  String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;


}
