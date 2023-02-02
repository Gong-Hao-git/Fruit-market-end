package com.wch.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 龚豪定义的购买类中的list里面的商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Information {
    private Integer itemId;
    private Integer num;
    private Integer price;
    private Integer total;
}
