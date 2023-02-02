package com.wch.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品收藏
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sc implements Serializable {

    private Integer id;

    /**
     * 商品id
     */
    private Integer itemId;

    /**
     * 商品对象
     */
    private Item item;

    /**
     * 收藏者id
     */
    private Integer userId;
    /**
     * 用户账号
     */
    private String userName;
    /**
     * 收藏时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;


}
