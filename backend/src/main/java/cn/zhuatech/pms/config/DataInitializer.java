/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.config;

import cn.zhuatech.pms.model.Milestone;
import cn.zhuatech.pms.model.Project;
import cn.zhuatech.pms.model.ProjectRisk;
import cn.zhuatech.pms.model.ProjectTask;
import cn.zhuatech.pms.model.Timesheet;
import cn.zhuatech.pms.model.UserAccount;
import cn.zhuatech.pms.repository.MilestoneRepository;
import cn.zhuatech.pms.repository.ProjectRepository;
import cn.zhuatech.pms.repository.ProjectRiskRepository;
import cn.zhuatech.pms.repository.ProjectTaskRepository;
import cn.zhuatech.pms.repository.TimesheetRepository;
import cn.zhuatech.pms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(UserRepository users, ProjectRepository projects, ProjectTaskRepository tasks,
                           MilestoneRepository milestones, ProjectRiskRepository risks,
                           TimesheetRepository timesheets, PasswordEncoder encoder) {
        return args -> {
            if (users.count() > 0) return;

            users.save(new UserAccount("admin", encoder.encode("admin123"), "项目运营平台主管", UserAccount.Role.ADMIN));
            users.save(new UserAccount("manager", encoder.encode("manager123"), "项目经理", UserAccount.Role.PROJECT_MANAGER));
            users.save(new UserAccount("member", encoder.encode("member123"), "项目成员", UserAccount.Role.MEMBER));

            projects.save(new Project("PRJ-2026-018", "华东智能仓储升级", "澄川智能制造", "陆嘉言",
                    LocalDate.of(2026, 4, 8), LocalDate.of(2026, 10, 30), new BigDecimal("3280000"), 68,
                    "执行中", "正常"));
            projects.save(new Project("PRJ-2026-023", "集团供应链协同平台", "启衡实业集团", "林清越",
                    LocalDate.of(2026, 5, 18), LocalDate.of(2026, 12, 18), new BigDecimal("4650000"), 43,
                    "执行中", "有风险"));
            projects.save(new Project("PRJ-2026-031", "工厂数据治理一期", "云岱精工", "周其安",
                    LocalDate.of(2026, 7, 1), LocalDate.of(2026, 11, 28), new BigDecimal("1860000"), 24,
                    "执行中", "正常"));
            projects.save(new Project("PRJ-2026-036", "售后服务数字化咨询", "锐恒装备", "苏景行",
                    LocalDate.of(2026, 7, 20), LocalDate.of(2026, 9, 26), new BigDecimal("680000"), 16,
                    "执行中", "需关注"));

            tasks.save(new ProjectTask("TSK-260731-108", "PRJ-2026-023", "确认跨组织订单权限矩阵", "许知遥", "紧急",
                    LocalDate.now(), "进行中", 12, 7));
            tasks.save(new ProjectTask("TSK-260731-096", "PRJ-2026-018", "完成入库策略联调与回归", "江叙", "重要",
                    LocalDate.now().plusDays(1), "待验收", 18, 16));
            tasks.save(new ProjectTask("TSK-260730-081", "PRJ-2026-031", "提交主数据质量规则清单", "唐予安", "普通",
                    LocalDate.now().plusDays(2), "进行中", 10, 4));
            tasks.save(new ProjectTask("TSK-260729-062", "PRJ-2026-036", "完成服务蓝图干系人访谈", "温书屿", "重要",
                    LocalDate.now().minusDays(1), "待开始", 8, 0));
            tasks.save(new ProjectTask("TSK-260728-041", "PRJ-2026-018", "冻结一期上线范围与验收口径", "陆嘉言", "普通",
                    LocalDate.now().minusDays(2), "已完成", 6, 6));

            milestones.save(new Milestone("MS-018-05", "PRJ-2026-018", "UAT 业务验收", "陆嘉言",
                    LocalDate.now().plusDays(5), 72, "进行中"));
            milestones.save(new Milestone("MS-023-03", "PRJ-2026-023", "核心流程方案评审", "林清越",
                    LocalDate.now().plusDays(8), 55, "进行中"));
            milestones.save(new Milestone("MS-031-02", "PRJ-2026-031", "数据标准发布", "周其安",
                    LocalDate.now().plusDays(12), 31, "准备中"));
            milestones.save(new Milestone("MS-036-01", "PRJ-2026-036", "现状诊断汇报", "苏景行",
                    LocalDate.now().plusDays(16), 18, "准备中"));

            risks.save(new ProjectRisk("RSK-260731-019", "PRJ-2026-023", "集团成员企业权限口径尚未统一", "林清越",
                    "高", "高", "组织两轮业务决策会，8 月 4 日前冻结权限矩阵", "应对中"));
            risks.save(new ProjectRisk("RSK-260730-014", "PRJ-2026-018", "自动化立库接口联调窗口偏紧", "江叙",
                    "中", "高", "提前开放仿真环境，并安排供应商联合值守", "应对中"));
            risks.save(new ProjectRisk("RSK-260729-008", "PRJ-2026-036", "关键用户访谈可用时间分散", "温书屿",
                    "中", "中", "采用分角色小组访谈并补充异步问卷", "观察中"));

            timesheets.save(new Timesheet("TS-260731-034", "PRJ-2026-023", "许知遥", LocalDate.now(),
                    new BigDecimal("6.5"), "权限矩阵梳理与业务确认", "待审批"));
            timesheets.save(new Timesheet("TS-260731-029", "PRJ-2026-018", "江叙", LocalDate.now(),
                    new BigDecimal("7.0"), "入库策略联调与缺陷修复", "已通过"));
            timesheets.save(new Timesheet("TS-260730-086", "PRJ-2026-031", "唐予安", LocalDate.now().minusDays(1),
                    new BigDecimal("7.5"), "质量规则工作坊及文档整理", "已通过"));
        };
    }
}
