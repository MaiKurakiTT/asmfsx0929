package com.hsd.asmfsx.cardviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.utils.GetAgeFromDate;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<String> mData;
    private List<UserInformationBean> mList;
    private float mBaseElevation;
    private Context context;

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
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.findfriendsitem_layout, container, false);
        TextView nameText = (TextView) view.findViewById(R.id.name);
        TextView ageText = (TextView) view.findViewById(R.id.age);
        TextView heightText = (TextView) view.findViewById(R.id.height);
        TextView schoolText = (TextView) view.findViewById(R.id.school);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        UserInformationBean userInformationBean = mList.get(position);
        Logger.d("当前昵称: " + userInformationBean.getUser_nickname());
        nameText.setText("" + userInformationBean.getUser_nickname());
        int age = GetAgeFromDate.getAge(userInformationBean.getUser_birthday());
        ageText.setText("" + age + "岁");
        heightText.setText("" + userInformationBean.getUser_height() + "cm");
        schoolText.setText("" + userInformationBean.getUser_school());
        Glide.with(context).load(mList.get(position).getUser_icon()).into(img);
        Log.d("CardPagerAdapter", "instantiateItem当前List总数: " + mList.size());
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
