package com.wch.pojo;

import lombok.Data;

import java.util.List;

/**
 * 一级类目和他的二级类目列表
 */
@Data
public class CategoryDto {
    private ItemCategory father;
    private List<ItemCategory> childrens;
}
