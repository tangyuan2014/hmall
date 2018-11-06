package com.hmall.service.impl;

import com.hmall.common.Const;
import com.hmall.common.ServerResponse;
import com.hmall.common.TokenCatch;
import com.hmall.dao.UserMapper;
import com.hmall.pojo.User;
import com.hmall.service.IUserService;
import com.hmall.Utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    //可以不用,直接扫描
    private UserMapper userMapper;


    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //密码登录MD5
        String md5code = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.selectLogin(username, md5code);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功", user);
    }

    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        user.setRole(Const.Role.CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNoneBlank(type)) {
            //开始校验
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }

        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        return ServerResponse.createBySuccessMessage("校验成功");

    }

    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNoneBlank(question)) {
            return ServerResponse.createBySuccessMessage(question);
        }

        return ServerResponse.createByErrorMessage("找回密码问题为空");

    }

    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            String forgettoken = UUID.randomUUID().toString();
            TokenCatch.setKey("tocken_" + username, forgettoken);
            return ServerResponse.createBySuccess(forgettoken);
        }
        return ServerResponse.createByErrorMessage("问题答案不正确");
    }

    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String token) {
        if (StringUtils.isBlank(token)) {
            ServerResponse.createByErrorMessage("token错误，需要token");
        }
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);

        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        if (StringUtils.isBlank(TokenCatch.getKey("token" + username))) {
            return ServerResponse.createByErrorMessage("token已过期");
        }

        if (StringUtils.equals(token, TokenCatch.getKey("token" + username))) {
            String MD5password = MD5Util.MD5EncodeUtf8(passwordNew);
            int resultCount = userMapper.updatePasswordByUsername(username, passwordNew);
            if (resultCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        } else {
            return ServerResponse.createByErrorMessage("token无效，请重置");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    public ServerResponse<String> resetPassword(User user, String passwordNew, String passwordOld) {
        int resultCount = userMapper.updatePassword(user.getId(), MD5Util.MD5EncodeUtf8(passwordOld));
        if (resultCount == 0) {
            return ServerResponse.createBySuccessMessage("原始密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updaterow = userMapper.updateByPrimaryKeySelective(user);
        if (updaterow > 0) {
            return ServerResponse.createBySuccessMessage("修改密码成功");
        } else return ServerResponse.createByErrorMessage("更新密码失败");
    }

    public ServerResponse<User> updateUserInfo(User user){
        int resultCount=userMapper.check_Email(user.getId(),(user.getEmail()));
        if (resultCount>0){
            return ServerResponse.createByErrorMessage("邮箱已被应用，请重新填写邮箱");
        }

        User userNew=new User();
        userNew.setId(user.getId());
        userNew.setAnswer(user.getAnswer());
        userNew.setEmail(user.getEmail());
        userNew.setPhone(user.getPhone());
        userNew.setQuestion(user.getQuestion());

        int rowCount=userMapper.updateByPrimaryKeySelective(userNew);
        if (rowCount>0){
            return ServerResponse.createBySuccess("更改信息成功",userNew);
        }
        else return ServerResponse.createByErrorMessage("更改信息失败");
    }

    public ServerResponse<User> getUserInformation(int userId){
        User user=userMapper.selectByPrimaryKey(userId);
        if(user==null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        return ServerResponse.createBySuccess("登陆成功",user);
    }

    public ServerResponse checkAdmin (User user){

       if(user!=null&&user.getRole().intValue()==Const.Role.ADMINISTRATER){
           return ServerResponse.createBySuccess();
       }
       return ServerResponse.createByError();
    }

}
