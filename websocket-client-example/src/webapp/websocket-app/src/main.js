import Vue from 'vue'
import App from './App.vue'
//import axios from 'axios'
import Router from 'router'
import Vuex from 'vuex'
import Im from "@/view/Im";

Vue.config.productionTip = false

// 注册 Vuex 插件
Vue.use(Vuex)
Vue.use(Router)

new Vue({
  render: h => h(App),
}).$mount('#app')

// 创建 Vuex Store 实例
// const store = new Vuex.Store({
//   state: { services: [] }, // 初始化状态对象
//   mutations: { setServices(state, services) { state.services = services } }, // 更新状态对象
//   actions: { async fetchServices({ commit }) { // 异步请求服务列表数据
//       const response = await axios.get('/actuator/nacos/discovery') // 发送 GET 请求
//       const services = response.data.instances.map(instance => instance.ip + ':' + instance.port) // 解析响应数据
//       commit('setServices', services) // 提交更新状态对象请求
//     } } })

// 导出 Store 实例
export default new Router({
    routes: [
        // 其他路由配置
        {
            path: './view',
            name: 'Im',
            component: Im
        }
    ]
})
