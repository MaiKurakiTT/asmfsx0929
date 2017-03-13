package com.hsd.asmfsx.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hsd.asmfsx.R;
import com.hsd.asmfsx.base.BaseActivity;
import com.hsd.asmfsx.bean.BaseBean2;
import com.hsd.asmfsx.bean.UserInformationBean2;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.presenter.SetAfterRegisterPresenter;
import com.hsd.asmfsx.utils.PermissionUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.orhanobut.logger.Logger;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sun on 2017/3/13.
 */

public class BigHeadImgActivity extends BaseActivity implements SetAfterRegisterContract.View{
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.change_img)
    ImageView changeImg;
    private int HEAD_PERMISSION = 0;
    private Uri snocrop = Uri.parse("file:///sdcard/snocrop");
    private Uri scropped = Uri.parse("file:///sdcard/scropped");
    private String cropSuccessPath;
    private PermissionUtils permissionUtils;
    private File imgFile;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder headBuilder;
    private SetAfterRegisterPresenter setAfterRegisterPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_head_img_layout);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        String img = getIntent().getStringExtra("img");
        Glide.with(this).load(img).into(headImg);
        setResult(1);
        permissionUtils = new PermissionUtils(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        setAfterRegisterPresenter = new SetAfterRegisterPresenter(this);
        changeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setHead();
            }
        });
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
                        /*通过相册选择图片裁剪 step 1
                        调用相册选择图片*/
                        Crop.pickImage(BigHeadImgActivity.this);
                        break;
                    case 1:
                        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
                        if (permissionUtils.checkPermissions(permissions)){
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, snocrop);
                            startActivityForResult(intent, 1);// 采用ForResult打开
                        }else {
                            requestPermission(permissions, HEAD_PERMISSION);
                        }


                        break;
                }
            }
        });
        headBuilder.show();
    }

    /**
     * 从其他Activity回传过来的信息
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
           /* *
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
            headImg.setImageResource(R.mipmap.ic_head);
            Uri uri = Crop.getOutput(result);
            cropSuccessPath = uri.getPath();
            headImg.setImageURI(uri);
            Logger.d("" + cropSuccessPath);
            imgFile = new File(cropSuccessPath);
            setAfterRegisterPresenter.start();
        } else if (resultCode == Crop.RESULT_ERROR) {
            Logger.d("" + Crop.getError(result).getMessage());
            ShowToast.show(BigHeadImgActivity.this, Crop.getError(result).getMessage());
        }
    }

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    public void requestPermission(String[] permissions, int requestCode) {
        permissionUtils.REQUEST_CODE_PERMISSION = requestCode;
        if (permissionUtils.checkPermissions(permissions)) {
            permissionSuccess(permissionUtils.REQUEST_CODE_PERMISSION);
        } else {
            List<String> needPermissions = permissionUtils.getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), permissionUtils.REQUEST_CODE_PERMISSION);
        }
    }


    /**
     * 获取权限成功
     *
     * @param requestCode
     */
    public void permissionSuccess(int requestCode) {
        if (requestCode == HEAD_PERMISSION){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, snocrop);
            startActivityForResult(intent, 1);// 采用ForResult打开
        }
//        Log.d(TAG, "获取权限成功=" + requestCode);

    }

    /**
     * 权限获取失败
     *
     * @param requestCode
     */
    public void permissionFail(int requestCode) {
//        Log.d(TAG, "获取权限失败=" + requestCode);
    }



    /**
     * 系统请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionUtils.REQUEST_CODE_PERMISSION) {
            if (permissionUtils.verifyPermissions(grantResults)) {
                permissionSuccess(permissionUtils.REQUEST_CODE_PERMISSION);
            } else {
                permissionFail(permissionUtils.REQUEST_CODE_PERMISSION);
                permissionUtils.showTipsDialog();
            }
        }
    }

    @Override
    public UserInformationBean2 getUserInformationBean() {
        UserInformationBean2 userInformationBean2 = new UserInformationBean2();
        return userInformationBean2;
    }

    @Override
    public File getImgFile() {
        return imgFile;
    }

    @Override
    public void showData(BaseBean2 baseBean) {

    }

    @Override
    public void showFailedForResult(BaseBean2 baseBean) {

    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showFailedForException(Throwable t) {

    }
}
