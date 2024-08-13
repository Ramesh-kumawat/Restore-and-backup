package com.example.restoreandbackup.Models;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;


public class HelperResizer {
    public static int SCALE_HEIGHT = 1920;
    public static int SCALE_WIDTH = 1080;
    public static int height;
    public static int width;

    public static void getheightandwidth(Context context) {
        getHeight(context);
        getwidth(context);
    }

    public static int getwidth(Context context) {
        width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static int getHeight(Context context) {
        height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public static void setHeight(Context context, View view, int i) {
        view.getLayoutParams().height = (context.getResources().getDisplayMetrics().heightPixels * i) / SCALE_HEIGHT;
    }

    public static void setWidth(Context context, View view, int i) {
        view.getLayoutParams().width = (context.getResources().getDisplayMetrics().widthPixels * i) / SCALE_WIDTH;
    }

    public static int setHeight(int i) {
        return (height * i) / 1920;
    }

    public static int setWidth(int i) {
        return (width * i) / 1080;
    }

    public static void setSize(View view, int i, int i2) {
        view.getLayoutParams().height = setHeight(i2);
        view.getLayoutParams().width = setWidth(i);
    }

    public static void setHeightWidthAsWidth(Context context, View view, int i, int i2) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i3 = (displayMetrics.widthPixels * i) / SCALE_WIDTH;
        int i4 = (displayMetrics.widthPixels * i2) / SCALE_WIDTH;
        view.getLayoutParams().width = i3;
        view.getLayoutParams().height = i4;
    }

    public static void setSize(View view, int i, int i2, boolean z) {
        if (z) {
            view.getLayoutParams().height = setWidth(i2);
            view.getLayoutParams().width = setWidth(i);
            return;
        }
        view.getLayoutParams().height = setHeight(i2);
        view.getLayoutParams().width = setHeight(i);
    }

    public static void setMargin(View view, int i, int i2, int i3, int i4) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(setWidth(i), setHeight(i2), setWidth(i3), setHeight(i4));
    }

    public static void setPadding(View view, int i, int i2, int i3, int i4) {
        view.setPadding(i, i2, i3, i4);
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }




}
