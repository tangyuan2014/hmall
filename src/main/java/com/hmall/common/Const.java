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


}
