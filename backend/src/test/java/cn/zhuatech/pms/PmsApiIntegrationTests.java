/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PmsApiIntegrationTests {
    @Autowired MockMvc mvc;

    private String login(String username, String password) throws Exception {
        String json = mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Matcher matcher = Pattern.compile("\\\"token\\\":\\\"([^\\\"]+)\\\"").matcher(json);
        if (!matcher.find()) throw new AssertionError("登录响应中缺少 token");
        return matcher.group(1);
    }

    @Test
    void adminCanReadPortfolioData() throws Exception {
        String token = login("admin", "admin123");
        mvc.perform(get("/api/pms/dashboard").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectCount").value(4))
                .andExpect(jsonPath("$.data.openTasks").value(4));
        mvc.perform(get("/api/pms/projects").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(4));
    }

    @Test
    void projectManagerCanCreateAndAdvanceTask() throws Exception {
        String token = login("manager", "manager123");
        String body = "{\"projectCode\":\"PRJ-2026-018\",\"title\":\"测试任务\",\"assignee\":\"测试员\"," +
                "\"priority\":\"普通\",\"dueDate\":\"" + LocalDate.now().plusDays(2) + "\",\"estimatedHours\":8}";
        String json = mvc.perform(post("/api/pms/tasks").header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("待开始"))
                .andReturn().getResponse().getContentAsString();
        Matcher id = Pattern.compile("\\\"id\\\":(\\d+)").matcher(json);
        if (!id.find()) throw new AssertionError("任务响应中缺少 id");
        mvc.perform(patch("/api/pms/tasks/" + id.group(1) + "/advance")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("进行中"));
    }

    @Test
    void memberCanSubmitTimesheet() throws Exception {
        String token = login("member", "member123");
        String body = "{\"projectCode\":\"PRJ-2026-018\",\"contributor\":\"项目成员\"," +
                "\"workDate\":\"" + LocalDate.now() + "\",\"hours\":7.5,\"workItem\":\"联调与测试\"}";
        mvc.perform(post("/api/pms/timesheets").header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.approvalStatus").value("待审批"));
    }

    @Test
    void anonymousRequestIsDenied() throws Exception {
        mvc.perform(get("/api/pms/projects")).andExpect(status().isForbidden());
    }

    @Test
    void managerCanEvaluateDeliveryConfidence() throws Exception {
        String token = login("manager", "manager123");
        mvc.perform(post("/api/pms/delivery-confidence").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"projectCode\":\"PRJ-2026-018\",\"plannedProgress\":80,\"actualProgress\":55,\"openCriticalTasks\":3,\"milestoneDelayDays\":7,\"budgetUsedPercent\":75}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.confidenceScore").value(22))
            .andExpect(jsonPath("$.data.confidence").value("LOW"))
            .andExpect(jsonPath("$.data.escalationRequired").value(true));
    }
}
