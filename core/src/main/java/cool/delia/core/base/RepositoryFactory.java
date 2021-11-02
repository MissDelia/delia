/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.base;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import cool.delia.core.base.repository.IBaseRepository;
import cool.delia.core.base.repository.impl.NetworkRepository;
import cool.delia.core.base.repository.impl.SharedPreferencesRepository;
import cool.delia.core.utils.LogUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 数据仓库工厂类
 * @author xiong'MissDelia'zhengkun
 * 2020/12/10 10:17
 */
public class RepositoryFactory {

    private static final String TAG = RepositoryFactory.class.getSimpleName();

    public static final int REPOSITORY_TYPE_NETWORK = 0;

    public static final int REPOSITORY_TYPE_SHARED = 1;

    @Deprecated
    private static NetworkRepository nRepository;

    @Deprecated
    private static SharedPreferencesRepository sRepository;

    /**
     * 创建数据仓库
     * @param type type字段只能取{@link #REPOSITORY_TYPE_NETWORK}和{@link #REPOSITORY_TYPE_SHARED}
     * @return 数据仓库
     * @deprecated 由于类型转换不便，此方法已弃用
     */
    @Deprecated
    public synchronized static IBaseRepository createRepository(@Type int type) {
        switch (type) {
            case REPOSITORY_TYPE_NETWORK:
                if (nRepository == null) {
                    nRepository = NetworkRepository.getInstance();
                }
                return nRepository;
            case REPOSITORY_TYPE_SHARED:
                if (sRepository == null) {
                    sRepository = SharedPreferencesRepository.getInstance();
                }
                return sRepository;
        }
        throw new RuntimeException("Incorrect repository type!");
    }

    /**
     * 创建数据仓库
     * 此方法依旧不稳定，待后续优化
     * @param clz clz字段只能是{@link IBaseRepository}子类的Class对象
     * @param <T> 泛型参数
     * @return 数据仓库
     */
    @SuppressWarnings("unchecked")
    public synchronized static <T extends IBaseRepository> T createRepository(@NonNull Class<T> clz) {
        T instance = null;
        try {
            Method method = clz.getMethod("getInstance");
            instance = (T) method.invoke(null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LogUtil.getInstance().e(TAG, e.getMessage());
        }
        return instance;
    }

    /**
     * @deprecated 因方法优化已弃用
     */
    @IntDef({REPOSITORY_TYPE_NETWORK, REPOSITORY_TYPE_SHARED})
    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface Type{}
}
