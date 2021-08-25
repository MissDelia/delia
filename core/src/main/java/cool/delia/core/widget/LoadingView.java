/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.widget;

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

import java.util.LinkedList;

import cool.delia.core.R;

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
            // 初始化16个画笔，颜色根据传入而定，透明度阶梯下降
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
        /*
         * 1、画16个圆弧，每个圆弧的透明度阶梯下降；
         * 2、初始位置每隔refreshTime毫秒推进一格；
         * 3、每个圆弧和圆弧间隔为1/32，即11.25°（π/16，即算法中的11.25f）；
         * 4、每个圆弧的宽度为组件高度的1/5；
         * 5、根据以上规则，绘制时，推进一格为22.5°（π/8，即算法中的22.5f），每推进一格，仅对其1/2进行绘制圆弧，余下1/2为间隔；
         */
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

    /**
     * 实例化画笔
     * @param color 颜色
     * @param alpha 透明度
     * @return 画笔
     */
    private Paint getPaint(int color, int alpha) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setAlpha(alpha);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }
}
