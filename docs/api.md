# PMS API 概览

Copyright 2026 上海如静知华信息科技有限公司

基础地址：`/api/pms`。除 `/api/auth/login` 外均需在请求头携带 `Authorization: Bearer <token>`。

| 方法 | 路径 | 说明 | 建议角色 |
| --- | --- | --- | --- |
| POST | `/api/auth/login` | 登录并签发 JWT | 公开 |
| GET | `/dashboard` | 项目组合、任务、风险和里程碑摘要 | 全部登录用户 |
| GET / POST | `/projects` | 项目列表 / 新建立项 | 查询：全部；新增：管理员、项目经理 |
| GET / POST | `/tasks` | 任务列表 / 创建任务 | 查询：全部；新增：管理员、项目经理 |
| PATCH | `/tasks/{id}/advance` | 待开始→进行中→待验收→已完成 | 全部登录用户 |
| GET | `/milestones` | 里程碑列表 | 全部登录用户 |
| GET | `/risks` | 风险登记册 | 全部登录用户 |
| GET / POST | `/timesheets` | 工时列表 / 登记工时 | 全部登录用户 |

演示角色为 `ADMIN`、`PROJECT_MANAGER`、`MEMBER`。当前版本重点呈现完整技术链路；生产落地必须增加组织、项目成员关系、资源归属和字段级校验。

示例：

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"manager","password":"manager123"}'
```

接口响应使用 JSON，日期采用 ISO-8601。业务错误由统一异常处理器返回 HTTP 状态码和消息，禁止在生产环境返回堆栈或数据库细节。
