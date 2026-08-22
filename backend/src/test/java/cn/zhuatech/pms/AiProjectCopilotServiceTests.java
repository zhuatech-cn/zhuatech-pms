/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms;
import cn.zhuatech.pms.ai.OpenAiCompatibleGateway;
import cn.zhuatech.pms.service.AiProjectCopilotService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
class AiProjectCopilotServiceTests {
    private final AiProjectCopilotService service = new AiProjectCopilotService(
        new OpenAiCompatibleGateway("local", "https://api.deepseek.com", "deepseek-chat", ""));
    @Test void summarizesCriticalProject() {
        var result = service.summarize(new AiProjectCopilotService.Request("核心系统升级", new BigDecimal("40"),
            new BigDecimal("70"), new BigDecimal("85"), new BigDecimal("60"), 4, 5, new BigDecimal("125")));
        assertThat(result.status()).isEqualTo("CRITICAL");
        assertThat(result.priorityActions()).hasSizeGreaterThan(3);
    }
}
