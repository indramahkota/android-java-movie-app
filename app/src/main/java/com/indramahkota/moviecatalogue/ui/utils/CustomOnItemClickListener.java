package com.indramahkota.moviecatalogue.ui.utils;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {
    private final int position;
    private final OnItemClickCallback onItemClickCallback;

    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(position);
    }

    public interface OnItemClickCallback {
        void onItemClicked(int position);
    }
}