import {
  createRouter,
  createWebHistory,
  RouteRecordRaw,
  createWebHashHistory,
} from "vue-router";
import EquipmentsList from "../views/EquipmentsList.vue";
import AddEquipment from "../views/AddEquipment.vue";
import UploadImage from "../views/UploadImage.vue";
import EditEquipment from "../views/EditEquipment.vue";
import HomeLayout from "../components/HomeLayout.vue";
import DetailEquipment from "../views/DetailEquipment.vue";
import AddTakeOver from "../views/AddTakeOver.vue";
const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    component: EquipmentsList,
  },
  {
    path: "/equipment/",
    name: "search",
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
  {
    path: "/add-takeover",
    name: "AddTakeOver",
    component: AddTakeOver,
  },
  {
    path: "/detail-equipment/:id",
    name: "DetailEquipment",
    component: DetailEquipment,
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
  history: createWebHashHistory(),

  routes,
});

export default router;
