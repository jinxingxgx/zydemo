package com.xgx.demo.utils;

/**
 * Created by xgx on 2019/5/16 for dormsign
 */
public class MyUrl {
    public static String baseUrl = "http://221.176.209.130:9010/";
   // public static String baseUrl = "http://192.168.0.111:9010/";
    public static String imageUrl = baseUrl + "tmp/file/";

    public static String loginAuth = "api/auth";
    public static String uploadPath = "api/upload";
    public static String saveSignLog = "api/saveSignLog";
    public static String updateStudent = "api/sutdent_edit";
    public static String updateUser = "api/user_edit";
    public static String changeStudentPwd = "api/changeStudentPwd";
    public static String changeUserPwd = "api/changeUserPwd";
    public static String addApprove = "api/addApprove";
    public static String addVisitor = "api/addVisitor";
    public static String getUserByfaceToken = "api/getUserByfaceToken";//根据faceToken查询用户
}
