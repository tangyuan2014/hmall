package com.hmall.dao;

import com.hmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByUserIdAndId(@Param("userId") int userId,@Param("shippingId") int shippingId);

    int updateByUserIdAndShippingId(Shipping record);

    Shipping selectByUserIdAndId(@Param("userId") int userId,@Param("shippingId") int shippingId);

    List<Shipping> selectByUserId(int userId);

}