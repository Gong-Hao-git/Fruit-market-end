package com.wch.service.impl;

import com.wch.base.BaseDao;
import com.wch.base.BaseServiceImpl;
import com.wch.mapper.MessageMapper;
import com.wch.pojo.Message;
import com.wch.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    @Override
    public BaseDao<Message> getBaseDao() {
        return messageMapper;
    }
}
