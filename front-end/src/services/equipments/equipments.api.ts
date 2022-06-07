import axios from "axios";
const axiosEquipment = axios.create({
  baseURL: "http://localhost:8887/equipment",
});
// export default async function getAllEquipments() {
//   try {
//     const res = await axiosEquipment.get("/search");
//     return res;
//   } catch (error) {
//     console.log(error);
//   }
// }
class EquipmentService {
  getAllEquipment() {
    return axiosEquipment.get("/search");
  }
  addEquipment() {
    const data = {
      category_id: "1",
      created_by: "tatthanh@rever.vn",
      created_time: "51532155215421 ",
      depreciated_value: "1",
      depreciation_period: "1",
      device_id: "ab",
      device_status: "1",
      import_date: "1520215210502",
      name: "a",
      period_type: "1",
      price: "1",
      start_status: "1",
      takeover_status: "1",
    };
    return axiosEquipment.post("/add", data);
  }
}
export default new EquipmentService();
