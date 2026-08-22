/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms;

import cn.zhuatech.pms.service.EarnedValueHealthService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EarnedValueHealthServiceTests {
    private final EarnedValueHealthService service = new EarnedValueHealthService();

    @Test
    void identifiesCriticalScheduleAndCostPerformance() {
        var result = service.evaluate(new EarnedValueHealthService.Request(
            "PRJ-ERP-01", new BigDecimal("500000"), new BigDecimal("350000"),
            new BigDecimal("450000"), new BigDecimal("1000000"), 12));

        assertEquals(new BigDecimal("0.7000"), result.schedulePerformanceIndex());
        assertEquals(new BigDecimal("0.7778"), result.costPerformanceIndex());
        assertEquals("CRITICAL", result.status());
    }

    @Test
    void recognizesHealthyProject() {
        var result = service.evaluate(new EarnedValueHealthService.Request(
            "PRJ-CRM-02", new BigDecimal("400000"), new BigDecimal("410000"),
            new BigDecimal("390000"), new BigDecimal("800000"), 8));

        assertEquals("ON_TRACK", result.status());
    }
}
