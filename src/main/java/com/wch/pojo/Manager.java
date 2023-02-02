package com.wch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 管理员
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager implements Serializable {//实现序列化可以很好的去传输
    /**
     * 主键
     * */
    private Integer id;
    /**
     * 登录名
     * */
    private String userName;
    /**
     * 密码
     * */
    private String passWord;
    /**
     * 姓名
     * */
    private String realName;


}
