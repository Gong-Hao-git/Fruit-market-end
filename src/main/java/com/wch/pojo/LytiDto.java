package com.wch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计订用户留言情况
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LytiDto {
    private Integer type; //留言类型
    private Integer num;//每种类型的留言数量
}
