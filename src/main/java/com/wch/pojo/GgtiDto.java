package com.wch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计公告发布情况
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GgtiDto {
    private String month; //月份
    private Integer num;//每月发布的公告数量
}
