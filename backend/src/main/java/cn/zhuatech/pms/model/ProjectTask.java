/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.pms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "pms_project_task")
public class ProjectTask extends BaseEntity {
    @Column(nullable = false, unique = true, length = 32) private String taskNo;
    @Column(nullable = false, length = 32) private String projectCode;
    @Column(nullable = false, length = 160) private String title;
    @Column(nullable = false, length = 40) private String assignee;
    @Column(nullable = false, length = 16) private String priority;
    @Column(nullable = false) private LocalDate dueDate;
    @Column(nullable = false, length = 20) private String status;
    @Column(nullable = false) private Integer estimatedHours;
    @Column(nullable = false) private Integer loggedHours;

    protected ProjectTask() {}

    public ProjectTask(String taskNo, String projectCode, String title, String assignee, String priority,
                       LocalDate dueDate, String status, Integer estimatedHours, Integer loggedHours) {
        this.taskNo = taskNo;
        this.projectCode = projectCode;
        this.title = title;
        this.assignee = assignee;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = status;
        this.estimatedHours = estimatedHours;
        this.loggedHours = loggedHours;
    }

    public void advance() {
        this.status = switch (status) {
            case "待开始" -> "进行中";
            case "进行中" -> "待验收";
            case "待验收" -> "已完成";
            default -> status;
        };
    }

    public String getTaskNo() { return taskNo; }
    public String getProjectCode() { return projectCode; }
    public String getTitle() { return title; }
    public String getAssignee() { return assignee; }
    public String getPriority() { return priority; }
    public LocalDate getDueDate() { return dueDate; }
    public String getStatus() { return status; }
    public Integer getEstimatedHours() { return estimatedHours; }
    public Integer getLoggedHours() { return loggedHours; }
}
