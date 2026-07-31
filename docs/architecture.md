# 架构说明

Copyright 2026 上海如静知华信息科技有限公司

ZhuaTech PMS 采用前后端分离的模块化单体架构。Vue 3 同时承载 PMO 管理端和成员 H5；Spring Boot 提供认证、项目、任务、里程碑、风险与工时 API；MySQL 保存业务数据，Flyway 管理结构演进。

```text
浏览器管理端 / 成员 H5
          │ HTTPS + JWT
          ▼
    Spring Security
          │
 Auth ─ PMS Application Service ─ Exception Handler
          │
 Project / Task / Milestone / Risk / Timesheet
          │
   Spring Data JPA + Flyway
          │
        MySQL 8
```

领域边界：

- 项目域：项目编码、客户、经理、阶段、健康、预算、进度与计划周期。
- 计划域：里程碑、交付时间与完成状态。
- 执行域：任务、优先级、负责人、截止日期、估算与已用工时。
- 风险域：概率、影响、责任人、应对措施和处置状态。
- 工时域：成员、工作日期、工时数、工作内容和审批状态。
- 身份域：账号、BCrypt 密码、JWT 和角色授权。

社区源码版以清晰可运行作为首要目标。企业扩展建议按模块拆分应用服务，并引入组织树、项目成员关系、租户隔离、数据权限、消息中心、对象存储、审计事件和可观测性。
