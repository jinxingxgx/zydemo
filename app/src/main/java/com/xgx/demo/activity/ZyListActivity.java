package com.xgx.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.xgx.demo.R;
import com.xgx.demo.adapter.ZyListAdapter;
import com.xgx.demo.vo.User;
import com.xgx.demo.vo.Zy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作业列表列表
 */
public class ZyListActivity extends AppCompatActivity {
    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    private List<Zy> list;
    private ZyListAdapter adapter;
    private boolean firstInit = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zy_list);
        ButterKnife.bind(this);
        listView.setLayoutManager(new LinearLayoutManager(this));
        titlebar.getLeftTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titlebar.getCenterTextView().setText("选课列表");
        list = new ArrayList<>();
        adapter = new ZyListAdapter(list);
        listView.setAdapter(adapter);
        listView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter madapter, View view, int position) {


            }

            @Override
            public void onItemLongClick(final BaseQuickAdapter madapter, View view, final int position) {
                super.onItemChildLongClick(adapter, view, position);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }

    private void loadData() {
    }


}
