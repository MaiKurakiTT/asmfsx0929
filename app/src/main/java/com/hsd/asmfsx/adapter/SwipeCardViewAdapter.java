package com.hsd.asmfsx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.utils.Date2Star;
import com.hsd.asmfsx.utils.DateFormatUtils;
import com.hsd.asmfsx.utils.GetAgeFromDate;
import com.orhanobut.logger.Logger;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/16.
 */

public class SwipeCardViewAdapter extends BaseAdapter {
    private Context context;
    private List<UserInformationBean2> userInformationBean2s;
    private OnItemClickListener itemClickListener;

    public SwipeCardViewAdapter(Context context, List<UserInformationBean2> userInformationBean2s) {
        this.context = context;
        this.userInformationBean2s = userInformationBean2s;
    }

    public interface OnItemClickListener{
        void click(int position, Long userID);
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getCount() {
        return userInformationBean2s.size();
    }

    @Override
    public UserInformationBean2 getItem(int i) {
        return userInformationBean2s.get(i);
    }

    @Override
    public long getItemId(int i) {
        Logger.d("getItemId = " + i);
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Logger.d("getView");
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.swipe_card_view_item_layout, viewGroup, false);
            holder = new ViewHolder();
            holder.cardParent = (LinearLayout) view.findViewById(R.id.card_parent);
            holder.img = (ImageView) view.findViewById(R.id.img);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.age = (TextView) view.findViewById(R.id.age);
            holder.star = (TextView) view.findViewById(R.id.star);
            holder.school = (TextView) view.findViewById(R.id.school);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        int s = i;
        UserInformationBean2 userInformationBean2 = userInformationBean2s.get(i);
        holder.name.setText("第" + userInformationBean2.getNickname() + "个");
        if (userInformationBean2.getBirthday() != null){
            Date date = DateFormatUtils.formatLong2Date(userInformationBean2.getBirthday());
            holder.age.setText(GetAgeFromDate.getAge(date) + "岁");
            holder.star.setText(Date2Star.date2Constellation(date) + "");
        }
        Integer schoolInt = userInformationBean2.getSchool();
        if (schoolInt == 0){
            holder.school.setText("河南师范大学");
        }

        return view;
    }


    public void remove(int index) {
        if (index > -1 && index < userInformationBean2s.size()) {
            userInformationBean2s.remove(index);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<UserInformationBean2> data) {
        if (isEmpty()) {
            userInformationBean2s.addAll(data);
            notifyDataSetChanged();
        } else {
            userInformationBean2s.addAll(data);
        }
    }

    public void clear() {
        userInformationBean2s.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return userInformationBean2s.isEmpty();
    }

    class ViewHolder {
        ImageView img;
        TextView name;
        TextView age;
        TextView star;
        TextView school;
        LinearLayout cardParent;
    }
}
