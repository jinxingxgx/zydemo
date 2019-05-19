package com.xgx.demo.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.xgx.demo.R;
import com.xgx.demo.activity.detail.MyInfoActivity;
import com.xgx.demo.utils.MyUrl;
import com.xgx.demo.vo.LoginInformation;
import com.xgx.demo.vo.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 主界面activity
 */
public class MainActivity extends AppCompatActivity {
    private static final int PRC_PHOTO_PICKER = 1;
    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.up_load_tv)
    SuperButton upLoadTv;
    @BindView(R.id.visitor_tv)
    SuperButton visitorTv;
    @BindView(R.id.listLayout)
    LinearLayout listLayout;
    @BindView(R.id.me_stv)
    SuperTextView meStv;
    @BindView(R.id.log_stv)
    SuperTextView logStv;
    @BindView(R.id.message_stv)
    SuperTextView messageStv;
    @BindView(R.id.about_stv)
    SuperTextView aboutStv;
    @BindView(R.id.sign_setting_stv)
    SuperTextView signSettingStv;
    @BindView(R.id.settingLayout)
    LinearLayout settingLayout;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    ConstraintLayout container;
    private User user;

    @Override
    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //底部菜单栏
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_me);
//初始化课程列表
        //获取验证手机用户权限
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        //当前登录人的信息
        initUserData();
    }

    /**
     * 当前登录人信息
     */
    private void initUserData() {
        if (LoginInformation.getInstance().isLogin()) {
            user = LoginInformation.getInstance().getUser();
            Glide.with(this).load(MyUrl.imageUrl + user.getFaceUrl()).apply(new RequestOptions().placeholder(R.drawable.user_unlogin).error(R.drawable.user_unlogin)).into(meStv.getLeftIconIV());
            if (StringUtils.isEmpty(user.getName())) {
                meStv.setLeftString(user.getAccount());
            } else {
                meStv.setLeftString(user.getName() + "(" + user.getAccount() + ")");
            }
            meStv.setLeftBottomString(user.getRoleName() + "" + user.getDeptName());
        } else {
            Glide.with(this).load(R.drawable.user_unlogin).into(meStv.getLeftIconIV());
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    settingLayout.setVisibility(View.GONE);
                    listLayout.setVisibility(View.VISIBLE);
                    titlebar.getCenterTextView().setText("作业列表");
                    return true;
                case R.id.navigation_me:
                    settingLayout.setVisibility(View.VISIBLE);
                    listLayout.setVisibility(View.GONE);
                    titlebar.getCenterTextView().setText("个人中心");
                    return true;
            }
            return false;
        }
    };

    @OnClick({ R.id.visitor_tv,  R.id.me_stv, R.id.about_stv, R.id.sign_setting_stv, R.id.settingLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.visitor_tv:
                startActivity(new Intent(MainActivity.this, AddVisitorActivity.class));

                break;

            case R.id.me_stv:
                startActivity(new Intent(MainActivity.this, MyInfoActivity.class));
                break;
            case R.id.about_stv:
                break;
            case R.id.sign_setting_stv:
                break;
            case R.id.settingLayout:
                break;
        }
    }
}
