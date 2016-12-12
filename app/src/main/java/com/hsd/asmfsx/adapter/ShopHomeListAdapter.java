package com.hsd.asmfsx.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.ShopVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/9.
 */

public class ShopHomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ShopVO> shopVOs;

    private OnItemClickListener itemClickListener;

    public ShopHomeListAdapter(Context context, List<ShopVO> shopVOs) {
        this.context = context;
        this.shopVOs = shopVOs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shoplist_item_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void click(View view, int position, ShopVO shopVO);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        final ShopVO shopVO = shopVOs.get(position);
        viewHolder.shoplistItemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.click(view, position, shopVO);
            }
        });
        viewHolder.shopName.setText(shopVO.getName());

    }

    @Override
    public int getItemCount() {
        return shopVOs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_photo)
        ImageView shopPhoto;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.shop_lowprice)
        TextView shopLowprice;
        @BindView(R.id.shop_sales)
        TextView shopSales;
        @BindView(R.id.shop_type)
        TextView shopType;
        @BindView(R.id.shoplist_item_parent)
        CardView shoplistItemParent;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
