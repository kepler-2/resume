package com.kepler.resume;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import butterknife.ButterKnife;

/**
 * Created by Amit on 28-02-2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestFeature();
        setWindowFlag();
        setContentView(getContentView());
        ButterKnife.bind(this);
    }

    public abstract int getContentView();

    public void requestFeature() {

    }

    public void setWindowFlag() {

    }
}
