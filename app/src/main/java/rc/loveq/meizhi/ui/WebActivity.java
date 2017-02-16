package rc.loveq.meizhi.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.daimajia.numberprogressbar.NumberProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import rc.loveq.meizhi.R;
import rc.loveq.meizhi.ui.base.ToolBarActivity;
import rc.loveq.meizhi.util.Androids;
import rc.loveq.meizhi.util.Toasts;

public class WebActivity extends ToolBarActivity {
    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_TITLE = "extra_title";
    public String mExtraUrl;
    public String mExtraTitle;

    @BindView(R.id.textSwticher)
    TextSwitcher mTextSwticher;
    @BindView(R.id.progressbar)
    NumberProgressBar mProgressbar;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    public static Intent newIntent(Context context, String extraUrl, String extralTitle) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, extraUrl);
        intent.putExtra(EXTRA_TITLE, extralTitle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mExtraUrl = getIntent().getStringExtra(EXTRA_URL);
        mExtraTitle = getIntent().getStringExtra(EXTRA_TITLE);

        WebSettings settings = mWebView.getSettings();
        //支持JavaScript 这很重要
        settings.setJavaScriptEnabled(true);
        // //设置webview自适应屏幕大小
        settings.setLoadWithOverviewMode(true);

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //        设置H5的缓存是否打开，默认关闭。
        settings.setAppCacheEnabled(true);

        settings.setSupportZoom(true);
        mWebView.setWebChromeClient(new ChromeClient());

        mWebView.setWebViewClient(new LiaoClient());
        mWebView.loadUrl(mExtraUrl);

        mTextSwticher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView textView=new TextView(WebActivity.this);
                textView.setSingleLine(true);
                textView.setTextAppearance(WebActivity.this,R.style.WebTitle);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       textView.setSelected(true);
                    }
                },100);
                return textView;
            }
        });
        mTextSwticher.setInAnimation(this,android.R.anim.fade_in);
        mTextSwticher.setOutAnimation(this,android.R.anim.fade_out);
        setTitle(mExtraTitle);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTextSwticher.setText(title);
    }

    private class LiaoClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) {
                view.loadUrl(url);
            }
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction()== KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()){
                        mWebView.goBack();
                    }else{
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressbar.setProgress(newProgress);
            if (newProgress==100) {
                mProgressbar.setVisibility(View.GONE);
            }else {
                mProgressbar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_refresh:
                refresh();
                return true;
            case R.id.action_copy_url:
                String copy=getString(R.string.tip_copy_done);
                Androids.copyToClipBoard(this,mWebView.getUrl(),copy);
                return true;
            case R.id.action_open_url:
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri=Uri.parse(mExtraUrl);
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivity(intent);
                }else{
                    Toasts.showLong(R.string.tip_open_fail);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        mWebView.reload();
    }
}
