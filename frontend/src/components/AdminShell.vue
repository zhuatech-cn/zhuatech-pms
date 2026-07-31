<!-- Copyright 2026 上海如静知华信息科技有限公司 -->
<script setup>
const groups = [
  {label:'项目运营', items:[['/admin/portfolio','组合驾驶舱','▦'],['/admin/projects','项目工作区','◫'],['/admin/delivery','交付任务','✓'],['/admin/risks','风险与资源','△']]},
  {label:'协作与分析', items:[['/admin/timesheets','工时管理','◷'],['/admin/reports','经营报表','⌁'],['/member/workbench','成员工作台','◇']]}
]
</script>
<template>
  <div class="app-shell">
    <aside class="sidebar">
      <div class="brand-mark"><span>ZH</span><div><b>ZhuaTech PMS</b><small>PROJECT OPERATIONS</small></div></div>
      <button class="tenant"><i></i><span><b>知华科技项目中心</b><small>上海总部 · 运营视图</small></span><em>⌄</em></button>
      <section v-for="group in groups" :key="group.label">
        <p>{{group.label}}</p>
        <nav><RouterLink v-for="item in group.items" :key="item[1]" :to="item[0]"><i>{{item[2]}}</i><span>{{item[1]}}</span><b v-if="item[1]==='风险与资源'">2</b></RouterLink></nav>
      </section>
      <div class="license-note"><b>社区源码版</b><span>仅限个人非商业学习交流</span><a href="https://www.zhuatech.cn/">zhuatech.cn ↗</a></div>
      <div class="profile"><span>陆</span><div><b>陆嘉言</b><small>项目运营平台主管</small></div><button>•••</button></div>
    </aside>
    <main class="workspace">
      <header class="topbar">
        <div class="crumb"><small>项目运营中心 /</small><b><slot name="title"/></b></div>
        <label class="search">⌕<input placeholder="搜索项目、任务、成员或客户"/><kbd>⌘ K</kbd></label>
        <div class="top-actions"><button>?</button><button class="bell">♢<i></i></button><span>2026 / 07 / 31</span></div>
      </header>
      <div class="page"><slot/></div>
      <footer>© 2026 上海如静知华信息科技有限公司 · <a href="https://www.zhuatech.cn/">知华科技</a></footer>
    </main>
  </div>
</template>
