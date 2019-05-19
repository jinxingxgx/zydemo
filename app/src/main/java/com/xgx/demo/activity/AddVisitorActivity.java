package com.xgx.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.allen.library.SuperButton;
import com.blankj.utilcode.util.ToastUtils;
import com.xgx.demo.R;
import com.xgx.demo.utils.JsonCallback;
import com.xgx.demo.utils.LzyResponse;
import com.xgx.demo.utils.MyUrl;
import com.xgx.demo.utils.StatusBarUtil;
import com.xgx.demo.vo.Approve;
import com.xgx.demo.vo.LoginInformation;
import com.xgx.demo.vo.Visitor;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.xw.repo.XEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xgx on 2019/4/16 for facesign
 */
public class AddVisitorActivity extends AppCompatActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.et_title)
    XEditText etTitle;
    @BindView(R.id.et_detial)
    XEditText etDetial;
    @BindView(R.id.sb_create)
    SuperButton sbCreate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visitor);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.sb_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sb_create:
                if (TextUtils.isEmpty(etTitle.getText())) {
                    ToastUtils.showShort("请输入访客姓名");
                    return;
                }
                if (TextUtils.isEmpty(etDetial.getText())) {
                    ToastUtils.showShort("请输入访问原因");
                    return;
                }
                Visitor visitor = new Visitor();
                visitor.setName(etTitle.getText().toString());
                visitor.setContent(etDetial.getText().toString());
                visitor.setUserid(LoginInformation.getInstance().getUser().getId());
                visitor.setOper(LoginInformation.getInstance().getUser().getName());
                visitor.setDepId(LoginInformation.getInstance().getUser().getDeptid());
                OkGo.<LzyResponse<Approve>>post(MyUrl.baseUrl + MyUrl.addVisitor).params("visitor", JSON.toJSONString(visitor)).execute(new JsonCallback<LzyResponse<Approve>>() {
                    @Override
                    public void onSuccess(Response<LzyResponse<Approve>> response) {
                        ToastUtils.showShort("记录成功");
                        finish();
                    }
                });
                break;
        }
    }
}
