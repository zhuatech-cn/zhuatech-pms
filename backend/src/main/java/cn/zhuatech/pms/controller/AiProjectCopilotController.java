/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.controller;
import cn.zhuatech.pms.common.ApiResponse;
import cn.zhuatech.pms.service.AiProjectCopilotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/pms/ai")
public class AiProjectCopilotController {
    private final AiProjectCopilotService service;
    public AiProjectCopilotController(AiProjectCopilotService service) { this.service = service; }
    @PostMapping("/project-copilot")
    public ApiResponse<AiProjectCopilotService.Result> summarize(@Valid @RequestBody AiProjectCopilotService.Request request) {
        return ApiResponse.ok(service.summarize(request));
    }
}
