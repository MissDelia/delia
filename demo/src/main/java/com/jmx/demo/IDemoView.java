/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.jmx.demo;

import com.jmx.library.base.BaseView;

/**
 * MVP使用教程
 * 在这里定义Presenter给View传递数据的方法，在Activity或Fragment中实现
 * @author xiong'MissDelia'zhengkun
 * 2020/7/17 15:55
 */
public interface IDemoView extends BaseView {

    void isRequestSuccess(boolean state);
}
