package com.hmall.service;

import com.hmall.common.ServerResponse;
import com.hmall.vo.CartVo;

public interface ICartService {

    ServerResponse<CartVo> addItem(Integer userId, Integer count, Integer productId);

    ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId);

    ServerResponse<CartVo> deleteProduct (Integer userId, String productIds);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnselectAll(Integer userId,Integer checked);

    ServerResponse<CartVo> selectOrUnselect(Integer userId, Integer productId,Integer checked);

    public ServerResponse<Integer> getCartItemQuantity(Integer userId);
}
