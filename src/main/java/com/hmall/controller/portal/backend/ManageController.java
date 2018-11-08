package com.hmall.controller.portal.backend;

import com.hmall.common.Const;
import com.hmall.common.ServerResponse;
import com.hmall.pojo.User;
import com.hmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "manage/user/")
public class ManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            if (response.getData().getRole() == Const.Role.ADMINISTRATER) {
                session.setAttribute(Const.CURRENT_USER, response.getData());
                return response;
            } else return ServerResponse.createByErrorMessage("不是管理员");
        }
        return response;
    }
}
