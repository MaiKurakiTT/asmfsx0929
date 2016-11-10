package com.hsd.asmfsx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.PictureBean;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 2016/11/10.
 */

public class FriendCircleAdapter extends RecyclerView.Adapter<FriendCircleAdapter.MyViewHolder> {


    private Context context;
    private List<FriendCircleBean> friendCircleList;

    public FriendCircleAdapter(Context context, List<FriendCircleBean> friendCircleList) {
        this.context = context;
        this.friendCircleList = friendCircleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friendcircle_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FriendCircleBean friendCircleBean = friendCircleList.get(position);
        Glide.with(context).load(friendCircleBean.getFriendsCircle_icon()).into(holder.headImage);
        holder.putName.setText("1" + friendCircleBean.getFriendsCircle_nickname());
        holder.friendcircleContent.setText("1" + friendCircleBean.getFriendsCircle_content());
        if (friendCircleBean.getPictures().size() > 0) {
            NineGridImageViewAdapter<String> nineGridImageViewAdapter = new NineGridImageViewAdapter<String>() {
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, String s) {
                    Glide.with(context).load(s).into(imageView);
                }

                @Override
                protected ImageView generateImageView(Context context) {
                    return super.generateImageView(context);
                }

                @Override
                protected void onItemImageClick(Context context, int index, List<String> list) {
                    super.onItemImageClick(context, index, list);
                }
            };
            holder.ninegridImg.setAdapter(nineGridImageViewAdapter);
            List<PictureBean> pictures = friendCircleBean.getPictures();
            List<String> pics = new ArrayList<>();
            for (int i = 0;i<pictures.size();i++){
                PictureBean pictureBean = pictures.get(i);
                String picture_url = pictureBean.getPicture_URL();
                pics.add(picture_url);
            }
                holder.ninegridImg.setImagesData(pics);
        }
    }

    @Override
    public int getItemCount() {
        return friendCircleList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.head_image)
        CircleImageView headImage;
        @BindView(R.id.put_name)
        TextView putName;
        @BindView(R.id.ninegrid_img)
        NineGridImageView ninegridImg;
        @BindView(R.id.put_time)
        TextView putTime;
        @BindView(R.id.comment_img)
        ImageView commentImg;
        @BindView(R.id.comment_counts)
        TextView commentCounts;
        @BindView(R.id.good_img)
        ImageView goodImg;
        @BindView(R.id.good_counts)
        TextView goodCounts;
        @BindView(R.id.friendcircle_content)
        TextView friendcircleContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
