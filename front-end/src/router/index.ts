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
<<<<<<< HEAD
=======
import DetailEquipment from "../views/DetailEquipment.vue"
import AddTakeOver from "../views/AddTakeOver.vue"
>>>>>>> origin/master
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
<<<<<<< HEAD
=======
  {
    path: "/add-takeover",
    name: "AddTakeOver",
    component: AddTakeOver,
  },
  {
    path: "/detail-equipment",
    name: "DetailEquipment",
    component: DetailEquipment,
  },
>>>>>>> origin/master

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
