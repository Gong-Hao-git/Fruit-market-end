package com.wch.controller;

import com.github.pagehelper.Page;
import com.wch.base.BaseController;
import com.wch.mapper.ItemMapper;
import com.wch.mapper.UserMapper;
import com.wch.pojo.Item;
import com.wch.pojo.ItemCategory;
import com.wch.pojo.Query;
import com.wch.pojo.User;
import com.wch.service.ItemCategoryService;
import com.wch.service.ItemService;
import com.wch.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 商品管理模块
 */
@RestController
@RequestMapping("/item")
public class ItemController extends BaseController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemCategoryService itemCategoryService;

    /**
     * 商品管理--分页查查询
     */
    @RequestMapping(value = "/findBySql.do",method = RequestMethod.GET)
    public Data findBySql(Item item, Pager<Item> pages){
        String sql="select * from item where isDelete=0 ";
        if(!isEmpty(item.getName())){
            sql+=" and name like '%"+item.getName()+"%'";
        }
        sql+=" order by id desc";
        pages=itemService.findBySqlRerturnEntity(sql,pages.getPageNum(),pages.getPageSize());
        if(pages==null){
            Data<String> strdata=new Data<>(20001,"分页查询失败");
            return strdata;
        }
        Data<Pager<Item>> pagedata=new Data<>(20000,pages);
        return pagedata;

    }
    /**
     * 商品管理---新增
     */
    @RequestMapping(value = "/exAdd.do",method = RequestMethod.POST)
    public Data exAdd(@RequestBody Item item){
//        ItemCategory byId=itemCategoryService.getById(item.getCategoryIdTwo());//根据商品的二级id得到二级类目的商品id,进而获得该二级类目商品的实体
//        item.setCategoryIdOne(byId.getPid());//根据二级类目实体的pid为一级类目的id
        System.out.println(item);
        item.setIsDelete(0);
        item.setScNum(0);
        item.setGmNum(0);
        //获取当前时间（Date类型）
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        item.setCreateTime( df.parse( df.format(new Date())));
        item.setCreateTime(DateUtil.convert(new Date()));

        int n = itemService.insert(item);
        if(n<=0){
            Data<String> new2data=new Data<>(20001,"新增失败");
            return new2data;
        }
        Data<Item> data=new Data<>(20000,item);
        return data;
    }

    /**
     * 商品管理--修改--详情(前台商品详情展示)
     */
    @RequestMapping(value = "/show.do",method = RequestMethod.GET)
    public Data show(Item item){
        Item load = itemService.load(item.getId());
        if(isEmpty(load)){
            Data<String> data1=new Data<>(20001,"数据为空");
            return data1;
        }
        Data<User> data=new Data(20000,load);
        return data;

    }

    /**
     * 商品管理--修改
     */
    @RequestMapping(value = "exUpdate.do",method = RequestMethod.PUT)
    public Data exUpdate(@RequestBody Item item) throws IOException {
        ItemCategory byId=itemCategoryService.getById(item.getCategoryIdTwo());//根据商品的二级id得到二级类目的商品id,进而获得该二级类目商品的实体
        item.setCategoryIdOne(byId.getPid());//根据二级类目实体的pid为一级类目的id
        itemService.updateById(item);
        Data<Item> data=new Data<>(20000,item);
        return data;
    }

    /**
     * 商品管理--删除(下架)
     */
    @RequestMapping(value = "/delete.do",method = RequestMethod.DELETE)
    public Data delete(@RequestBody Item item){
        Item load = itemService.load(item.getId());
        load.setIsDelete(1);
        load.setCreateTime(null);
        itemService.updateById(load);
        Data<Item> data=new Data<>(20000,load);
        return data;
    }

    /**
     * 文件上传
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    private Data upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println("进入");
        System.out.println(file);
        String n= UUIDUtils.create();
        String path= SystemContext.getRealPath()+"\\res\\upload\\"+n+ file.getOriginalFilename();//设置文件保存的路径
        //String path="D:\\JAVACX\\OnlineFruitStore\\src\\main\\webapp\\res\\upload\\"+n+ file.getOriginalFilename();//设置文件保存的路径
        file.transferTo(new File(path));//将上传文件写到服务器上指定的文件
        System.out.println("路径");
        System.out.println(request.getContextPath()+"\\res\\upload\\"+n+ file.getOriginalFilename());
        Data<String> data=new Data<>(20000,request.getContextPath()+"\\res\\upload\\"+n+ file.getOriginalFilename());//返回相对路径
        return data;
    }

    /**
     * 前台折扣促销商品展示
     */
    @RequestMapping(value = "/uIndex2",method = RequestMethod.GET)
    public Data uIndex2(){
        String sql="select * from item where isDelete=0 and zk is not null order by zk desc limit 1,10";
        List<Item> zks=itemService.listBySqlReturnEntity(sql);
        Data<List<Item>> data=new Data<>(20000,zks);
        return data;
    }

    /**
     * 前台热门商品展示
     */
    @RequestMapping(value = "/uIndex3",method = RequestMethod.GET)
    public Data uIndex3(){
        String sql="select * from item where isDelete=0 order by gmNum desc limit 0,10";
        List<Item> rxs=itemService.listBySqlReturnEntity(sql);
        Data<List<Item>> data=new Data<>(20000,rxs);
        return data;
    }

    /**
     * 前台精品推荐商品展示
     */
    @RequestMapping(value = "/uIndex4",method = RequestMethod.GET)
    public Data uIndex4(){
        String sql="select * from item where isDelete=0 and name like '精选%'";
        List<Item> tjs=itemService.listBySqlReturnEntity(sql);
        Data<List<Item>> data=new Data<>(20000,tjs);
        return data;
    }

    /**
     * 按关键字或者二级分类查询商品
     */
    @RequestMapping(value = "/shopList",method = RequestMethod.GET)
    public Data shopList(Item item, String condition,Query query,Pager<Item> pages){
//        System.out.println("值开始减肥MV了大地飞歌，发电量过来的");
//        System.out.println(query);
        String sql="select * from item where isDelete=0 ";
        if(!isEmpty(item.getCategoryIdTwo())){
            sql+=" and category_id_two= "+item.getCategoryIdTwo();
        }
        if(!isEmpty(condition)){
            sql+=" and name like '%"+condition+"%'";
        }

        //当最大值存在，最小值不存在
        if(isEmpty(query.getMinMoney())&&!isEmpty(query.getMaxMoney())){
            sql+=" and price<="+query.getMaxMoney();
        }

        //当最大值不存在，最小值存在
        if(!isEmpty(query.getMinMoney())&&isEmpty(query.getMaxMoney())){
            sql+=" and price>="+query.getMinMoney();
        }

//        System.out.println("数据");
//        System.out.println(query.getMinMoney());
//        System.out.println(isEmpty(query.getMinMoney()));
//        System.out.println(!isEmpty(query.getMinMoney()));
        //当最大值最小值都存在
        if(!isEmpty(query.getMinMoney())&&(!isEmpty(query.getMaxMoney()))){
            sql+=" and price>="+query.getMinMoney()+" and price<="+query.getMaxMoney();
        }

        //按综合查询商品
        if("all".equals(query.getScreenField())){
            sql+=" order by createTime desc";
        }

        //按销量查询商品
        if("sales".equals(query.getScreenField())){
            sql+=" order by sales desc";
        }
        //按价格升序查询商品
        if("price".equals(query.getScreenField())&&(query.getSort()==1)){
            sql+=" order by price";
        }

        //按价格降序查询商品
        if("price".equals(query.getScreenField())&&(query.getSort()==2)){
            sql+=" order by price desc";
        }
//        System.out.println("jdhfifoask莫斯科的劳动纠纷看老师");
//        System.out.println(sql);
        pages=itemService.findBySqlRerturnEntity(sql,pages.getPageNum(),pages.getPageSize());
        if(pages==null){
            Data<String> strdata=new Data<>(20001,"分页查询失败");
            return strdata;
        }
        Data<Pager<Item>> pagedata=new Data<>(20000,pages);
          return pagedata;
    }

    /**
     * 随机商品
     */
    @RequestMapping(value = "/itemRandom",method = RequestMethod.GET)
    public Data itemRandom(){
        String sql="select * from item where isDelete=0 ";
        List<Item> items = itemService.listBySqlReturnEntity(sql);
        List<Item> list=new ArrayList();//存放随机生成的商品
        //随机生成 5个商品放进集合
        Random r=new Random();
        for (int i = 0; i < 5; i++) {
            int i1 = r.nextInt(items.size());//随机生成集合范围内的索引
            Item item = items.get(i1);//根据索引的到对应的元素
            if (list.contains(item)) {
                i=i-1;//如果该索引处的数据已经list集合里存在，那么将循环索引-1
            }else{
                list.add(item);//如果该索引处的元素不存在的话，将瓷元素添加进list集合里
            }
        }
        Data<List<Item>> data=new Data<>(20000,list);
        return data;
    }

}
