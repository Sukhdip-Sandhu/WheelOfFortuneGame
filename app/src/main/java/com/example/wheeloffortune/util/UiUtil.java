package com.example.wheeloffortune.util;

import android.content.Context;

import androidx.core.content.res.ResourcesCompat;

public class UiUtil {

    public static int setColorHelper(Context context, int color_id) {
        return ResourcesCompat.getColor(context.getResources(), color_id, null);
    }

}
