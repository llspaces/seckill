package com.llspace.seckill.utils;

/**
 * <p>@filename OrderStatus</p>
 * <p>
 * <p>@description 订单状态枚举类 0 新建未支付 1 已支付 2 已发货 3 已收货 4 已退款 5 已完成</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/2 14:26
 **/

public enum OrderStatus {

    CREATED(0, "新建未支付"),

    PAYED(1, "已付款"),

    DELIVERED(2, "已发货"),

    RECEIVED(3, "已收货"),

    REFUNDED(4, "已退款"),

    FINISHED(5, "已完成");

    private int statusCode;
    private String statusInfo;

    OrderStatus(int statusCode, String statusInfo){
        this.statusCode = statusCode;
        this.statusInfo = statusInfo;
    }

    public int getStatusCode(){
        return this.statusCode;
    }

    public String getStatusInfo(){
        return this.statusInfo;
    }

}
