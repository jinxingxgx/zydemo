package com.xgx.demo.adapter;

import android.support.annotation.Nullable;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgx.demo.R;
import com.xgx.demo.vo.Zy;

import java.util.List;

/**
 * Created by xgx on 2019/4/9 for facesign
 */
public class ZyListAdapter extends BaseQuickAdapter<Zy, BaseViewHolder> {
    public ZyListAdapter(@Nullable List<Zy> data) {
        super(R.layout.adapter_school, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Zy item) {
        SuperTextView stv = helper.getView(R.id.stv);
        stv.setLeftString(item.getName());
        stv.setRightString("下载");
    }
}
