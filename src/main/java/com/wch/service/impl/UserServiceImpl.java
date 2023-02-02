package com.wch.service.impl;

import com.wch.base.BaseDao;
import com.wch.base.BaseServiceImpl;
import com.wch.mapper.UserMapper;
import com.wch.pojo.User;
import com.wch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public BaseDao<User> getBaseDao() {
        return userMapper;
    }
}
