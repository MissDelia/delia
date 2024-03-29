/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ConvertUtils;

import cool.delia.core.R;
import cool.delia.core.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 如Fragment不需要使用MVP，则继承此类
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:32:45
 */
public abstract class BaseCompatFragment extends Fragment {

    /**
     * 对于定义的整形常量推荐使用十六进制
     * 默认的startActivityForResult的requestCode
     */
    protected static final int DEFAULT_REQUEST_CODE = -0x00000001;

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

    protected boolean isFirstLoad = true;

    private View view;

    protected ViewGroup toolBar, tools;

    protected View btnBack;

    protected ImageView ivBack;

    protected LinearLayout llBarTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisposable = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            isFirstLoad = false;
            loadData();
        }
        attachData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable.size() > 0) {
            mDisposable.clear();
        }
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
                btnBack.setOnClickListener(v -> {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                });
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
        TextView tvTitle = new TextView(getActivity());
        tvTitle.setText(getString(R.string.title_bar_default_str));
        tvTitle.setTextSize(ConvertUtils.px2sp(getResources().getDimension(R.dimen.dimen_19sp)));
        tvTitle.setTextColor(getResources().getColor(R.color.default_black));
        return tvTitle;
    }

    protected ArrayList<View> getBarToolViews() {
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams layoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = (int) getResources().getDimension(R.dimen.dimen_48dp);
        layoutParams.width = (int) getResources().getDimension(R.dimen.dimen_48dp);
        layout.setLayoutParams(layoutParams);

        ImageView iv = new ImageView(getActivity());
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
                if (Objects.requireNonNull(getActivity()).checkSelfPermission(permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 找出对应的控件
     *
     * @param id 组件ID
     * @param <T> 组件类型
     * @return 组件对象
     */
    protected <T extends View> T findViewById(int id) {
        return getContentView().findViewById(id);
    }

    /**
     * 获取设置的布局
     *
     * @return 页面根布局的对象
     */
    protected View getContentView() {
        return view;
    }

    /**
     * 设置Fragment要显示的布局
     *
     * @return 布局的layoutId
     */
    protected abstract int setContentView();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void loadData();

    /**
     * 将数据与视图绑定
     */
    protected abstract void attachData();

    /**
     * 提示内容
     *
     * @param msg 传入内容
     */
    public void showCommon(String msg, int gravity){
        ToastUtil.getInstance().showCommon(msg, gravity);
    }

    public void showCommon(String msg){
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
        Intent intent = new Intent(getActivity(), c);
        if (extras != null) intent.putExtras(extras);
        if (flags != DEFAULT_FLAGS) intent.addFlags(flags);
        startActivityForResult(intent, requestCode);
    }

}
