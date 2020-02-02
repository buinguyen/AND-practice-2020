package com.alan.asm.vnexpressdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alan.asm.vnexpressdemo.R;

public class ArticleActivity extends BaseActivity {

    private static final String EXTRA_TITLE = "EXTRA_TITLE";
    private static final String EXTRA_LINK = "EXTRA_LINK";

    private String mTitle;
    private String mUrl;

    private ImageView btnBack;
    private SwipeRefreshLayout refreshLayout;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_article);

        bindViews();

        Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;

        extractData(bundle);

        setupViews();
    }

    private void bindViews() {
        btnBack = findViewById(R.id.btn_back);
        refreshLayout = findViewById(R.id.refresh_layout);
        webView = findViewById(R.id.web_view);
    }

    private void extractData(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        mTitle = bundle.getString(EXTRA_TITLE);
        mUrl = bundle.getString(EXTRA_LINK);
    }

    private void setupViews() {
        btnBack.setOnClickListener(v -> finish());
        refreshLayout.setOnRefreshListener(() -> refreshLayout.setRefreshing(false));

        refreshLayout.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if (isDarkMode()) {
                    view.loadUrl("javascript:document.body.style.setProperty(\"color\", \"white\");");
                    view.loadUrl("javascript:(function() { " +
                            "document.body.style.background='black';" +
                            "document.getElementsByClassName(\"title_news_detail\")[0].style.color=\"white\";" +
                            "var cols = document.getElementsByTagName(\"p\");" +
                            "for(i = 0; i < cols.length; i++) {" +
                            "  cols[i].style.color = \"white\";" +
                            "  cols[i].style.backgroundColor = \"black\";" +
                            "  cols[i].style.background = \"black\";" +
                            "} " +
                            "})();");
                }
                refreshLayout.setRefreshing(false);
                webView.setVisibility(View.VISIBLE);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);

        if (mUrl != null) {
            webView.loadUrl(mUrl);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(EXTRA_TITLE, mTitle);
        outState.putString(EXTRA_LINK, mUrl);
        super.onSaveInstanceState(outState);
    }

    public static Intent getIntent(Context context, String title, String url) {
        if (context == null || url == null) {
            return null;
        }
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtras(getBundle(title, url));
        return intent;
    }

    public static Bundle getBundle(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        bundle.putString(EXTRA_LINK, url);
        return bundle;
    }
}
