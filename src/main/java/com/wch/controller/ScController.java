package com.wch.controller;


import com.wch.base.BaseController;
import com.wch.pojo.Item;
import com.wch.pojo.ItemCategory;
import com.wch.pojo.News;
import com.wch.pojo.Sc;
import com.wch.service.ItemService;
import com.wch.service.ScService;
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
import java.util.List;

/**
 * 商品收藏
 */
@RestController
@RequestMapping("/sc")
public class ScController extends BaseController {
    @Autowired
    private ScService scService;
    @Autowired
    private ItemService itemService;

    /**
     * 商品收藏
     */
    @RequestMapping(value = "/exAdd", method = RequestMethod.POST)
    public Data exAdd(@RequestBody Sc sc) {
        //判断该商品是否收藏
        String sql = "select * from sc where user_id=" + sc.getUserId()+" and item_id=" + sc.getItemId();
        Sc bySqlReturnEntity = scService.getBySqlReturnEntity(sql);
//        System.out.println("1111111111111");
//        System.out.println(bySqlReturnEntity);
        if (!isEmpty(bySqlReturnEntity)) {
            Data<String> strdata = new Data<>(20001, "该商品已收藏");
            return strdata;
        } else {
            //保存到收藏列表
            sc.setCreateTime(DateUtil.convert(new Date()));//获取获取当前时间
            scService.insert(sc);
            //商品表收藏数+1
            Item item = itemService.load(sc.getItemId());
            item.setScNum(item.getScNum() + 1);
            item.setCreateTime(null);//不更新时间
            itemService.updateById(item);
            Data<Sc> data = new Data<>(20000, sc);
            return data;
        }

    }

    /**
     * 商品收藏列表分页查询
     */
    @RequestMapping(value = "findBySql", method = RequestMethod.GET)
    public Data findBySql(Sc sc, Pager<Sc> pages) {
        String sql = "select * from sc where user_id=" + sc.getUserId() + " order by createTime desc";
        pages = scService.findBySqlRerturnEntity(sql, pages.getPageNum(), pages.getPageSize());
        if (pages == null) {
            Data<String> strdata = new Data<>(20001, "分页查询失败");
            return strdata;
        }
        Data<Pager<Sc>> pagerData = new Data<>(20000, pages);
        return pagerData;
    }

    /**
     * 取消收藏
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Data delete(@RequestBody Sc sc) {
        scService.deleteById(sc.getId());
        Data<Sc> data = new Data<>(20000, sc);
        return data;
    }

    /**
     * 查询该用户是否收藏了该商品  详情页五角星星星亮与不亮
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public Data show(Sc sc) {
        //判断该商品是否收藏
        String sql = "select * from sc where user_id=" + sc.getUserId()+" and item_id=" + sc.getItemId();
        Sc bySqlReturnEntity = scService.getBySqlReturnEntity(sql);
        if (!isEmpty(bySqlReturnEntity)) {//商品已被收藏
            Data<Sc> strdata = new Data<>(20000, bySqlReturnEntity);
            return strdata;
        } else if (isEmpty(bySqlReturnEntity)){//商品未被收藏
            Data<Sc> strdata2 = new Data<>(20000, null);
            return strdata2;
        }else{
            Data<Sc> strdata3 = new Data<>(20001, null);
            return strdata3;
        }

    }
}


