import http from "@/http-common";
class StatisticCategoryService {
  getAllStatisticCategory(queryParams: string) {
    return http.get(`/statistic?${queryParams}`);
  }
}
export default new StatisticCategoryService();
