package com.hmall.service;

import com.hmall.common.ServerResponse;

public interface IOrderService{

        ServerResponse pay(Long orderNo, Integer userId, String path);
        }
