package com.wch.pojo;

import lombok.Data;

@Data
public class Query {
    /**
     * all(时间降序)、sales(按销量降序排序)、price(按价格排序)
     */
    private  String screenField;
    /**
     * 1:降序  2：升序
     */
    private int sort;
    /**
     * 价格最小值
     */
    private Integer minMoney;
    /**
     * 价格最大值
     */
    private Integer maxMoney;
}

