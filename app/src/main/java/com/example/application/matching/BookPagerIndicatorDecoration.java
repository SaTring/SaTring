package com.example.application.matching;

import android.content.res.Resources;
import android.graphics.Interpolator;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.recyclerview.widget.RecyclerView;

public class  BookPagerIndicatorDecoration extends RecyclerView.ItemDecoration
{
    private static final float DISPLAY_METRICS = Resources.getSystem().getDisplayMetrics().density;

    private final int indicatorHeight = (int) (DISPLAY_METRICS*15.9999999);
    private final float indicatorStrokeWidth =DISPLAY_METRICS*2;
    private final float indicatorItemLength = DISPLAY_METRICS*16;
    private final float indicatorItemPadding = DISPLAY_METRICS*4;

}
