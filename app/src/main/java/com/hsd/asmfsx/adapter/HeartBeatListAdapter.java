package com.hsd.asmfsx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserInformationBean;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by apple on 16/10/5.
 */

public class HeartBeatListAdapter extends RecyclerView.Adapter<HeartBeatListAdapter.MyViewHolder>{

    private Context context;
    private List<UserInformationBean> userInformationList;
    public String TAG = "HeartBeatListAdapter";
    private final LayoutInflater layoutInflater;

    public HeartBeatListAdapter(Context context, List<UserInformationBean> userInformationList) {
        this.context = context;
        this.userInformationList = userInformationList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = View.inflate(context, R.layout.heartbeat_cardview, parent);
        View inflate = layoutInflater.inflate(R.layout.heartbeat_cardview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "当前显示的是 == " + userInformationList.get(position).getUser_nickname());
        holder.nameText.setText(userInformationList.get(position).getUser_nickname());
    }

    @Override
    public int getItemCount() {
        return userInformationList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        public MyViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.heartbeatname);
        }
    }
}
