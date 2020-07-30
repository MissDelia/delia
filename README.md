# delia
[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)
[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)<br>

一个基于MVP架构，集成了RxJava3、Retrofit、Glide等工具的组件化Android开发框架

## 项目说明
1、框架架构图请参考[App架构图](App架构图.png)<br>
2、MVP请参考demo模块中的代码<br>
3、沉浸式状态栏请参考demo模块中DemoActivity.onCreate方法<br>
4、本框架使用了AndroidUtilCode，如有疑问，请参考[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/README-CN.md)<br>
5、数据库工具暂未完善，请用户暂时先用Room替代（后续可能移除Room，请开发者不要使用高度耦合的代码，以免增加替换难度）<br>
6、对于AOP框架（用于埋点或者热修复），暂时使用[Epic](https://github.com/tiann/epic.git)，我将会在未来某个版本中尝试自己实现（也可能不会）<br>
7、暂时不对核心库混淆，如果上层业务需要混淆，请参阅依赖库的文档<br>
8、仅支持AndroidX的原因：由于本项目是新项目的基础框架，我们相信在新项目中使用AndroidX是比Support更好的选择，故不支持Support，未来也没有适配Support的计划<br>

## 版本说明
2020年7月22日：<br>
1、增加了对ViewPager部分事件冲突解决方案的支持；<br>
2、进一步提高网络框架封装的通用性；<br>
3、Fragment适配懒加载，增加一些通用性代码；<br>
<br>
2020年7月23日：<br>
1、增加了通用的标题栏支持；<br>
2020年7月27日：<br>
1、增加城市列表及代码获取功能；<br>
2020年7月30日：<br>
1、添加默认的生命周期管理；<br>
2、优化了通用标题栏；<br>
