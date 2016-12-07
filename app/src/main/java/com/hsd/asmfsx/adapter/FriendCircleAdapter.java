package com.hsd.asmfsx.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.CommentVO;
import com.hsd.asmfsx.bean.FriendCircleBean;
import com.hsd.asmfsx.bean.FriendCircleVO;
import com.hsd.asmfsx.bean.PictureBean;
import com.hsd.asmfsx.utils.DateFormatUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 2016/11/10.
 */

public class FriendCircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int TYPE_ITEM = 0;
    private static int TYPE_FOOTER = 1;
    //上拉加载的刷新状态，1为正在刷新
    private int status = 0;


    private Context context;
    private List<FriendCircleVO> friendCircleList;
    private List<Integer> beClickGood = new ArrayList<>();
    private List<Integer> beCommented = new ArrayList<>();
    private Map isGoodMap = new HashMap();

    private OnGoodClickListener goodClickListener;
    private OnCommentClickListener commentClickListener;

    public FriendCircleAdapter(Context context, List<FriendCircleVO> friendCircleList) {
        this.context = context;
        this.friendCircleList = friendCircleList;
    }

    public void setOnGoodClickListener(OnGoodClickListener goodClickListener) {
        this.goodClickListener = goodClickListener;
    }
    public void setOnCommentClickListener(OnCommentClickListener commentClickListener){
        this.commentClickListener = commentClickListener;
    }

    public interface OnGoodClickListener {
        void click(View view, int position, Long friendCircleId);
    }
    public interface OnCommentClickListener{
        void click(View view, int position, Long friendCircleId);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        if (holder instanceof MyViewHolder) {
            final int position = pos;
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            //给每个item的父布局加上tag为当前position
            myViewHolder.itemParent.setTag(position);
            final FriendCircleVO friendCircleBean = friendCircleList.get(position);
            setData2View(myViewHolder, position, friendCircleBean);

        } else {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        }
    }

    private void setData2View(final MyViewHolder myViewHolder, final int position, final FriendCircleVO friendCircleBean) {
//        Logger.d("是否赞过" + friendCircleBean.isLike());
        //显示头像
        Glide.with(context).load(friendCircleBean.getUserVO().getIcon()).placeholder(R.mipmap.ic_inithead).into(myViewHolder.headImage);
        //显示发表人昵称
        myViewHolder.putName.setText(friendCircleBean.getUserVO().getNickname());
        //显示说说内容，为空的就直接隐藏该textview
        if (TextUtils.isEmpty(friendCircleBean.getContent())) {
            myViewHolder.friendcircleContent.setVisibility(View.GONE);
        } else {
            myViewHolder.friendcircleContent.setVisibility(View.VISIBLE);
            myViewHolder.friendcircleContent.setText(friendCircleBean.getContent());
        }

        //显示发布时间
        myViewHolder.putTime.setText("" + DateFormatUtils.formatDate2Before(friendCircleBean.getCreateTime()));

        //将点过赞的item的position加到List集合中，加载item之前先判断该item的position是否在集合中，防止复用时候错乱
        if (beClickGood.contains((myViewHolder.itemParent.getTag()))) {
//                Logger.d("点过赞了" + position);
            myViewHolder.goodImg.setImageResource(R.mipmap.ic_good_press);
        } else {
//                Logger.d("当前item没有点过赞" + position);
            myViewHolder.goodImg.setImageResource(R.mipmap.ic_good);
        }
        //让刚点过赞的那条说说的点赞imageView变色
        myViewHolder.goodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long friendCircleId = friendCircleBean.getId();
                if (beClickGood.contains((myViewHolder.itemParent.getTag()))) {
                    myViewHolder.goodImg.setImageResource(R.mipmap.ic_good);
                } else {
                    //点赞过的item的position加入集合中
                    beClickGood.add(position);
                    goodClickListener.click(view, position, friendCircleId);
                    myViewHolder.goodImg.setImageResource(R.mipmap.ic_good_press);
                    //点赞后改变text
                    if (friendCircleBean.getLikeUserVOs().size() == 0){
                        myViewHolder.goodCounts.setText("1");
                    }else {
                        myViewHolder.goodCounts.setText("" + (friendCircleBean.getLikeUserVOs().size() + 1));
                    }

//                    Logger.d("给第" + position + "个item加了");
//                    ShowToast.show(context, "点赞" + position);
                }
            }
        });
        /**
         * 点击评论按钮
         */
        myViewHolder.commentImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long friendCircleId = friendCircleBean.getId();
                if (friendCircleId != null) {
                    commentClickListener.click(view, position, friendCircleId);
                }
            }
        });
        //设置点赞数量textView
        if (friendCircleBean.getLikeUserVOs().size() == 0) {
            myViewHolder.goodCounts.setText("0");
        } else {
            myViewHolder.goodCounts.setText("" + friendCircleBean.getLikeUserVOs().size());
        }

        List<CommentVO> comments = friendCircleBean.getCommentVOs();

        //设置评论数量textView
        myViewHolder.commentCounts.setText(comments.size() + "");
        String display = "";
        //判断评论条数，如果是最后一条就不加换行符了
        if (comments.size() == 0) {
            //如果评论数为0则不显示textView
            myViewHolder.commentsText.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < comments.size(); i++) {
                String temp = comments.get(i).getContent();
                if (i == comments.size() - 1) {
                    display = display + temp;
                } else {
                    display = display + temp + '\n';
                }
            }
            myViewHolder.commentsText.setVisibility(View.VISIBLE);
            myViewHolder.commentsText.setText(display);
        }
        //显示说说的图片，利用NineGridImageView
        if (friendCircleBean.getPicutres().size() > 0) {
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
            /*List<PictureBean> pictures = friendCircleBean.getPictures();
            List<String> pics = new ArrayList<>();
            for (int i = 0; i < pictures.size(); i++) {
                PictureBean pictureBean = pictures.get(i);
                String picture_url = pictureBean.getPicture_URL();
                pics.add(picture_url);
            }*/
            myViewHolder.ninegridImg.setImagesData(friendCircleBean.getPicutres());
        } else {
            myViewHolder.ninegridImg.setVisibility(View.GONE);
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

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
