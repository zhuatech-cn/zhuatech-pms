/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.service;

import cn.zhuatech.pms.common.BusinessException;
import cn.zhuatech.pms.dto.PmsDto.CreateProjectRequest;
import cn.zhuatech.pms.dto.PmsDto.CreateTaskRequest;
import cn.zhuatech.pms.dto.PmsDto.Dashboard;
import cn.zhuatech.pms.dto.PmsDto.MilestoneView;
import cn.zhuatech.pms.dto.PmsDto.ProjectView;
import cn.zhuatech.pms.dto.PmsDto.RiskView;
import cn.zhuatech.pms.dto.PmsDto.SubmitTimesheetRequest;
import cn.zhuatech.pms.dto.PmsDto.TaskView;
import cn.zhuatech.pms.dto.PmsDto.TimesheetView;
import cn.zhuatech.pms.model.Project;
import cn.zhuatech.pms.model.ProjectTask;
import cn.zhuatech.pms.model.Timesheet;
import cn.zhuatech.pms.repository.MilestoneRepository;
import cn.zhuatech.pms.repository.ProjectRepository;
import cn.zhuatech.pms.repository.ProjectRiskRepository;
import cn.zhuatech.pms.repository.ProjectTaskRepository;
import cn.zhuatech.pms.repository.TimesheetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PmsService {
    private final ProjectRepository projects;
    private final ProjectTaskRepository tasks;
    private final MilestoneRepository milestones;
    private final ProjectRiskRepository risks;
    private final TimesheetRepository timesheets;

    public PmsService(ProjectRepository projects, ProjectTaskRepository tasks,
                      MilestoneRepository milestones, ProjectRiskRepository risks,
                      TimesheetRepository timesheets) {
        this.projects = projects;
        this.tasks = tasks;
        this.milestones = milestones;
        this.risks = risks;
        this.timesheets = timesheets;
    }

    public Dashboard dashboard() {
        BigDecimal budget = projects.findAll().stream().map(Project::getBudget)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Dashboard(
                projects.count(),
                projects.countByHealth("有风险"),
                tasks.countByStatusNot("已完成"),
                tasks.countByDueDateBeforeAndStatusNot(LocalDate.now(), "已完成"),
                milestones.countByPlannedDateLessThanEqualAndStatusNot(LocalDate.now().plusDays(14), "已完成"),
                risks.countByImpactAndStatusNot("高", "已关闭"),
                budget,
                projects.findAllByOrderByPlannedEndAsc().stream().limit(6).map(ProjectView::from).toList(),
                tasks.findAllByOrderByDueDateAsc().stream().filter(t -> !"已完成".equals(t.getStatus()))
                        .limit(6).map(TaskView::from).toList(),
                milestones.findAllByOrderByPlannedDateAsc().stream().limit(6).map(MilestoneView::from).toList()
        );
    }

    public List<ProjectView> projects() {
        return projects.findAllByOrderByPlannedEndAsc().stream().map(ProjectView::from).toList();
    }

    public List<TaskView> tasks() {
        return tasks.findAllByOrderByDueDateAsc().stream().map(TaskView::from).toList();
    }

    public List<MilestoneView> milestones() {
        return milestones.findAllByOrderByPlannedDateAsc().stream().map(MilestoneView::from).toList();
    }

    public List<RiskView> risks() {
        return risks.findAllByOrderByIdDesc().stream().map(RiskView::from).toList();
    }

    public List<TimesheetView> timesheets() {
        return timesheets.findAllByOrderByWorkDateDesc().stream().map(TimesheetView::from).toList();
    }

    @Transactional
    public ProjectView createProject(CreateProjectRequest request) {
        if (projects.findByProjectCode(request.projectCode()).isPresent()) {
            throw new BusinessException("项目编码已存在");
        }
        if (request.plannedEnd().isBefore(request.plannedStart())) {
            throw new BusinessException("计划结束日期不能早于开始日期");
        }
        Project project = new Project(request.projectCode(), request.projectName(), request.customer(),
                request.manager(), request.plannedStart(), request.plannedEnd(), request.budget(),
                0, "立项中", "正常");
        return ProjectView.from(projects.save(project));
    }

    @Transactional
    public TaskView createTask(CreateTaskRequest request) {
        if (projects.findByProjectCode(request.projectCode()).isEmpty()) {
            throw new BusinessException("关联项目不存在");
        }
        String no = "TSK-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        ProjectTask task = new ProjectTask(no, request.projectCode(), request.title(), request.assignee(),
                request.priority(), request.dueDate(), "待开始", request.estimatedHours(), 0);
        return TaskView.from(tasks.save(task));
    }

    @Transactional
    public TaskView advanceTask(Long id) {
        ProjectTask task = tasks.findById(id).orElseThrow(() -> new BusinessException("项目任务不存在"));
        if ("已完成".equals(task.getStatus())) {
            throw new BusinessException("项目任务已完成");
        }
        task.advance();
        return TaskView.from(task);
    }

    @Transactional
    public TimesheetView submitTimesheet(SubmitTimesheetRequest request) {
        if (projects.findByProjectCode(request.projectCode()).isEmpty()) {
            throw new BusinessException("关联项目不存在");
        }
        String no = "TS-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Timesheet sheet = new Timesheet(no, request.projectCode(), request.contributor(), request.workDate(),
                request.hours(), request.workItem(), "待审批");
        return TimesheetView.from(timesheets.save(sheet));
    }
}
