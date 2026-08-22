/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.service;

import cn.zhuatech.pms.ai.OpenAiCompatibleGateway;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiProjectCopilotService {
    private final OpenAiCompatibleGateway gateway;
    public AiProjectCopilotService(OpenAiCompatibleGateway gateway) { this.gateway = gateway; }

    public Result summarize(Request request) {
        int risk = 5;
        List<String> actions = new ArrayList<>();
        BigDecimal scheduleGap = request.plannedProgress().subtract(request.actualProgress());
        if (scheduleGap.compareTo(BigDecimal.valueOf(10)) > 0) { risk += 30; actions.add("重排关键路径并确认追回进度的责任人"); }
        if (request.budgetUsedRate().subtract(request.elapsedScheduleRate()).compareTo(BigDecimal.valueOf(15)) > 0) {
            risk += 25; actions.add("复核成本提前消耗及剩余工作量估算");
        }
        if (request.openHighRisks() > 0) { risk += Math.min(25, request.openHighRisks() * 8); actions.add("逐项关闭高风险事项并记录触发条件"); }
        if (request.blockedTasks() > 0) { risk += Math.min(25, request.blockedTasks() * 5); actions.add("组织阻塞清理会并设置升级时限"); }
        if (request.teamUtilization().compareTo(BigDecimal.valueOf(110)) > 0) { risk += 15; actions.add("降低团队超载并补充关键技能资源"); }
        risk = Math.min(100, risk);
        if (actions.isEmpty()) actions.add("保持当前节奏并在下个周报周期复核关键指标");
        String status = risk >= 70 ? "CRITICAL" : risk >= 40 ? "AT_RISK" : "ON_TRACK";

        String context = "项目=%s，状态=%s，计划/实际=%s/%s，预算/时间=%s/%s，高风险=%d，阻塞=%d，动作=%s"
            .formatted(request.projectName(), status, request.plannedProgress(), request.actualProgress(),
                request.budgetUsedRate(), request.elapsedScheduleRate(), request.openHighRisks(), request.blockedTasks(), actions);
        var enhanced = gateway.complete("你是 PMO 项目副驾驶，请生成管理层周报摘要、偏差解释和下周优先事项。", context);
        var metadata = gateway.metadata();
        String local = "%s 当前为 %s，风险分 %d，首要动作：%s"
            .formatted(request.projectName(), status, risk, actions.getFirst());
        return new Result(status, risk, scheduleGap, enhanced.orElse(local), List.copyOf(actions),
            enhanced.isPresent() ? "EXTERNAL_MODEL" : "LOCAL_RULES", metadata.provider(), metadata.model());
    }

    public record Request(@NotBlank String projectName,
                          @DecimalMin("0") @DecimalMax("100") BigDecimal actualProgress,
                          @DecimalMin("0") @DecimalMax("100") BigDecimal plannedProgress,
                          @DecimalMin("0") BigDecimal budgetUsedRate,
                          @DecimalMin("0") BigDecimal elapsedScheduleRate,
                          @Min(0) int openHighRisks, @Min(0) int blockedTasks,
                          @DecimalMin("0") BigDecimal teamUtilization) {}
    public record Result(String status, int riskScore, BigDecimal scheduleGap, String executiveBrief,
                         List<String> priorityActions, String aiMode, String provider, String model) {}
}
