/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.dto;

import cn.zhuatech.pms.model.Milestone;
import cn.zhuatech.pms.model.Project;
import cn.zhuatech.pms.model.ProjectRisk;
import cn.zhuatech.pms.model.ProjectTask;
import cn.zhuatech.pms.model.Timesheet;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public final class PmsDto {
    private PmsDto() {}

    public record ProjectView(Long id, String projectCode, String projectName, String customer,
                              String manager, LocalDate plannedStart, LocalDate plannedEnd,
                              BigDecimal budget, Integer progress, String status, String health) {
        public static ProjectView from(Project project) {
            return new ProjectView(project.getId(), project.getProjectCode(), project.getProjectName(),
                    project.getCustomer(), project.getManager(), project.getPlannedStart(),
                    project.getPlannedEnd(), project.getBudget(), project.getProgress(),
                    project.getStatus(), project.getHealth());
        }
    }

    public record TaskView(Long id, String taskNo, String projectCode, String title, String assignee,
                           String priority, LocalDate dueDate, String status, Integer estimatedHours,
                           Integer loggedHours) {
        public static TaskView from(ProjectTask task) {
            return new TaskView(task.getId(), task.getTaskNo(), task.getProjectCode(), task.getTitle(),
                    task.getAssignee(), task.getPriority(), task.getDueDate(), task.getStatus(),
                    task.getEstimatedHours(), task.getLoggedHours());
        }
    }

    public record MilestoneView(Long id, String milestoneNo, String projectCode, String milestoneName,
                                String owner, LocalDate plannedDate, Integer completionRate, String status) {
        public static MilestoneView from(Milestone milestone) {
            return new MilestoneView(milestone.getId(), milestone.getMilestoneNo(), milestone.getProjectCode(),
                    milestone.getMilestoneName(), milestone.getOwner(), milestone.getPlannedDate(),
                    milestone.getCompletionRate(), milestone.getStatus());
        }
    }

    public record RiskView(Long id, String riskNo, String projectCode, String title, String owner,
                           String probability, String impact, String mitigation, String status) {
        public static RiskView from(ProjectRisk risk) {
            return new RiskView(risk.getId(), risk.getRiskNo(), risk.getProjectCode(), risk.getTitle(),
                    risk.getOwner(), risk.getProbability(), risk.getImpact(), risk.getMitigation(),
                    risk.getStatus());
        }
    }

    public record TimesheetView(Long id, String sheetNo, String projectCode, String contributor,
                                LocalDate workDate, BigDecimal hours, String workItem, String approvalStatus) {
        public static TimesheetView from(Timesheet sheet) {
            return new TimesheetView(sheet.getId(), sheet.getSheetNo(), sheet.getProjectCode(),
                    sheet.getContributor(), sheet.getWorkDate(), sheet.getHours(), sheet.getWorkItem(),
                    sheet.getApprovalStatus());
        }
    }

    public record Dashboard(long projectCount, long atRiskProjects, long openTasks, long overdueTasks,
                            long upcomingMilestones, long highImpactRisks, BigDecimal portfolioBudget,
                            List<ProjectView> activeProjects, List<TaskView> urgentTasks,
                            List<MilestoneView> upcomingMilestoneList) {}

    public record CreateProjectRequest(
            @NotBlank String projectCode,
            @NotBlank @Size(max = 120) String projectName,
            @NotBlank String customer,
            @NotBlank String manager,
            @NotNull LocalDate plannedStart,
            @NotNull @FutureOrPresent LocalDate plannedEnd,
            @NotNull @PositiveOrZero BigDecimal budget) {}

    public record CreateTaskRequest(
            @NotBlank String projectCode,
            @NotBlank @Size(max = 160) String title,
            @NotBlank String assignee,
            @Pattern(regexp = "普通|重要|紧急") String priority,
            @NotNull @FutureOrPresent LocalDate dueDate,
            @NotNull @Positive @Max(999) Integer estimatedHours) {}

    public record SubmitTimesheetRequest(
            @NotBlank String projectCode,
            @NotBlank String contributor,
            @NotNull LocalDate workDate,
            @NotNull @Positive BigDecimal hours,
            @NotBlank @Size(max = 180) String workItem) {}
}
