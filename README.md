# 框架说明
[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)
[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)<br>
1、框架架构图请参考[App架构图](App架构图.png)<br>
2、MVP请参考demo模块中的代码<br>
3、沉浸式状态栏请参考demo模块中DemoActivity.onCreate方法<br>
4、本框架使用了AndroidUtilCode，如有疑问，请参考[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/README-CN.md)<br>
5、数据库工具暂未完善，请用户暂时先用Room替代（后续可能移除Room，请开发者不要使用高度耦合的代码，以免增加替换难度）<br>
6、对于AOP框架（用于埋点或者热修复），暂时使用[Epic](https://github.com/tiann/epic.git)，我将会在未来某个版本中尝试自己实现（也可能不会）<br>
7、暂时不对核心库混淆，如果上层业务需要混淆，请参阅依赖库的文档<br>