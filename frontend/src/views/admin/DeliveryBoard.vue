<!-- Copyright 2026 上海如静知华信息科技有限公司 -->
<script setup>
import AdminShell from '../../components/AdminShell.vue'
import StatusBadge from '../../components/StatusBadge.vue'
import {tasks} from '../../api/mock'
const columns=[
  {name:'待开始',count:12,color:'slate',items:tasks.filter(t=>t.status==='待开始')},
  {name:'进行中',count:18,color:'blue',items:tasks.filter(t=>t.status==='进行中')},
  {name:'待验收',count:7,color:'amber',items:tasks.filter(t=>t.status==='待验收')},
  {name:'已完成',count:31,color:'green',items:tasks.filter(t=>t.status==='已完成')}
]
</script>
<template><AdminShell><template #title>交付任务</template>
  <div class="page-heading"><div><p>DELIVERY EXECUTION</p><h1>交付任务看板</h1><span>按交付状态组织任务，识别阻塞、逾期和待验收工作</span></div><div><button class="button secondary">列表视图</button><button class="button primary">＋ 新建任务</button></div></div>
  <section class="delivery-toolbar"><div class="view-tabs"><button class="active">全部项目</button><button>我负责的</button><button>本周到期</button><button>已逾期 <b>3</b></button></div><div><label>⌕ <input placeholder="搜索任务"/></label><button>筛选条件 2</button><button>按优先级排序</button></div></section>
  <section class="kanban"><article v-for="column in columns" :key="column.name" class="kanban-column"><header :class="column.color"><span>{{column.name}}</span><b>{{column.count}}</b><button>＋</button></header><div class="kanban-body"><div v-for="task in column.items" :key="task.no" class="task-card"><div><span class="project-tag">{{task.project}}</span><StatusBadge :value="task.priority"/></div><h3>{{task.title}}</h3><p>{{task.no}}</p><div class="work-progress"><span><i :style="{width:Math.min(100,task.logged/task.estimate*100)+'%'}"></i></span><small>{{task.logged}} / {{task.estimate}}h</small></div><footer><span><i>{{task.assignee[0]}}</i>{{task.assignee}}</span><time :class="task.due.includes('逾期')?'late':''">◷ {{task.due}}</time></footer></div><div v-if="!column.items.length" class="empty-card">暂无示例任务</div><button class="add-card">＋ 添加任务</button></div></article></section>
  <div class="delivery-summary"><span>本周计划 <b>68</b> 项</span><span>已完成 <b>31</b> 项</span><span>进行中 <b>18</b> 项</span><span>平均周期 <b>3.8</b> 天</span><span>逾期任务 <b class="danger-text">3</b> 项</span><em>数据更新于 10:26</em></div>
</AdminShell></template>
