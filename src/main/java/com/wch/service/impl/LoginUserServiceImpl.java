package com.wch.service.impl;

import com.wch.base.BaseDao;
import com.wch.base.BaseServiceImpl;
import com.wch.mapper.LoginUserMapper;
import com.wch.pojo.LoginUser;
import com.wch.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class LoginUserServiceImpl extends BaseServiceImpl<LoginUser> implements LoginUserService {
    @Autowired
    LoginUserMapper loginUserMapper;

    @Override
    public BaseDao<LoginUser> getBaseDao() {
        return loginUserMapper;
    }
}
