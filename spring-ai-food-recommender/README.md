# Spring AI 智能美食推荐系统

基于 Spring AI + MySQL 构建的智能餐品推荐 Demo 项目，采用行业最佳实践设计，支持未来无缝切换到向量数据库。

## 🎯 核心特性

- **智能意图识别**: 使用 Spring AI 的 Function Calling 能力，自动从自然语言中提取搜索条件
- **策略模式架构**: 统一的检索接口设计，当前使用 JPA/SQL，可无缝切换到向量数据库
- **RAG 增强生成**: 结合数据库查询结果和 LLM 生成个性化推荐回复
- **RESTful API**: 提供简洁易用的 HTTP 接口

## 🏗️ 技术架构

```
┌─────────────┐     ┌──────────────────┐     ┌─────────────────┐
│   用户请求   │ ──▶ │  ChatController  │ ──▶ │   ChatService   │
└─────────────┘     └──────────────────┘     └────────┬────────┘
                                                      │
                    ┌─────────────────────────────────┼─────────────────────────────────┐
                    │                                 │                                 │
           ┌────────▼────────┐              ┌────────▼────────┐              ┌────────▼────────┐
           │  FoodRetrieval  │              │  JpaFoodRetrieval│              │ (Future) Vector │
           │    Tools        │─────────────▶│    Service      │              │    Service      │
           └─────────────────┘              └────────┬────────┘              └─────────────────┘
                                                     │
                                          ┌──────────▼──────────┐
                                          │  MySQL Database     │
                                          │  - shops 表          │
                                          │  - dishes 表         │
                                          └─────────────────────┘
```

## 📦 项目结构

```
spring-ai-food-recommender/
├── src/main/java/com/example/foodai/
│   ├── FoodAiApplication.java          # 启动类
│   ├── config/
│   │   └── AiToolConfig.java           # AI 工具配置
│   ├── controller/
│   │   └── ChatController.java         # REST 控制器
│   ├── service/
│   │   └── ChatService.java            # 核心业务逻辑
│   ├── retriever/
│   │   ├── FoodRetrievalService.java   # 统一检索接口
│   │   ├── JpaFoodRetrievalService.java # SQL 实现
│   │   └── FoodRetrievalTools.java     # AI 工具类
│   ├── repository/
│   │   ├── ShopRepository.java         # 店铺数据访问
│   │   └── DishRepository.java         # 菜品数据访问
│   └── model/
│       ├── Shop.java                   # 店铺实体
│       ├── Dish.java                   # 菜品实体
│       ├── FoodSearchCriteria.java     # 搜索条件
│       └── FoodSearchResult.java       # 搜索结果
├── src/main/resources/
│   ├── application.properties          # 配置文件
│   └── data.sql                        # 测试数据
└── pom.xml                             # Maven 配置
```

## 🚀 快速开始

### 1. 环境要求

- JDK 17+
- MySQL 8.0+
- Maven 3.6+
- OpenAI API Key

### 2. 数据库准备

```sql
CREATE DATABASE food_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 配置修改

编辑 `src/main/resources/application.properties`:

```properties
# MySQL 连接配置
spring.datasource.url=jdbc:mysql://localhost:3306/food_db
spring.datasource.username=root
spring.datasource.password=your_password

# OpenAI API Key
spring.ai.openai.api-key=your-openai-api-key
```

或使用环境变量:
```bash
export OPENAI_API_KEY=sk-your-api-key
```

### 4. 运行项目

```bash
cd spring-ai-food-recommender
mvn spring-boot:run
```

## 📡 API 使用

### 获取美食推荐

**POST** `/api/chat/recommend`

```bash
curl -X POST http://localhost:8080/api/chat/recommend \
  -H "Content-Type: application/json" \
  -d '{"message": "我想吃辣的川菜，人均 100 元以内"}'
```

**GET** `/api/chat/recommend?query=xxx`

```bash
curl "http://localhost:8080/api/chat/recommend?query=推荐一些清淡的粤菜"
```

### 普通对话

**POST** `/api/chat/message`

```bash
curl -X POST http://localhost:8080/api/chat/message \
  -H "Content-Type: application/json" \
  -d '{"message": "你好，介绍一下你自己"}'
```

## 💡 使用示例

### 示例 1: 按菜系搜索
```
用户：我想吃川菜
AI: 根据您的需求，我为您推荐以下川菜馆...
```

### 示例 2: 按价格搜索
```
用户：人均 50 元以内有什么好吃的？
AI: 在您的预算范围内，我推荐以下几家餐厅...
```

### 示例 3: 组合条件
```
用户：想吃辣的，但是不要太贵，最好评分高一点
AI: 根据您的要求（辣味、实惠、高评分），我为您找到...
```

## 🔧 扩展到向量数据库

项目采用策略模式设计，未来切换到向量数据库只需:

1. 创建新的实现类 `VectorFoodRetrievalService implements FoodRetrievalService`
2. 实现向量相似度搜索逻辑
3. 修改配置切换实现类

```java
@Service("vectorFoodRetrievalService")
public class VectorFoodRetrievalService implements FoodRetrievalService {
    // 向量数据库实现
}
```

## 📝 数据库模型

### shops 表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR(200) | 店铺名称 |
| cuisine_type | VARCHAR(100) | 菜系类型 |
| flavor_tags | VARCHAR(500) | 口味标签 |
| avg_price | DECIMAL(10,2) | 人均消费 |
| address | VARCHAR(500) | 地址 |
| rating | DECIMAL(3,1) | 评分 (1-5) |
| description | VARCHAR(1000) | 店铺描述 |

### dishes 表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| shop_id | BIGINT | 所属店铺 ID |
| name | VARCHAR(200) | 菜品名称 |
| price | DECIMAL(10,2) | 价格 |
| description | VARCHAR(1000) | 菜品描述 |
| flavor_tags | VARCHAR(200) | 口味标签 |
| is_spicy | BOOLEAN | 是否辣味 |

## 🛠️ 开发说明

### 添加新的检索策略
1. 实现 `FoodRetrievalService` 接口
2. 使用 `@Service("strategyName")` 注解
3. 在配置中切换策略

### 自定义 AI Prompt
在 `ChatService` 中修改 `systemPrompt` 变量

### 添加新的搜索条件
1. 在 `FoodSearchCriteria` 中添加字段
2. 在 `FoodRetrievalTools.searchFood()` 中添加参数
3. 更新 Repository 查询方法

## 📄 License

MIT License
