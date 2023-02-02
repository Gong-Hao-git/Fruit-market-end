package com.wch.service.impl;

import com.wch.base.BaseDao;
import com.wch.base.BaseServiceImpl;
import com.wch.mapper.ScMapper;
import com.wch.pojo.Sc;
import com.wch.service.ScService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScServiceImpl extends BaseServiceImpl<Sc> implements ScService {

    @Autowired
    private ScMapper scMapper;
    @Override
    public BaseDao<Sc> getBaseDao() {
        return scMapper;
    }
}
