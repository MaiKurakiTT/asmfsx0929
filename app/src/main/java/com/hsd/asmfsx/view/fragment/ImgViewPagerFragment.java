package com.hsd.asmfsx.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/12.
 */

public class ImgViewPagerFragment extends Fragment {

    @BindView(R.id.img_pager_img)
    ImageView imgPagerImg;

    public static ImgViewPagerFragment getInstance(Bundle bundle){
        ImgViewPagerFragment imgViewPagerFragment = new ImgViewPagerFragment();
        imgViewPagerFragment.setArguments(bundle);
        return imgViewPagerFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.img_viewpager_item, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(this).load(getArguments().getString("url")).placeholder(R.mipmap.ic_loadingimg).into(imgPagerImg);
    }
}
