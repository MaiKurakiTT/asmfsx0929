package com.hsd.asmfsx.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.bean.UserInformationBean;
import com.hsd.asmfsx.contract.SetAfterRegisterContract;
import com.hsd.asmfsx.presenter.SetAfterRegisterPresenter;
import com.hsd.asmfsx.utils.DateFormatUtils;
import com.hsd.asmfsx.utils.PickViewUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.orhanobut.logger.Logger;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.toolbar_centertext)
    TextView toolbarCentertext;
    @BindView(R.id.toolbar_righttext)
    TextView toolbarRighttext;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    private SetAfterRegisterPresenter setAfterRegisterPresenter;
    //拍照裁剪图片前后图片URI
    private Uri snocrop = Uri.parse("file:///sdcard/snocrop");
    private Uri scropped = Uri.parse("file:///sdcard/scropped");
    private String[] sexItems = {"男", "女"};
    private String[] schoolItems = {"河南师范大学"};
    private String[] statusItems = {"单身", "恋爱ing", "分手了"};
    private AlertDialog.Builder headBuilder;
    private String phone;
    private String name;
    private String sex;
    private String birthdayString;
    private String heightString;
    private Date birthday;
    private Integer height;
    private String home;
    private String school;
    private String statusString;
    private UserInformationBean userInformationBean;

    private String cropSuccessPath;
    private File imgFile;
    private ProgressDialog progressDialog;

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

    private void getData() {
        phone = phonetext.getText().toString();
        name = nametext.getText().toString();
        sex = sextext.getText().toString();
        birthdayString = birthdaytext.getText().toString();
        if (!TextUtils.isEmpty(birthdayString)) {
            birthday = DateFormatUtils.FormatString2Date(birthdayString);
        }
        heightString = heighttext.getText().toString().replace("cm", "");
        if (!TextUtils.isEmpty(heightString)) {
            height = Integer.valueOf(heightString);
        }
        home = hometext.getText().toString();
        school = schooltext.getText().toString();
        statusString = statustext.getText().toString();
    }

    private void initData() {

    }

    private void initView() {
        setSupportActionBar(normalToolbar);
        getSupportActionBar().setTitle("");
        toolbarCentertext.setText("填写信息");
        toolbarRighttext.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
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

        okbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(name) || TextUtils.isEmpty(sex)
                        || TextUtils.isEmpty(birthdayString) || TextUtils.isEmpty(heightString) || TextUtils.isEmpty(home) ||
                        TextUtils.isEmpty(school) || TextUtils.isEmpty(statusString)) {
                    ShowToast.show(SetAfterRegisterActivity.this, "请先完善信息");
                } else if (TextUtils.isEmpty(cropSuccessPath)) {
                    ShowToast.show(SetAfterRegisterActivity.this, "要设置头像哦~");
                } else {
                    setAfterRegisterPresenter.start();
                }
            }
        });
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
                        Crop.pickImage(SetAfterRegisterActivity.this);
                        break;
                    case 1:
                        if (ContextCompat.checkSelfPermission(SetAfterRegisterActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                ) {
                            ActivityCompat.requestPermissions(SetAfterRegisterActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                            if (ActivityCompat.shouldShowRequestPermissionRationale(SetAfterRegisterActivity.this, Manifest.permission.CAMERA)
                                    ) {
                                new AlertDialog.Builder(SetAfterRegisterActivity.this)
                                        .setMessage("申请相机权限")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //申请相机权限
                                                ActivityCompat.requestPermissions(SetAfterRegisterActivity.this,
                                                        new String[]{Manifest.permission.CAMERA}, 1);
                                            }
                                        })
                                        .show();
                            } else {
                                //申请相机权限
                                ActivityCompat.requestPermissions(SetAfterRegisterActivity.this,
                                        new String[]{Manifest.permission.CAMERA}, 1);
                            }
                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, snocrop);
                            startActivityForResult(intent, 1);// 采用ForResult打开
                        }

                        break;
                }
            }
        });
        headBuilder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, snocrop);
                    startActivityForResult(intent, 1);// 采用ForResult打开
                } else {
                    ShowToast.show(SetAfterRegisterActivity.this, "缺少相机权限");
                }
                break;
        }
    }

    @Override
    public UserInformationBean getUserInformationBean() {
        userInformationBean = new UserInformationBean();
        userInformationBean.setUser_phone(phone);
        userInformationBean.setUser_nickname(name);
        userInformationBean.setUser_sex(sex);
        userInformationBean.setUser_birthday(birthday);
        userInformationBean.setUser_height(height);
        userInformationBean.setUser_locality(home);
        userInformationBean.setUser_school(school);
        userInformationBean.setUser_state(statusString);
        userInformationBean.setUUID("84f4b998-17df-4997-8fc2-828f89aec37d");
        return userInformationBean;
    }

    @Override
    public File getImgFile() {
        return imgFile;
    }

    @Override
    public void showData(UserInformationBean userInformationBean) {
        if (userInformationBean.getResultCode() == 1) {
            Snackbar.make(okbut, "信息设置成功", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(okbut, "信息设置失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAfterRegisterPresenter.start();
                }
            }).show();
        }
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
    public void showFailed() {
        Snackbar.make(okbut, "信息设置失败", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAfterRegisterPresenter.start();
            }
        }).show();
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
            headimg.setImageResource(R.mipmap.ic_head);
            Uri uri = Crop.getOutput(result);
            cropSuccessPath = uri.getPath();
            headimg.setImageURI(uri);
            Logger.d("" + cropSuccessPath);
            imgFile = new File(cropSuccessPath);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Logger.d("" + Crop.getError(result).getMessage());
            ShowToast.show(SetAfterRegisterActivity.this, Crop.getError(result).getMessage());
        }
    }
}
