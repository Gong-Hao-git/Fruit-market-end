package com.wch.controller;

import com.wch.base.BaseController;
import com.wch.pojo.User;
import com.wch.service.UserService;
import com.wch.utils.Data;
import com.wch.utils.DateUtil;
import com.wch.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 用户管理模块
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * 用户管理--分页查查询
     */

    @RequestMapping(value = "/findBySql.do",method = RequestMethod.GET)
    public Data findBySql(User user,Pager<User> pages){
        String sql="select * from user where 1=1 and isDelete=0 ";
        if(!isEmpty(user.getUserName())){
            sql+=" and userName like '%"+user.getUserName()+"%'";
        }
        if(!isEmpty(user.getPhone())){
            sql+=" and phone like '%"+user.getPhone()+"%' ";
        }
        if(!isEmpty(user.getRealName())){
            sql+=" and realName like '%"+user.getRealName()+"%' ";
        }
        if(!isEmpty(user.getSex())){
            sql+=" and sex like '%"+user.getSex()+"%' ";
        }
        if(!isEmpty(user.getAddress())){
            sql+=" and address like '%"+user.getAddress()+"%' ";
        }
        if(!isEmpty(user.getEmail())){
            sql+=" and email like '%"+user.getEmail()+"%' ";
        }
        sql+=" order by id";

        pages=userService.findBySqlRerturnEntity(sql,pages.getPageNum(),pages.getPageSize());
        if(pages==null){
            Data<String> strdata=new Data<>(20001,"分页查询失败");
            return strdata;
        }
        Data<Pager<User>> pagedata=new Data<>(20000,pages);
        return pagedata;
    }

    /**
     * 用户管理---新增（用户管理后台只无需新增）
     */
    @RequestMapping(value = "/exAdd.do",method = RequestMethod.POST)
    public Data exAdd(@RequestBody User user) throws ParseException {
        User byEntity = userService.listByParms(user.getUserName());
        if(byEntity!=null){
            Data<String> strdata = new Data<>(20001, "用户名已存在");
            return strdata;
        }else {
            user.setIsDelete(0);
            //获取当前添加的的时间
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            user.setCreateTime(df.parse( df.format(new Date())));
              user.setCreateTime(DateUtil.convert(new Date()));
            int n=userService.insert(user);
            if(n<=0){
                Data<String> new2data=new Data<>(20001,"注册失败");
                return new2data;
            }
            Data<User> newdata=new Data<>(20000,user);
            return newdata;
        }
    }
    /**
     * 用户管理--修改--详情
     */
    @RequestMapping(value = "/show.do",method = RequestMethod.GET)
    public Data show(User user){
        User load = userService.load(user.getId());
        if(isEmpty(load)){
            Data<String> data1=new Data<>(20001,"数据为空");
            return data1;
        }
        Data<User> data=new Data(20000,load);
        return data;
    }
    /**
     * 用户管理--修改（前后台）
     */
    @RequestMapping(value = "/exUpdate.do",method = RequestMethod.PUT)
    public Data exUpdate(@RequestBody User user){
        userService.updateById(user);
        Data<User> data=new Data<>(20000,user);
        return data;
    }
    /**
     * 用户管理--删除（用户管理后台无需删除）
     */
    @RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
    public Data delete(@RequestBody User user){
        String sql="update user set isDelete=1 where id="+user.getId();
        userService.updateBysql(sql);
        Data<User> data=new Data<>(20000,user);
        return data;
    }

}
