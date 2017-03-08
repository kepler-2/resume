package com.kepler.resume.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.kepler.resume.R;

/**
 * Created by Amit on 06-03-2017.
 */

public class MyDialog {
    public static Dialog openConfirmDialog(Activity activity, final String title, final String message, final OnActionListener onOkCancel, boolean isCustomAnimation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onOkCancel.onOkay(null);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onOkCancel.onCancel(null);
            }
        }).setCancelable(false).setMessage(message);
        final Dialog dialog = builder.create();
        if (title == null)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        else
            dialog.setTitle(title);
        if (isCustomAnimation)
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static Dialog openAlertDialog(Activity activity, final String title, final String message, final OnActionListener onOkCancel, boolean isCustomAnimation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onOkCancel.onOkay(null);
            }
        }).setCancelable(false).setMessage(message);
        final Dialog dialog = builder.create();
        if (title == null)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        else
            dialog.setTitle(title);
        if (isCustomAnimation)
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

//    public static Dialog openInputDialog(Activity activity, final String title, final OnActionListener onOkCancel, boolean isCustomAnimation) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setTitle(title);
//
//// Set up the input
//        final EditText input = new EditText(activity);
//// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//        input.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
//        builder.setView(input);
//
//// Set up the buttons
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                m_Text = input.getText().toString();
//                onOkCancel.onOkay(input.getText().toString());
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//                onOkCancel.onCancel(null);
//            }
//        });
//        final Dialog dialog = builder.create();
//        if (isCustomAnimation)
//            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
////
//        dialog.show();
//        return dialog;
//    }

}
