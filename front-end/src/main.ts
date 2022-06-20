import { createApp } from "vue";
import "./assets/tailwind.css";
import App from "./App.vue";
import router from "./router";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome"
import {library} from "@fortawesome/fontawesome-svg-core"
import {fas} from "@fortawesome/free-solid-svg-icons"

library.add(fas)

const app = createApp(App);
app.component('fa',FontAwesomeIcon).use(router).mount("#app");
