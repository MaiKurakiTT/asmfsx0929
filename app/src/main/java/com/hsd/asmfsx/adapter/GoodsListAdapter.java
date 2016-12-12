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
import com.hsd.asmfsx.bean.CommodityVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/9.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CommodityVO> commodityVOs;

    private OnItemClickListener itemClickListener;

    public GoodsListAdapter(Context context, List<CommodityVO> commodityVOs) {
        this.context = context;
        this.commodityVOs = commodityVOs;
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
        void click(View view, int position, Long goodId);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        final CommodityVO commodityVO = commodityVOs.get(position);
        viewHolder.goodName.setText(commodityVO.getName());
        viewHolder.shoplistItemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.click(view, position, new Long(commodityVO.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return commodityVOs.size();
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
