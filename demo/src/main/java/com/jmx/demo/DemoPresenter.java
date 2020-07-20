/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.jmx.demo;

import com.jmx.library.BaseApplication;
import com.jmx.library.base.BasePresenter;
import com.jmx.library.base.Repository;
import com.jmx.library.net.callback.OnRequestCompleteListener;
import com.jmx.library.net.data.Response;

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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("obj", "213");
        // 这里一定要将Disposable交给mDisposable来进行管理，否则可能会导致内存泄露
        mDisposable.add(Repository.getInstance().getDataFromNetwork(BaseApplication.getApplication().getString(R.string.demoApi)
                , map, new OnRequestCompleteListener() {
            @Override
            public void onComplete(Response<?> response) {
                if (response.getCode() == 200) {
                    getView().isRequestSuccess(true);
                } else {
                    getView().isRequestSuccess(false);
                }
            }
        }));
    }
}
