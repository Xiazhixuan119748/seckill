# Seckill 高并发秒杀系统

基于 **Java 21 + Spring Boot 3.5** 构建的多模块高并发秒杀系统，集成多级缓存、Redis + Lua 原子库存预扣、秒杀令牌大闸、行为验证码、RabbitMQ 异步削峰与可靠投递补偿、SSE 实时结果推送等完整秒杀核心链路。

## 技术栈

| 分类 | 技术 / 组件 | 版本 / 说明 |
| --- | --- | --- |
| 基础框架 | Java / Spring Boot | JDK 21 / Spring Boot 3.5.8 |
| 持久层 | MyBatis / MySQL | MyBatis Spring Boot Starter 3.0.5 / HikariCP |
| 缓存与分布式 | Redis / Redisson / Caffeine | Lettuce + Lua 脚本、布隆过滤器、本地多级缓存 |
| 消息队列 | RabbitMQ | Publisher Confirm + Return + 手动 ACK + 失败补偿 |
| 认证鉴权 | Sa-Token | 1.45.0（整合 RedisTemplate，Bearer Token） |
| 安全防护 | Tianai Captcha | 1.5.5（行为滑块/点选验证码 + 二次验证） |
| 日志与工具 | Log4j2 + Disruptor / Hutool | 异步高性能日志、Spring Retry |

## 项目结构

```text
seckill
├── pom.xml                # 父工程 POM（依赖与插件统一管理）
├── seckill-app            # 启动入口、多环境配置（dev / prod）与日志配置
├── seckill-common         # 公共配置、统一响应、全局异常、AOP 日志、DO/Mapper、Lua 脚本
├── seckill-user           # 用户注册/登录、短信/验证码限流、行为验证码接口
├── seckill-goods          # 秒杀商品列表与详情、多级缓存、管理端缓存预热
└── seckill-order          # 秒杀令牌大闸、限流、Lua 预扣库存、MQ 异步下单、补偿任务、SSE 推送
```

## 核心特性

- **多级缓存与防穿透**：商品数据采用本地缓存 + Redis 多级缓存，结合 Redisson 布隆过滤器与管理端缓存预热接口。
- **防刷与流量削峰**：
  - 行为验证码（支持二次验证）+ 分布式限流（`seckill_rate_limit.lua`）。
  - **秒杀令牌大闸**（`seckill_token_gate.lua` / `seckill_check_token.lua`）：按库存放大系数控制放行流量（默认 `multiplier: 5`）。
- **原子预扣库存防超卖**：通过 Redis Lua 脚本（`seckill_pre_deduct_stock.lua`）原子完成限购校验与库存预扣，异常时通过补偿脚本（`seckill_compensate_pre_deduct_stock.lua`）回滚库存。
- **异步下单与可靠消息投递**：
  - 预扣成功后发送下单消息至 RabbitMQ，消费者手动 ACK 落库。
  - 开启 Publisher Confirm 与 Return 回调，投递失败记录补偿表并由定时任务（`SeckillOrderSendCompensationTask`）自动重试补偿。
- **实时结果通知**：支持轮询查询秒杀结果，也支持通过 SSE（`text/event-stream`）订阅实时下单结果。

## 核心接口一览

### 用户与验证码（`seckill-user`）

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| `POST` | `/user/register` | 用户注册 |
| `POST` | `/user/login` | 用户登录（带失败次数限制） |
| `POST` | `/user/code/send` | 发送验证码（带每日限流） |
| `POST` | `/user/logout` | 退出登录 |
| `POST` | `/captcha/gen` | 生成行为验证码 |
| `POST` | `/captcha/check` | 校验行为验证码 |

### 商品与预热（`seckill-goods`）

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| `POST` | `/seckill/goods/list` | 秒杀商品列表 |
| `POST` | `/seckill/goods/detail` | 秒杀商品详情 |
| `POST` | `/admin/seckill/goods/cache/preheat` | 秒杀商品缓存预热 |

### 秒杀订单（`seckill-order`）

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| `POST` | `/seckill/order/token` | 获取秒杀令牌 |
| `POST` | `/seckill/order` | 提交秒杀下单请求 |
| `POST` | `/seckill/order/result` | 查询秒杀订单结果 |
| `POST` | `/seckill/order/result/subscribe` | SSE 订阅秒杀结果（`text/event-stream`） |

## 快速开始

### 1. 环境准备

- JDK 21+
- Maven 3.8+
- MySQL 8.x（默认数据库名：`seckill`）
- Redis 6.x+
- RabbitMQ 3.x+（默认 Virtual Host：`/seckill`）

### 2. 修改本地配置

编辑 `seckill-app/src/main/resources/application-dev.yml`，按实际环境修改 MySQL、Redis 与 RabbitMQ 连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&useSSL=false
    username: root
    password: your_mysql_password
  data:
    redis:
      host: 127.0.0.1
      port: 6379
      password: your_redis_password
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: seckill
    password: your_rabbitmq_password
    virtual-host: /seckill
```

### 3. 编译与启动

```bash
# 编译打包
mvn clean package -DskipTests

# 启动应用（默认端口 8080，激活 dev 环境）
java -jar seckill-app/target/seckill-app-0.0.1-SNAPSHOT.jar
```

也可以直接在 IntelliJ IDEA 中导入根目录 `pom.xml`，运行 `com.seckill.app.SeckillApplication` 启动。
