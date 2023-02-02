package com.wch.controller;

import com.wch.pojo.Car;
import com.wch.pojo.Item;
import com.wch.service.CarService;
import com.wch.service.ItemService;
import com.wch.utils.Data;
import com.wch.utils.DateUtil;
import com.wch.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * 购物车
 */
@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private ItemService itemService;

    /**
     * 加入购物车
     * @param car
     * @return
     */
    @RequestMapping(value = "/exAdd",method = RequestMethod.POST)
    public Data exAdd(@RequestBody Car car){
//        System.out.println("22222222222");
//        System.out.println(car);
        Item item = itemService.load(car.getItemId());//根据商品id获取该商品对象
        //未打折的商品单价
        Double price=Double.valueOf(item.getPrice());//将该商品对象转换为double类型(double)
        car.setPrice(price);//将double类型的商品单价存入购物车对象里
        //打折后的商品单价
        if(item.getZk()!=null){
            price=price*item.getZk()/10;//打着后的商品单价（double）
            BigDecimal bg=new BigDecimal(price).setScale(2, RoundingMode.UP);//向上保留两位小数
            price=bg.doubleValue();//bg.doubleValue()：获取转换后的值(double)
            car.setPrice(price);
        }

        //如果该商品在购物车已经存在，则添加该商品加入购物车的数量
        String sql="select * from car where user_id="+car.getUserId()+" and item_id="+car.getItemId();
        Car bySqlReturnEntity = carService.getBySqlReturnEntity(sql);
//        System.out.println("333333333333");
//        System.out.println(bySqlReturnEntity);
        if(bySqlReturnEntity!=null){
            car.setId(bySqlReturnEntity.getId());
            car.setNum(car.getNum()+ bySqlReturnEntity.getNum());//数量
            Double total=price*car.getNum();
//            System.out.println("44444444444");
//            System.out.println(total);
            BigDecimal bg=new BigDecimal(total).setScale(2, RoundingMode.UP);//向上保留两位小数
            total=bg.doubleValue();//bg.doubleValue()：获取转换后的值(double)
            car.setTotal(total+"");//小计
            //获取获取当前添加购物车的时间
            car.setCreateTime(DateUtil.convert(new Date()));
//            System.out.println("11111111111111");
//            System.out.println(car);
            carService.updateById(car);
            Data<Car> data2=new Data<>(20000,car);
            return data2;
        }

        //小计
        Double total=price*car.getNum();
        BigDecimal bg=new BigDecimal(total).setScale(2, RoundingMode.UP);//向上保留两位小数
        total=bg.doubleValue();//bg.doubleValue()：获取转换后的值(double)
        car.setTotal(total+"");
        //获取获取当前添加购物车的时间
        car.setCreateTime(DateUtil.convert(new Date()));
        carService.insert(car);
        Data<Car> data=new Data<>(20000,car);
        return data;
    }

    /**
     * 删除购物车
     * @param carList
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Data delete(@RequestBody List<Car> carList){
        for (Car car : carList) {
            carService.deleteById(car.getId());
        }
        Data<List<Car>> data=new Data<>(20000,carList);
        return data;
    }

    /**
     * 查询购物车列表
     * @param car
     * @return
     */
    @RequestMapping(value = "findBySql",method = RequestMethod.GET)
    public Data findBySql(Car car,Pager<Car> pages){
        String sql = "select * from car where user_id=" + car.getUserId() + " order by createTime desc";
        pages = carService.findBySqlRerturnEntity(sql, pages.getPageNum(), pages.getPageSize());
        if (pages == null) {
            Data<String> strdata = new Data<>(20001, "分页查询失败");
            return strdata;
        }
        Data<Pager<Car>> pagerData = new Data<>(20000, pages);
        return pagerData;
    }

    /**
     * 修改购物车的购买数量
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Data update(@RequestBody Car car){

        BigDecimal bg=new BigDecimal(car.getPrice()*car.getNum()).setScale(2, RoundingMode.UP);//向上保留两位小数
        car.setTotal(bg.doubleValue()+"");
        String total=(car.getNum()*car.getPrice()+"");
        String sql="update car set num="+car.getNum()+" ,total="+car.getTotal()+" where id="+car.getId();
        carService.updateBysql(sql);
        Data<Car> data=new Data<>(20000,car);
        return data;
    }


}
