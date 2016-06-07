# Web-OTC-Trading
a simple web OTC Electronic Trading System 

### 开发环境
- Intellij IDEA 2016.1
- Jboss EAP 6.4
- Mysql 5.6.28

### 文件说明
- 网页后台： /WebBackEnd/*
   访问后台的测试样例可以参见/WebBackEnd/web/test.html
- 业务逻辑： /EJBLogic/*
- 数据库 ： /sql/otc*.sql

### 流程说明
#### 简要说明
- 未登录用户只能浏览交易的期货
- 注册用户分为三种类型:
     1. admin
     2. trader 买卖期货
     3. broker 中间联系人
- order的参量:
  - 三个类型:STOP,LIMIT,MARKET;
  - 四个状态:TODO,DOING,DONE,CANCEL
  - condition:即价格落在该区间内order被激活
  ![enter description here][1]

   用户注册登录后可以察看futures,order和trade;
   Trader只能察看和自己相关的order和trade,并可以在order页面新建或取消订单;
   Broker只能察看和自己有关的futures,order和trade.

   ![enter description here][2]

  	 当trader用户成功新建订单后,则新生成一个status为TODO的order,根据order的type判断该订单是否被成功激活(如果是MARKET,直接激活;若是LIMIT,则价位须优于市场价;若是STOP,则须优于指定价),激活后词order的status为DOING,当交易完成后,如果此交易是买,status被设为DONE;如果是卖,当剩余量为0时,status转化为DONE.TODO和DOING状态的order都可以被cancel,cancel后的order status转为CANCEL.




  [1]: ./doc/OTCflow.png "OTCflow.png"
  [2]: ./doc/orderFlow.png "orderFlow.png"
