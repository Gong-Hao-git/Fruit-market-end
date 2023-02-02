package com.wch.service.impl;
import com.wch.base.BaseDao;
import com.wch.base.BaseServiceImpl;
import com.wch.mapper.ItemMapper;
import com.wch.pojo.Item;
import com.wch.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
    @Autowired
    ItemMapper itemMapper;
    @Override
    public BaseDao<Item> getBaseDao() {
        return itemMapper;
    }
}
