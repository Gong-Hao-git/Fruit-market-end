package com.wch.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 存放登录过的用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    /**
     *主键id
     */
    private Integer id;
    /**
     *用户名
     */
    private String userName;
    /**
     *密码
     */
    private String passWord;
    /**
     *手机号
     */
    private String phone;
    /**
     *真实姓名
     */
    private String realName;
    /**
     *性别
     */
    private String sex;
    /**
     *地址
     */
    private String address;
    /**
     *电子邮箱
     */
    private String email;
    /**
     * 登录时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 是否已删除
     */
    private Integer isDelete;

}
