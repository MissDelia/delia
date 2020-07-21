package com.delia.core.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.delia.core.BaseApplication;
import com.delia.core.exception.SharedInitialException;

import java.lang.reflect.Constructor;

/**
 * SharedPreferences工具类
 * @author xiong'MissDelia'zhengkun
 * 2020年7月17日14:36:57
 */
public class SharedPreUtil {

    private static final String TAG = "JMX_SHARED";

    // 创建一个写入器
    private SharedPreferences mPreferences;
    private static SharedPreUtil instance;

    public synchronized static SharedPreUtil getInstance() {
        if (instance == null) {
            instance = new SharedPreUtil();
        }
        return instance;
    }

    private SharedPreUtil() {
        mPreferences = BaseApplication.getApplication().getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    /**
     * 存数据
     * @param key 关键字
     * @param value 值
     * @param <T> 只接受基本数据类型或者其包装类
     */
    public <T> void setValue(String key, T value) throws SharedInitialException {
        if (mPreferences == null) {
            throw new SharedInitialException("Fail to initialize SharedPreferences" +
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
    public <T> T getValue(String key, T def) throws SharedInitialException {
        if (mPreferences == null) {
            throw new SharedInitialException("Fail to initialize SharedPreferences" +
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
    public void removeKey(String key) {
        mPreferences.edit().remove(key).apply();
    }

}
