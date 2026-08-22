-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(40) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    display_name VARCHAR(40) NOT NULL,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE pms_project (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_code VARCHAR(32) NOT NULL UNIQUE,
    project_name VARCHAR(120) NOT NULL,
    customer VARCHAR(100) NOT NULL,
    manager VARCHAR(40) NOT NULL,
    planned_start DATE NOT NULL,
    planned_end DATE NOT NULL,
    budget DECIMAL(14,2) NOT NULL,
    progress INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    health VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE pms_project_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_no VARCHAR(32) NOT NULL UNIQUE,
    project_code VARCHAR(32) NOT NULL,
    title VARCHAR(160) NOT NULL,
    assignee VARCHAR(40) NOT NULL,
    priority VARCHAR(16) NOT NULL,
    due_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    estimated_hours INT NOT NULL,
    logged_hours INT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE pms_milestone (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    milestone_no VARCHAR(32) NOT NULL UNIQUE,
    project_code VARCHAR(32) NOT NULL,
    milestone_name VARCHAR(120) NOT NULL,
    owner VARCHAR(40) NOT NULL,
    planned_date DATE NOT NULL,
    completion_rate INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE pms_project_risk (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    risk_no VARCHAR(32) NOT NULL UNIQUE,
    project_code VARCHAR(32) NOT NULL,
    title VARCHAR(160) NOT NULL,
    owner VARCHAR(40) NOT NULL,
    probability VARCHAR(16) NOT NULL,
    impact VARCHAR(16) NOT NULL,
    mitigation VARCHAR(260) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE pms_timesheet (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sheet_no VARCHAR(32) NOT NULL UNIQUE,
    project_code VARCHAR(32) NOT NULL,
    contributor VARCHAR(40) NOT NULL,
    work_date DATE NOT NULL,
    hours DECIMAL(4,1) NOT NULL,
    work_item VARCHAR(180) NOT NULL,
    approval_status VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE INDEX idx_pms_project_health ON pms_project(health);
CREATE INDEX idx_pms_project_end ON pms_project(planned_end);
CREATE INDEX idx_pms_task_status_due ON pms_project_task(status, due_date);
CREATE INDEX idx_pms_milestone_date ON pms_milestone(planned_date);
CREATE INDEX idx_pms_risk_project ON pms_project_risk(project_code);
CREATE INDEX idx_pms_timesheet_date ON pms_timesheet(work_date);
