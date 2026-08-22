<!-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ -->
<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import {login} from '../api/http'

const router = useRouter()
const username = ref('admin')
const password = ref('admin123')
const submitting = ref(false)
const errorMessage = ref('')

async function submit() {
  errorMessage.value = ''
  submitting.value = true
  try {
    if (import.meta.env.VITE_DEMO_MODE === 'true') {
      localStorage.setItem('zhuatech_pms_token', 'demo-mode')
    } else {
      await login(username.value, password.value)
    }
    await router.push('/admin/portfolio')
  } catch (error) {
    errorMessage.value = error.response?.data?.message || '登录失败，请检查账号、密码和后端服务'
  } finally {
    submitting.value = false
  }
}
</script>
<template>
  <div class="login-page">
    <section class="login-story">
      <div class="login-brand"><span>ZH</span><div><b>ZhuaTech PMS</b><small>企业项目管理系统</small></div></div>
      <div class="story-copy"><p>CLARITY FOR EVERY DELIVERY</p><h1>让目标、进度、风险<br/>始终处于同一视图。</h1><span>从项目立项到验收复盘，连接计划、里程碑、任务、资源与工时，让团队围绕真实交付协作。</span></div>
      <div class="story-metric"><div><b>24</b><span>在执行项目</span></div><div><b>92.6%</b><span>里程碑准时率</span></div><div><b>18.2M</b><span>在管项目预算</span></div></div>
      <footer>上海如静知华信息科技有限公司 · www.zhuatech.cn</footer>
    </section>
    <form @submit.prevent="submit">
      <p>欢迎回来</p><h2>登录项目运营中心</h2><label>账号<input v-model="username" autocomplete="username"/></label><label>密码<input v-model="password" type="password" autocomplete="current-password"/></label>
      <div class="form-meta"><span>☑ 保持登录</span><a>忘记密码？</a></div><p v-if="errorMessage" class="login-error">{{errorMessage}}</p><button class="button primary" :disabled="submitting">{{submitting ? '正在验证…' : '进入系统'}}</button><small>演示账号：admin / admin123</small>
    </form>
  </div>
</template>
