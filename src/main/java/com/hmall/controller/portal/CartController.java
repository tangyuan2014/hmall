package com.hmall.controller.portal;

import com.hmall.common.Const;
import com.hmall.common.ResponseCode;
import com.hmall.common.ServerResponse;
import com.hmall.pojo.User;
import com.hmall.service.ICartService;
import com.hmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @RequestMapping("list")
    @ResponseBody
    public ServerResponse<CartVo> list(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iCartService.list(user.getId());
    }

    @RequestMapping("add")
    @ResponseBody
    public ServerResponse<CartVo> addItem(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iCartService.addItem(user.getId(), count, productId);
    }

    @RequestMapping("update")
    @ResponseBody
    public ServerResponse<CartVo> update(HttpSession session, Integer count, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iCartService.update(user.getId(), count, productId);
    }

    @RequestMapping("delete")
    @ResponseBody
    public ServerResponse<CartVo> delete(HttpSession session, String productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iCartService.deleteProduct(user.getId(), productId);
    }

    @RequestMapping("selectAll")
    @ResponseBody
    public ServerResponse<CartVo> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iCartService.selectOrUnselectAll(user.getId(),Const.Cart.CHECKED);
    }

    @RequestMapping("unselectAll")
    @ResponseBody
    public ServerResponse<CartVo> UnSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iCartService.selectOrUnselectAll(user.getId(),Const.Cart.UNCHECKED);
    }

    @RequestMapping("select_product")
    @ResponseBody
    public ServerResponse<CartVo> SelectProduct(HttpSession session,Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iCartService.selectOrUnselect(user.getId(),Const.Cart.CHECKED,productId);
    }

    @RequestMapping("unselect_product")
    @ResponseBody
    public ServerResponse<CartVo> unSelectProduct(HttpSession session,Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iCartService.selectOrUnselect(user.getId(),Const.Cart.UNCHECKED,productId);
    }

    @RequestMapping("getcart_product_count")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount (HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createBySuccess(0);
        }
        return iCartService. getCartItemQuantity(user.getId());
    }








}
