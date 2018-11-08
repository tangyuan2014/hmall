package com.hmall.controller.portal.backend;


import com.hmall.common.Const;
import com.hmall.common.ServerResponse;
import com.hmall.pojo.Product;
import com.hmall.pojo.User;
import com.hmall.service.IProductService;
import com.hmall.service.IUserService;
import com.hmall.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("manage/product/")
public class ManageProductController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            return iProductService.saveProduct(product);
        }
        return ServerResponse.createByErrorMessage("YOU have no permission");
    }

    @RequestMapping("set_salestatus")
    @ResponseBody
    public ServerResponse<String> setSalseStatus(HttpSession session,Integer productId, Integer status){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            return iProductService.setSaleStatus(productId,status);
        }
        return ServerResponse.createByErrorMessage("YOU have no permission");
    }

    public  ServerResponse<ProductVo> getDetail (HttpSession session, Integer productId){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            return iProductService.getDetail(productId);
        }
        return ServerResponse.createByErrorMessage("YOU have no permission");

    }




}
