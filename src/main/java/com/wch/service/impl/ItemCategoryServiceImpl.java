package com.wch.service.impl;

import com.wch.base.BaseDao;
import com.wch.base.BaseServiceImpl;
import com.wch.mapper.ItemCategoryMapper;
import com.wch.pojo.ItemCategory;
import com.wch.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCategoryServiceImpl extends BaseServiceImpl<ItemCategory> implements ItemCategoryService {
    @Autowired
    ItemCategoryMapper itemCategoryMapper;

    @Override
    public BaseDao<ItemCategory> getBaseDao() {
        return itemCategoryMapper;
    }
}
