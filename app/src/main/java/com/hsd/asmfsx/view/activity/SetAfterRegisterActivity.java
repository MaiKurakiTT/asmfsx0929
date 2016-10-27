package com.hsd.asmfsx.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.presenter.SetAfterRegisterPresenter;
import com.hsd.asmfsx.utils.PickViewUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.orhanobut.logger.Logger;
import com.soundcloud.android.crop.Crop;

import org.w3c.dom.Text;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.TimePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class SetAfterRegisterActivity extends AppCompatActivity implements SetAfterRegisterContract.View {

    @BindView(R.id.headimg)
    CircleImageView headimg;
    @BindView(R.id.match_phonetext)
    TextView matchPhonetext;
    @BindView(R.id.phonetext)
    TextView phonetext;
    @BindView(R.id.phoneparent)
    RelativeLayout phoneparent;
    @BindView(R.id.nametext)
    EditText nametext;
    @BindView(R.id.nameparent)
    RelativeLayout nameparent;
    @BindView(R.id.match_sextext)
    TextView matchSextext;
    @BindView(R.id.sextext)
    TextView sextext;
    @BindView(R.id.sexparent)
    RelativeLayout sexparent;
    @BindView(R.id.match_birthdaytext)
    TextView matchBirthdaytext;
    @BindView(R.id.birthdaytext)
    TextView birthdaytext;
    @BindView(R.id.birthdayparent)
    RelativeLayout birthdayparent;
    @BindView(R.id.match_heighttext)
    TextView matchHeighttext;
    @BindView(R.id.heighttext)
    TextView heighttext;
    @BindView(R.id.heightparent)
    RelativeLayout heightparent;
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
    @BindView(R.id.okbut)
    AppCompatButton okbut;
    private SetAfterRegisterPresenter setAfterRegisterPresenter;
    //拍照裁剪图片前后图片URI
    private Uri snocrop = Uri.parse("file:///sdcard/snocrop");
    private Uri scropped = Uri.parse("file:///sdcard/scropped");
    private String[] sexItems = {"男", "女"};
    private String[] schoolItems = {"河南师范大学"};
    private String[] statusItems = {"单身", "热恋ing", "分手了"};
    private AlertDialog.Builder headBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setafterregister_layout);
        ButterKnife.bind(this);
        initData();
        initView();
        setData2View();
        setAfterRegisterPresenter = new SetAfterRegisterPresenter(this);

    }

    private void initData() {

    }

    private void initView() {
        headimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHead();
            }
        });
    }

    private void setData2View() {
        //设置性别
        sexparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickViewUtils(SetAfterRegisterActivity.this, sextext).pickOther(sexItems);
            }
        });
        //设置生日
        birthdayparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickViewUtils(SetAfterRegisterActivity.this, birthdaytext).pickDate();
            }
        });
        //设置身高
        heightparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickViewUtils(SetAfterRegisterActivity.this, heighttext).pickHeight();
            }
        });
        //设置地区
        homeparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickViewUtils(SetAfterRegisterActivity.this, hometext).pickProvince();
            }
        });
        //设置学校
        schoolparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickViewUtils(SetAfterRegisterActivity.this, schooltext).pickOther(schoolItems);
            }
        });
        //设置恋爱状态
        statusparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickViewUtils(SetAfterRegisterActivity.this, statustext).pickOther(statusItems);
            }
        });
    }

    /**
     * 从其他Activity回传过来的信息
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            /**
             * 通过相册选择图片裁剪 step 2
             * 当调用相册选择一张图片后，将图片信息通过data传过来
             * data.getData()获取到图片URI
             */
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            //调用Crop.start后来到这
            handleCrop(resultCode, data);
        }
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Crop.of(snocrop, scropped).asSquare().start(this);
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 设置头像
     */
    private void setHead() {
        headBuilder = new AlertDialog.Builder(this);
        headBuilder.setItems(new String[]{"选择图片", "拍照"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        /**
                         * 通过相册选择图片裁剪 step 1
                         * 调用相册选择图片
                         */
                        Crop.pickImage(SetAfterRegisterActivity.this);
                        break;
                    case 1:
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, snocrop);
                        startActivityForResult(intent, 1);// 采用ForResult打开
                        break;
                }
            }
        });
        headBuilder.show();
    }


    @Override
    public UserInformationBean getUserInformationBean() {
        UserInformationBean userInformationBean = new UserInformationBean();
        UserBean userBean = new UserBean();
        userBean.setUUID("84f4b998-17df-4997-8fc2-828f89aec37d");
        userInformationBean.setUser(userBean);
        userInformationBean.setUser_nickname("2016年10月22日");
        return userInformationBean;
    }

    @Override
    public void showData(UserInformationBean userInformationBean) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFailed() {

    }

    /**
     * 通过相册选择图片裁剪 step 3
     * 调用Crop.pickImage后选择的图片调用Crop来裁剪
     *
     * @param source 通过相册选择的图片URI
     */
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    /**
     * 裁剪完成
     *
     * @param resultCode 通过此参数判断是否完成裁剪
     * @param result
     */
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            //如果裁剪正常，resultCode == RESULT_OK则到这里裁剪完成
            headimg.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Logger.d("" + Crop.getError(result).getMessage());
            ShowToast.show(SetAfterRegisterActivity.this, Crop.getError(result).getMessage());
        }
    }
}
