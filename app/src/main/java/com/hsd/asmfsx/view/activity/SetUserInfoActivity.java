package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.utils.Date2Star;
import com.hsd.asmfsx.utils.DateFormatUtils;
import com.hsd.asmfsx.utils.PickViewUtils;
import com.hsd.asmfsx.utils.ShowToast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2016/11/21.
 */

public class SetUserInfoActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.nametext)
    EditText nametext;
    @BindView(R.id.nameparent)
    RelativeLayout nameparent;
    @BindView(R.id.match_birthdaytext)
    TextView matchBirthdaytext;
    @BindView(R.id.birthdaytext)
    TextView birthdaytext;
    @BindView(R.id.birthdayparent)
    RelativeLayout birthdayparent;
    @BindView(R.id.match_startext)
    TextView matchStartext;
    @BindView(R.id.startext)
    TextView startext;
    @BindView(R.id.starparent)
    RelativeLayout starparent;
    @BindView(R.id.match_hometext)
    TextView matchHometext;
    @BindView(R.id.hometext)
    TextView hometext;
    @BindView(R.id.homeparent)
    RelativeLayout homeparent;
    @BindView(R.id.match_schooltext)
    TextView matchSchooltext;
    @BindView(R.id.schooltext)
    TextView schooltext;
    @BindView(R.id.schoolparent)
    RelativeLayout schoolparent;
    @BindView(R.id.match_statustext)
    TextView matchStatustext;
    @BindView(R.id.statustext)
    TextView statustext;
    @BindView(R.id.statusparent)
    RelativeLayout statusparent;
    @BindView(R.id.match_signtext)
    TextView matchSigntext;
    @BindView(R.id.signtext)
    TextView signtext;
    @BindView(R.id.signparent)
    RelativeLayout signparent;
    @BindView(R.id.okbut)
    AppCompatButton okbut;

    private String[] schoolItems = {"河南师范大学"};
    private String[] statusItems = {"单身", "恋爱ing", "分手了"};
    private String nickName;
    private String birthdayString;
    private Date birthday;
    private String star;
    private String home;
    private String school;
    private String statusString;
    private int status;
    private String sign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setuserinfo_layout);
        ButterKnife.bind(this);
        initData();
        initView();
        setData2View();
    }

    private void initView() {
        setSupportActionBar(normalToolbar);
        getSupportActionBar().setTitle("");
        toolbarCentertext.setText("设置信息");
        toolbarRighttext.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initData() {

    }

    private void getData() {
        nickName = nametext.getText().toString();
        birthdayString = birthdaytext.getText().toString();
        if (!TextUtils.isEmpty(birthdaytext.getText().toString())) {
            birthday = DateFormatUtils.formatString2Date(birthdayString);
        }
        star = startext.getText().toString();
        home = hometext.getText().toString();
        school = schooltext.getText().toString();
        statusString = startext.getText().toString();
        switch (statusString) {
            case "单身":
                status = 0;
                break;
            case "恋爱ing":
                status = 1;
                break;
            case "分手了":
                status = 2;
                break;
        }
        sign = signtext.getText().toString();
    }

    private void setData2View() {
        birthdayparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickViewUtils(SetUserInfoActivity.this, birthdaytext).pickDate();
            }
        });
        starparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = birthdaytext.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    Date date = DateFormatUtils.formatString2Date(s);
                    if (date != null){
                        startext.setText(Date2Star.date2Constellation(date)+"");
                    }
                }else {
                    ShowToast.show(SetUserInfoActivity.this, "要先设置生日~");
                }
            }
        });
        homeparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickViewUtils(SetUserInfoActivity.this, hometext).pickProvince();
            }
        });
        schoolparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickViewUtils(SetUserInfoActivity.this, schooltext).pickOther(schoolItems);
            }
        });
        statusparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickViewUtils(SetUserInfoActivity.this, statustext).pickOther(statusItems);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
