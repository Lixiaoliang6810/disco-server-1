# 夜店项目服务端

### 项目结构

```lua
disco-server
├── dusco-admin   --后台API模块
├── dusco-basic   --基础类模块
├── dusco-front   --前台API模块
├── dusco-pojo    --POJO对象模块
├── dusco-quartz  --定时任务模块
├── dusco-support  --项目额外支持模块
    ├── mart-boot-support     --spring boot 通用配置模块
    ├── mart-mybatis-support  --mybatis 支持模块
    ├── mart-netease-support  --网易IM支持模块
    ├── mart-oss-support      --阿里OSS支持模块
```

#### 项目规范约定

```

- 所有枚举类都必须实现自 com.miner.disco.basic.constants.BasicEnum 并实现方法
- 所有自定义异常类都必须集成自 com.miner.disco.basic.exception.BasicException 以便统一异常处理
- 分页查询Request处理必须集成自 com.miner.disco.basic.model.request.Pagination

```