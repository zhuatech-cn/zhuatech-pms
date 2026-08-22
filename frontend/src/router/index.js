/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
import {createRouter, createWebHistory} from 'vue-router'
import LoginView from '../views/LoginView.vue'
import PortfolioDashboard from '../views/admin/PortfolioDashboard.vue'
import ProjectWorkspace from '../views/admin/ProjectWorkspace.vue'
import DeliveryBoard from '../views/admin/DeliveryBoard.vue'
import RiskResource from '../views/admin/RiskResource.vue'
import MemberWorkbench from '../views/member/MemberWorkbench.vue'

const router = createRouter({history:createWebHistory(), routes:[
  {path:'/', redirect:'/admin/portfolio'},
  {path:'/login', component:LoginView},
  {path:'/admin/portfolio', component:PortfolioDashboard},
  {path:'/admin/projects', component:ProjectWorkspace},
  {path:'/admin/delivery', component:DeliveryBoard},
  {path:'/admin/risks', component:RiskResource},
  {path:'/admin/timesheets', component:RiskResource},
  {path:'/admin/reports', component:PortfolioDashboard},
  {path:'/member/workbench', component:MemberWorkbench}
]})

router.beforeEach((to) => {
  if (import.meta.env.VITE_DEMO_MODE !== 'true' && to.path !== '/login' && !localStorage.getItem('zhuatech_pms_token')) {
    return '/login'
  }
})

export default router
