package com.framgia.diary.adapter;

import java.util.List;

import com.framgia.diary.R;
import com.framgia.diary.model.Diary;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DiaryAdapter extends ArrayAdapter<Diary> {
    private int width;

    public DiaryAdapter(Context context, List<Diary> data) {
        super(context, R.layout.adapter_diary);
        width = (context.getResources().getDisplayMetrics().widthPixels - 80) / 3;
        /*while (data.hasNext()) {
            add(data.next());
        }*/
        for (Diary diary : data) {
            add(diary);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // if (convertView == null)
        convertView =
            LayoutInflater.from(getContext()).inflate(R.layout.adapter_diary, parent, false);
        TextView textView = (TextView) convertView;
        textView.getLayoutParams().width = width;
        textView.getLayoutParams().height = width + 30;
        Diary diary = getItem(position);
        if (!TextUtils.isEmpty(diary.content))
            textView.setText(diary.content);
        return convertView;
    }
}
