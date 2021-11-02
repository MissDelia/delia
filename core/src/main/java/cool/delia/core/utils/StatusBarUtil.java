/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.utils;

import android.app.Activity;

import com.blankj.utilcode.util.BarUtils;

/**
 * 状态栏浅色模式，根据沉浸式状态栏时实际的背景色使用此类
 * 这里因为历史原因封装了一层，如果碰到性能问题可以考虑直接调用{@link BarUtils}，可减少一层调用帧
 * @author xiong'MissDelia'zhengkun
 * 2020年7月16日15:37:19
 */
public class StatusBarUtil {
	
	private static StatusBarUtil sbu;
	
	public static synchronized StatusBarUtil getInstance() {
		if (sbu == null) {
			sbu = new StatusBarUtil();
		}
		return sbu;
	}

	/**
	 * 设置状态栏黑色字体图标
	 */
	public void statusBarLightMode(Activity activity) {
		BarUtils.setStatusBarLightMode(activity, true);
	}

	/**
	 * 设置状态栏白字体图标
	 */
	public void statusBarDarkMode(Activity activity) {
		BarUtils.setStatusBarLightMode(activity, false);
	}
}
