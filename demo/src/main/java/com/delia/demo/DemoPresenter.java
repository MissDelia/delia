/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.demo;

import com.delia.core.CoreApplication;
import com.delia.core.base.BasePresenter;
import com.delia.core.base.Repository;
import com.delia.core.net.callback.OnRequestCompleteListener;
import com.delia.core.net.data.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * MVP使用教程
 * 在这里从Repository中获取数据并进行逻辑处理后通知View进行处理
 * @author xiong'MissDelia'zhengkun
 * 2020/7/17 15:55
 */
public class DemoPresenter extends BasePresenter<IDemoView> {

    public void request() {
        Map<String, Object> map = new HashMap<>();
        map.put("obj", "213");
        // 这里一定要将Disposable交给mDisposable来进行管理，否则可能会导致内存泄露
        mDisposable.add(Repository.getInstance().getDataFromNetwork(CoreApplication.getApplication().getString(R.string.demoApi)
                , map, new OnRequestCompleteListener<Response<?>>() {
            @Override
            public void onComplete(Response<?> response) {
                getView().isRequestSuccess(response.getCode() == 200);
            }

            @Override
            public void onError(String message) {
                getView().isRequestSuccess(false);
            }
        }));
        int code = Repository.getInstance().getDataFromShared("code", 123);
    }
}
