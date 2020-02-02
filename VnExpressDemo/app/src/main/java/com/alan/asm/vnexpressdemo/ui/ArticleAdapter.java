package com.alan.asm.vnexpressdemo.ui;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alan.asm.vnexpressdemo.R;
import com.alan.asm.vnexpressdemo.model.ImageType;
import com.alan.asm.vnexpressdemo.model.RssItem;
import com.alan.asm.vnexpressdemo.task.ChannelXmlParser;
import com.alan.asm.vnexpressdemo.utils.DateTimeUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleVH> {

    private List<RssItem> rssItemList;
    private OnItemClickListener onItemClickListener;

    public ArticleAdapter(OnItemClickListener listener) {
        rssItemList = new ArrayList<>();
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ArticleAdapter.ArticleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleVH(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ArticleVH holder, int position) {
        holder.bindViews(rssItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return rssItemList.size();
    }

    public void submitList(List<RssItem> list) {
        if (list == null) return;
        rssItemList.clear();
        rssItemList.addAll(list);
        notifyDataSetChanged();
    }

    public List<RssItem> getData() {
        return rssItemList;
    }

    public static class ArticleVH extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvPubDate;
        private SimpleDraweeView ivPreview;
        private WebView ivPreviewGif;

        public ArticleVH(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPubDate = itemView.findViewById(R.id.tv_pub_date);
            ivPreview = itemView.findViewById(R.id.iv_preview);
            ivPreviewGif = itemView.findViewById(R.id.iv_preview_gif);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }

        public void bindViews(RssItem rssItem) {
            tvTitle.setText(rssItem.getTitle());
            tvDescription.setText(rssItem.getDescription());
            tvPubDate.setText(rssItem.getPubDate(DateTimeUtil.DATE_TEXT_2));
            if (rssItem.getImageUrl() != null) {
                if (rssItem.getImageType() == ImageType.GIF) {
                    String webContent = ChannelXmlParser.buildWebImageContent(rssItem.getImageUrl());
                    ivPreviewGif.loadData(webContent, "text/html", "utf-8");
                    ivPreviewGif.setVisibility(View.VISIBLE);
                    ivPreview.setVisibility(View.GONE);
                } else {
                    ivPreview.setImageURI(Uri.parse(rssItem.getImageUrl()));
                    ivPreview.setVisibility(View.VISIBLE);
                    ivPreviewGif.setVisibility(View.GONE);
                }
            } else {
                ivPreview.setImageResource(R.drawable.ic_newspaper);
                ivPreview.setVisibility(View.VISIBLE);
                ivPreviewGif.setVisibility(View.GONE);
            }
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
