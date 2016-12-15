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
import com.hsd.asmfsx.bean.OrderListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/15.
 */

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<OrderListBean> orderListBeen;
    private OnItemClickListener itemClickListener;

    public OrderListAdapter(Context context, List<OrderListBean> orderListBeen) {
        this.context = context;
        this.orderListBeen = orderListBeen;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, Long id);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orderlist_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        OrderListBean orderBean = orderListBeen.get(position);
        Glide.with(context).load(orderBean.getPicture()).into(viewHolder.orderImg);
        viewHolder.goodName.setText("" + orderBean.getCommodityName());
        viewHolder.orderAmount.setText("数量：" + orderBean.getAmount());
        viewHolder.orderPrice.setText("总价：￥" + orderBean.getPrice());
        if (orderBean.getState() != null) {
            switch (orderBean.getState()) {
                case 0:
                    viewHolder.orderStatus.setText("未付款");
                    break;
                case 1:
                    viewHolder.orderStatus.setText("已付款");
                    break;
                case 2:
                    viewHolder.orderStatus.setText("已取消");
                    break;
                case 3:
                    viewHolder.orderStatus.setText("冻结");
                    break;
            }
        }
        final Long id = orderBean.getId();
        viewHolder.itemParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClick(view, position, id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderListBeen.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_parent)
        RelativeLayout itemParent;
        @BindView(R.id.order_img)
        ImageView orderImg;
        @BindView(R.id.good_name)
        TextView goodName;
        @BindView(R.id.order_amount)
        TextView orderAmount;
        @BindView(R.id.order_price)
        TextView orderPrice;
        @BindView(R.id.order_status)
        TextView orderStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
