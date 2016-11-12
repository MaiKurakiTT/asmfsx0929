package com.hsd.asmfsx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2016/11/12.
 */

public class AddImgAdapter extends RecyclerView.Adapter<AddImgAdapter.MyViewHolder> {

    private Context context;
    private List<String> photos;
    private OnItemClickListener itemClickListener;
    private final int lastItem;

    public AddImgAdapter(Context context, List<String> photos) {
        this.context = context;
        this.photos = photos;
        lastItem = photos.size();
    }
    public interface OnItemClickListener{
        void onclick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.addimg_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (position == lastItem) {
            holder.img.setImageResource(R.mipmap.ic_addimg);
        } else {
            Glide.with(context).load(photos.get(position)).into(holder.img);
        }
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == lastItem){
                    itemClickListener.onclick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
