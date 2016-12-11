package com.hsd.asmfsx.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hsd.asmfsx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/9.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private OnItemClickListener itemClickListener;

    public GoodsListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.good_list_item_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void click(View view, int position, Long shopId);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.shoplistItemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.click(view, position, new Long(0));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.good_photo)
        ImageView goodPhoto;
        @BindView(R.id.good_name)
        TextView goodName;
        @BindView(R.id.now_price)
        TextView nowPrice;
        @BindView(R.id.normal_price)
        TextView normalPrice;
        @BindView(R.id.good_sales)
        TextView goodSales;
        @BindView(R.id.reserver)
        TextView reserver;
        @BindView(R.id.shoplist_item_parent)
        LinearLayout shoplistItemParent;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
