import http from "@/http-common";
class CategoryService {
  getAllCategories(queryParams: string | null) {
    return http.get(`/category/search?${queryParams}`);
  }
  addCategory(data: any) {
    return http.post(`/category/add`, data);
  }
  updateCategory(data: any) {
    return http.put(`/category/update`, data);
  }
  getCategoryDetail(id: any) {
    return http.get(`/category/${id}`);
  }
  deleteCategory(id: number) {
    return http.delete(`/category/delete?id=${id}`);
  }
}
export default new CategoryService();
