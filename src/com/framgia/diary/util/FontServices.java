package com.framgia.diary.util;

import java.util.HashMap;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontServices {
    private static FontServices fontService;

    public static FontServices getInstance(Context context) {
        if (fontService == null) {
            fontService = new FontServices(context);
        }
        return fontService;
    }
    private Context context;
    private HashMap<String, Typeface> listFont;

    private FontServices(Context context) {
        this.context = context;
        listFont = new HashMap<String, Typeface>();
    }

    private Typeface getTypeface(String fontPath) {
        if (!listFont.containsKey(fontPath)) {
            Typeface font = Typeface.createFromAsset(context.getAssets(), fontPath);
            listFont.put(fontPath, font);
        }
        return listFont.get(fontPath);
    }

    public void setTypeface(TextView textview) {
        int style =
            textview.getTypeface() == null ? Typeface.NORMAL : textview.getTypeface().getStyle();
        setTypeface(textview, style);
    }

    public void setTypeface(TextView... textviews) {
        for (TextView textview : textviews) {
            if (textview == null)
                continue;
            setTypeface(textview);
        }
    }

    private void setTypeface(TextView textview, int style) {
        if (textview.getContentDescription() == null)
            return;
        String path = (String) textview.getContentDescription();
        Typeface font = getTypeface(path);
        textview.setTypeface(font, style);
    }
}
