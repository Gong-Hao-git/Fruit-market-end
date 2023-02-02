package com.wch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer itemId;
    private Item item;

    /**
     * 订单id
     */
    private Integer orderId;
    private ItemOrder itemOrder;

    /**
     * 0.未退货 1已退货
     */
    private Integer status;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 小计
     */
    private String total;


}
