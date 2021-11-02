/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.net.callback;

/**
 * 文件下载监听器
 * @author xiong'MissDelia'zhengkun
 * 2020/11/4 11:02
 */
public interface DownloadProgressListener {

    void onStart();//下载开始

    void onProgress(int progress);//下载进度

    void onFinish(String path);//下载完成

    void onFail(String errorInfo);//下载失败
}
