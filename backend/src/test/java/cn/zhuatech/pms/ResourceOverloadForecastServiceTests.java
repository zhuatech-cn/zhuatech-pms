/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.pms;

import cn.zhuatech.pms.service.ResourceOverloadForecastService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class ResourceOverloadForecastServiceTests {
    private final ResourceOverloadForecastService service = new ResourceOverloadForecastService();

    @Test void escalatesSevereOverload() {
        var result = service.forecast(new ResourceOverloadForecastService.Request(
            bd("100"), bd("145"), bd("110"), 4, bd("10"), 3));
        assertThat(result.status()).isEqualTo("ESCALATE");
        assertThat(result.reallocationHours()).isPositive();
    }

    @Test void keepsBalancedPlan() {
        var result = service.forecast(new ResourceOverloadForecastService.Request(
            bd("120"), bd("90"), bd("60"), 1, bd("8"), 0));
        assertThat(result.status()).isEqualTo("BALANCED");
        assertThat(result.shortageHours()).isZero();
    }

    private BigDecimal bd(String value) { return new BigDecimal(value); }
}
