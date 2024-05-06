/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */
// Plugins
import {registerPlugins} from '@/plugins'
// Components
import 'highlight.js/styles/stackoverflow-light.css'
import hljs from "highlight.js/lib/core";
import hljsVuePlugin from "@highlightjs/vue-plugin";
//import "highlight.js/lib/common"; //单一加载
import cpp from "highlight.js/lib/languages/cpp";
import App from './App.vue'
import {createApp} from 'vue'

hljs.registerLanguage("cpp", cpp);
const app = createApp(App)
registerPlugins(app)
app.use(hljsVuePlugin).mount('#app')
