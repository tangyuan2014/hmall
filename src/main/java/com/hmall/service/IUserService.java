package com.hmall.service;

import com.hmall.common.ServerResponse;
import com.hmall.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String token);

    ServerResponse<String> resetPassword(User user, String passwordNew, String passwordOld);

    ServerResponse<User> updateUserInfo(User user);

    ServerResponse<User> getUserInformation(int userId);
}
