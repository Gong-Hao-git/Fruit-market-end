package com.wch.controller;

import com.wch.pojo.ItemOrder;
import com.wch.pojo.OrderDetail;
import com.wch.service.OrderDetailService;
import com.wch.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * 订单详情
 */
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;
    /**
     * 订单详情（查看购买详情）
     * @param orderDetail
     * @return
     */
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    public Data show(OrderDetail orderDetail){
        String sql="select * from order_detail where order_id="+orderDetail.getOrderId();
        OrderDetail bySqlReturnEntity = orderDetailService.getBySqlReturnEntity(sql);
        if(bySqlReturnEntity==null){
            Data<String> data1=new Data<>(20001,"没有此订单");
            return data1;
        }
        Data<OrderDetail> data=new Data<>(20000,bySqlReturnEntity);
        return data;

    }

    /**
     * 修改订单退货状态（管理后台无需修改）
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Data update(@RequestBody OrderDetail orderDetail){
        String sql="update order_detail ";
        if(!isEmpty(orderDetail.getStatus())) {
            if (orderDetail.getStatus() == 0) {  //未退货
                sql += " set status=0";
            }
            if (orderDetail.getStatus() == 1) {  //已退货
                sql += " set status=1";
            }

        }
        sql+=" where id="+ orderDetail.getId();
        orderDetailService.updateBysql(sql);
        Data<OrderDetail> data=new Data<>(20000,orderDetail);
        return data;
    }
}

