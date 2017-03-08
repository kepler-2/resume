package com.kepler.resume;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.kepler.resume.support.ActivityStarter;

import butterknife.BindView;

public class Splash extends BaseActivity {
    @BindView(R.id.activity_splash)
    LinearLayout activity_splash;
    final static int INTERVAL = 500; // 1/2 second
    final static String[] colors= {"#D0DFB8","#937969","#A2E0DA","#F2AFD2","#F5B9AC","#D29EED"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        new Thread(new Runnable() {
            int i=0;
            public void run() {
                while (i<6) {
                    try {
                        Thread.sleep(INTERVAL);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateColor(i);
                    i++;
                }
            }
        }).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityStarter.startBaseActivity(Splash.this, null, WebActivity.class, true);
            }
        }, 3000);
    }

    private void updateColor(final int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity_splash.setBackgroundColor(Color.parseColor(colors[i]));
            }
        });
    }

    @Override
    public void requestFeature() {
        super.requestFeature();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void setWindowFlag() {
        super.setWindowFlag();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }
}
