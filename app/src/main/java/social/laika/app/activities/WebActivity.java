package social.laika.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import social.laika.app.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Tito_Leiva on 24-03-15.
 */
public class WebActivity extends Activity {

    public static final String URL = "url";
    private int mIdLayout = R.layout.web_activity;

    public ProgressBar mProgressBar;
    public WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(mIdLayout);

        Intent intent = getIntent();
        String url = intent.getStringExtra(URL);

        mProgressBar = (ProgressBar) findViewById(R.id.web_progressBar);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl(url);
        mWebView.setWebChromeClient(new MyWebViewClient());

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            WebActivity.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }

    public void setValue(int progress) {

        if (progress >= 95 && mProgressBar.getVisibility() != View.GONE) {
            this.mProgressBar.setVisibility(View.GONE);
        }

        this.mProgressBar.setProgress(progress);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}