# HIS
> 联系邮箱 aUBzaGFuZ3llcy5uZXQ=
## 技能栈

> 网络通信相关： Netty / RPC /JAVA NIO
>
> 用户界面：     Javafx Webview / Vue.js + Muse-ui
>
> 序列化处理：   Fastjson
>
> 日志输出：     Log4j2
>
> 版本管理：     Git
>
> 依赖管理：     Gradle / NPM
>
> 辅助插件：     Lombok

## 如何运行
1. 进入`/client/front/`，`npm i`安装依赖，`npm run build`构建输出

2. 使用`gradle clean`安装第三方jar包依赖

3. `/server`下的`Main`为服务端启动程序，关闭时请使用`kill -15 pid`，只有这样才能触发程序关闭时自动将未持久化的数据写入文件

4. 执行`/client`下的gradle task：`/Tasks/application/run`，此为客户端程序

5. 日志在`/server/logs`下，数据在`/server/data`下