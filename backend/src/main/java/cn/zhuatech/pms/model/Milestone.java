/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "pms_milestone")
public class Milestone extends BaseEntity {
    @Column(nullable = false, unique = true, length = 32) private String milestoneNo;
    @Column(nullable = false, length = 32) private String projectCode;
    @Column(nullable = false, length = 120) private String milestoneName;
    @Column(nullable = false, length = 40) private String owner;
    @Column(nullable = false) private LocalDate plannedDate;
    @Column(nullable = false) private Integer completionRate;
    @Column(nullable = false, length = 20) private String status;

    protected Milestone() {}

    public Milestone(String milestoneNo, String projectCode, String milestoneName, String owner,
                     LocalDate plannedDate, Integer completionRate, String status) {
        this.milestoneNo = milestoneNo;
        this.projectCode = projectCode;
        this.milestoneName = milestoneName;
        this.owner = owner;
        this.plannedDate = plannedDate;
        this.completionRate = completionRate;
        this.status = status;
    }

    public String getMilestoneNo() { return milestoneNo; }
    public String getProjectCode() { return projectCode; }
    public String getMilestoneName() { return milestoneName; }
    public String getOwner() { return owner; }
    public LocalDate getPlannedDate() { return plannedDate; }
    public Integer getCompletionRate() { return completionRate; }
    public String getStatus() { return status; }
}
