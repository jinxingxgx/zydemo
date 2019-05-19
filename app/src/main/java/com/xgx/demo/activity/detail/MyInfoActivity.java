package com.xgx.demo.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.lzy.okgo.OkGo;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.xgx.demo.R;
import com.xgx.demo.activity.LoginActivity;
import com.xgx.demo.utils.DialogCallback;
import com.xgx.demo.utils.LzyResponse;
import com.xgx.demo.utils.MyUrl;
import com.xgx.demo.utils.StatusBarUtil;
import com.xgx.demo.vo.LoginInformation;
import com.xgx.demo.vo.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by xgx on 2019/4/25 for facesign
 * 我的个人资料
 */
public class MyInfoActivity extends AppCompatActivity {
    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.my_icon_stv)
    SuperTextView myIconStv;
    @BindView(R.id.my_username_stv)
    SuperTextView myUsernameStv;
    @BindView(R.id.my_realname_stv)
    SuperTextView myRealnameStv;
    @BindView(R.id.my_birthday_stv)
    SuperTextView myBirthdayStv;
    @BindView(R.id.my_email_stv)
    SuperTextView myEmailStv;
    @BindView(R.id.my_tel_stv)
    SuperTextView myTelStv;
    @BindView(R.id.sb_create)
    SuperButton sbCreate;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this);
        ButterKnife.bind(this);//注解注册控件
        titlebar.getLeftTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        if (LoginInformation.getInstance().isLogin()) {
            user = LoginInformation.getInstance().getUser();

            Glide.with(this).load(MyUrl.imageUrl + user.getFaceUrl()).apply(new RequestOptions().placeholder(R.drawable.user_unlogin).error(R.drawable.user_unlogin)).into(myIconStv.getRightIconIV());
            myUsernameStv.setRightString(user.getAccount() + "(" + user.getRoleName() + ")");
            myRealnameStv.setRightString(StringUtils.null2Length0(user.getName()));
            myBirthdayStv.setRightString(StringUtils.null2Length0(user.getBirthday()));
            myEmailStv.setRightString(StringUtils.null2Length0(user.getEmail()));
            myTelStv.setRightString(StringUtils.null2Length0(user.getPhone()));
        }
    }


    @OnClick({R.id.password_stv, R.id.my_icon_stv, R.id.my_username_stv, R.id.my_realname_stv, R.id.my_birthday_stv, R.id.my_email_stv, R.id.my_tel_stv, R.id.sb_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.password_stv:
                StyledDialog.buildMdInput("修改密码", "请输入新密码", "请确认新密码", "确定", "取消", new MyDialogListener() {
                    @Override
                    public void onFirst() {

                    }

                    @Override
                    public void onSecond() {
                    }

                    @Override
                    public void onGetInput(CharSequence input1, CharSequence input2) {
                        super.onGetInput(input1, input2);
                        if (SPUtils.getInstance().getString("type", "0").equals("0")) {
                            OkGo.<LzyResponse<User>>post(MyUrl.baseUrl + MyUrl.changeStudentPwd).params("newPwd", input1.toString()).params("rePwd", input2.toString()).params("userId", LoginInformation.getInstance().getUser().getId()).execute(new DialogCallback<LzyResponse<User>>(MyInfoActivity.this) {
                                @Override
                                public void onSuccess(com.lzy.okgo.model.Response<LzyResponse<User>> response) {
                                    ToastUtils.showShort("修改成功");
                                }
                            });
                        } else {
                            OkGo.<LzyResponse<User>>post(MyUrl.baseUrl + MyUrl.changeUserPwd).params("newPwd", input1.toString()).params("userId", LoginInformation.getInstance().getUser().getId()).params("rePwd", input2.toString()).execute(new DialogCallback<LzyResponse<User>>(MyInfoActivity.this) {
                                @Override
                                public void onSuccess(com.lzy.okgo.model.Response<LzyResponse<User>> response) {
                                    ToastUtils.showShort("修改成功");
                                }
                            });
                        }

                    }
                }).show();
                break;
            case R.id.my_icon_stv:

                break;
            case R.id.my_username_stv:
                break;
            case R.id.my_realname_stv:
                StyledDialog.buildMdInput("真实姓名", "请输入真实姓名", "", "确定", "取消", new MyDialogListener() {
                    @Override
                    public void onFirst() {

                    }

                    @Override
                    public void onSecond() {
                    }

                    @Override
                    public void onGetInput(CharSequence input1, CharSequence input2) {
                        super.onGetInput(input1, input2);
                        user.setName(input1.toString());
                        //更新数据到bmob上
                        updateUser();
                    }
                }).show();
                break;
            case R.id.my_birthday_stv:
                break;
            case R.id.my_email_stv:
                break;
            case R.id.my_tel_stv:
                break;
            case R.id.sb_create:
                SPUtils.getInstance().remove("isLogin");
                SPUtils.getInstance().remove("user");
                LoginInformation.getInstance().setUser(null);
                LoginInformation.getInstance().setLogin(false);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }

    }

    public void updateUser() {
        if (SPUtils.getInstance().getString("type", "0").equals("0")) {
            OkGo.<LzyResponse<User>>post(MyUrl.baseUrl + MyUrl.updateStudent).params("user", JSON.toJSONString(this)).execute(new DialogCallback<LzyResponse<User>>(MyInfoActivity.this) {
                @Override
                public void onSuccess(com.lzy.okgo.model.Response<LzyResponse<User>> response) {
                    ToastUtils.showShort("修改成功");
                    SPUtils.getInstance().put("user", JSON.toJSONString(response.body().data));
                    SPUtils.getInstance().put("isLogin", true);
                    LoginInformation.getInstance().setLogin(true);
                    LoginInformation.getInstance().setUser(response.body().data);
                    setData();
                }
            });
        } else {
            OkGo.<LzyResponse<User>>post(MyUrl.baseUrl + MyUrl.updateUser).params("user", JSON.toJSONString(this)).execute(new DialogCallback<LzyResponse<User>>(MyInfoActivity.this) {
                @Override
                public void onSuccess(com.lzy.okgo.model.Response<LzyResponse<User>> response) {
                    ToastUtils.showShort("修改成功");
                    SPUtils.getInstance().put("user", JSON.toJSONString(response.body().data));
                    SPUtils.getInstance().put("isLogin", true);
                    LoginInformation.getInstance().setLogin(true);
                    LoginInformation.getInstance().setUser(response.body().data);
                    setData();
                }
            });
        }

    }
}
