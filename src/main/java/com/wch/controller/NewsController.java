package com.wch.controller;

import com.wch.base.BaseController;
import com.wch.pojo.*;
import com.wch.service.NewsService;
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
 * 公告管理模块
 */
@RestController
@RequestMapping("/news")
public class NewsController extends BaseController {

    @Autowired
    private NewsService newsService;

    /**
     * 公告管理--分页查查询（前后台）
     */
    @RequestMapping(value = "/findBySql.do",method = RequestMethod.GET)
    public Data findBySql(News news, Pager<News> pages){
        String sql="select * from news where 1=1 ";
        if(!isEmpty(news.getName())){
            sql+=" and name like'%"+news.getName()+"%'";
        }
        sql+=" order by id desc";
        pages=newsService.findBySqlRerturnEntity(sql,pages.getPageNum(),pages.getPageSize());
        if(pages==null){
            Data<String> strdata=new Data<>(20001,"分页查询失败");
            return strdata;
        }
        Data<Pager<News>> pagedata=new Data<>(20000,pages);
        return pagedata;
    }
    /**
     * 公告管理--新增
     */
    @RequestMapping(value = "/exAdd.do",method = RequestMethod.POST)
    public Data exAdd(@RequestBody News news) throws ParseException {
//        System.out.println(news);
        //获取当前时间（Date类型）
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        news.setCreateTime( df.parse( df.format(new Date())));
        news.setCreateTime(DateUtil.convert(new Date()));

        int n = newsService.insert(news);
        if(n<=0){
            Data<String> new2data=new Data<>(20001,"新增失败");
            return new2data;
        }
        Data<News> data=new Data<>(20000,news);
        return data;
    }

    /**
     *公告管理--修改 --详情(前后台)
     */
    @RequestMapping(value = "/show.do",method = RequestMethod.GET)
    public Data show(News news){
        News load = newsService.load(news.getId());
        if(isEmpty(load)){
            Data<String> data1=new Data<>(20001,"数据为空");
            return data1;
        }
        Data<News> data=new Data<>(20000,load);
        return data;
    }
    /**
     * 公告管理--修改
     */
    @RequestMapping(value = "/exUpdate.do",method = RequestMethod.PUT)
    public Data exUpdate(@RequestBody News news){
        News load = newsService.load(news.getId());
        load.setName(news.getName());
        load.setContent(news.getContent());
        newsService.updateById(load);
        Data<News> data=new Data<>(20000,load);
        return data;
    }

    /**
     * 公告管理--删除
     */
    @RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
    public Data delete(@RequestBody News news){
        newsService.deleteByEntity(news);
        Data<News> data=new Data<>(20000,news);
        return data;
    }


    /**
     * 统计公告发布情况
     */
    @RequestMapping(value = "/Ggti",method = RequestMethod.GET)
    public Data Ggti(){
        String sysYear = DateUtil.getSysYear();//获取当年年份
        List<GgtiDto> list = new ArrayList<>();
        for(int i=0;i<12;i++){
            String sql="select * from news where year(createTime)='"+sysYear+"' and month(createTime)='"+(i+1)+"'";//查询当前年份每月的所有公告
            List<News> news = newsService.listBySqlReturnEntity(sql);
//            System.out.println("1111111111");
//            System.out.println(news);
            GgtiDto tj=new GgtiDto();
            tj.setMonth(i+1+"月");
            tj.setNum(news.size());
            list.add(tj);
        }
        Data<List<GgtiDto>> data=new Data<>(20000,list);
        return data;
    }

}
