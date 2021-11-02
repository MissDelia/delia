/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;

import cool.delia.core.BuildConfig;
import cool.delia.core.CoreApplication;
import cool.delia.core.R;
import cool.delia.core.utils.ToastUtil;

import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 如Activity不需要使用MVP，则继承此类
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:32:45
 */
public abstract class BaseCompatActivity extends AppCompatActivity {

    /**
     * 对于定义的整形常量推荐使用十六进制
     * 默认的startActivityForResult的requestCode
     */
    protected static final int DEFAULT_REQUEST_CODE = 0x00001000;

    /**
     * 默认的onActivityResult常量，表示成功返回数据
     */
    protected static final int RESPONSE_SUCCESS = 0x00000100;

    /**
     * 默认的onActivityResult常量，表示无返回数据或返回数据存在问题
     */
    protected static final int RESPONSE_FAULT = 0x00000200;

    /**
     * 默认无效的flag
     */
    protected static final int DEFAULT_FLAGS = -0x00000001;

    protected CompositeDisposable mDisposable;

    protected ViewGroup toolBar, tools;

    protected View btnBack;

    protected ImageView ivBack;

    protected LinearLayout llBarTitle;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getViewId() != -1) {
            setContentView(getViewId());
        } else {
            bindView();
        }
        initView();
        mDisposable = new CompositeDisposable();
        loadData();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 测试模式开启屏幕常亮，方便测试
        if (BuildConfig.DEBUG) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        if (CoreApplication.secureMode) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        // 配置是否全局置灰
        if (CoreApplication.globalGrayMode) {
            Paint paint = new Paint();
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0f);
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        attachData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }

    protected void initToolBar(int toolBarId) {
        initToolBar(toolBarId, false, false, false);
    }

    protected void initToolBar(int toolBarId, boolean isBack) {
        initToolBar(toolBarId, isBack, false, false);
    }

    protected void initToolBar(int toolBarId, boolean isBack, boolean isTool) {
        initToolBar(toolBarId, isBack, false, isTool);
    }

    protected void initToolBar(int toolBarId, boolean isBack, boolean isBiggerBackIcon, boolean isTool) {
        initToolBar(findViewById(toolBarId), isBack, isBiggerBackIcon, isTool);
    }

    /**
     * 标题栏组件初始化
     * @param toolBar ViewGroup
     * @param isBack 是否显示返回键
     * @param isBiggerBackIcon 返回键图片大小
     * @param isTool 是否显示右上角菜单栏
     */
    protected void initToolBar(ViewGroup toolBar, boolean isBack, boolean isBiggerBackIcon, boolean isTool) {
        this.toolBar = toolBar;
        if (this.toolBar != null) {
            // 初始化返回键
            if (isBack) {
                if (isBiggerBackIcon) {
                    btnBack = findViewById(R.id.iv_bar_back);
                    ivBack = (ImageView) btnBack;
                } else {
                    btnBack = findViewById(R.id.ll_bar_back);
                    ivBack = findViewById(R.id.iv_bar_back_);
                }
                if (getIconBackId() != -1) {
                    ivBack.setImageResource(getIconBackId());
                }
                btnBack.setOnClickListener(v -> finish());
                btnBack.setVisibility(View.VISIBLE);
            }
            llBarTitle = findViewById(R.id.ll_bar_title);
            llBarTitle.addView(getBarCenterView(), getLayoutParams());
            if (isTool) {
                tools = findViewById(R.id.ll_bar_tool);
                tools.setVisibility(View.VISIBLE);
                ArrayList<View> barToolViews = getBarToolViews();
                for (View barToolView : barToolViews) {
                    tools.addView(barToolView);
                }
            }
            this.toolBar.setBackground(getBarBackground());
        }
    }

    /**
     * 获取标题栏返回键图片ID
     */
    protected int getIconBackId() {
        return -1;
    }

    protected View getBarCenterView() {
        TextView tvTitle = new TextView(this);
        tvTitle.setText(getString(R.string.title_bar_default_str));
        tvTitle.setTextSize(ConvertUtils.px2sp(getResources().getDimension(R.dimen.dimen_19sp)));
        tvTitle.setTextColor(getResources().getColor(R.color.default_black));
        return tvTitle;
    }

    protected ArrayList<View> getBarToolViews() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams layoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = (int) getResources().getDimension(R.dimen.dimen_48dp);
        layoutParams.width = (int) getResources().getDimension(R.dimen.dimen_48dp);
        layout.setLayoutParams(layoutParams);

        ImageView iv = new ImageView(this);
        ViewGroup.LayoutParams ivLayoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivLayoutParams.height = (int) getResources().getDimension(R.dimen.dimen_24dp);
        ivLayoutParams.width = (int) getResources().getDimension(R.dimen.dimen_24dp);
        iv.setLayoutParams(ivLayoutParams);
        iv.setImageResource(R.drawable.more);

        layout.addView(iv);
        ArrayList<View> list = new ArrayList<>();
        list.add(layout);
        return list;
    }

    protected Drawable getBarBackground() {
        return getResources().getDrawable(R.drawable.drawable_default_bar);
    }

    protected ViewGroup.LayoutParams getLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    protected boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (checkSelfPermission(permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 沉浸式状态栏
     */
    protected void useImmersiveBar() {
        BarUtils.transparentStatusBar(this);
    }

    /**
     * 旧的沉浸式状态栏方法（已废弃）
     */
    @Deprecated
    protected void useImmersiveBarOld() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                attributes.flags |= flagTranslucentStatus | flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 设置根布局参数
     */
    @Deprecated
    private void setRootView() {
        ViewGroup parent = findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    /**
     * 获取页面布局ID
     * 返回值为-1时代表子类必须重写onCreate方法实现ViewBinding
     */
    protected abstract int getViewId();

    /**
     * 载入初始化数据
     */
    protected abstract void loadData();

    /**
     * 初始化页面及控件（使用ViewBinding时重写此方法）
     */
    protected void bindView() {}

    /**
     * 初始化页面及控件
     */
    protected abstract void initView();

    /**
     * 将数据写入控件
     */
    protected abstract void attachData();

    /**
     * 提示内容
     *
     * @param msg 内容
     */
    protected void showCommon(String msg, int gravity){
        ToastUtil.getInstance().showCommon(msg, gravity);
    }

    protected void showCommon(String msg){
        ToastUtil.getInstance().showCommon(msg);
    }

    /**
     * 跳转UI页面
     * @param c 目标页面对应的Class
     */
    protected void goIntent(Class<? extends Activity> c) {
        goIntent(c, null, DEFAULT_FLAGS, DEFAULT_REQUEST_CODE);
    }

    /**
     * 跳转UI页面
     * @param c 目标页面对应的Class
     * @param extras 要传的值
     */
    protected void goIntent(Class<? extends Activity> c, Bundle extras){
        goIntent(c, extras, DEFAULT_FLAGS, DEFAULT_REQUEST_CODE);
    }

    /**
     * 跳转UI页面
     * @param c 目标页面对应的Class
     * @param extras 要传的值
     */
    protected void goIntent(Class<? extends Activity> c, Bundle extras, int requestCode){
        goIntent(c, extras, DEFAULT_FLAGS, requestCode);
    }

    /**
     * 跳转UI页面
     * @param c 目标页面对应的Class
     * @param flags 跳转的flags
     */
    protected void goIntent(Class<? extends Activity> c, int flags){
        goIntent(c, null, flags, DEFAULT_REQUEST_CODE);
    }

    /**
     * 跳转UI页面
     * @param c 目标页面对应的Class
     * @param flags 跳转的flags
     * @param extras 要传的值
     */
    protected void goIntent(Class<? extends Activity> c, int flags, Bundle extras){
        goIntent(c, extras, flags, DEFAULT_REQUEST_CODE);
    }

    /**
     * 跳转UI页面
     * @param c 目标页面对应的Class
     * @param extras 要传的值
     * @param flags 跳转的flags
     */
    private void goIntent(Class<? extends Activity> c, Bundle extras, int flags, int requestCode) {
        Intent intent = new Intent(this, c);
        if (extras != null) intent.putExtras(extras);
        if (flags != DEFAULT_FLAGS) intent.addFlags(flags);
        startActivityForResult(intent, requestCode);
    }

}
