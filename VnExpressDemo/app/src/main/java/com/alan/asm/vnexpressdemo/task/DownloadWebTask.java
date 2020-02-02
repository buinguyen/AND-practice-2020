package com.alan.asm.vnexpressdemo.task;

import android.os.AsyncTask;

import com.alan.asm.vnexpressdemo.model.Channel;

public class DownloadWebTask extends AsyncTask<String, Void, Channel> {

    private OnDownloadCallback mCallback;

    public DownloadWebTask(OnDownloadCallback callback) {
        this.mCallback = callback;
    }

    @Override
    protected Channel doInBackground(String... urls) {
        for (String urlStr : urls) {
            Channel channel = ChannelXmlParser.parseRss(urlStr);
            if (channel != null) return channel;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Channel channel) {
        if (mCallback != null) mCallback.onResult(channel);
    }

    public interface OnDownloadCallback {
        void onResult(Channel channel);
    }
}