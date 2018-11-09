package com.hmall.service.impl;

import com.google.common.collect.Lists;
import com.hmall.common.Const;
import com.hmall.common.ServerResponse;
import com.hmall.dao.CartMapper;
import com.hmall.pojo.Cart;
import com.hmall.service.ICartService;
import com.hmall.vo.CartProductVo;
import com.hmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iCartService")
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    public ServerResponse addItem(Integer userId,Integer count, Integer productId){
        Cart cart=cartMapper.selectCartByUserIdProductId(userId,productId);
        if(cart==null){
            Cart cartItem=new Cart();
            cartItem.setProductId(productId);
            cartItem.setQuantity(count);
            cartItem.setUserId(userId);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cartItem);
        }else{
            count=count+cart.getQuantity();
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
    }

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo=new CartVo();
        List<Cart> cartList=cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList= Lists.newArrayList();


    }



}
