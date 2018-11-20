package com.hmall.service;

import com.github.pagehelper.PageInfo;
import com.hmall.common.ServerResponse;
import com.hmall.pojo.Shipping;

public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse del(Integer userId, Integer shippingId);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer userId,Integer shippingId);

    ServerResponse<PageInfo> list (int pageNum, int pageSize, Integer userId);
}
