package com.hsd.asmfsx.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.HBListBean;
import com.hsd.asmfsx.utils.GetAgeFromDate;
import com.hsd.asmfsx.utils.SchoolUtils;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sun on 2017/3/3.
 */

public class HBListQuickAdapter extends BaseQuickAdapter<HBListBean, BaseViewHolder>{
    private Context context;
    public HBListQuickAdapter(List<HBListBean> data) {
        super(R.layout.hblist_item_layout, data);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HBListBean item) {
        Glide.with(context).load(item.getIcon()).into((CircleImageView)helper.getView(R.id.likelist_head));
        helper.setText(R.id.likelist_name, "" + item.getNickname());
        Long birthdayLong = item.getBirthday();
        if (birthdayLong != null){
            helper.setText(R.id.likelist_age, GetAgeFromDate.getAge(new Date(birthdayLong)) + "");
        }
        helper.setText(R.id.likelist_school, SchoolUtils.SchoolInt2String(item.getSchool()) + "");
    }
}
