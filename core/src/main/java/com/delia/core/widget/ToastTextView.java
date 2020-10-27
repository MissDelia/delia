package com.delia.core.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.delia.core.R;

/**
 * Created by x5035 on 2018/3/18.
 */
public class ToastTextView extends AppCompatTextView {
    public ToastTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(Color.argb(200, 60, 60, 60));
    }

    public ToastTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(Color.argb(200, 60, 60, 60));
    }

    public ToastTextView(Context context) {
        super(context);
        this.init(Color.argb(200, 60, 60, 60));
    }

    public ToastTextView(Context context, int color) {
        super(context);
        this.init(color);
    }

    @SuppressLint({"NewApi"})
    private void init(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(8.0F);
        this.setBackground(drawable);
        this.setPadding(15, 15, 15, 15);
        this.setTextColor(-1);
        this.setTextSize(ConvertUtils.px2sp(getResources().getDimension(R.dimen.dimen_15sp)));
    }
}
