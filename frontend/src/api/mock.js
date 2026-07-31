/* Copyright 2026 上海如静知华信息科技有限公司 */
export const projects = [
  {code:'PRJ-2026-018',name:'华东智能仓储升级',customer:'澄川智能制造',manager:'陆嘉言',progress:68,budget:'328.0 万',end:'10 月 30 日',health:'正常',stage:'交付验证'},
  {code:'PRJ-2026-023',name:'集团供应链协同平台',customer:'启衡实业集团',manager:'林清越',progress:43,budget:'465.0 万',end:'12 月 18 日',health:'有风险',stage:'方案实施'},
  {code:'PRJ-2026-031',name:'工厂数据治理一期',customer:'云岱精工',manager:'周其安',progress:24,budget:'186.0 万',end:'11 月 28 日',health:'正常',stage:'蓝图设计'},
  {code:'PRJ-2026-036',name:'售后服务数字化咨询',customer:'锐恒装备',manager:'苏景行',progress:16,budget:'68.0 万',end:'09 月 26 日',health:'需关注',stage:'现状调研'}
]

export const tasks = [
  {no:'TSK-260731-108',project:'PRJ-2026-023',title:'确认跨组织订单权限矩阵',assignee:'许知遥',priority:'紧急',due:'今天',status:'进行中',estimate:12,logged:7},
  {no:'TSK-260731-096',project:'PRJ-2026-018',title:'完成入库策略联调与回归',assignee:'江叙',priority:'重要',due:'明天',status:'待验收',estimate:18,logged:16},
  {no:'TSK-260730-081',project:'PRJ-2026-031',title:'提交主数据质量规则清单',assignee:'唐予安',priority:'普通',due:'08 月 02 日',status:'进行中',estimate:10,logged:4},
  {no:'TSK-260729-062',project:'PRJ-2026-036',title:'完成服务蓝图干系人访谈',assignee:'温书屿',priority:'重要',due:'已逾期 1 天',status:'待开始',estimate:8,logged:0},
  {no:'TSK-260728-041',project:'PRJ-2026-018',title:'冻结一期上线范围与验收口径',assignee:'陆嘉言',priority:'普通',due:'07 月 29 日',status:'已完成',estimate:6,logged:6}
]

export const milestones = [
  {no:'MS-018-05',project:'PRJ-2026-018',name:'UAT 业务验收',owner:'陆嘉言',date:'08 月 05 日',progress:72,status:'进行中'},
  {no:'MS-023-03',project:'PRJ-2026-023',name:'核心流程方案评审',owner:'林清越',date:'08 月 08 日',progress:55,status:'进行中'},
  {no:'MS-031-02',project:'PRJ-2026-031',name:'数据标准发布',owner:'周其安',date:'08 月 12 日',progress:31,status:'准备中'},
  {no:'MS-036-01',project:'PRJ-2026-036',name:'现状诊断汇报',owner:'苏景行',date:'08 月 16 日',progress:18,status:'准备中'}
]

export const risks = [
  {no:'RSK-260731-019',project:'PRJ-2026-023',title:'集团成员企业权限口径尚未统一',owner:'林清越',probability:'高',impact:'高',status:'应对中',action:'8 月 4 日前冻结权限矩阵'},
  {no:'RSK-260730-014',project:'PRJ-2026-018',title:'自动化立库接口联调窗口偏紧',owner:'江叙',probability:'中',impact:'高',status:'应对中',action:'开放仿真环境并安排联合值守'},
  {no:'RSK-260729-008',project:'PRJ-2026-036',title:'关键用户访谈可用时间分散',owner:'温书屿',probability:'中',impact:'中',status:'观察中',action:'小组访谈并补充异步问卷'}
]

export const people = [
  {name:'许知遥',role:'业务分析',allocation:92,project:'供应链协同',load:'偏高'},
  {name:'江叙',role:'后端开发',allocation:86,project:'智能仓储',load:'偏高'},
  {name:'唐予安',role:'数据顾问',allocation:74,project:'数据治理',load:'适中'},
  {name:'温书屿',role:'业务顾问',allocation:63,project:'售后咨询',load:'适中'},
  {name:'顾呈',role:'前端开发',allocation:48,project:'智能仓储',load:'可调配'}
]
