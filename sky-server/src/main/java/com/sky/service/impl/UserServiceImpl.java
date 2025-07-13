package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String s = getOpenid(userLoginDTO);

        JSONObject jsonObject = JSON.parseObject(s);
        String openid = jsonObject.getString("openid");
        if(openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.getUserById(openid);
        if(user == null){
            user = User.builder().openid(openid).createTime(LocalDateTime.now()).build();
            userMapper.addUser(user);

        }
        return user;
    }

    private String getOpenid(UserLoginDTO userLoginDTO) {
        HashMap<String , String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",userLoginDTO.getCode());
        map.put("grant_type","authorization_code");

        return HttpClientUtil.doGet(WX_LOGIN, map);
    }
}
