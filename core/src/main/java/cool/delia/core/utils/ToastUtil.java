package cool.delia.core.utils;

import android.widget.Toast;

import cool.delia.core.CoreApplication;
import cool.delia.core.widget.ToastTextView;

import java.lang.ref.WeakReference;

/**
 * @author xiong'MissDelia'zhengkun
 * 2020年7月15日14:22:49
 * 用于一些关键步骤的提示
 */
public class ToastUtil {

    private static ToastUtil myToast;
//    private ToastTextView tv_content;

    public static synchronized ToastUtil getInstance() {
        if (myToast == null) {
            myToast = new ToastUtil();
        }

        return myToast;
    }

    public void showCommon(String text) {
        this.setAttribute(text, 80, 0, 20);
    }

    public void showCommon(String text, int gravity) {
        this.setAttribute(text, gravity, 0, 0);
    }

    @Deprecated
    public void showCommonCenter(String text) {
        this.setAttribute(text, 17, 0, 0);
    }

    public void showCommon(String text, int gravity, int xOffset, int yOffset) {
        this.setAttribute(text, gravity, xOffset, yOffset);
    }

    /**
     * 使用弱引用解决短时间内进行Toast时的提示语混乱问题
     */
    private synchronized void setAttribute(String text, int gravity, int xOffset, int yOffset) {
//        if (this.tv_content == null) {
//            this.tv_content = new ToastTextView(CoreApplication.getApplication());
//        }
        WeakReference<ToastTextView> mContent = new WeakReference<>(new ToastTextView(CoreApplication.getApplication()));

        mContent.get().setText(text);
        Toast toast = Toast.makeText(CoreApplication.getApplication(), "", Toast.LENGTH_SHORT);
        toast.setView(mContent.get());
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }

}
