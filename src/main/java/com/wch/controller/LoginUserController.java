package com.wch.controller;

import com.wch.base.BaseController;
import com.wch.pojo.LoginUser;
import com.wch.service.LoginUserService;
import com.wch.utils.Data;
import com.wch.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 存放登录用户
 */
@RestController
@RequestMapping("/loginUser")
public class LoginUserController extends BaseController {
    @Autowired
    private LoginUserService loginUserService;

    /**
     *分页查询
     */
    @RequestMapping(value = "/findBySql.do",method = RequestMethod.GET)
    public Data findBySql(Pager<LoginUser> pages){
        String sql="select * from loginUser order by createTime desc";
        pages=loginUserService.findBySqlRerturnEntity(sql,pages.getPageNum(),pages.getPageSize());
        if(pages==null){
            Data<String> strdata=new Data<>(20001,"分页查询失败");
            return strdata;
        }
        Data<Pager<LoginUser>> pagedata=new Data<>(20000,pages);
        return pagedata;
    }

}
