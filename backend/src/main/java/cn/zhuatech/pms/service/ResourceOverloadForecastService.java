/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceOverloadForecastService {
    public record Request(
        @DecimalMin("0.01") BigDecimal availableHours,
        @DecimalMin("0.0") BigDecimal plannedHours,
        @DecimalMin("0.0") BigDecimal criticalPathHours,
        @Min(0) int sharedResourceProjects,
        @DecimalMin("0.0") BigDecimal overtimeLimitHours,
        @Min(0) int skillGapCount
    ) {}

    public record Result(String status, BigDecimal loadRate, BigDecimal shortageHours,
                         BigDecimal suggestedOvertimeHours, BigDecimal reallocationHours, List<String> actions) {}

    public Result forecast(Request request) {
        BigDecimal loadRate = request.plannedHours().divide(request.availableHours(), 4, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(100)).setScale(1, RoundingMode.HALF_UP);
        BigDecimal shortage = request.plannedHours().subtract(request.availableHours()).max(BigDecimal.ZERO)
            .setScale(2, RoundingMode.HALF_UP);
        BigDecimal overtime = shortage.min(request.overtimeLimitHours()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal reallocation = shortage.subtract(overtime).max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);

        List<String> actions = new ArrayList<>();
        String status;
        if (loadRate.compareTo(BigDecimal.valueOf(125)) >= 0 || request.skillGapCount() >= 3
            || request.criticalPathHours().compareTo(request.availableHours()) > 0) {
            status = "ESCALATE";
            actions.add("升级资源冲突并调整关键路径计划");
        } else if (shortage.signum() > 0 || request.sharedResourceProjects() >= 3) {
            status = "MITIGATE";
            actions.add("在加班上限内平衡负荷并跨项目调配资源");
        } else {
            status = "BALANCED";
            actions.add("保持当前排期并按周复核资源负荷");
        }
        if (reallocation.signum() > 0) actions.add("将剩余超载工时转移至可替代资源或外部产能");
        if (request.skillGapCount() > 0) actions.add("为技能缺口安排结对与专项培训");
        return new Result(status, loadRate, shortage, overtime, reallocation, List.copyOf(actions));
    }
}
