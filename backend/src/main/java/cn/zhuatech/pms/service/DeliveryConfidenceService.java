/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.pms.service;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryConfidenceService {
    public Result evaluate(Request request) {
        double progressGap = Math.max(0, request.plannedProgress() - request.actualProgress());
        double costPressure = Math.max(0, request.budgetUsedPercent() - request.actualProgress());
        int score = Math.max(0, (int) Math.round(100 - progressGap * 1.2
            - request.openCriticalTasks() * 8 - request.milestoneDelayDays() * 2 - costPressure * .5));
        String confidence = score >= 75 ? "HIGH" : score >= 45 ? "MEDIUM" : "LOW";
        List<String> actions = new ArrayList<>();
        if (progressGap >= 10) actions.add("重排关键路径并确认剩余工作量");
        if (request.openCriticalTasks() > 0) actions.add("为关键任务明确负责人和清零日期");
        if (request.milestoneDelayDays() > 0) actions.add("更新里程碑预测并同步干系人");
        if (actions.isEmpty()) actions.add("保持当前周度交付检查节奏");
        return new Result(request.projectCode(), score, confidence, score < 45, actions);
    }

    public record Request(@NotBlank String projectCode,
                          @DecimalMin("0") @DecimalMax("100") double plannedProgress,
                          @DecimalMin("0") @DecimalMax("100") double actualProgress,
                          @Min(0) int openCriticalTasks, @Min(0) int milestoneDelayDays,
                          @DecimalMin("0") @DecimalMax("100") double budgetUsedPercent) {}
    public record Result(String projectCode, int confidenceScore, String confidence,
                         boolean escalationRequired, List<String> actions) {}
}
