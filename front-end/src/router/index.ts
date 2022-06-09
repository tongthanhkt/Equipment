import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import EquipmentsList from "../views/EquipmentsList.vue";
import AddEquipment from "../views/AddEquipment.vue";
import Tables from "../views/Tables.vue";
import UIElements from "../views/UIElements.vue";
import Login from "../views/Login.vue";
import Modal from "../views/Modal.vue";
import Chart from "../views/ChartView.vue";
import Card from "../views/CardView.vue";
import UploadImage from "../views/UploadImage.vue";
import NotFound from "../views/NotFound.vue";
import EditEquipment from "../views/EditEquipment.vue";
import EquipmentDetail from "../views/EquipmentDetail.vue";
const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    component: EquipmentsList,
  },

  {
    path: "/equipments",
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

  // {
  //   path: "/upload_image",
  //   name: "UploadImage",
  //   component: UploadImage,
  // },
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
