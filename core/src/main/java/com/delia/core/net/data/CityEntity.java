/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.net.data;

/**
 * 城市名称和编码
 * @author xiong'MissDelia'zhengkun
 * 2020/7/27 9:22
 */
public class CityEntity {

    private String name;

    private String code;

    public CityEntity(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
