/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.base;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import cool.delia.core.BuildConfig;

/**
 * 用于设置Glide的日志级别
 * @author xiong'MissDelia'zhengkun
 * 2020/10/29 17:29
 */
@GlideModule
public class GlideConfiguration extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, GlideBuilder builder) {
        // 设置日志级别
        builder.setLogLevel(BuildConfig.DEBUG ? Log.DEBUG : Log.ERROR);
    }
}
