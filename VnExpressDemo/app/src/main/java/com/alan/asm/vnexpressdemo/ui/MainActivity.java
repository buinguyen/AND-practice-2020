package com.alan.asm.vnexpressdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alan.asm.vnexpressdemo.R;
import com.alan.asm.vnexpressdemo.model.Channel;
import com.alan.asm.vnexpressdemo.model.RssItem;
import com.alan.asm.vnexpressdemo.task.DownloadWebTask;
import com.alan.asm.vnexpressdemo.utils.Constant;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends BaseActivity implements DownloadWebTask.OnDownloadCallback,
        ArticleAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rvData;
    private ArticleAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bindViews();

        setUpViews();

        fetchNews();
    }

    private void bindViews() {
        rvData = findViewById(R.id.rv_data);
        refreshLayout = findViewById(R.id.refresh_layout);
        ImageView ivChangeTheme = findViewById(R.id.iv_change_theme);

        ivChangeTheme.setOnClickListener(v -> changeTheme());
    }

    private void setUpViews() {
        rvData.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticleAdapter(this);
        rvData.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(this);

        Snackbar.make(refreshLayout, R.string.reload_note, Snackbar.LENGTH_LONG).show();
    }

    private void fetchNews() {
        refreshLayout.setRefreshing(true);

        DownloadWebTask task = new DownloadWebTask(this);
        task.execute(Constant.SOURCE_REMOTE_URL);
    }

    @Override
    public void onResult(Channel channel) {
        if (channel != null) {
            adapter.submitList(channel.getRssItems());
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(int position) {
        List<RssItem> items = adapter.getData();
        if (items == null || items.size() <= position) {
            return;
        }
        RssItem item = items.get(position);
        Intent intent = ArticleActivity.getIntent(this, item.getTitle(), item.getLink());
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        fetchNews();
    }

    private void changeTheme() {
        boolean isDarkMode = isDarkMode();
        saveDarkMode(!isDarkMode);
        restartActivity();
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
