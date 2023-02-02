package com.wch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 在更新用户密码时，用于封装传过来的登录用户，密码，新密码等数据，便于后期以对象的形式转换为json格式
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    /**
     *用户名
     */
    private String userName;
    /**
     *密码
     */
    private String passWord;
    /**
     *新密码
     */
    private String newPassword;

}
