package com.hmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hmall.common.ServerResponse;
import com.hmall.dao.ShippingMapper;
import com.hmall.pojo.Shipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("iShippingService")
public class ShippingServiceImpl {

    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount=shippingMapper.insert(shipping);
        if(rowCount>0){
            Map result=new HashMap();
            result.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("Address added success",result);
        }
        return ServerResponse.createByErrorMessage("Address added fail");
    }

    public ServerResponse del(Integer userId,Integer shippingId){
        int rowCount=shippingMapper.deleteByUserIdAndId(userId,shippingId);
        if(rowCount>0){
            return ServerResponse.createBySuccess("Address deleted");
        }
        return ServerResponse.createByErrorMessage("Address deleted fail");
    }


    public ServerResponse update(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount=shippingMapper.updateByUserIdAndShippingId(shipping);
        if(rowCount>0){
            return ServerResponse.createBySuccess("Address update success");
        }
        return ServerResponse.createByErrorMessage("Address update fail");
    }

    public ServerResponse<Shipping> select(Integer userId,Integer shippingId){
        Shipping shipping=shippingMapper.selectByUserIdAndId(userId,shippingId);
        if(shipping==null){
                return ServerResponse.createByErrorMessage("No shipping address of this user");
        }
        return ServerResponse.createBySuccess("shipping information",shipping);
    }

    public ServerResponse<PageInfo> list (int pageNum,int pageSize,Integer userId){
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList=shippingMapper.selectByUserId(userId);
        PageInfo pageInfo=new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
