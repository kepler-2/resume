package com.kepler.resume.support;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.kepler.resume.BaseActivity;
import com.kepler.resume.R;
import com.kepler.resume.WebActivity;

/**
 * Created by 12 on 1/16/2016.
 */
public class ActivityStarter {


    public static void startAppInBrowserActivity(Context context, Bundle bundle, boolean isFinished) {
        if (bundle == null) {
            Toast.makeText(context, "Sorry! Something wrong.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtras(bundle);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.startActivity(intent);
            finishActivity(activity, isFinished);
            activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static void startBrowserActivity(Context context, Bundle bundle, boolean isFinished) {
        try {
            if (bundle == null) {
                Toast.makeText(context, "Sorry! Something wrong.", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bundle.getString(Params.URL)));
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                activity.startActivity(intent);
                finishActivity(activity, isFinished);
            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Browser not found in your device.", Toast.LENGTH_LONG).show();
            startAppInBrowserActivity(context, bundle, isFinished);
        }
    }


    private static void finishActivity(Activity activity, boolean isFinish) {
        try {
            if (isFinish)
                activity.finish();
        } catch (Exception ignored) {

        }
    }


    public static void startAppInPlayStore(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.indiashopps.android"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.indiashopps.android"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


//    public static void startActivity(Context context, Bundle bundle, Class<? extends BaseActivityNoToolbar> activityClass) {
//        Intent intent = new Intent(context, activityClass);
//        if (bundle != null)
//            intent.putExtras(bundle);
//        if (context instanceof Activity) {
//            Activity activity = (Activity) context;
//            activity.startActivity(intent);
//            finishActivity(activity, false);
//            activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//        } else {
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        }
//    }

    public static void startBaseActivity(Context context, Bundle bundle, Class<? extends BaseActivity> activityClass, boolean isFinished) {
        Intent intent = new Intent(context, activityClass);
        if (bundle != null)
            intent.putExtras(bundle);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.startActivity(intent);
            finishActivity(activity, isFinished);
            activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static void startBaseActivityForResult(Activity activity, Bundle bundle, Class<? extends BaseActivity> activityClass,int request_code) {
        Intent intent = new Intent(activity, activityClass);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivityForResult(intent, request_code);
        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


}
