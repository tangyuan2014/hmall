package com.hmall.service;

import com.hmall.common.ServerResponse;

import java.util.Map;

public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);

    ServerResponse alipayCallBack(Map<String, String> params);

    ServerResponse queryOrderPayStatus( Integer userId,Long orderNo);
}
