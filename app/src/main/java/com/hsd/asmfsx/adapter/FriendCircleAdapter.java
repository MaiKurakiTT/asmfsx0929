package com.hsd.asmfsx.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.PictureBean;
import com.hsd.asmfsx.utils.ShowToast;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 2016/11/10.
 */

public class FriendCircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int TYPE_ITEM = 0;
    private static int TYPE_FOOTER = 1;


    private Context context;
    private List<FriendCircleBean> friendCircleList;
    private List<Integer> beClickGood = new ArrayList<>();

    public FriendCircleAdapter(Context context, List<FriendCircleBean> friendCircleList) {
        this.context = context;
        this.friendCircleList = friendCircleList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.friendcircle_item_layout, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        } else {
            View footerView = LayoutInflater.from(context).inflate(R.layout.footer_view, parent, false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(footerView);
            return footerViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            final FriendCircleBean friendCircleBean = friendCircleList.get(position);
            //给每个item的父布局加上tag为当前position
            myViewHolder.itemParent.setTag(position);
            Glide.with(context).load(friendCircleBean.getFriendsCircle_icon()).into(myViewHolder.headImage);
            myViewHolder.putName.setText(friendCircleBean.getFriendsCircle_nickname());
            myViewHolder.friendcircleContent.setText(friendCircleBean.getFriendsCircle_content());
            //将点过赞的item的position加到List集合中，加载item之前先判断该item的position是否在集合中，防止复用时候错乱
            if (beClickGood.contains(((int) myViewHolder.itemParent.getTag()))) {
                Logger.d("点过赞了" + position);
                myViewHolder.goodImg.setImageResource(R.mipmap.ic_good_press);
            } else {
                Logger.d("当前item没有点过赞" + position);
                myViewHolder.goodImg.setImageResource(R.mipmap.ic_good);
            }
            myViewHolder.goodImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点赞过的item的position加入集合中
                    beClickGood.add(position);
                    Logger.d("给第" + position + "个item加了");
                    ShowToast.show(context, "点赞" + friendCircleBean.getFriendsCircle_nickname());
                    myViewHolder.goodImg.setImageResource(R.mipmap.ic_good_press);
                }
            });
            List<String> comments = friendCircleBean.getComments();
            //判断评论条数，如果是最后一条就不加换行符了
            if (comments.size() > 0) {
                String display = "";
                for (int i = 0; i < comments.size(); i++) {
                    String temp = comments.get(i);
                    if (i == comments.size() - 1) {
                        display = display + temp;
                    } else {
                        display = display + temp + '\n';
                    }
                }
                myViewHolder.commentsText.setText(display);
            } else {
                myViewHolder.commentsText.setVisibility(View.GONE);
            }
            if (friendCircleBean.getPictures().size() > 0) {
                NineGridImageViewAdapter<String> nineGridImageViewAdapter = new NineGridImageViewAdapter<String>() {
                    @Override
                    protected void onDisplayImage(Context context, ImageView imageView, String s) {
                        Glide.with(context).load(s).placeholder(R.mipmap.ic_initimg).into(imageView);
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
                myViewHolder.ninegridImg.setAdapter(nineGridImageViewAdapter);
                List<PictureBean> pictures = friendCircleBean.getPictures();
                List<String> pics = new ArrayList<>();
                for (int i = 0; i < pictures.size(); i++) {
                    PictureBean pictureBean = pictures.get(i);
                    String picture_url = pictureBean.getPicture_URL();
                    pics.add(picture_url);
                }
                myViewHolder.ninegridImg.setImagesData(pics);
            }
        } else {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return friendCircleList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
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
        @BindView(R.id.comments_text)
        TextView commentsText;
        @BindView(R.id.friendcircle_item_parent)
        CardView itemParent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.footer_progressbar)
        ProgressBar footerProgressbar;
        @BindView(R.id.footer_text)
        TextView footerText;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
