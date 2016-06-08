# Web-OTC-Trading
a simple web OTC Electronic Trading System 

### 开发环境
- Intellij IDEA 2016.1
- Jboss EAP 6.4
- Mysql 5.6.28

##

###文件说明
- doc/*
  项目相关说明的文档和截图
- sql/*
  数据库文件
- lib/*
  项目所需依赖库
- WebBackEnd/*
  网页后台相关代码，servlet为主，负责请求解析、转发、权限认证
- EJBLogic/*
  业务逻辑相关代码，都是无状态会话bean，被网页后台所调用，将会运行在ejb容器内。

项目说明参见doc内pdf文档