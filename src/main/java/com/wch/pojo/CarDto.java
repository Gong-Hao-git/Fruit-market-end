package com.wch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装前台传过来的要购买的商品id和数量
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private Integer itemId;
    private Integer num;
    private double price;
    private String total;
    private String address;
}
