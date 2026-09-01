/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.controller;

import cn.zhuatech.pms.common.ApiResponse;
import cn.zhuatech.pms.service.ProjectStageGateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/pms")
public class ProjectStageGateController {
    private final ProjectStageGateService service;
    public ProjectStageGateController(ProjectStageGateService service) { this.service = service; }
    @PostMapping("/project-stage-gate")
    public ApiResponse<ProjectStageGateService.Assessment> assess(
            @Valid @RequestBody ProjectStageGateService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
