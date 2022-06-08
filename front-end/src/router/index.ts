import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import EquipmentsList from "../views/EquipmentsList.vue";
import AddEquipment from "../views/AddEquipment.vue";
import Tables from "../views/Tables.vue";
import UIElements from "../views/UIElements.vue";
import Login from "../views/Login.vue";
import Modal from "../views/Modal.vue";
import Chart from "../views/ChartView.vue";
import Card from "../views/CardView.vue";
import Blank from "../views/BlankView.vue";
import NotFound from "../views/NotFound.vue";
import EditEquipment from "../views/EditEquipment.vue";
import EquipmentDetail from "../views/EquipmentDetail.vue";
const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Login",
    component: EquipmentsList,
  },

  {
    path: "/dashboard",
    name: "EquipmentsList",
    component: EquipmentsList,
  },
  {
    path: "/add-equipment",
    name: "add equipment",
    component: AddEquipment,
  },
  {
    path: "/edit-equipment",
    name: "EditEquipment",
    component: EditEquipment,
  },
  {
    path: "/cards",
    name: "Cards",
    component: Card,
  },
  {
    path: "/tables",
    name: "Tables",
    component: Tables,
  },
  {
    path: "/ui-elements",
    name: "UIElements",
    component: UIElements,
  },
  {
    path: "/modal",
    name: "Modal",
    component: Modal,
  },
  {
    path: "/charts",
    name: "Chart",
    component: Chart,
  },
  {
    path: "/blank",
    name: "Blank",
    component: Blank,
  },
  { path: "/:pathMatch(.*)*", component: NotFound },
  {
    path: "/equipments/update/:id",
    name: "update",
    component: EditEquipment,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
