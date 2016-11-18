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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2016/11/17.
 */

public class SeeAdapter extends RecyclerView.Adapter<SeeAdapter.MyAdapter> {


    private Context context;
    private String[] titles;
    private int[] imgs;
    private OnItemClickListener itemClickListener;

    public SeeAdapter(Context context, String[] titles, int[] imgs) {
        this.context = context;
        this.titles = titles;
        this.imgs = imgs;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void click(View view, int position);
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.see_recycle_item, parent, false);
        MyAdapter myAdapter = new MyAdapter(view);
        return myAdapter;
    }

    @Override
    public void onBindViewHolder(MyAdapter holder, final int position) {
        holder.img.setImageResource(imgs[position]);
        holder.text.setText(titles[position]);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.click(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.parent)
        CardView parent;

        public MyAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
