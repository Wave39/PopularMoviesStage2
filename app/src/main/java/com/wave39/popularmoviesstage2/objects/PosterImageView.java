package com.wave39.popularmoviesstage2.objects;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * PosterImageView
 * Created by bp on 8/17/15.
 */

public class PosterImageView extends ImageView {
    public PosterImageView(Context context) {
        super(context);
    }

    public PosterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PosterImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), (int)(getMeasuredWidth() * 278.0 / 185.0));
    }
}
