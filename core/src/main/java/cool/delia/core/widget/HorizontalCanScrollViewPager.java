/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import androidx.viewpager.widget.ViewPager;

/**
 * 用于左右滑动的View与ViewPager冲突
 * 增加禁止左右滑动功能
 * @author xiong'MissDelia'zhengkun
 * 2020/7/22 10:51
 */
public class HorizontalCanScrollViewPager extends ViewPager {

    private boolean isScroll = true;

    public HorizontalCanScrollViewPager(Context context) {
        this(context, null);
    }

    public HorizontalCanScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof HorizontalScrollView || v instanceof HorizontalSwipeView || v instanceof ViewPager) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }

    /**
     * 1.dispatchTouchEvent一般情况不做处理
     *,如果修改了默认的返回值,子孩子都无法收到事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);   // return true;不行
    }
    /**
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return false;//可行,不拦截事件,
        // return true;//不行,孩子无法处理事件
        //return super.onInterceptTouchEvent(ev);//不行,会有细微移动
        if (isScroll){
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }
    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往父控件传
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return false;// 可行,不消费,传给父控件
        //return true;// 可行,消费,拦截事件
        //super.onTouchEvent(ev); //不行,
        //虽然onInterceptTouchEvent中拦截了,
        //但是如果viewpage里面子控件不是viewgroup,还是会调用这个方法.
        if (isScroll){
            return super.onTouchEvent(ev);
        }else {
            return true;// 可行,消费,拦截事件
        }
    }
    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}

