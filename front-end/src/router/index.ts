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
import TakeOverHistory from "../views/TakeOverHistory.vue";
import TakeBackHistory from "../views/TakeBackHistory.vue";
import History from "../views/History.vue";
import FixEquipmentHistory from "../views/FixEquipmentHistory.vue";
import CategoryList from "../views/CategoryList.vue";
import StatisticCategory from "../views/StatisticCategory.vue";
import Historical from "../views/Historical.vue"

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
    path: "/statistic-category",
    name: "StatisticCategory",
    component: StatisticCategory,
  },
  {
    path: "/equipments",
    name: "EquipmentsList",
    component: EquipmentsList,
  },
  {
    path: "/category",
    name: "Category",
    component: CategoryList,
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
  {
    path: "/historical",
    name: "Historical",
    component: Historical,
  },

  {
    path: "/takeover-history",
    name: "TakeOverHistory",
    component: TakeOverHistory,
  },
  {
    path: "/takeback-history",
    name: "TakeBackHistory",
    component: TakeBackHistory,
  },
  {
    path: "/history",
    name: "History",
    component: History,
  },
  {
    path: "/equipments/update/:id",
    name: "update",
    component: EditEquipment,
  },
  {
    path: "/fix-equipment",
    name: "FixEquipment",
    component: FixEquipmentHistory,
  },
];

const router = createRouter({
  history: createWebHashHistory(),

  routes,
});

export default router;
