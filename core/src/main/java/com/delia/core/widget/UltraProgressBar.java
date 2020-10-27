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
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.ConvertUtils;
import com.delia.core.R;
import com.delia.core.exception.ViewDrawException;
import com.delia.core.utils.LogUtil;

/**
 * 可定制的进度条组件
 * @author xiong'MissDelia'zhengkun
 * 2020/10/16 9:50
 */
public class UltraProgressBar extends View {

    private static final String TAG = UltraProgressBar.class.getSimpleName();

    /**
     * 控件垂直模式
     */
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;

    private static final float MAX_PROGRESS = 4096f;

    private float targetProgress = MAX_PROGRESS;

    private int barWidth = 4;

    private int orientation = LinearLayout.HORIZONTAL;

    private int background, foreground;

    private boolean startProgress = false;

    private float progress = 0;

    /**
     * 背景条绘制参数
     */
    private float halfWidth, left, right, top, bottom;

    /**
     * 前景条绘制参数
     */
    private float foreLeft, foreRight, foreTop, foreBottom;

    private final Paint mPaint;

    private OnProgressFinishedListener mListener;

    public UltraProgressBar(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public UltraProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        initCustomAttr(context, attrs);
        mListener = () -> LogUtil.getInstance().i(System.currentTimeMillis() + " Progress finished!");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        halfWidth = barWidth / 2.0f;
        switch (orientation) {
            case HORIZONTAL:
                left = 0f + ConvertUtils.dp2px(5);
                right = width - ConvertUtils.dp2px(5);
                top = height / 2.0f - halfWidth;
                bottom = height / 2.0f + halfWidth;

                foreLeft = left;
                foreRight = ConvertUtils.dp2px(5) + (width - ConvertUtils.dp2px(10)) * (progress / MAX_PROGRESS);
                foreTop = top;
                foreBottom = bottom;
                break;
            case VERTICAL:
                left = width / 2.0f - halfWidth;
                right = width / 2.0f + halfWidth;
                top = 0f + ConvertUtils.dp2px(5);
                bottom = height - ConvertUtils.dp2px(5);

                foreLeft = left;
                foreRight = right;
                foreTop = ConvertUtils.dp2px(5) + (height - ConvertUtils.dp2px(10)) * ((MAX_PROGRESS - progress) / MAX_PROGRESS);
                foreBottom = bottom;
                break;
            default:
                throw new ViewDrawException("Bad orientation,please check setting!");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.getInstance().i(TAG, System.currentTimeMillis() + " draw start");
        mPaint.setColor(background);
        canvas.drawRoundRect(left, top, right, bottom, halfWidth, halfWidth, mPaint);
        mPaint.setColor(foreground);
        canvas.drawRoundRect(foreLeft, foreTop, foreRight, foreBottom, halfWidth, halfWidth, mPaint);

        if (startProgress) {
            if (progress + 75 < targetProgress) {
                progress += 75;
                requestLayout();
                postInvalidateDelayed(16);
            } else {
                progress = 0;
                startProgress = false;
                postInvalidate();
                if (mListener != null) {
                    mListener.onFinished();
                }
            }
        }
    }

    private void initCustomAttr(Context context, AttributeSet attrs) {
        //获取自定义属性。
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.UltraProgressBar);
        try {
            barWidth = ta.getInt(R.styleable.UltraProgressBar_bar_width, 4);
            orientation = ta.getInt(R.styleable.UltraProgressBar_android_orientation, HORIZONTAL);
            background = ta.getColor(R.styleable.UltraProgressBar_background_color, Color.GRAY);
            foreground = ta.getColor(R.styleable.UltraProgressBar_foreground_color, Color.RED);
        } catch (Exception e) {
            LogUtil.getInstance().e(TAG, e.getMessage());
        } finally {
            ta.recycle();
        }
    }

    /**
     * 设置控件进度条
     * @param progress 进度值，默认4096级调节
     */
    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void setProgressAnim(int progress) {
        this.progress = 0;
        targetProgress = progress;
        startProgress();
    }

    /**
     * 开启一次进度条滚动
     */
    public void startProgress() {
        startProgress = true;
        postInvalidate();
    }

    /**
     * 当自动滚动进度条时
     */
    public interface OnProgressFinishedListener {

        void onFinished();
    }
}
