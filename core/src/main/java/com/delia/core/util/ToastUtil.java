package com.delia.core.util;

import android.widget.Toast;

import com.delia.core.BaseApplication;
import com.delia.core.widget.ToastTextView;

/**
 * @author xiong'MissDelia'zhengkun
 * 2020年7月15日14:22:49
 * 用于一些关键步骤的提示
 */
public class ToastUtil {

    private static ToastUtil myToast;
    private static Toast mToast;
    private ToastTextView tv_content;

    public static synchronized ToastUtil getInstance() {
        if (myToast == null) {
            myToast = new ToastUtil();
        }
        if (mToast == null) {
            mToast = new Toast(BaseApplication.getApplication());
            mToast.setDuration(Toast.LENGTH_LONG);
        }

        return myToast;
    }

    public void setTime(int length) {
        if (mToast != null) {
            mToast.setDuration(length);
        }

    }

    public void showCommon(String text) {
        this.setAttribute(text, 80, 0, 20);
    }

    public void showCommon(String text, int gravity) {
        this.setAttribute(text, gravity, 0, 0);
    }

    public void showCommonCenter(String text) {
        this.setAttribute(text, 17, 0, 0);
    }

    public void showCommon(String text, int gravity, int xOffset, int yOffset) {
        this.setAttribute(text, gravity, xOffset, yOffset);
    }

    private void setAttribute(String text, int gravity, int xOffset, int yOffset) {
        if (this.tv_content == null) {
            this.tv_content = new ToastTextView(BaseApplication.getApplication());
        }

        this.tv_content.setText(text);
        mToast.setView(this.tv_content);
        mToast.setGravity(gravity, xOffset, yOffset);
        mToast.show();
    }

}
