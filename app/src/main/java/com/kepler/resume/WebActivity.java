package com.kepler.resume;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kepler.resume.support.ActivityStarter;
import com.kepler.resume.support.CheckNetworkState;
import com.kepler.resume.support.Logger;
import com.kepler.resume.support.MyDialog;
import com.kepler.resume.support.OnActionListener;
import com.kepler.resume.support.Params;

import butterknife.BindView;

public class WebActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    private static final int requestCode = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Resume</font>",Html.FROM_HTML_MODE_LEGACY));
        }else
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Resume</font>"));
        webView.setPadding(0, 0, 0, 0);
        webView.setWebViewClient(new HelloWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportMultipleWindows(true);
        loadUrl();
    }

    private void loadUrl() {
        webView.loadUrl(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Params.URL, Params.URL_AMITKUMARJAISWAL));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_web;
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
//            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        } else {
            MyDialog.openConfirmDialog(this, "Exit Application", "Exit Now ?", new OnActionListener() {
                @Override
                public void onOkay(Object object) {
                    WebActivity.super.onBackPressed();
                }

                @Override
                public void onCancel(Object object) {

                }
            }, true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                ActivityStarter.startBaseActivityForResult(WebActivity.this, null, UrlListing.class, requestCode);
                break;
            case R.id.action_refresh:
                loadUrl();
                break;
        }
        return true;
    }

    private class HelloWebViewClient extends WebViewClient {
        final ProgressDialog mProgressDialog;

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Logger.e(HelloWebViewClient.class, url);
//            view.loadUrl(url);
//            return true;
//
//        }

        public HelloWebViewClient() {
            // do nothing
            mProgressDialog = ProgressDialog.show(
                    WebActivity.this, "", "Loading Page...");
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (CheckNetworkState.isNetworkAvailable(getBaseContext())) {
                try {
//                    handler.postDelayed(runnable, 15000);
                    if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                        mProgressDialog.show();
                    }
                } catch (Exception ignored) {

                }
            } else
                Logger.makeSimpleToast(getApplicationContext(), Params.NETWORK_ERROR);
        }

        private void dismissProgressDialog() {
            try {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            } catch (Exception ignored) {

            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Logger.makeSimpleToast(getApplicationContext(), description);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(String.valueOf(request.getUrl()));
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            dismissProgressDialog();
//            Logger.e(BaseActivityShowProductPageInWebBrowser.class, getCookie(faqJson, "_bb_vid"));
//            Logger.e(BaseActivityShowProductPageInWebBrowser.class, getCookie(faqJson, "csrftoken"));
//            Logger.e(BaseActivityShowProductPageInWebBrowser.class, getCookie(faqJson, "_bb_rdt"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 999:
                if (resultCode == RESULT_OK) {
                    webView.clearHistory();
                    loadUrl();
                }
                break;
        }
    }
}
