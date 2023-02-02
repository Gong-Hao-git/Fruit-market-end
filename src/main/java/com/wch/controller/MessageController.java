package com.wch.controller;

import com.wch.base.BaseController;
import com.wch.pojo.LytiDto;
import com.wch.pojo.Message;
import com.wch.service.MessageService;
import com.wch.utils.Data;
import com.wch.utils.DateUtil;
import com.wch.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 留言管理模块
 */
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {
    @Autowired
    private MessageService messageService;

    /**
     * 留言管理--分页查查询
     */
    @RequestMapping(value = "/findBySql.do",method = RequestMethod.GET)
    public Data findBySql(Message message, Pager<Message> pages){
        String sql="select * from message where 1=1 ";
        if(!isEmpty(message.getName())){
            sql+=" and name like '%"+message.getName()+"%'";
        }
        if(!isEmpty(message.getPhone())){
            sql+=" and phone like '%"+message.getPhone()+"%'";
        }
        if(!isEmpty(message.getContent())){
            sql+=" and content like '%"+message.getContent()+"%'";
        }
        if(!isEmpty(message.getType())){
            sql+=" and type like '%"+message.getType()+"%'";
        }
        sql+=" order by id";
        pages = messageService.findBySqlRerturnEntity(sql, pages.getPageNum(), pages.getPageSize());
         if(pages==null){
             Data<String> strdata=new Data<>(20001,"分页查询失败");
             return strdata;
         }
         Data<Pager<Message>> pagerData=new Data<>(20000,pages);
         return pagerData;


    }
    /**
     * 留言管理--新增（管理后台只无需新增）(前台用户留言)
     */
    @RequestMapping(value = "/exAdd",method = RequestMethod.POST)
    public Data exAdd(@RequestBody Message message) throws ParseException {
        //获取当前留言时间
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        message.setCreateTime(df.parse( df.format(new Date())));
        message.setCreateTime(DateUtil.convert(new Date()));
        int n = messageService.insert(message);
        if(n<=0){
            Data<String> strdata=new Data<>(20001,"提交失败");
            return strdata;
        }
        Data<Message> data=new Data<>(20000,message);
            return data;
    }
    /**
     * 留言管理--修改（管理后台只无需修改）
     */

    /**
     * 留言管理--删除
     */
    @RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
    public Data delete(@RequestBody Message  message){
        messageService.deleteByEntity(message);
        Data<Message> data=new Data<>(20000,message);
        return data;
    }

    /**
     * 统计订用户留言情况
     */
    @RequestMapping(value = "/Lyti",method = RequestMethod.GET)
    public Data Lyti(){
        List<LytiDto> list = new ArrayList<>();
        for(int i=0;i<4;i++){
            String sql="select * from message where type="+(i+1);//查询每种类型的留言情况
            List<Message> messages = messageService.listBySqlReturnEntity(sql);
            LytiDto tj=new LytiDto();
            tj.setType(i+1);
            tj.setNum(messages.size());
            list.add(tj);
        }
        Data<List<LytiDto>> data=new Data<>(20000,list);
        return data;
    }
}
