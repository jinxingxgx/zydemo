package com.xgx.demo.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.allen.library.SuperButton;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.xgx.demo.MyApplication;
import com.xgx.demo.R;
import com.xgx.demo.utils.DialogCallback;
import com.xgx.demo.utils.LzyResponse;
import com.xgx.demo.utils.MyUrl;
import com.xgx.demo.utils.StatusBarUtil;
import com.xgx.demo.vo.LoginInformation;
import com.xgx.demo.vo.User;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.xw.repo.XEditText;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.allen.library.SuperButton.LEFT_RIGHT;


/**
 * Created by xgx on 2019/4/8 for facesign
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_userName)
    XEditText etUserName;
    @BindView(R.id.et_password)
    XEditText etPassword;
    @BindView(R.id.et_type)
    TextView etType;
    @BindView(R.id.sb_login)
    SuperButton sbLogin;
    @BindView(R.id.input)
    LinearLayout input;
    @BindView(R.id.tv_registration)
    TextView tvRegistration;
    @BindView(R.id.l_f)
    LinearLayout lF;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    private String userName;
    private String password;

    @Override
    @AfterPermissionGranted(1)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", 1, perms);
        }
        ButterKnife.bind(this);

        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this);
        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userName = editable.toString();
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(userName)) {
                    sbLogin.setShapeGradientOrientation(LEFT_RIGHT).setUseShape();
                } else {

                    sbLogin.setShapeGradientOrientation(-1).setUseShape();
                }
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = editable.toString();
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(userName)) {
                    sbLogin.setShapeGradientOrientation(LEFT_RIGHT).setShapeUseSelector(true).setShapeSelectorPressedColor(getResources().getColor(R.color.sGradientEndColor)).setUseShape();
                } else {
                    sbLogin.setShapeGradientOrientation(-1).setUseShape();
                }
            }
        });
        if (SPUtils.getInstance().getBoolean("isLogin", false)) {
            String userString = SPUtils.getInstance().getString("user", "[]");
            User user = new User();
            try {
                user = JSON.parseObject(userString, User.class);
                LoginInformation.getInstance().setLogin(true);
                LoginInformation.getInstance().setUser(user);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } catch (Exception e) {

            }

        }
    }


    private void startLogin() {
        OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(), this);
        OkGo.<LzyResponse<User>>post(MyUrl.baseUrl + MyUrl.loginAuth).params("username", etUserName.getText().toString()).params("password", etPassword.getText().toString()).params("type", etType.getText().toString().equals("宿管") ? "1" : "0").tag(this).execute(new DialogCallback<LzyResponse<User>>(this) {
            @Override
            public void onSuccess(Response<LzyResponse<User>> response) {
                ToastUtils.showShort(response.body().message);
                String token = response.body().token;
                SPUtils.getInstance().put("token", token);
                HttpHeaders headerstemp = new HttpHeaders();
                headerstemp.put(MyApplication.token, "Bearer " + token);
                OkGo.getInstance().addCommonHeaders(headerstemp);
                User user = response.body().data;
                user.setRoleName(response.body().roleName);
                user.setDeptName(response.body().deptName);
                SPUtils.getInstance().put("user", JSON.toJSONString(response.body().data));
                SPUtils.getInstance().put("isLogin", true);
                SPUtils.getInstance().put("type", etType.getText().toString().equals("宿管") ? "1" : "0");
                LoginInformation.getInstance().setLogin(true);
                LoginInformation.getInstance().setUser(response.body().data);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(Response<LzyResponse<User>> response) {
                super.onError(response);
                ToastUtils.showShort(response.getException().getMessage());
            }

        });


    }

    @OnClick({R.id.et_type, R.id.sb_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_type:
                StyledDialog.buildIosSingleChoose(Arrays.asList(new String[]{"学生", "宿管"}), new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence charSequence, int i) {
                        etType.setText(charSequence);
                    }
                }).show();
                break;
            case R.id.goBackBtn:
                finish();
                break;
            case R.id.sb_login:
                if (StringUtils.isEmpty(etUserName.getText().toString())) {
                    ToastUtils.showShort("请输入用户名");
                    return;
                }
                if (StringUtils.isEmpty(etPassword.getText().toString())) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                if (StringUtils.isEmpty(etType.getText().toString())) {
                    ToastUtils.showShort("请选择用户类型");
                    return;
                }
                startLogin();
                break;
        }
    }

    @OnClick(R.id.tv_registration)
    public void onRegistrClicked() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
