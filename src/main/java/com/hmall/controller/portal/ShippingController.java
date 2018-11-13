package com.hmall.controller.portal;

import com.hmall.common.ServerResponse;
import com.hmall.pojo.Shipping;
import com.hmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    public ServerResponse addAddress(HttpSession session, Shipping shipping){

    }
}
