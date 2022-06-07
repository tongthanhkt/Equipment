// import axios from "axios";
// const axiosEquipment = axios.create({
//   baseURL: "http://localhost:8887/equipment",
//   headers: {
//     "Content-type": "application/json",
//   },
//   // headers: {
//   //   "Access-Control-Allow-Origin": "*",
//   //   "Content-Type": "application/json",
//   // },
//   // withCredentials: true,
// });
// export default async function getAllEquipments() {
//   try {
//     const res = await axiosEquipment.get("/search");
//     return res;
//   } catch (error) {
//     console.log(error);
//   }
// }
import http from "@/http-common";
class EquipmentService {
  getAllEquipment() {
    return http.get("/search");
  }
  addEquipment(data: object) {
    console.log(data);
    return http.post("/add", data);
  }
  deleteEquipment(equipment_id: any) {
    return http.delete("/delete", equipment_id);
  }
}
export default new EquipmentService();
