<style scoped>
.pagination {
  list-style-type: none;
}

.pagination-item {
  display: inline-block;
}

.active {
  background-color: #4aae9b;
  color: #ffffff;
}

.action-hover:hover {
  background-color: beige !important;
  cursor: pointer;
}
</style>
<template>
  <div class="px-3">
    <div class="flex flex-wrap -mx-6 my-6">
      <div class="w-full px-6 sm:w-1/2 xl:w-1/5">
        <div class="flex flex-row">
          <router-link to="/category">
            <a
              class="transition duration-300 ease-in-out bg-stone-700 text-white font-bold py-2 px-4 rounded w-75px mt-1 block py-2 px-3 w-36">
              Danh mục
            </a>
          </router-link>
          <select
            class="mt-1 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            v-model="categoryId" @change="filterCategory(categoryId)" required>
            <option v-bind:value="0">Tất cả</option>
            <option v-for="(category, index) in categories" v-bind:value="category.id">
              {{ category.name }}
            </option>
          </select>
        </div>
      </div>
      <div class="w-full px-6 sm:w-1/2 xl:w-1/5">
        <router-link to="/add-equipment">
          <a class="sm:w-48 bg-stone-700 text-white font-bold py-2 px-4 rounded mt-1 block py-2 px-3">
            Tạo mới thiết bị
          </a>
        </router-link>
      </div>
      <div class="w-full px-6 sm:w-1/2 xl:w-1/5">
        <div class="btn-search relative lg:mx-0 rounded-full">
          <span class="absolute inset-y-0 left-0 flex items-center pl-3">
            <svg class="w-5 h-5 text-gray-500" viewBox="0 0 24 24" fill="none">
              <path
                d="M21 21L15 15M17 10C17 13.866 13.866 17 10 17C6.13401 17 3 13.866 3 10C3 6.13401 6.13401 3 10 3C13.866 3 17 6.13401 17 10Z"
                stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
            </svg>
          </span>

          <input
            class="pl-10 pr-4 py-2 border-gray-200 rounded-md sm:w-48 focus:border-indigo-600 focus:ring focus:ring-opacity-40 focus:ring-indigo-500"
            type="text" placeholder="Thiết bị" v-model="keyword" v-on:input="searchEquipments()" />
        </div>
      </div>
      <div class="w-full px-6 sm:w-1/2 xl:w-1/5">
        <div class="ml-10 btn-search relative lg:mx-0 rounded-full">
          <span class="absolute inset-y-0 left-0 flex items-center pl-3">
            <svg class="h-5 text-gray-500" viewBox="0 0 24 24" fill="none">
              <path
                d="M21 21L15 15M17 10C17 13.866 13.866 17 10 17C6.13401 17 3 13.866 3 10C3 6.13401 6.13401 3 10 3C13.866 3 17 6.13401 17 10Z"
                stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
            </svg>
          </span>

          <input
            class="pl-10 pr-4 py-2 border-gray-200 rounded-md sm:w-48 focus:border-indigo-600 focus:ring focus:ring-opacity-40 focus:ring-indigo-500"
            type="text" placeholder="Người sử dụng" v-model="takeOverPerson" v-on:input="searchTakeOverPerson()" />
        </div>
      </div>
    </div>
    <div class="flex flex-wrap -mx-6">
      <div class="w-full px-6 sm:w-1/2 xl:w-1/5">
        <div class="flex items-center py-6 bg-white rounded-md shadow-sm action-hover"
          @click="filterOverView(null, null)">
          <div class="mx-5">
            <h4 class="text-2xl font-semibold text-gray-700">
              {{ sumOfEquipments }}
            </h4>
            <div class="text-gray-500">Tổng thiết bị</div>
          </div>
        </div>
      </div>
      <div class="w-full px-6 sm:w-1/2 xl:w-1/5">
        <div class="flex items-center py-6 bg-white rounded-md shadow-sm action-hover" @click="filterOverView(1, null)">
          <div class="mx-5">
            <h4 class="text-2xl font-semibold text-gray-700">
              {{ sumOfTakeOverEquipment }}
            </h4>
            <div class="text-gray-500">Tổng bàn giao</div>
          </div>
        </div>
      </div>
      <div class="w-full px-6 sm:w-1/2 xl:w-1/5">
        <div class="flex items-center py-6 bg-white rounded-md shadow-sm action-hover" @click="filterOverView(0, null)">
          <div class="mx-5">
            <h4 class="text-2xl font-semibold text-gray-700">
              {{ sumOfInventoryEquipment }}
            </h4>
            <div class="text-gray-500">Tổng tồn kho</div>
          </div>
        </div>
      </div>
      <div class="w-full px-6 sm:w-1/2 xl:w-1/5">
        <div class="flex items-center py-6 bg-white rounded-md shadow-sm action-hover" @click="filterOverView(null, 2)">
          <div class="mx-5">
            <h4 class="text-2xl font-semibold text-gray-700">
              {{ sumOfDamagedEquipment }}
            </h4>
            <div class="text-gray-500">Tổng hư hỏng</div>
          </div>
        </div>
      </div>
      <div class="w-full px-6 sm:w-1/2 xl:w-1/5">
        <div class="flex items-center py-6 bg-white rounded-md shadow-sm action-hover" @click="filterOverView(null, 0)">
          <div class="mx-5">
            <h4 class="text-2xl font-semibold text-gray-700">
              {{ sumOfCompensationEquipment }}/
              {{ sumOfLostEquipment }}
            </h4>
            <div class="text-gray-500">Tổng chưa đền bù / bị mất</div>
          </div>
        </div>
      </div>
    </div>
    <div class="flex flex-col mt-8">
      <div class="py-2 -my-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
        <div class="inline-block min-w-full overflow-hidden align-middle border-b border-gray-200 shadow sm:rounded-lg">
          <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
            <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
              <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                  <th scope="col" class="px-6 py-3">Mã thiết bị</th>
                  <th scope="col" class="px-6 py-3">Tên thiết bị</th>
                  <th scope="col" class="px-6 py-3">Trạng thái bàn giao</th>
                  <th scope="col" class="px-6 py-3">Người sử dụng</th>
                  <th scope="col" class="px-6 py-3">Người tạo</th>
                  <th scope="col" class="px-6 py-3">Thời gian nhận</th>
                  <th scope="col" class="px-6 py-3">Thời gian cập nhật</th>
                  <th scope="col" class="px-6 py-3">Người cập nhật</th>
                  <th scope="col" class="px-6 py-3">Trạng thái thiết bị</th>
                  <th scope="col" class="px-6 py-3"></th>
                </tr>
              </thead>
              <tbody>
                <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
                  v-for="(equipment, index) in equipments" :key="index">
                  <td scope="row"
                    class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap action-hover"
                    @click.prevent="detailEquipment(equipment.id)">
                    {{ equipment.device_id }}
                  </td>
                  <td class="px-6 py-4">{{ equipment.name }}</td>
                  <td class="px-6 py-4 font-bold">
                    <p class="text-orange-700" v-if="equipment.take_over_status == '0'">
                      {{ takeOverStatusEnum[equipment.take_over_status] }}
                    </p>
                    <p class="text-green-500" v-else-if="equipment.take_over_status == '1'">
                      {{ takeOverStatusEnum[equipment.take_over_status] }}
                    </p>
                  </td>
                  <td class="px-6 py-4">
                    {{ equipment.take_over_person_name }}
                  </td>
                  <td class="px-6 py-4">{{ equipment.created_by }}</td>
                  <td class="px-6 py-4">{{ equipment.import_date }}</td>
                  <td class="px-6 py-4">{{ equipment.updated_time }}</td>
                  <td class="px-6 py-4">{{ equipment.updated_by }}</td>
                  <td class="px-6 py-4 font-bold">
                    <div class="flex items-center">
                      <div class="ml-4">
                        <div class="text-sm leading-5 text-gray-500">
                          <p class="text-orange-700" v-if="equipment.device_status == '0'">
                            {{ deviceStatusEnum[equipment.device_status] }}
                          <p v-if="equipment.compensation_status == '0'"> chưa đền bù</p>
                          <p v-if="equipment.compensation_status == '1'"> đã đền bù</p>
                          </p>
                          <p class="text-green-500" v-else-if="equipment.device_status == '1'">
                            {{ deviceStatusEnum[equipment.device_status] }}
                          </p>
                          <p class="text-amber-500" v-else-if="equipment.device_status == '2'">
                            {{ deviceStatusEnum[equipment.device_status] }}
                          </p>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4 text-right">
                    <div>
                      <span class="flex justify-center">
                        <a class="mx-2 px-2 rounded-md action-hover" @click.prevent="editEquipment(equipment.id)">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-green-700" viewBox="0 0 20 20"
                            fill="currentColor">
                            <path d="M17.414 2.586a2 2 0 00-2.828 0L7 10.172V13h2.828l7.586-7.586a2 2 0 000-2.828z" />
                            <path fill-rule="evenodd"
                              d="M2 6a2 2 0 012-2h4a1 1 0 010 2H4v10h10v-4a1 1 0 112 0v4a2 2 0 01-2 2H4a2 2 0 01-2-2V6z"
                              clip-rule="evenodd" />
                          </svg>
                        </a>

                        <button class="mx-2 px-2 rounded-md list_equipments" @click="
                          deleteEquipment(
                            equipment.id!,
                            equipment.take_over_status!
                          )
                        ">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-red-700" viewBox="0 0 20 20"
                            fill="currentColor">
                            <path fill-rule="evenodd"
                              d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z"
                              clip-rule="evenodd" />
                          </svg>
                        </button>
                      </span>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div>
            <nav class="flex justify-center inline-block">
              <ul class="flex -space-x-px">
                <li>
                  <a type="button" @click="onClickFirstPage"
                    class="py-2 px-3 text-blue-600 bg-blue-50 border border-gray-300 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white">First</a>
                </li>
                <li>
                  <a type="button" @click="onClickPreviousPage"
                    class="py-2 px-3 text-blue-600 bg-blue-50 border border-gray-300 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white">Preivous</a>
                </li>

                <li>
                  <a type="button" aria-current="page"
                    class="py-2 px-3 text-blue-600 bg-blue-50 border border-gray-300 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white">{{
                        currentPage
                    }}</a>
                </li>
                <li>
                  <a type="button" @click="onClickNextPage"
                    class="py-2 px-3 text-blue-600 bg-blue-50 border border-gray-300 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white">Next</a>
                </li>

                <li>
                  <a type="button" @click="onClickLastPage"
                    class="py-2 px-3 text-blue-600 bg-blue-50 border border-gray-300 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white">Last</a>
                </li>
              </ul>
              <ul class="flex -space-x-px text-gray-500 m-2 text-sm inline-block">
                Tổng số trang:
                {{
                    totalPages
                }}
              </ul>
            </nav>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Equipment from "@/types/Equipment";
import EquipmentDataService from "../services/equipments/EquipmentDataService";
import CategoryService from "../services/category/categoryService";
import { Vue, Options } from "vue-property-decorator";
import Pagination from "./Pagination.vue";
import Category from "@/types/Category";
@Options({
  components: {
    Pagination,
  },
})
export default class Dashboard extends Vue {
  public categories: Category[] = [];
  public deviceStatusEnum = {
    0: "Bị Mất",
    1: "Sử dụng được",
    2: "Bị hư hỏng",
    3: "Đã bán cho nhân viên",
  };
  public takeOverStatusEnum = {
    0: "Tồn kho",
    1: "Bàn giao",
  };

  public equipments: Equipment[] = [];
  public sumOfTakeOverEquipment: number = 0;
  public sumOfEquipments: number = 0;
  public sumOfInventoryEquipment: number = 0;
  public sumOfDamagedEquipment: number = 0;
  public sumOfLostEquipment: number = 0;
  public sumOfCompensationEquipment: number = 0;
  public currentPage: number = 1;
  public currentLimit: number = 10;
  public currentCategoryId: number | null = null;
  public keyword?: string | null = null;
  public takeOverPerson: string | null = null;
  public deviceStatus: number | null = null;
  public takeOverStatus: number | null = null;
  public totalPages: number = 0;
  public queryParams: any;
  public categoryId = 0;
  async mounted() {
    this.retrieveEquipments();
    this.retrieveOverview();
    this.retrieveCategories();
  }
  async retrieveCategories() {
    CategoryService.getAllCategories(this.queryParams).then((res: any) => {
      this.categories = res.data.categories;
    });
  }
  async retrieveOverview() {
    EquipmentDataService.getCountTotal(this.getQueryParams()).then((res) => {
      console.log(res);
      this.sumOfEquipments = res.data.total_equipments;
      this.sumOfDamagedEquipment = res.data.total_damaged_equipments;
      this.sumOfInventoryEquipment = res.data.total_inventory_equipments;
      this.sumOfTakeOverEquipment = res.data.total_take_over_equipments;
      this.sumOfLostEquipment = res.data.total_lost_equipments;
      this.sumOfCompensationEquipment = res.data.total_compensation_equipments;
    });
  }
  async retrieveEquipments() {
    EquipmentDataService.getAllEquipments(this.getQueryParams())
      .then((res: any) => {
        this.totalPages = res.data.n_pages;
        this.equipments = res.data.equipments;
        console.log(this.equipments);
      })
      .then(() => {
        this.handleFieldEquipment();
      });
  }
  handleFieldEquipment() {
    for (let i = 0; i < this.equipments.length; i++) {
      if (this.equipments[i].updated_time != null) {
        this.equipments[i].updated_time = this.handleImportDate(
          this.equipments[i].updated_time!
        );
      }
      if (this.equipments[i].import_date != null) {
        this.equipments[i].import_date = this.handleImportDate(
          this.equipments[i].import_date!
        );
      }
    }
  }
  getQueryParams() {
    this.queryParams = {
      page: this.currentPage,
      limit: this.currentLimit,
      category_id: this.currentCategoryId,
      keyword: this.keyword,
      take_over_person: this.takeOverPerson,
      take_over_status: this.takeOverStatus,
      device_status: this.deviceStatus,
    };

    Object.keys(this.queryParams).forEach((key) => {
      if (
        this.queryParams[key] === null ||
        this.queryParams[key] === undefined
      ) {
        delete this.queryParams[key];
      }
    });
    this.$router.push({ name: "search", query: this.queryParams });

    let queryParams = "";
    const temp = Object.entries(this.queryParams);
    for (let i = 0; i < temp.length; i++) {
      if (i < temp.length - 1) {
        let param = temp[i][0] + "=" + temp[i][1] + "&";
        queryParams += param;
      } else {
        let param = temp[i][0] + "=" + temp[i][1];
        queryParams += param;
      }
    }
    return queryParams;
  }
  editEquipment(id: any) {
    this.$router.push({ name: "update", params: { id: id } });
  }
  detailEquipment(id: any) {
    this.$router.push({ name: "DetailEquipment", params: { id: id } });
  }
  deleteEquipment(id: String, takeOverStatus: string) {
    let queryParams = `page=1`;
    if (takeOverStatus == "1") {
      alert("Không thể xóa thiết bị vì thiết bị đang được bàn giao ");
    } else {
      if (confirm("Bạn có chắc chắn muốn xóa thiết bị này ?")) {
        EquipmentDataService.deleteEquipment(id)
          .then((res) => console.log("Delete Successfully !!"))
          .then(() => this.retrieveEquipments())
          .catch((err) => console.log(err));
      }
    }
  }
  filterOverView(takeOverStatus: number | null, deviceStatus: number | null) {
    this.takeOverStatus = takeOverStatus;

    this.deviceStatus = deviceStatus;

    this.retrieveEquipments();
  }
  async filterCategory(categoryId: number) {
    this.currentPage = 1;
    this.currentLimit = 5;
    this.keyword = null;
    this.takeOverPerson = null;
    this.takeOverStatus = null;
    this.deviceStatus = null;
    if (categoryId == 0) {
      this.currentCategoryId = null;
    } else {
      this.currentCategoryId = categoryId;
    }
    this.retrieveEquipments();
    this.retrieveOverview();
  }
  async onClickFirstPage() {
    this.currentPage = 1;
    this.retrieveEquipments();
  }
  onClickNextPage() {
    if (this.currentPage != this.totalPages) {
      this.currentPage++;
      this.retrieveEquipments();
    }
  }
  onClickPreviousPage() {
    if (this.currentPage != 1) {
      this.currentPage--;
      this.retrieveEquipments();
    }
  }
  onClickLastPage() {
    this.currentPage = this.totalPages;
    this.retrieveEquipments();
  }


  handleImportDate(data: string) {
    var d = new Date(parseInt(data));
    return d.toLocaleString();
  }
  searchEquipments() {
    this.currentPage = 1;
    if (this.keyword == "") {
      this.keyword = null;
    }
    this.retrieveEquipments()
      .then((res) => { })
      .catch((err) => alert("Lỗi tìm kiếm"));
  }

  searchTakeOverPerson() {
    this.currentPage = 1;
    if (this.takeOverPerson == "") {
      this.takeOverPerson = null;
    }
    this.retrieveEquipments()
      .then((res) => { })
      .catch((err) => alert("Lỗi tìm kiếm"));
  }
}
</script>
