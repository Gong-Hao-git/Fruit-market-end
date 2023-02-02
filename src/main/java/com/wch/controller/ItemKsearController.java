package com.wch.controller;

import com.wch.base.BaseController;
import com.wch.pojo.ItemKsear;
import com.wch.service.ItemKsearService;
import com.wch.utils.Data;
import com.wch.utils.DateUtil;
import com.wch.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 快捷搜索关键字
 */
@RestController
@RequestMapping("/itemKsear")
public class ItemKsearController extends BaseController {
    @Autowired
    public ItemKsearService itemKsearService;
    /**
     * 分页查询
     */
    @RequestMapping(value = "/findBySql",method = RequestMethod.GET)
    public Data findBySql(ItemKsear itemKsear, Pager<ItemKsear> pages){ //接收每页的数据大小、第几页、name
        String sql="select * from item_ksear where 1=1 ";
        if(!isEmpty(itemKsear.getName())){
            sql+=" and name like'%"+itemKsear.getName()+"%'";
        }
        sql+=" order by id desc";
        pages=itemKsearService.findBySqlRerturnEntity(sql,pages.getPageNum(),pages.getPageSize());
        if(pages==null){
            Data<String> strdata=new Data<>(20001,"分页查询失败");
            return strdata;
        }
        Data<Pager<ItemKsear>> pagerData=new Data<>(20000,pages);
        return pagerData;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "show",method = RequestMethod.GET)
    public Data show(ItemKsear itemKsear){ //接收id
        ItemKsear load = itemKsearService.load(itemKsear.getId());
        if(isEmpty(load)){
            Data<String> data1=new Data<>(20001,"数据为空");
            return data1;
        }
        Data<ItemKsear> data=new Data<>(20000,load);
        return data;
    }

    /**
     * 添加数据
     */
    @RequestMapping(value = "/exAdd",method = RequestMethod.POST)
    public Data exAdd(@RequestBody ItemKsear itemKsear){ //接收 name
        //获取当前时间
        itemKsear.setCreateTime(DateUtil.convert(new Date()));
        int n =itemKsearService.insert(itemKsear);
        if(n<=0){
            Data<String> strdata=new Data<>(20001,"新增失败");
            return strdata;
        }else{
            Data<ItemKsear> data=new Data<>(20000,itemKsear);
            return data;
        }
    }

    /**
     * 修改数据
     */
    @RequestMapping(value = "/exUpdate",method = RequestMethod.PUT)
    public Data exUpdate(@RequestBody ItemKsear itemKsear){ //接收id、name
        ItemKsear load = itemKsearService.load(itemKsear.getId());
        load.setName(itemKsear.getName());
        itemKsearService.updateById(load);
        Data<ItemKsear> data=new Data<>(20000,load);
        return data;
    }

    /**
     * 删除数据
     */
    @RequestMapping(value = "delete",method = RequestMethod.DELETE)
    public Data delete(@RequestBody ItemKsear itemKsear){ //接收id
        itemKsearService.deleteById(itemKsear.getId());
        Data<ItemKsear> data=new Data<>(20000,itemKsear);
        return data;
    }
}
