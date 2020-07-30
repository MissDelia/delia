/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.util;

import com.delia.core.CoreApplication;
import com.delia.core.R;
import com.delia.core.net.RestApiHolder;
import com.delia.core.net.data.CityEntity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 *  *    天气网把城市分为了3级
 *  *    1级列表获取地址：http://www.weather.com.cn/data/city3jdata/china.html。通过访问这个地址，天气
 *  *    网会返回一级省（自治区）的名称、ID信息；
 *  *    2级城市城市列表获取地址：http://www.weather.com.cn/data/city3jdata/provshi/10120.html。其中“10120”
 *  *    为一级城市的ID，返回结果是归属于该城市的2级省市的名称、ID；
 *  *    3级城市列表获取地址：http://www.weather.com.cn/data/city3jdata/station/1012002.html。其中“1012002”
 *  *    为2级省市ID，返回结果就是3级城市的名称和ID了。
 *  *    获取到3级城市的名称和ID之后，就可以根据上面那篇博客里的内容获取当地的天气信息了！
 * @author xiong'MissDelia'zhengkun
 * 2020/7/23 11:10
 */
public class CityUtil {

    private static CityUtil mInstance;

    public synchronized static CityUtil getInstance() {
        if (mInstance == null) {
            mInstance = new CityUtil();
        }
        return mInstance;
    }

    private CityUtil() {}

    public Disposable getProvinceList(OnCityApiCallback callback) {
        return RestApiHolder.getCityRestService()
                .get(CoreApplication.getApplication().getString(R.string.province))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    ArrayList<CityEntity> list = new ArrayList<>();
                    Set<String> strings = jsonObject.keySet();
                    for (String key : strings) {
                        String name = jsonObject.get(key).getAsString();
                        CityEntity entity = new CityEntity(name, key);
                        list.add(entity);
                    }
                    callback.callback(list);
                });
    }

    public Disposable getCityList(String provinceCode, OnCityApiCallback callback) {
        return RestApiHolder.getCityRestService()
                .get(String.format(Locale.getDefault(), CoreApplication.getApplication().getString(R.string.city), provinceCode))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    ArrayList<CityEntity> list = new ArrayList<>();
                    Set<String> strings = jsonObject.keySet();
                    for (String key : strings) {
                        String name = jsonObject.get(key).getAsString();
                        CityEntity entity = new CityEntity(name, provinceCode + key);
                        list.add(entity);
                    }
                    callback.callback(list);
                });
    }

    public Disposable getAreaList(String cityCode, OnCityApiCallback callback) {
        return RestApiHolder.getCityRestService()
                .get(String.format(Locale.getDefault(), CoreApplication.getApplication().getString(R.string.area), cityCode))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> {
                    ArrayList<CityEntity> list = new ArrayList<>();
                    Set<String> strings = jsonObject.keySet();
                    for (String key : strings) {
                        String name = jsonObject.get(key).getAsString();
                        CityEntity entity = new CityEntity(name, cityCode + key);
                        list.add(entity);
                    }
                    callback.callback(list);
                });
    }

    public interface OnCityApiCallback {
        void callback(ArrayList<CityEntity> cityList);
    }
}
