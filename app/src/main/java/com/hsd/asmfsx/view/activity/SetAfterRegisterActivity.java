package com.hsd.asmfsx.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.presenter.SetAfterRegisterPresenter;
import com.hsd.asmfsx.utils.ShowToast;
import com.orhanobut.logger.Logger;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.TimePicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * Created by 紫荆 on 2016/10/22.
 */

public class SetAfterRegisterActivity extends AppCompatActivity implements SetAfterRegisterContract.View {

    @BindView(R.id.tempimg)
    ImageView tempimg;
    private SetAfterRegisterPresenter setAfterRegisterPresenter;
    //拍照裁剪图片前后图片URI
    private Uri snocrop = Uri.parse("file:///sdcard/snocrop");
    private Uri scropped = Uri.parse("file:///sdcard/scropped");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setafterregister_layout);
        ButterKnife.bind(this);
        setAfterRegisterPresenter = new SetAfterRegisterPresenter(this);
//        setAfterRegisterPresenter.start();
        setHead();

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
        }else if (requestCode == Crop.REQUEST_CROP) {
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
     * 通过相册选择图片裁剪 step 3
     * 调用Crop.pickImage后选择的图片调用Crop来裁剪
     * @param source  通过相册选择的图片URI
     */
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    /**
     *  裁剪完成
     * @param resultCode 通过此参数判断是否完成裁剪
     * @param result
     */
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            //如果裁剪正常，resultCode == RESULT_OK则到这里裁剪完成
            tempimg.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Logger.d(""+Crop.getError(result).getMessage());
            ShowToast.show(SetAfterRegisterActivity.this, Crop.getError(result).getMessage());
        }
    }
    /**
     * 设置头像
     */
    private void setHead() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new String[]{"选择图片", "拍照"}, new DialogInterface.OnClickListener() {
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
        builder.show();
    }

    private void pickOther() {
        OptionPicker picker = new OptionPicker(this, new String[]{
                "第一项", "第二项", "这是一个很长很长很长很长很长很很长很长的项"});
        picker.setOffset(2);
        picker.setSelectedIndex(1);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {

            }
        });
        picker.show();
    }

    private void pickHeight() {
        NumberPicker picker = new NumberPicker(this);
        picker.setOffset(2);//偏移量
        picker.setRange(145, 200, 1);//数字范围
        picker.setSelectedItem(172);
        picker.setLabel("厘米");
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {

            }
        });
        picker.show();
    }

    private void pickDate() {
        TimePicker picker = new TimePicker(this, TimePicker.HOUR_24);
        picker.setRangeStart(0, 0);//
        picker.setRangeEnd(23, 59);//
        picker.setTopLineVisible(false);
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {

            }
        });
        picker.show();
    }

    private void pickProvince() {
        try {
            ArrayList<Province> data = new ArrayList<Province>();
            InputStream inputStream = this.getResources().getAssets().open("city.json");
            String json = ConvertUtils.toString(inputStream);
            ArrayList<Province> list = GetGson.getGson().fromJson(json, new TypeToken<ArrayList<Province>>() {
            }.getType());
            data.addAll(list);
            AddressPicker picker = new AddressPicker(this, data);
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    ShowToast.show(SetAfterRegisterActivity.this, province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
            });
//            picker.setHideProvince(true);//加上此句举将只显示地级及县级
            //picker.setHideCounty(true);//加上此句举将只显示省级及地级
            //picker.setColumnWeight(2/8.0, 3/8.0, 3/8.0);//省级、地级和县级的比例为2:3:3
            picker.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
