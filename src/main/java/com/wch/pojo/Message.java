package com.wch.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 姓名
     */
    private  String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 添加时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 留言类型
     */
    private String type;

}
