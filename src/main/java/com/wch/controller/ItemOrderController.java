package com.wch.controller;

import com.wch.base.BaseController;
import com.wch.pojo.*;
import com.wch.service.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单管理模块
 */
@RestController
@RequestMapping("/itemOrder")
public class ItemOrderController extends BaseController {
    @Autowired
    private ItemOrderService itemOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ItemService itemService;
    /**
     * 订单管理--分页查查询
     */
    @RequestMapping(value = "/findBySql.do",method = RequestMethod.GET)
    public Data findBySql(ItemOrder itemOrder, Pager<ItemOrder> pages){
        String sql="select * from item_order where isDelete=0 ";
        if(!isEmpty(itemOrder.getCode())){
            sql+=" and code like '%"+itemOrder.getCode()+"%'";
        }
//        if(!isEmpty(itemOrder.getUser().getUserName())){
//            sql+=" and code like '%"+itemOrder.getUser().getUserName()+"%'";
//        }
        sql+=" order by id desc";
        pages=itemOrderService.findBySqlRerturnEntity(sql, pages.getPageNum(), pages.getPageSize());
        if(pages==null){
            Data<String> strdata=new Data<>(20001,"分页查询失败");
            return strdata;
        }
        Data<Pager<ItemOrder>> pagedata=new Data<>(20000,pages);
        return pagedata;
    }

    /**
     * 我的订单--分页查查询
     */
    @RequestMapping(value = "/findBySql1",method = RequestMethod.GET)
    public Data findBySql1(ItemOrder itemOrder, Pager<ItemOrder> pages){ //用户id 状态
        String sql="select * from item_order where isDelete=0 and user_id="+ itemOrder.getUserId();
        if(!isEmpty(itemOrder.getStatus())) {
            if (itemOrder.getStatus() == 0) {
                sql += " and status=0";
            }
            if (itemOrder.getStatus() == 1) {
                sql += " and status=1";
            }
            if (itemOrder.getStatus() == 2) {
                sql += " and status=2";
            }
            if (itemOrder.getStatus() == 3) {
                sql += " and status=3";
            }
        }
        sql+=" order by id desc";
            pages=itemOrderService.findBySqlRerturnEntity(sql, pages.getPageNum(), pages.getPageSize());
            if(pages==null){
                Data<String> strdata=new Data<>(20001,"分页查询失败");
                return strdata;
            }
            Data<Pager<ItemOrder>> pagedata=new Data<>(20000,pages);
            return pagedata;
    }


    /**
     * 订单管理--新增（管理后台只无需新增）(前台：购物车结算(立即购买))
     */
    //（1）、定义一个订单类
    //（2）、订单类的商品id、商品购买数量、商品总价就用前端传过来的，直接设置
    //（3）、生成订单号，写入订单类
    //（4）、生成创建时间，写入订单类
    //（5）、生成订单状态为：待发货，写入订单类
    //（6）、一个订单就生成好了，就写入数据库
    //（7）、此处并不需要后端把购物车里面的数据删除，因为前端在新增订单成功后，会调用购物车删除按钮，把已经购买的商品从购物车里面删除
    @RequestMapping(value = "/exAdd",method = RequestMethod.POST)
    public Data exAdd(@RequestBody BuyDto<Information> buy) throws ParseException {
        //2、获取传入的商品的aa这个集合，并循环
        List<Information> list= buy.getList();
        //3、循环内部写代码：（此处前端应该把商品的总价传过来，单价也传过来）
        for(Information inlist:list){
            ItemOrder itemOrder=new ItemOrder();
            itemOrder.setStatus(0);//新建待发货
            itemOrder.setCode(getOrderNo());//订单编号
            itemOrder.setIsDelete(0);//未删除
            itemOrder.setTotal(inlist.getTotal()+"");//每件商品的小计
            itemOrder.setUserId(buy.getUserId());//购买商品的用户id
            itemOrder.setAddress(buy.getAddress());//购买商品的用户地址
            itemOrder.setItemId(inlist.getItemId());//购买商品的商品id
            itemOrder.setCreateTime(DateUtil.convert(new Date()));//添加当前时间
            itemOrderService.insert(itemOrder);

            //将订单加入订单详情
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setItemId(inlist.getItemId());
            orderDetail.setOrderId(itemOrder.getId());
            orderDetail.setStatus(0);
            orderDetail.setNum(inlist.getNum());
            orderDetail.setTotal(inlist.getTotal()+"");
            orderDetailService.insert(orderDetail);

        }
        Data<String> data=new Data<>(20000,"成功");
        return data;
    }
    private static String date;
    private static long orderNum = 0L;
    public static synchronized String getOrderNo(){
        String str = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        if(date==null||!date.equals(str)){
            date = str;
            orderNum = 0L;
        }
        orderNum++;
        long orderNO = Long.parseLong(date)*10000;
        orderNO += orderNum;
        return orderNO+"";
    }

    /**
     * 我的订单--详情
     */
    @RequestMapping(value = "show",method = RequestMethod.GET)
    public Data show(ItemOrder itemOrder){ //接收订单id
        ItemOrder load1 = itemOrderService.load1(itemOrder.getId());
        if(isEmpty(load1)){
            Data<String> data1=new Data<>(20001,"数据为空");
            return data1;
        }else{
            Data<ItemOrder> data=new Data<>(20000,load1);
            return data;
        }

    }


    /**
     * 订单管理--修改订单状态（管理后台无需修改）
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Data update(@RequestBody ItemOrder itemOrder){ //接收前台的 订单id 和 需要改成的状态
        String sql="update item_order ";
        if(!isEmpty(itemOrder.getStatus())) {
            if (itemOrder.getStatus() == 0) {  //新建待发货
                sql += " set status=0";
            }
            if (itemOrder.getStatus() == 1) {  //已取消
                sql += " set status=1";
            }
            if (itemOrder.getStatus() == 2) {  //待收货
                sql += " set status=2";
            }
            if (itemOrder.getStatus() == 3) {  //已收货
                sql += " set status=3";
            }
        }
        sql+=" where isDelete=0 and id="+ itemOrder.getId();
        itemOrderService.updateBysql(sql);
        Data<ItemOrder> data=new Data<>(20000,itemOrder);
        return data;
    }

    /**
     * 订单管理--用户删除（管理后台无需删除）
     */
//    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
//    public Data delete(@RequestBody ItemOrder itemOrder){  //接收前台的 用户id 和 商品id
//        String sql="update item_order set isDelete=1 where user_id="+itemOrder.getUserId()+" and item_id="+itemOrder.getItemId();
//        itemOrderService.updateBysql(sql);
//        Data<ItemOrder> data=new Data<>(20000,itemOrder);
//        return data;
//    }

    /**
     * 统计每种状态订单的数量
     */
    @RequestMapping(value = "/Ddsl",method = RequestMethod.GET)
    public Data Ddsl(ItemOrder itemOrder){ //userId
        List<Integer> list = new ArrayList<>();
        //查询全部订单的数量
        String sql="select * from item_order where user_id=" + itemOrder.getUserId();
        List<ItemOrder> itemOrders = itemOrderService.listBySqlReturnEntity(sql);
        list.add(itemOrders.size());
        //查询四种状态的订单数量
        for(int i=0;i<4;i++) {
            String sql1 = "select * from item_order where  status=" + i + " and user_id=" + itemOrder.getUserId();
            List<ItemOrder> itemOrders2 = itemOrderService.listBySqlReturnEntity(sql1);
            list.add(itemOrders2.size());
        }
        Data<List<Integer>> data=new Data<>(20000,list);
        return data;
    }




    /**
     * 统计订单新增情况
     */
    @RequestMapping(value = "/Ddti",method = RequestMethod.GET)
    public Data Ddti(){
        String sysYear = DateUtil.getSysYear();//获取当年年份
        List<DdtiDto> list = new ArrayList<>();
        for(int i=0;i<12;i++){
//          String sql="select * from item_order where year(createTime)='"+sysYear+"' and month(createTime)='"+(i+1)+"'";//查询当前年份每月的所有公告
            String sql="select * from item_order where date_format(createTime,'%Y-%c') = '"+sysYear+"-"+(i+1)+"'";//查询当前年份每月的所有公告
            List<ItemOrder> itemOrders = itemOrderService.listBySqlReturnEntity(sql);
            DdtiDto tj=new DdtiDto();
            tj.setMonth(i+1+"月");
            tj.setNum(itemOrders.size());
            list.add(tj);
        }
        Data<List<DdtiDto>> data=new Data<>(20000,list);
        return data;
    }
}
