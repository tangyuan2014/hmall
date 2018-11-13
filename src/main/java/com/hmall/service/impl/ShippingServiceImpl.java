package com.hmall.service.impl;

import com.hmall.common.ServerResponse;
import com.hmall.dao.ShippingMapper;
import com.hmall.pojo.Shipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("iShippingService")
public class ShippingServiceImpl {

    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse addAddress(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount=shippingMapper.insert(shipping);
        if(rowCount>0){
            Map result=new HashMap();
            result.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("Address added success",result);
        }
        return ServerResponse.createByErrorMessage("Address added fail");


    }
}
