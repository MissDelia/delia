/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 状态栏浅色模式，根据沉浸式状态栏时实际的背景色使用此类
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
	 * 设置状态栏黑色字体图标， 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
	 * 
	 * @param activity
	 * @return 1:MIUI 2:Flyme 3:android6.0
	 */
	public int statusBarLightMode(Activity activity) {
		int result = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (Build.VERSION.SDK_INT < 23 && MIUISetStatusBarLightMode(activity.getWindow(), true)) {
				result = 1;
			} else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
				result = 2;
			} else if (Build.VERSION.SDK_INT >= 23) {
				Window window = activity.getWindow();
//				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				window.addFlags(0x80000000);
				window.getDecorView().setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | 0x00002000);
				result = 3;
			}
		}
		return result;
	}

	/**
	 * 设置状态栏黑色字体图标， 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
	 *
	 * @param activity
	 * @return 1:MIUI 2:Flyme 3:android6.0
	 */
	public int statusBarDarkMode(Activity activity) {
		int result = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (Build.VERSION.SDK_INT < 23 && MIUISetStatusBarLightMode(activity.getWindow(), false)) {
				result = 1;
			} else if (FlymeSetStatusBarLightMode(activity.getWindow(), false)) {
				result = 2;
			} else if (Build.VERSION.SDK_INT >= 23) {
				Window window = activity.getWindow();
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
				result = 3;
			}
		}
		return result;
	}

	/**
	 * 已知系统类型时，设置状态栏黑色字体图标。 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
	 * 
	 * @param activity
	 * @param type
	 *            1:MIUUI 2:Flyme 3:android6.0
	 */
	public void statusBarLightMode(Activity activity, int type) {
		if (type == 1) {
			MIUISetStatusBarLightMode(activity.getWindow(), true);
		} else if (type == 2) {
			FlymeSetStatusBarLightMode(activity.getWindow(), true);
		} else if (type == 3) {
			activity.getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | 0x00002000);
		}

	}

	/**
	 * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
	 */
	public void statusBarDarkMode(Activity activity, int type) {
		if (type == 1) {
			MIUISetStatusBarLightMode(activity.getWindow(), false);
		} else if (type == 2) {
			FlymeSetStatusBarLightMode(activity.getWindow(), false);
		} else if (type == 3) {
			activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
		}

	}

	/**
	 * 设置状态栏图标为深色和魅族特定的文字风格 可以用来判断是否为Flyme用户
	 * 
	 * @param window
	 *            需要设置的窗口
	 * @param dark
	 *            是否把状态栏字体及图标颜色设置为深色
	 * @return boolean 成功执行返回true
	 *
	 */
	public boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
		boolean result = false;
		if (window != null) {
			try {
				WindowManager.LayoutParams lp = window.getAttributes();
				Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
				Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
				darkFlag.setAccessible(true);
				meizuFlags.setAccessible(true);
				int bit = darkFlag.getInt(null);
				int value = meizuFlags.getInt(lp);
				if (dark) {
					value |= bit;
				} else {
					value &= ~bit;
				}
				meizuFlags.setInt(lp, value);
				window.setAttributes(lp);
				result = true;
			} catch (Exception e) {

			}
		}
		return result;
	}

	/**
	 * 设置状态栏字体图标为深色，需要MIUIV6以上
	 * 
	 * @param window
	 *            需要设置的窗口
	 * @param dark
	 *            是否把状态栏字体及图标颜色设置为深色
	 * @return boolean 成功执行返回true
	 *
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
		boolean result = false;
		if (window != null) {
			Class clazz = window.getClass();
			try {
				int darkModeFlag = 0;
				Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
				Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
				darkModeFlag = field.getInt(layoutParams);
				Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
				if (dark) {
					extraFlagField.invoke(window, darkModeFlag, darkModeFlag);// 状态栏透明且黑色字体
				} else {
					extraFlagField.invoke(window, 0, darkModeFlag)

					;// 清除黑色字体
				}
				result = true;

			} catch (Exception e) {

			}

		}
		return result;
	}

}
