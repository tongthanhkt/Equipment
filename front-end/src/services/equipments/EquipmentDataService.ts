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
  addData(data: any) {
    return http.post("/add", data);
  }
  getAllEquipments() {
    return http.get("search?size=10");
  }
  updateEquipment(data: any) {
    return http.put("/update", data);
  }
  getEquipmentDetail(id: any) {
    return http.get(`/${id}`);
  }
  deleteEquipment(id: String) {
    return http.delete(`/delete?id=${id}`);
  }
  searchEquipment(keyword: String) {
    return http.get(`/search?keyword=${keyword}&size=10`);
  }
}
export default new EquipmentDataService();
