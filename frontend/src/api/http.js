/* Copyright 2026 上海如静知华信息科技有限公司 */
import axios from 'axios'

const http = axios.create({baseURL: '/api', timeout: 12000})

http.interceptors.request.use((config) => {
  const token = localStorage.getItem('zhuatech_pms_token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

export async function login(username, password) {
  const response = await http.post('/auth/login', {username, password})
  const token = response.data?.data?.token
  if (!token) throw new Error('登录响应缺少访问令牌')
  localStorage.setItem('zhuatech_pms_token', token)
  return response.data.data
}

export default http
