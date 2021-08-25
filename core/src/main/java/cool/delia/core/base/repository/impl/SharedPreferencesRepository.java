/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.base.repository.impl;

import cool.delia.core.base.repository.IBaseRepository;
import cool.delia.core.exception.CacheInitialException;
import cool.delia.core.utils.SharedPreUtil;

/**
 * @author xiong'MissDelia'zhengkun
 * 2020/12/11 11:02
 */
public class SharedPreferencesRepository implements IBaseRepository {

    private static SharedPreferencesRepository mInstance;

    private SharedPreferencesRepository() {

    }

    public synchronized static SharedPreferencesRepository getInstance() {
        if (mInstance == null) {
            mInstance = new SharedPreferencesRepository();
        }
        return mInstance;
    }

    /**
     * 此方法和{@link #setDataIntoShared}方法将SharedPreUtil中的方法映射到Repository中
     * 交给上层调用，上层暂时禁止直接使用SharedPreUtil中的方法
     */
    public <T> T getDataFromShared(String key, T def) {
        try {
            return SharedPreUtil.INSTANCE.getValue(key, def);
        } catch (CacheInitialException e) {
            e.printStackTrace();
        }
        return def;
    }

    public <T> void setDataIntoShared(String key, T value) {
        try {
            SharedPreUtil.INSTANCE.setValue(key, value);
        } catch (CacheInitialException e) {
            e.printStackTrace();
        }
    }

    public boolean hasKeyInShared(String key) {
        try {
            return SharedPreUtil.INSTANCE.hasKey(key);
        } catch (CacheInitialException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void removeKeyInShared(String key) {
        try {
            SharedPreUtil.INSTANCE.removeKey(key);
        } catch (CacheInitialException e) {
            e.printStackTrace();
        }
    }
}
