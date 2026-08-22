/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class EarnedValueHealthService {
    public Result evaluate(Request request) {
        BigDecimal scheduleIndex = request.earnedValue()
            .divide(request.plannedValue(), 4, RoundingMode.HALF_UP);
        BigDecimal costIndex = request.earnedValue()
            .divide(request.actualCost(), 4, RoundingMode.HALF_UP);
        BigDecimal estimateAtCompletion = request.budgetAtCompletion()
            .divide(costIndex, 2, RoundingMode.HALF_UP);
        BigDecimal forecastVariance = request.budgetAtCompletion().subtract(estimateAtCompletion)
            .setScale(2, RoundingMode.HALF_UP);
        String status = scheduleIndex.compareTo(new BigDecimal("0.80")) < 0
            || costIndex.compareTo(new BigDecimal("0.80")) < 0 ? "CRITICAL"
            : scheduleIndex.compareTo(new BigDecimal("0.95")) < 0
                || costIndex.compareTo(new BigDecimal("0.95")) < 0 ? "AT_RISK" : "ON_TRACK";

        List<String> actions = new ArrayList<>();
        if (scheduleIndex.compareTo(BigDecimal.ONE) < 0) actions.add("压缩关键路径并复核剩余里程碑资源");
        if (costIndex.compareTo(BigDecimal.ONE) < 0) actions.add("冻结低价值支出并复盘成本偏差来源");
        if ("ON_TRACK".equals(status)) actions.add("保持当前基线并按周更新挣值指标");
        return new Result(request.projectCode(), scheduleIndex, costIndex,
            estimateAtCompletion, forecastVariance, status, actions);
    }

    public record Request(@NotBlank String projectCode,
                          @DecimalMin("0.01") BigDecimal plannedValue,
                          @DecimalMin("0.01") BigDecimal earnedValue,
                          @DecimalMin("0.01") BigDecimal actualCost,
                          @DecimalMin("0.01") BigDecimal budgetAtCompletion,
                          @Min(0) int remainingWeeks) {}

    public record Result(String projectCode, BigDecimal schedulePerformanceIndex,
                         BigDecimal costPerformanceIndex, BigDecimal estimateAtCompletion,
                         BigDecimal forecastBudgetVariance, String status,
                         List<String> actions) {}
}
