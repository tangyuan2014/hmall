package com.hmall.common;

import com.google.common.collect.Sets;
import org.apache.commons.lang.enums.Enum;

import java.util.Set;

public class Const {
    public static final String CURRENT_USER="current user";

    public static final String EMAIL="email";

    public static final String USERNAME="username";

    public interface Role{
        int CUSTOMER=0;//普通用户
        int ADMINISTRATER=1;//管理员
    }

    public interface OrderBy{
        Set<String> price_asc_desc = Sets.newHashSet("price_asc","price_desc");
    }

    public interface Cart{
        int CHECKED=1;
        int UNCHECKED=0;
        String LIMIT_NUM_FAIL="LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS="LIMIT_NUM_SUCCESS";
    }

     public enum ProductStatusEnum {
         ON_SALE(1,"ON SALE");

         private int statusCode;
         private String status;
         ProductStatusEnum(int statusCode,String status){
             this.statusCode=statusCode;
             this.status=status;
        }


         public int getStatusCode() {
             return statusCode;
         }

         public String getStatus() {
             return status;
         }
     }

    public enum OrderStatus{
        CANCEL(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_FINISH(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭")
        ;
        private String value;
        private int code;
        OrderStatus(int code,String value){
            this.code=code;
            this.value=value;}

        public String getValue() {
            return value;
        }
        public int getCode() {
            return code;
        }
    }

    public interface TradeStatus{
        String WAIT_BUYER_PAY="WAIT_BUYER_PAY";
        String TRADE_SUCCESS="TRADE_SUCCESS";
        String RESPONSE_SUCCESS="RESPONSE_SUCCESS";
        String RESPONSE_FAIL="RESPONSE_FAIL";
    }

    public enum Platform{
        ALIPAY(1,"支付宝");
        int code;
        String val;
        Platform(int code,String val){
            this.code=code;
            this.val=val;
        }

        public int getCode() {
            return code;
        }

        public String getVal() {
            return val;
        }
    }
}
