package com.wch.pojo;

import com.alipay.api.domain.OrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrder implements Serializable {
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
     * 购买者id
     */
    private Integer userId;

    private User user;

    /**
     * 订单号
     */
    private String code;

    /**
     * 购买时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 总金额
     */
    private String total;

    /**
     * 0未删除   1已删除
     */
    private Integer isDelete;

    /**
     * 0.新建待发货1.已取消 2已发货3.到收货4已评价
     */
    private Integer status;
    /**
     * 收获地址
     */
    private String address;

//    private List<OrderDetail> details;

}
