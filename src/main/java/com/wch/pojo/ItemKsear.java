package com.wch.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 快捷搜索关键字
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemKsear implements Serializable{
    private Integer id;
    /**
     * 快捷搜索关键字
     */
    private String name;
    /**
     * 添加时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
