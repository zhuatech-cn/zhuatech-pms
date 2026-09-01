/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectStageGateService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.businessCaseCurrent()) blockers.add("项目商业论证未更新");
        if (!request.scopeBaselineApproved()) blockers.add("范围基线未批准");
        if (!request.scheduleBaselineApproved()) blockers.add("进度基线未批准");
        if (!request.budgetApproved()) blockers.add("预算未批准");
        if (!request.criticalResourcesCommitted()) blockers.add("关键资源未落实");
        if (!request.risksAndDependenciesReviewed()) blockers.add("风险与依赖未完成评审");
        if (!request.securityAndPrivacyApproved()) blockers.add("安全与隐私评审未批准");
        if (!request.steeringApprovalComplete()) blockers.add("项目治理委员会未批准阶段晋级");
        if (!blockers.isEmpty()) {
            actions.add("阻断阶段晋级并完成基线和治理整改");
            return new Assessment(Decision.BLOCKED, blockers, actions);
        }
        if (!request.acceptanceCriteriaApproved() || !request.rollbackOrExitPlanReady()
                || !request.stakeholderCommunicationReady()) {
            if (!request.acceptanceCriteriaApproved()) actions.add("批准阶段交付与验收标准");
            if (!request.rollbackOrExitPlanReady()) actions.add("准备回退、暂停或退出方案");
            if (!request.stakeholderCommunicationReady()) actions.add("完成相关方沟通与决策同步");
            return new Assessment(Decision.CONDITIONAL, blockers, actions);
        }
        actions.add("批准进入下一阶段并锁定基线、决策与责任人");
        return new Assessment(Decision.ADVANCE, blockers, actions);
    }

    public record Request(@NotBlank String projectId, boolean businessCaseCurrent,
                          boolean scopeBaselineApproved, boolean scheduleBaselineApproved,
                          boolean budgetApproved, boolean criticalResourcesCommitted,
                          boolean risksAndDependenciesReviewed, boolean securityAndPrivacyApproved,
                          boolean acceptanceCriteriaApproved, boolean rollbackOrExitPlanReady,
                          boolean stakeholderCommunicationReady, boolean steeringApprovalComplete) {}
    public record Assessment(Decision decision, List<String> blockers, List<String> actions) {}
    public enum Decision { ADVANCE, CONDITIONAL, BLOCKED }
}
