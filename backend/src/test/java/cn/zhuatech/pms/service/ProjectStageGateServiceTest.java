/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ProjectStageGateServiceTest {
    private final ProjectStageGateService service = new ProjectStageGateService();
    @Test void advancesGovernedProject() {
        var r = service.assess(new ProjectStageGateService.Request("P1", true, true, true, true,
                true, true, true, true, true, true, true));
        assertThat(r.decision()).isEqualTo(ProjectStageGateService.Decision.ADVANCE);
    }
    @Test void conditionallyAdvancesDeliveryGaps() {
        var r = service.assess(new ProjectStageGateService.Request("P2", true, true, true, true,
                true, true, true, false, false, false, true));
        assertThat(r.actions()).hasSize(3);
    }
    @Test void blocksMissingBaselines() {
        var r = service.assess(new ProjectStageGateService.Request("P3", false, false, false, false,
                false, false, false, true, true, true, false));
        assertThat(r.blockers()).hasSize(8);
    }
}
