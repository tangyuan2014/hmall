package com.hmall.service;

import com.hmall.common.ServerResponse;
import com.hmall.vo.CartVo;

public interface ICartService {

    ServerResponse<CartVo> addItem(Integer userId, Integer count, Integer productId);

    ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId);
}
