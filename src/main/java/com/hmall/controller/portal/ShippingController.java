package com.hmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.hmall.common.Const;
import com.hmall.common.ServerResponse;
import com.hmall.pojo.Shipping;
import com.hmall.pojo.User;
import com.hmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    @RequestMapping("add_do")
    @ResponseBody
    public ServerResponse add(HttpSession session, Shipping shipping){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iShippingService.add(user.getId(),shipping);
    }

    @RequestMapping("del_do")
    @ResponseBody
    public ServerResponse del(HttpSession session,Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iShippingService.del(user.getId(),shippingId);
    }

    @RequestMapping("update_do")
    @ResponseBody
    public ServerResponse update(HttpSession session, Shipping shipping){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iShippingService.update(user.getId(),shipping);
    }

    @RequestMapping("select_do")
    @ResponseBody
    public ServerResponse<Shipping> select(HttpSession session,Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iShippingService.select(user.getId(),shippingId);
    }

    @RequestMapping("list_do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "1") int pageSize,
                                           HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("PLEASE LOG IN");
        }
        return iShippingService.list(pageNum,pageSize,user.getId());
    }

}
