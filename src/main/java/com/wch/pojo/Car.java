package com.wch.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 购物车
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car implements Serializable {

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
     * 用户id
     */
    private Integer userId;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 商品单价
     */
    private double price;

    /**
     * 商品总价
     */
    private String total;

    /**
     * 添加购物车的时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

}
