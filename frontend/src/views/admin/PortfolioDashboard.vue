<!-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ -->
<script setup>
import AdminShell from '../../components/AdminShell.vue'
import StatusBadge from '../../components/StatusBadge.vue'
import {projects, tasks, milestones} from '../../api/mock'
</script>
<template>
  <AdminShell><template #title>组合驾驶舱</template>
    <div class="page-heading"><div><p>PORTFOLIO CONTROL ROOM</p><h1>项目组合驾驶舱</h1><span>聚合项目健康、关键里程碑、资源负荷与经营指标</span></div><div><button class="button secondary">导出周报</button><button class="button primary">＋ 新建项目</button></div></div>
    <section class="metric-grid">
      <article><header><span>在执行项目</span><i>▦</i></header><strong>24</strong><p>本月新立项 <b>3</b> 个</p></article>
      <article><header><span>组合合同额</span><i>¥</i></header><strong>1,826<small> 万</small></strong><p>已确认收入 1,042 万</p></article>
      <article class="warning"><header><span>风险项目</span><i>△</i></header><strong>4</strong><p><b>2</b> 项需本周升级处理</p></article>
      <article><header><span>里程碑准时率</span><i>✓</i></header><strong>92.6%</strong><p>较上月提升 1.8%</p></article>
    </section>
    <div class="dashboard-grid">
      <section class="card portfolio-trend"><header><div><h2>项目组合健康度</h2><p>按计划、成本、范围与资源综合评分</p></div><button>查看经营报表 →</button></header><div class="health-layout"><div class="portfolio-ring"><b>86</b><small>组合健康分</small></div><div class="trend-chart"><span v-for="(n,i) in [58,70,66,82,75,91,86]" :key="i"><i :style="{height:n+'%'}"></i><small>{{['1月','2月','3月','4月','5月','6月','7月'][i]}}</small></span></div></div><footer><span><i class="green"></i>正常 18</span><span><i class="amber"></i>需关注 2</span><span><i class="red"></i>有风险 4</span></footer></section>
      <section class="card milestone-card"><header><div><h2>未来 14 天里程碑</h2><p>按日期与风险优先级排列</p></div><em>共 7 项</em></header><div class="milestone-list"><article v-for="m in milestones" :key="m.no"><time>{{m.date}}</time><div><b>{{m.name}}</b><small>{{m.project}} · {{m.owner}}</small><span><i :style="{width:m.progress+'%'}"></i></span></div><StatusBadge :value="m.status"/></article></div></section>
    </div>
    <section class="card"><header><div><h2>重点项目态势</h2><p>按风险与计划完成时间排序</p></div><button>进入项目工作区 →</button></header><div class="project-table"><div class="table-head"><span>项目 / 客户</span><span>阶段</span><span>项目经理</span><span>计划进度</span><span>预算</span><span>健康状态</span><span>计划完成</span></div><article v-for="project in projects" :key="project.code"><div><b>{{project.name}}</b><small>{{project.code}} · {{project.customer}}</small></div><span class="stage-pill">{{project.stage}}</span><span><i class="person">{{project.manager[0]}}</i>{{project.manager}}</span><div class="progress-cell"><i><em :style="{width:project.progress+'%'}"></em></i><b>{{project.progress}}%</b></div><span>¥ {{project.budget}}</span><StatusBadge :value="project.health"/><span>{{project.end}}</span></article></div></section>
    <div class="dashboard-bottom">
      <section class="card task-focus"><header><div><h2>今日交付焦点</h2><p>逾期及即将到期的关键任务</p></div><button>进入交付任务 →</button></header><article v-for="task in tasks.slice(0,4)" :key="task.no"><div><StatusBadge :value="task.priority"/><small>{{task.no}}</small></div><h3>{{task.title}}</h3><p>{{task.project}} · {{task.assignee}}</p><footer><span :class="task.due.includes('逾期')?'late':''">{{task.due}}</span><StatusBadge :value="task.status"/></footer></article></section>
      <section class="card capacity"><header><div><h2>本周资源容量</h2><p>核心角色可用工时</p></div><button>资源视图 →</button></header><div v-for="row in [['业务顾问',184,78],['产品与设计',126,62],['研发工程师',342,88],['测试与交付',196,71]]" :key="row[0]"><span><b>{{row[0]}}</b><small>{{row[1]}} 小时已分配</small></span><i><em :style="{width:row[2]+'%'}"></em></i><strong>{{row[2]}}%</strong></div></section>
      <section class="card decision"><header><div><h2>待决策事项</h2><p>需要项目委员会关注</p></div></header><article><span>01</span><div><b>供应链协同权限口径</b><small>影响方案冻结 · 截止 08 月 04 日</small></div><em>高</em></article><article><span>02</span><div><b>智能仓储接口窗口</b><small>影响 UAT 排期 · 截止 08 月 02 日</small></div><em>中</em></article><footer>下一次项目委员会：8 月 3 日 10:00</footer></section>
    </div>
  </AdminShell>
</template>
