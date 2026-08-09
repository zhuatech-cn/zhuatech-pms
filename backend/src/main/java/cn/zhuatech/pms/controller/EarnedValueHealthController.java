/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.pms.controller;

import cn.zhuatech.pms.common.ApiResponse;
import cn.zhuatech.pms.service.EarnedValueHealthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pms/insights")
public class EarnedValueHealthController {
    private final EarnedValueHealthService service;

    public EarnedValueHealthController(EarnedValueHealthService service) {
        this.service = service;
    }

    @PostMapping("/earned-value-health")
    public ApiResponse<EarnedValueHealthService.Result> evaluate(
        @Valid @RequestBody EarnedValueHealthService.Request request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
