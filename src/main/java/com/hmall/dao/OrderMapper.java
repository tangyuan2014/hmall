package com.hmall.dao;

import com.hmall.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByUserIdAndOrderNo(@Param(value = "userId") int userId,@Param(value = "orderNo") long orderNo);

    Order selectByOrderNo(long orderNo);
}