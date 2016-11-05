package com.hsd.asmfsx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by apple on 2016/11/5.
 */

public class TestBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object extras = intent.getExtras().get("metas");
    }
}
