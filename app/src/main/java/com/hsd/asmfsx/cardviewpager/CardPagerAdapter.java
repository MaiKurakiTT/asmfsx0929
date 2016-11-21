package com.hsd.asmfsx.cardviewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.utils.GetAgeFromDate;
import com.hsd.asmfsx.view.activity.UserInfoActivity;
import com.orhanobut.logger.Logger;


import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<String> mData;
    private List<UserInformationBean> mList;
    private float mBaseElevation;
    private Context context;
    private OnItemClickListener itemClickListener;

    public CardPagerAdapter(Context context, List<UserInformationBean> mList) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.mList = mList;
        this.context = context;

        for (int i = 0; i < mList.size(); i++) {
            mData.add("");
            mViews.add(null);
        }
    }
    public interface OnItemClickListener{
        void onClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.findfriendsitem_layout, container, false);
        TextView nameText = (TextView) view.findViewById(R.id.name);
        TextView ageText = (TextView) view.findViewById(R.id.age);
        TextView starText = (TextView) view.findViewById(R.id.star);
        TextView schoolText = (TextView) view.findViewById(R.id.school);
        ImageView img = (ImageView) view.findViewById(R.id.img);

        final UserInformationBean userInformationBean = mList.get(position);
        Logger.d("当前昵称: " + userInformationBean.getUser_nickname());
        nameText.setText("" + userInformationBean.getUser_nickname());
        int age = GetAgeFromDate.getAge(userInformationBean.getUser_birthday());

        ageText.setText("" + age + "岁");
        starText.setText(userInformationBean.getUser_star() + "");
        schoolText.setText("" + userInformationBean.getUser_school());
        Glide.with(context).load(mList.get(position).getUser_icon()).into(img);
        Log.d("CardPagerAdapter", "instantiateItem当前List总数: " + mList.size());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShowToast.show(context, "点击了" + position);
                String s = GetGson.getGson().toJson(userInformationBean);
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra("userInformationBean", userInformationBean);
                context.startActivity(intent);
            }
        });
        container.addView(view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("CardPagerAdapter", "destroyItem第 " + position + "个卡片");
        container.removeView((View) object);
        mViews.set(position, null);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
