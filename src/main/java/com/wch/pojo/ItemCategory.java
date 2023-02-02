package com.wch.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 类目
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCategory implements Serializable {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 类目名称
     */
    private String name;
    /**
     * 父id
     */
    private Integer pid;
    /**
     * 是否已删除
     */
    private Integer isDelete;
    /**
     * 添加时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
