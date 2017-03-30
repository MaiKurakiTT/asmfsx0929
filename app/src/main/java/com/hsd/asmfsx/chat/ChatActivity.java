package com.hsd.asmfsx.chat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.hsd.asmfsx.R;
import com.hsd.asmfsx.utils.PermissionUtils;
import com.hsd.asmfsx.utils.ShowToast;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;

import java.util.List;

/**
 * Created by apple on 2016/11/5.
 */

public class ChatActivity extends AppCompatActivity {

    private String toName;

    private PermissionUtils permissionUtils;
    private int HEAD_PERMISSION = 89;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        openChat();




    }

    private void openChat() {
        toName = getIntent().getStringExtra("name");
        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new ChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, toName);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.chat_parent, chatFragment).commit();
        permissionUtils = new PermissionUtils(this);

        String[] permissions = {Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO};
        if (permissionUtils.checkPermissions(permissions)){

        }else {
            requestPermission(permissions, HEAD_PERMISSION);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("name");
        if (toName.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
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
        }
//        Log.d(TAG, "获取权限成功=" + requestCode);

    }

    /**
     * 权限获取失败
     *
     * @param requestCode
     */
    public void permissionFail(int requestCode) {
        ShowToast.show(ChatActivity.this, "获取权限失败");
//        finish();
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
}
