package com.framgia.diary;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

public abstract class AbsActivity extends Activity {
    private static final int INVALID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        if (!TextUtils.isEmpty(getActionBarTitle()))
            getActionBar().setTitle(getActionBarTitle());
        final FrameLayout contentView = (FrameLayout) findViewById(R.id.fullscreen_content);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        contentView.removeAllViews();
        if (getContentLayout() != INVALID) {
            View view = getLayoutInflater().inflate(getContentLayout(), null);
            contentView.addView(view, params);
        }
    }

    protected abstract String getActionBarTitle();

    protected abstract int getContentLayout();
}
