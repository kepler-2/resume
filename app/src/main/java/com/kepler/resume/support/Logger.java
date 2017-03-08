package com.kepler.resume.support;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Amit on 07-03-2017.
 */

public class Logger {

    public static void makeSimpleToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
