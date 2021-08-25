# delia
[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)
[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)<br>

一个基于MVP架构，集成了RxJava3、Retrofit、Glide等工具的组件化Android开发框架

## 项目说明
1、框架架构图请参考[App架构图](App架构图.png)<br>
2、MVP请参考demo模块中的代码<br>
3、沉浸式状态栏请参考demo模块中DemoActivity.onCreate方法<br>
4、本框架使用了AndroidUtilCode，如有疑问，请参考[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/README-CN.md)<br>
5、暂时不对核心库混淆，如果上层业务需要混淆，请参阅依赖库的文档<br>
6、仅支持AndroidX的原因：由于本项目是新项目的基础框架，我们相信在新项目中使用AndroidX是比Support更好的选择，故不支持Support，未来也没有适配Support的计划<br>

## 2.0版本说明
1、删除部分不应该整合进基础框架的第三方工具；<br>
2、增加数据库工具，位于cool.delia.core.db包下；<br>
3、修复一些bug；<br>
