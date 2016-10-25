package com.hsd.asmfsx.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserBean;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.global.GetGson;
import com.hsd.asmfsx.presenter.SetAfterRegisterPresenter;
import com.hsd.asmfsx.utils.ShowToast;

import java.io.InputStream;
import java.util.ArrayList;

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

public class SetAfterRegisterActivity extends AppCompatActivity implements SetAfterRegisterContract.View{

    private SetAfterRegisterPresenter setAfterRegisterPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setafterregister_layout);
        setAfterRegisterPresenter = new SetAfterRegisterPresenter(this);
//        setAfterRegisterPresenter.start();

    }
    private void pickOther(){
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
    private void pickHeight(){
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
    private void pickDate(){
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
    private void pickProvince(){
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
        }catch (Exception e){
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
