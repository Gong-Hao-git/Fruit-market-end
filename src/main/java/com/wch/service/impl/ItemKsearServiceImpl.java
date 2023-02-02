package com.wch.service.impl;

import com.wch.base.BaseDao;
import com.wch.base.BaseServiceImpl;
import com.wch.mapper.ItemKsearMapper;
import com.wch.pojo.ItemKsear;
import com.wch.service.ItemKsearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ItemKsearServiceImpl extends BaseServiceImpl<ItemKsear> implements ItemKsearService {
    @Autowired
    private ItemKsearMapper itemKsearMapper;

    @Override
    public BaseDao<ItemKsear> getBaseDao() {
        return itemKsearMapper;
    }
}
