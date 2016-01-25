package parteam.letspartya.activity;


import android.os.Bundle;
import android.webkit.WebView;

import parteam.letspartya.R;

/**
 * Created by mengce on 2016/1/7.
 */
public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private String mUrl;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity);
        mUrl = getIntent().getStringExtra("");
    }

    private void init(){

        mWebView = (WebView)findViewById(R.id.web_view);
        mWebView.loadUrl(mUrl);
    }
}
