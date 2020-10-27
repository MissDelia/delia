/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.delia.core.R;

import java.util.LinkedList;

/**
 * 用于Loading的加载动画控件
 * @author xiong'MissDelia'zhengkun
 * 2020/7/31 16:31
 */
public class LoadingView extends View {

    /**
     * 刷新时间（单位：ms）
     */
    private int refreshTime = 100;

    private final LinkedList<Paint> paintsList = new LinkedList<>();

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        try {
            refreshTime = ta.getInt(R.styleable.LoadingView_refresh_time, 100);
            int paintColor = ta.getColor(R.styleable.LoadingView_refresh_color, Color.rgb(0, 0, 0));
            for (int i = 255; i > 0; i = i - 16) {
                paintsList.add(getPaint(paintColor, i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ta.recycle();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getHeight() != getWidth()) {
            return;
        }
        Paint temp = paintsList.get(0);
        paintsList.remove(0);
        paintsList.add(temp);
        for (float i = 0f; i < 360; i = i + 22.5f)  {
            Paint paint = paintsList.get((int) (i / 22.5));
            paint.setStrokeWidth(getHeight() * 0.2f);
            canvas.drawArc(getHeight() / 4f, getHeight() / 4f, getHeight() * 3 / 4f, getHeight() * 3 / 4f
                    , i, 11.25f, false, paint);
        }
        postInvalidateDelayed(refreshTime);
    }

    private Paint getPaint(int color, int alpha) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setAlpha(alpha);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }
}
