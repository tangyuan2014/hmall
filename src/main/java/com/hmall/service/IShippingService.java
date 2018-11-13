package com.hmall.service;

import com.hmall.common.ServerResponse;
import com.hmall.pojo.Shipping;

public interface IShippingService {

    ServerResponse addAddress(Integer userId, Shipping shipping);
}
