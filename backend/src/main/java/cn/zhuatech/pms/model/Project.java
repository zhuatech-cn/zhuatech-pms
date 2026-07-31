/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.pms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pms_project")
public class Project extends BaseEntity {
    @Column(nullable = false, unique = true, length = 32) private String projectCode;
    @Column(nullable = false, length = 120) private String projectName;
    @Column(nullable = false, length = 100) private String customer;
    @Column(nullable = false, length = 40) private String manager;
    @Column(nullable = false) private LocalDate plannedStart;
    @Column(nullable = false) private LocalDate plannedEnd;
    @Column(nullable = false, precision = 14, scale = 2) private BigDecimal budget;
    @Column(nullable = false) private Integer progress;
    @Column(nullable = false, length = 20) private String status;
    @Column(nullable = false, length = 20) private String health;

    protected Project() {}

    public Project(String code, String name, String customer, String manager, LocalDate plannedStart,
                   LocalDate plannedEnd, BigDecimal budget, Integer progress, String status, String health) {
        this.projectCode = code;
        this.projectName = name;
        this.customer = customer;
        this.manager = manager;
        this.plannedStart = plannedStart;
        this.plannedEnd = plannedEnd;
        this.budget = budget;
        this.progress = progress;
        this.status = status;
        this.health = health;
    }

    public String getProjectCode() { return projectCode; }
    public String getProjectName() { return projectName; }
    public String getCustomer() { return customer; }
    public String getManager() { return manager; }
    public LocalDate getPlannedStart() { return plannedStart; }
    public LocalDate getPlannedEnd() { return plannedEnd; }
    public BigDecimal getBudget() { return budget; }
    public Integer getProgress() { return progress; }
    public String getStatus() { return status; }
    public String getHealth() { return health; }
}
