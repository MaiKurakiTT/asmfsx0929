package com.hsd.asmfsx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.HBListBean;
import com.hsd.asmfsx.utils.GetAgeFromDate;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sun on 2016/12/16.
 */

public class HBListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HBListBean> hbList;
    private OnItemClickListener itemClickListener;

    public HBListAdapter(Context context, List<HBListBean> hbList) {
        this.context = context;
        this.hbList = hbList;
    }

    public interface OnItemClickListener{
        void click(View view, int position, Long userId);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hblist_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        final HBListBean hbListBean = hbList.get(position);
        Glide.with(context).load(hbListBean.getIcon()).into(viewHolder.likelistHead);
        viewHolder.likelistName.setText("" + hbListBean.getNickname());
        Long birthdayLong = hbListBean.getBirthday();
        if (birthdayLong != null){
            viewHolder.likelistAge.setText("" + GetAgeFromDate.getAge(new Date(birthdayLong)));
        }
        viewHolder.likelistSchool.setText("" + hbListBean.getSchool());
        viewHolder.likelistChatBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.click(view, position, hbListBean.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return hbList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.likelist_head)
        CircleImageView likelistHead;
        @BindView(R.id.likelist_name)
        TextView likelistName;
        @BindView(R.id.likelist_age)
        TextView likelistAge;
        @BindView(R.id.likelist_school)
        TextView likelistSchool;
        @BindView(R.id.likelist_text_parent)
        RelativeLayout likelistTextParent;
        @BindView(R.id.likelist_chat_bt)
        ImageView likelistChatBt;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
