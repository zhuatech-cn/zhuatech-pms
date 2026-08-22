/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pms_timesheet")
public class Timesheet extends BaseEntity {
    @Column(nullable = false, unique = true, length = 32) private String sheetNo;
    @Column(nullable = false, length = 32) private String projectCode;
    @Column(nullable = false, length = 40) private String contributor;
    @Column(nullable = false) private LocalDate workDate;
    @Column(nullable = false, precision = 4, scale = 1) private BigDecimal hours;
    @Column(nullable = false, length = 180) private String workItem;
    @Column(nullable = false, length = 20) private String approvalStatus;

    protected Timesheet() {}

    public Timesheet(String sheetNo, String projectCode, String contributor, LocalDate workDate,
                     BigDecimal hours, String workItem, String approvalStatus) {
        this.sheetNo = sheetNo;
        this.projectCode = projectCode;
        this.contributor = contributor;
        this.workDate = workDate;
        this.hours = hours;
        this.workItem = workItem;
        this.approvalStatus = approvalStatus;
    }

    public String getSheetNo() { return sheetNo; }
    public String getProjectCode() { return projectCode; }
    public String getContributor() { return contributor; }
    public LocalDate getWorkDate() { return workDate; }
    public BigDecimal getHours() { return hours; }
    public String getWorkItem() { return workItem; }
    public String getApprovalStatus() { return approvalStatus; }
}
