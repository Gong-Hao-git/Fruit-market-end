package com.wch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计订单新增情况
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DdtiDto {
    private String month; //月份
    private Integer num;//每月发布的公告数量
}
