package com.pichui.news.ui.activity;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pi.core.uikit.statusbar.Eyes;
import com.pichui.news.R;
import com.pichui.news.ui.base.BaseActivity;
import com.pichui.news.ui.base.BasePresenter;
import com.pichui.news.uitil.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {
    public static final String URL = "url";
    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.tv_author)
    TextView mTvTitle;

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    @BindView(R.id.wv_content)
    WebView mWvContent;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_webview;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_BDBDBD));
    }
    @Override
    public void initData() {
        String url = getIntent().getStringExtra(URL);
        mWvContent.loadUrl(url);
    }
    @Override
    public void initListener() {
        WebSettings settings = mWvContent.getSettings();
        settings.setJavaScriptEnabled(true);


        mWvContent.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mPbLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mPbLoading.setVisibility(View.GONE);
            }
        });

        mWvContent.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mPbLoading.setProgress(newProgress);
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTvTitle.setText(title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        mWvContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWvContent.canGoBack()) {  //表示按返回键
                        mWvContent.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    }
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
