# WebService learn project

基于 SpringBoot WebService 搭建的服务生产消费项目。

重点参考文档：
- https://docs.spring.io/spring-ws/docs/current/reference/html/#overview
- https://spring.io/guides/gs/producing-web-service
- https://spring.io/guides/gs/consuming-web-service

AI 辅助编码工具：
- https://aistudio.google.com/
- https://gemini.google.com
- https://chat.qwen.ai/

## 注意
在编程中，“callback” 通常指：
你提供一个函数（或对象），系统会在某个特定时刻自动调用它来完成某些定制化操作。

这种模式叫做 回调函数（callback function） 或 回调对象（callback object） 。
SoapActionCallback 不是服务端回调我的意思。
它的意思是： “在发送 SOAP 请求前，我要设置一下 SOAPAction 头”，不代表服务端要调用你，而是你传给框架的一个操作钩子（hook）
并不是“服务端回调”
而是“客户端在发送请求前要做的一些额外处理”

如果 Spring 给这个类换个更清晰的名字，可能是：
SoapActionHeaderSetter
BeforeSendSoapActionConfigurer
SoapRequestHeaderCallback
但为了统一命名风格，Spring 使用了 XxxCallback 的方式，虽然有点抽象，但在熟悉后就习惯了。