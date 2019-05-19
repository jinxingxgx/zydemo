package com.xgx.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xgx.demo.R;
import com.xgx.demo.utils.JsonCallback;
import com.xgx.demo.utils.LzyResponse;
import com.xgx.demo.utils.MyUrl;
import com.xgx.demo.utils.StatusBarUtil;
import com.xgx.demo.vo.Approve;
import com.xgx.demo.vo.LoginInformation;
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
public class AddApproveActivity extends AppCompatActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.et_title)
    XEditText etTitle;
    @BindView(R.id.select_starttime_stv)
    SuperTextView selectStarttimeStv;
    @BindView(R.id.select_endtime_stv)
    SuperTextView selectEndtimeStv;
    @BindView(R.id.et_detial)
    XEditText etDetial;
    @BindView(R.id.sb_create)
    SuperButton sbCreate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_approve);
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

    @OnClick({R.id.select_starttime_stv, R.id.select_endtime_stv, R.id.sb_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_starttime_stv:
                TimePickerView pvTime = new TimePickerBuilder(AddApproveActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(java.util.Date date, View v) {
                        selectStarttimeStv.setRightString(TimeUtils.date2String(date));
                    }

                }).setType(new boolean[]{true, true, true, true, true, true}).build();
                pvTime.show();
                break;
            case R.id.select_endtime_stv:
                TimePickerView pvTime1 = new TimePickerBuilder(AddApproveActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(java.util.Date date, View v) {
                        selectEndtimeStv.setRightString(TimeUtils.date2String(date));
                    }

                }).setType(new boolean[]{true, true, true, true, true, true}).build();
                pvTime1.show();
                break;
            case R.id.sb_create:
                if (TextUtils.isEmpty(etTitle.getText())) {
                    ToastUtils.showShort("请输入请假标题");
                    return;
                }
                if (TextUtils.isEmpty(selectStarttimeStv.getRightString())) {
                    ToastUtils.showShort("请选择请假开始时间");
                    return;
                }
                if (TextUtils.isEmpty(selectEndtimeStv.getRightString())) {
                    ToastUtils.showShort("请选择请假结束时间");
                    return;
                }
                if (TextUtils.isEmpty(etDetial.getText())) {
                    ToastUtils.showShort("请输入请假原因");
                    return;
                }
                Approve approve = new Approve();
                approve.setTitle(etTitle.getText().toString());
                approve.setStartTime(selectStarttimeStv.getRightString());
                approve.setEndTime(selectEndtimeStv.getRightString());
                approve.setDetail(etDetial.getText().toString());
                approve.setUserId(LoginInformation.getInstance().getUser().getId());
                OkGo.<LzyResponse<Approve>>post(MyUrl.baseUrl + MyUrl.addApprove).params("approve", JSON.toJSONString(approve)).execute(new JsonCallback<LzyResponse<Approve>>() {
                    @Override
                    public void onSuccess(Response<LzyResponse<Approve>> response) {
                        ToastUtils.showShort("发送成功");
                        finish();
                    }
                });
                break;
        }
    }
}
