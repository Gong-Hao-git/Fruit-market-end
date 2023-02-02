package com.wch.service.impl;

import com.wch.base.BaseDao;
import com.wch.base.BaseServiceImpl;
import com.wch.mapper.ManagerMapper;
import com.wch.pojo.Manager;
import com.wch.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl extends BaseServiceImpl<Manager> implements ManagerService {
    @Autowired
    ManagerMapper managerMapper;
    @Override
    public BaseDao<Manager> getBaseDao() {
        return managerMapper;
    }
}
