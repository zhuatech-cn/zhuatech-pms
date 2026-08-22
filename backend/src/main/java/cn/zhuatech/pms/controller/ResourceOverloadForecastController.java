/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.controller;

import cn.zhuatech.pms.common.ApiResponse;
import cn.zhuatech.pms.service.ResourceOverloadForecastService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pms/insights")
public class ResourceOverloadForecastController {
    private final ResourceOverloadForecastService service;
    public ResourceOverloadForecastController(ResourceOverloadForecastService service) { this.service = service; }

    @PostMapping("/resource-overload")
    public ApiResponse<ResourceOverloadForecastService.Result> forecast(
        @Valid @RequestBody ResourceOverloadForecastService.Request request) {
        return ApiResponse.ok(service.forecast(request));
    }
}
