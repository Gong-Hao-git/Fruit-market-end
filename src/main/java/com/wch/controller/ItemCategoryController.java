package com.wch.controller;
import com.wch.base.BaseController;
import com.wch.pojo.CategoryDto;
import com.wch.pojo.ItemCategory;
import com.wch.service.ItemCategoryService;
import com.wch.utils.Data;
import com.wch.utils.DateUtil;
import com.wch.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类目管理模块
 */
@RestController
@RequestMapping("/itemCategory")
public class ItemCategoryController extends BaseController {
    @Autowired
    private ItemCategoryService itemCategoryService;



    /**
     * 类目管理--一级类目--分页查查询
     */
   @RequestMapping(value = "findBySql1.do",method = RequestMethod.GET)
//   @GetMapping("/findBySql1.do")

    public Data findBySql1(ItemCategory itemCategory, Pager<ItemCategory> pages){
        String sql="select * from item_category where isDelete=0 and pid is null ";
        if(!isEmpty(itemCategory.getName())){
            sql+=" and name like '%"+itemCategory.getName()+"%'";
        }
        sql+=" order by id";
        pages=itemCategoryService.findBySqlRerturnEntity(sql, pages.getPageNum(), pages.getPageSize());
        if(pages==null){
            Data<String> strdata=new Data<>(20001,"分页查询失败");
            return strdata;
        }
        Data<Pager<ItemCategory>> pagerData=new Data<>(20000,pages);
            return pagerData;
    }
    /**
     * 类目管理--二级类目--分页查查询
     */
    @RequestMapping(value = "findBySql2.do",method = RequestMethod.GET)
    public Data findBySql2(ItemCategory itemCategory,Pager<ItemCategory> pages){
//        System.out.println(itemCategory);

//        String sql="select * from item_category where isDelete=0 and pid is not null ";
//        String sql="select * from item_category where isDelete=0 and pid="+itemCategory.getId();
        String sql="select * from item_category where isDelete=0 and pid="+itemCategory.getPid();
        if(!isEmpty(itemCategory.getName())){
            sql+=" and name like '%"+itemCategory.getName()+"%'";
        }
        sql+=" order by id";
        pages=itemCategoryService.findBySqlRerturnEntity(sql, pages.getPageNum(), pages.getPageSize());
        if(pages==null){
            Data<String> strdata=new Data<>(20001,"分页查询失败");
            return strdata;
        }
        Data<Pager<ItemCategory>> pagerData=new Data<>(20000,pages);
        return pagerData;
    }

    /**
     * 类目管理--新增一级类目
     */
    @RequestMapping(value = "exAdd1.do",method = RequestMethod.POST)
    public Data exAdd1(@RequestBody ItemCategory itemCategory) throws ParseException {
        itemCategory.setIsDelete(0);
        //获取当前时间（Date类型）
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        itemCategory.setCreateTime( df.parse( df.format(new Date())));
        itemCategory.setCreateTime(DateUtil.convert(new Date()));
        String sql="select * from item_category where id>=1000 and id<=19999 order by id desc limit 1";//查出最大的一条数据
        ItemCategory bySqlReturnEntity = itemCategoryService.getBySqlReturnEntity(sql);
        itemCategory.setId(bySqlReturnEntity.getId()+1);
        int n=itemCategoryService.insert(itemCategory);
        if(n<=0){
            Data<String> new2data=new Data<>(20001,"新增失败");
            return new2data;
        }
        Data<ItemCategory> data=new Data<>(20000,itemCategory);
        return data;

    }
    /**
     * 类目管理--新增二级类目
     */
    @RequestMapping(value = "exAdd2.do",method = RequestMethod.POST)
    public Data exAdd2(@RequestBody ItemCategory itemCategory) throws ParseException {
//        System.out.println(itemCategory);
        itemCategory.setIsDelete(0);
        //获取当前时间（Date类型）
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        itemCategory.setCreateTime( df.parse( df.format(new Date())));
        itemCategory.setCreateTime(DateUtil.convert(new Date()));
        itemCategory.setPid(itemCategory.getPid());
//        System.out.println("pid的值。。。。。");
//        System.out.println(itemCategory.getPid());
        String sql="select * from item_category where id>="+itemCategory.getPid()*100000+" and id<="+(itemCategory.getPid()*100000+99999) +" order by id desc limit 1";
        ItemCategory bySqlReturnEntity = itemCategoryService.getBySqlReturnEntity(sql);
//        System.out.println("pid的值。。。。。");
//        System.out.println(bySqlReturnEntity);
        int n=0;
        if(bySqlReturnEntity==null){
            itemCategory.setId(itemCategory.getPid()*100000+10001);
            n=itemCategoryService.insert(itemCategory);
        }else{
            itemCategory.setId(bySqlReturnEntity.getId()+1);
            n=itemCategoryService.insert(itemCategory);
        }
        if(n<=0){
            Data<String> new2data=new Data<>(20001,"新增失败");
            return new2data;
        }
        Data<ItemCategory> data=new Data<>(20000,itemCategory);
        return data;
    }

    /**
     * 类目管理--修改 --一、二级类目详情
     */

    @RequestMapping(value = "/show.do",method = RequestMethod.GET)
    public Data show(ItemCategory itemCategory){
        ItemCategory load = itemCategoryService.load(itemCategory.getId());
        if(isEmpty(load)){
            Data<String> data1=new Data<>(20001,"数据为空");
            return data1;
        }
        Data<ItemCategory> data=new Data<>(20000,load);
        return data;
    }

    /**
     * 类目管理--修改一级类目
     */
    @RequestMapping(value = "/exUpdate1.do",method = RequestMethod.PUT)
    public Data exUpdate1(@RequestBody ItemCategory itemCategory){
        itemCategoryService.updateById(itemCategory);
        Data<ItemCategory> data=new Data<>(20000,itemCategory);
        return data;
    }

    /**
     * 类目管理--修改二级类目
     */
    @RequestMapping(value = "/exUpdate2.do",method = RequestMethod.PUT)
    public Data exUpdate2(@RequestBody ItemCategory itemCategory){
        itemCategoryService.updateById(itemCategory);
        Data<ItemCategory> data=new Data<>(20000,itemCategory);
        return data;
    }

    /**
     * 类目管理--删除二级类目
     */
    @RequestMapping(value = "/delete2.do",method = RequestMethod.DELETE)
    public Data delete2(@RequestBody ItemCategory itemCategory) throws ParseException {
        ItemCategory load = itemCategoryService.load(itemCategory.getId());

//        String s = String.valueOf(load.getCreateTime());
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
//        Date d = sf.parse(s);
//        System.out.println(d);

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        load.setCreateTime( df.parse( df.format(load.getCreateTime())));
//        System.out.println(load.getCreateTime());

//        System.out.println("开始时间");
//        System.out.println(System.currentTimeMillis());
//        Date date= new Date(System.currentTimeMillis());
//        String pattern="yyyy-MM-dd HH:mm:ss";
//        SimpleDateFormat sdf= new SimpleDateFormat(pattern);
//        String datestr=sdf.format(date);// format  为格式化方法
//        System.out.println("时间");
//        System.out.println(datestr);

//        load.setCreateTime(null);
        load.setIsDelete(1);
        itemCategoryService.updateById(load);
        Data<ItemCategory> data=new Data<>(20000,load);
        return data;
    }

    /**
     * 类目管理--删除一级类目
     */
    @RequestMapping(value = "/delete1.do",method = RequestMethod.DELETE)
    public Data delete1(@RequestBody ItemCategory itemCategory){
        //删除一级目录本身
        ItemCategory load = itemCategoryService.load(itemCategory.getId());
        load.setIsDelete(1);
        load.setCreateTime(null);
        itemCategoryService.updateById(load);
        //删除一级目录下的二级目录
        String sql="update item_category set isDelete=1 where pid="+load.getId();
        itemCategoryService.updateBysql(sql);
        Data<ItemCategory> data=new Data<>(20000,load);
        return data;
    }


    /**
     * 前台首页类目展示
     */
    @RequestMapping(value = "/uIndex",method = RequestMethod.GET)
    public Data uIndex(){
        String sql="select * from item_category where isDelete=0 and pid is null order by createTime  ";
        List<ItemCategory> fatherLists = itemCategoryService.listBySqlReturnEntity(sql);//查询所有一级类目返回一个List集合
//        System.out.println("父类类目。。。。");
//        System.out.println(fatherLists);
        List<CategoryDto> list=new ArrayList<>();
        if(!CollectionUtils.isEmpty(fatherLists)){//如果一级类目集合不为空
            for(ItemCategory fatherList:fatherLists){//从fatherLists集合中依次取出一个fatherList对象
                CategoryDto dto=new CategoryDto();
                dto.setFather(fatherList);
                //查询该一级类目下的二级类目
                String sql2="select * from item_category where isDelete=0 and pid="+fatherList.getId();
                List<ItemCategory> childrensLists=itemCategoryService.listBySqlReturnEntity(sql2);
                dto.setChildrens(childrensLists);
                list.add(dto);
            }
        }
        Data<List<CategoryDto>> data=new Data<>(20000,list);
//        System.out.println("数据。。。。。。。");
//        System.out.println(list);
        return data;
    }


}

