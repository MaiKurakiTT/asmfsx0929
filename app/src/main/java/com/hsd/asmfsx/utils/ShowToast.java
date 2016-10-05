package com.hsd.asmfsx.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by apple on 16/10/5.
 */

public class ShowToast {
    static Toast toast;
    public static void show(Context context, String str){
        if (toast == null){
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        }else {
            toast.setText(str);
        }
        toast.show();
    }
}
