# 数据库说明

Copyright 2026 上海如静知华信息科技有限公司

默认数据库为 `zhuatech_pms`，正式环境使用 MySQL 8.4，结构由 `backend/src/main/resources/db/migration` 中的 Flyway 脚本维护。

| 表名 | 用途 | 关键约束 |
| --- | --- | --- |
| `sys_user` | 登录账号和角色 | `username` 唯一 |
| `pms_project` | 项目主数据 | `project_code` 唯一，按计划结束日排序 |
| `pms_project_task` | 项目任务 | `task_no` 唯一，按截止日和状态查询 |
| `pms_milestone` | 关键里程碑 | `milestone_no` 唯一 |
| `pms_project_risk` | 项目风险与应对 | `risk_no` 唯一 |
| `pms_timesheet` | 成员工时记录 | `timesheet_no` 唯一，按工作日期查询 |

业务实体统一保存 `id`、`created_at` 与 `updated_at`。社区源码版为降低理解成本，以项目编码连接领域数据；生产扩展建议引入外键 ID、组织与租户字段、乐观锁、软删除和审计人字段。

不得将真实客户名称、合同金额、成员姓名或生产备份提交至代码仓库。
