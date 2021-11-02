/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.utils

import android.Manifest.permission.ACCESS_WIFI_STATE
import android.Manifest.permission.BLUETOOTH
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.net.Proxy
import android.net.wifi.WifiManager
import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresPermission
import cool.delia.core.CoreApplication
import java.net.NetworkInterface
import java.util.*


/**
 * 所有无线硬件功能相关工具类
 * @author xiong'MissDelia'zhengkun
 * 2021/7/20 10:23
 */
object WirelessUtil {

    /**
     * 判断设备 是否使用代理上网
     */
    @SuppressLint("ObsoleteSdkInt")
    @Deprecated("Use isWifiProxy() when minSDK above API Level 14")
    fun isWifiProxy(context: Context): Boolean {
        // 是否大于等于4.0
        val isIcsOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
        val proxyAddress: String?
        val proxyPort: Int
        if (isIcsOrLater) {
            proxyAddress = System.getProperty("http.proxyHost")
            val portStr = System.getProperty("http.proxyPort")
            proxyPort = (portStr ?: "-1").toInt()
        } else {
            proxyAddress = Proxy.getHost(context)
            proxyPort = Proxy.getPort(context)
        }
        return !TextUtils.isEmpty(proxyAddress) && proxyPort != -1
    }

    /**
     * 判断设备 是否使用代理上网
     * 项目API等级大于4.0（14）时直接调用此方法
     */
    fun isWifiProxy(): Boolean {
        val proxyAddress = System.getProperty("http.proxyHost")
        val portStr = System.getProperty("http.proxyPort")
        val proxyPort = (portStr ?: "-1").toInt()
        return !TextUtils.isEmpty(proxyAddress) && proxyPort != -1
    }

    /**
     * ## 判断当前是否处于VPN环境
     * 常用于环境安全性判断场景
     *
     * 注意：此方法正在进行测试
     * @return 返回当前是否处于VPN环境
     */
    fun isDeviceInVPN(): Boolean {
        try {
            // 获取网络接口列表
            val all: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            // 如果网络接口中有名字包含 ppp0 或 tun0 的，即代表当前正处于VPN环境
            for (nif in all) {
                val name = nif.name
                if (name.equals("tun0") || name.equals("ppp0")) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 检查蓝牙是否可用
     */
    @RequiresPermission(BLUETOOTH)
    fun isBluetoothOpen(): Boolean {
        val blueAdapter = BluetoothAdapter.getDefaultAdapter()
        return blueAdapter.isEnabled
    }

    /**
     * 检查wifi是否可用
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    fun checkWifiIsEnable(): Boolean {
        val wifiManager = CoreApplication.getApplication().applicationContext.getSystemService(
            Context.WIFI_SERVICE
        ) as WifiManager
        return wifiManager.isWifiEnabled
    }
}