package com.xgx.demo.vo;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;

public class LoginInformation {

    private User user;
    private boolean isLogin;
    private static LoginInformation instance;


    private LoginInformation() {
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public static LoginInformation getInstance() {
        if (instance == null) {
            synchronized (LoginInformation.class) {
                if (instance == null) {
                    instance = new LoginInformation();
                }
            }
        }

        return instance;
    }


    public static void setInstance(LoginInformation instance) {
        LoginInformation.instance = instance;
    }

    public User getUser() {
        if (user == null) {
            // 进入主页面
            String userString = SPUtils.getInstance().getString("user");
            user = new User();
            try {
                user = new Gson().fromJson(userString, User.class);
            } catch (Exception e) {
            }
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
