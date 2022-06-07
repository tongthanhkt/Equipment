import http from "@/http-common";
class EquipmentDataService {
  add() {
    const data = {
      category_id: "1",
      created_by: "tatthanh@rever.vn",
      created_time: "51532155215421 ",
      depreciated_value: "1",
      depreciation_period: "1",
      device_id: "abcd",
      device_status: "1",
      import_date: "1520215210502",
      name: "abc",
      period_type: "1",
      price: "1",
      start_status: "1",
      takeover_status: "1",
    };
    console.log(data);
    return http.post("/add", data);
  }
  getEquipment() {
    return http.get("search");
  }
}
export default new EquipmentDataService();
