package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.adapter.ImgViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2016/12/12.
 * 朋友圈图片浏览
 */

public class ImgViewPagerActivity extends AppCompatActivity {
    @BindView(R.id.img_pager)
    ViewPager imgPager;
    @BindView(R.id.cur_position)
    TextView curPosition;
    @BindView(R.id.img_pager_parent)
    RelativeLayout imgPagerParent;

    private List<String> urls = new ArrayList<>();
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_viewpager_layout);
        ButterKnife.bind(this);
        imgPagerParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        urls = getIntent().getStringArrayListExtra("urls");
        position = getIntent().getIntExtra("position", 0);
        if (urls != null) {
            curPosition.setText(position + 1 + "/" + urls.size());
            ImgViewPagerAdapter adapter = new ImgViewPagerAdapter(getSupportFragmentManager(), urls);
            imgPager.setAdapter(adapter);
            imgPager.setCurrentItem(position);
            imgPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    curPosition.setText(position + 1 + "/" + urls.size());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }
}
