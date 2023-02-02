package com.wch.controller;

import com.wch.base.BaseController;
import com.wch.pojo.LoginUser;
import com.wch.pojo.Manager;
import com.wch.pojo.User;
import com.wch.pojo.UserDto;
import com.wch.service.LoginUserService;
import com.wch.service.ManagerService;
import com.wch.service.UserService;
import com.wch.utils.Data;

import com.wch.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;


/**
 * 登录相关
 * */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginUserService loginUserService;

    /**
     * 转向管理员登录页面
     *//*
    @RequestMapping("/mLogin.html")
    public String toLogin(){
        return "login/mLogin";
    }*/
    /**
     * 管理员登录验证
     */
    @RequestMapping(value = "/mLogin.do",method = RequestMethod.POST)
    public Data login(@RequestBody Manager manager, HttpServletRequest request){
        Manager byEntiter=managerService.getByEntity(manager);
        if(byEntiter==null){
            Data<String> strdata=new Data<>(20001,"失败");
            //登录失败，返回登录界面
//            return "redirect:/login/mExit.do";
            return strdata;
        }
        byEntiter.setPassWord(null);
        Data<Manager> mandata=new Data<>(20000,byEntiter);
//        request.getSession().setAttribute(Consts.MANAGE,byEntiter);//将获取到的值存储到session里
//        return "login/mIndex";
         return mandata;
    }
    /**
     * 管理员退出
     *//*
    @RequestMapping("/mExit.do")
    public String mExit(HttpServletRequest request){
        request.getSession().setAttribute(Consts.MANAGE,null);//清空session信息
        return "login/mLogin";
    }*/


  /*  *//**
     * 转向普通用户注册页面
     *//*
    @RequestMapping("/res.html")
    public String toRes(){
        return "login/res";
    }*/
    /**
     * 执行普通用户注册
     */
    @RequestMapping(value = "/res.do",method = RequestMethod.POST)
    @ResponseBody
    public Data res(@RequestBody User user) throws ParseException {
        String userName= user.getUserName();
//        System.out.println("开始名字");
//        System.out.println(userName);
        User byEntity=userService.listByParms(userName);
//        System.out.println("数据");
//        System.out.println(byEntity);
        if(byEntity!=null) {
            Data<String> strdata = new Data<>(20001, "用户名已存在");
            return strdata;
        }else{
            user.setIsDelete(0);
            //获取当前注册的时间
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            user.setCreateTime(df.parse( df.format(new Date())));
            user.setCreateTime(DateUtil.convert(new Date()));

            int n=userService.insert(user);
            if(n<=0){
                Data<String> new2data=new Data<>(20001,"注册失败");
                return new2data;
            }
            user.setPassWord(null);
            Data<User> newdata=new Data<>(20000,user);
            return newdata;
//            return "login/uLogin";
        }

    }


   /* *//**
     * 转向普通用户登录页面
     *//*
    @RequestMapping("/uLogin.html")
    public String toULogin(){
        return "login/uLogin";
    }*/


    /**
     * 执行普通用户登录
     */
    @RequestMapping(value = "/uLogin.do",method = RequestMethod.POST)
    @ResponseBody
    public Data uLogin(@RequestBody User user, LoginUser loginUser,HttpServletRequest request) throws ParseException {
//        System.out.println("进入");
//        System.out.println(user);
        User byEntity = userService.getByEntity(user);
//        System.out.println(byEntity);

        Data<String> strdata = new Data<>(20001, "用户不存在");
        if (byEntity == null || byEntity.getIsDelete()==1) {
//            return "redirect:/login/res.html";
            return strdata;
        } else {

            //登录成功
            loginUser.setUserName(byEntity.getUserName());
            loginUser.setPassWord(byEntity.getPassWord());
            loginUser.setPhone(byEntity.getPhone());
            loginUser.setRealName(byEntity.getRealName());
            loginUser.setSex(byEntity.getSex());
            loginUser.setAddress(byEntity.getAddress());
            loginUser.setEmail(byEntity.getEmail());
            loginUser.setCreateTime(DateUtil.convert(new Date()));//获取并设置当前时间（Date类型）
            loginUser.setIsDelete(byEntity.getIsDelete());

            //登录成功，把登陆成功的用户放到LoginUser表里
            loginUserService.insert(loginUser);

            byEntity.setPassWord(null);
            Data<User> mandata = new Data<>(20000, byEntity);
//            request.getSession().setAttribute("role",2);
//            request.getSession().setAttribute(Consts.USERNAME,byEntity.getUserName());
//            request.getSession().setAttribute(Consts.USERID,byEntity.getId());
//            return "redirect:/login/uIndex.do";//登录成功后跳转到商品主页面
            return mandata;

        }

    }


 /*   *//**
     * 普通用户退出
     *//*
    @RequestMapping("/uExit.do")
    public String uExit(HttpServletRequest request){
        HttpSession session=request.getSession();
        session.invalidate();//清空session
        return "redirect:/login/uIndex.do";
    }*/


    /**
    * 修改密码
    */
//    @RequestMapping(value = "/upass1",method = RequestMethod.PUT)
//    public Data upass1(@RequestParam("userName")String userName,@RequestParam("passWord")String passWord,@RequestParam("newPassword")String newPassword){
//       System.out.println(userName);
//        System.out.println(passWord);
//        System.out.println(newPassword);
//        User user = userService.listByParms(userName);//根据前端传过来的已经登录的账户的用户名查询该用户
//        //        System.out.println("用户数据");
//        //        System.out.println(user);
//        //        System.out.println("比较");
//        //        System.out.println(!passWord.equals(user.getPassWord()));
//        if(!passWord.equals(user.getPassWord())){//如果该用户的密码和传过来的登录密码一致，则可以修改密码
//            Data<String> data=new Data<>(20001,"输入的密码错误，请重新输入");
//            return data;
//        }
//        //user.setPassWord(newPassword);//将该用户的密码设置为前端传过来的新密码
//        String sql="update user set passWord='"+newPassword+" ' where userName= '"+userName+"'";
//        userService.updateBysql(sql);
//        Data<String> data1=new Data<>(20000,"修改密码成功");
//        return data1;
//    }


    @RequestMapping(value = "/upass",method = RequestMethod.PUT)
    public Data upass(@RequestBody UserDto user2){
        User user = userService.listByParms(user2.getUserName());//根据前端传过来的已经登录的账户的用户名查询该用户
        if(!user2.getPassWord().equals(user.getPassWord())){//如果该用户的密码和传过来的登录密码一致，则可以修改密码
            Data<String> data=new Data<>(20001,"输入的旧密码错误，请重新输入");
            return data;
        }
        //将该用户的密码设置为前端传过来的新密码
        String sql="update user set passWord='"+user2.getNewPassword()+"' where userName= '"+user2.getUserName()+"'";
        userService.updateBysql(sql);
        Data<String> data1=new Data<>(20000,"修改密码成功");
        return data1;
    }




}
