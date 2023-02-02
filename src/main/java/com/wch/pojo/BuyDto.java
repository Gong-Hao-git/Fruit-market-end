package com.wch.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 龚豪定义的购买类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyDto<T> {
    private Integer userId;
    private String address;
    private List<T> list;
}
