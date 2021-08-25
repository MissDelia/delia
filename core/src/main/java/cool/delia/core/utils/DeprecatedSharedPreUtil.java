package cool.delia.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import cool.delia.core.CoreApplication;
import cool.delia.core.exception.CacheInitialException;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

/**
 * SharedPreferences工具类
 * @deprecated 此工具已过时，SharedPreferences数据已迁移到MMKV
 * @author xiong'MissDelia'zhengkun
 * 2020年7月17日14:36:57
 */
@Deprecated
public class DeprecatedSharedPreUtil implements MemoryCacheModel {

    // 创建一个写入器
    private final SharedPreferences mPreferences;
    private static DeprecatedSharedPreUtil instance;

    public synchronized static DeprecatedSharedPreUtil getInstance() {
        if (instance == null) {
            instance = new DeprecatedSharedPreUtil();
        }
        return instance;
    }

    private DeprecatedSharedPreUtil() {
        if (CoreApplication.SHARED_TAG == null || "".equals(CoreApplication.SHARED_TAG)) {
            mPreferences = null;
            return;
        }
        mPreferences = CoreApplication.getApplication().getSharedPreferences(CoreApplication.SHARED_TAG, Context.MODE_PRIVATE);
    }

    /**
     * 存数据
     * @param key 关键字
     * @param value 值
     * @param <T> 只接受基本数据类型或者其包装类
     */
    public <T> void setValue(@NonNull String key, T value) {
        if (mPreferences == null) {
            throw new CacheInitialException("Fail to initialize SharedPreferences" +
                    ", SharedPreUtil is unavailable!");
        }
        String tempValue = String.valueOf(value);
        mPreferences.edit().putString(key, tempValue).apply();
    }

    /**
     * 取数据
     * @param key 关键字
     * @param def 默认数据
     * @param <T> 只接受基本数据类型或者其包装类
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(@NonNull String key, T def) {
        if (mPreferences == null) {
            throw new CacheInitialException("Fail to initialize SharedPreferences" +
                    ", SharedPreUtil is unavailable!");
        }
        try {
            String tempDef = String.valueOf(def);
            String tempValue = mPreferences.getString(key, tempDef);
            Class<T> clz = (Class<T>) def.getClass();
            Constructor<T> constructor = clz.getConstructor(String.class);
            return constructor.newInstance(tempValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    /**
     * 移除数据
     */
    public void removeKey(@NonNull String key) {
        mPreferences.edit().remove(key).apply();
    }

    @Override
    public boolean hasKey(@NotNull String key) {
        return mPreferences.contains(key);
    }
}
