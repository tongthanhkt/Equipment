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
.list_equipments:hover {
  background-color: beige;
  cursor: pointer;
}
</style>
<template>
  <div class="px-3">
    <div class="h-24">
      <div class="inline-block w-full sm:w-1/2 xl:w-1/4">
        <div class="btn-search relative mx-4 lg:mx-0 rounded-full">
          <span class="absolute inset-y-0 left-0 flex items-center pl-3">
            <svg class="w-5 h-5 text-gray-500" viewBox="0 0 24 24" fill="none">
              <path
                d="M21 21L15 15M17 10C17 13.866 13.866 17 10 17C6.13401 17 3 13.866 3 10C3 6.13401 6.13401 3 10 3C13.866 3 17 6.13401 17 10Z"
                stroke="currentColor"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </span>

          <input
            class="pl-10 pr-4 py-2 border-gray-200 rounded-md sm:w-48 focus:border-indigo-600 focus:ring focus:ring-opacity-40 focus:ring-indigo-500"
            type="text"
            placeholder="Thiết bị"
            v-model="this.keyword"
            v-on:input="searchEquipments()"
          />
        </div>
      </div>
      <div class="inline-block w-full sm:w-1/2 xl:w-1/4">
        <div class="btn-search relative mx-4 lg:mx-0 rounded-full">
          <span class="absolute inset-y-0 left-0 flex items-center pl-3">
            <svg class="w-5 h-5 text-gray-500" viewBox="0 0 24 24" fill="none">
              <path
                d="M21 21L15 15M17 10C17 13.866 13.866 17 10 17C6.13401 17 3 13.866 3 10C3 6.13401 6.13401 3 10 3C13.866 3 17 6.13401 17 10Z"
                stroke="currentColor"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </span>

          <input
            class="pl-10 pr-4 py-2 border-gray-200 rounded-md sm:w-48 focus:border-indigo-600 focus:ring focus:ring-opacity-40 focus:ring-indigo-500"
            type="text"
            placeholder="Người sử dụng"
            v-model="this.takeOverPerson"
            v-on:input="searchTakeOverPerson()"
          />
        </div>
      </div>

      <div class="inline-block w-full sm:w-1/2 xl:w-1/4">
        <div class="inline-block category">
          <div class="relative">
            <div class="flex flex-row ml-10">
              <a
                class="bg-stone-700 text-white font-bold py-2 px-4 rounded w-75px mt-1 block py-2 px-3 w-36"
              >
                Danh mục
              </a>
              <select
                class="w-75px mt-1 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                v-model="categoryId"
                @change="filterCategory(categoryId)"
                required
                name=""
              >
                <option v-bind:value="0" selected>Tất cả</option>
                <option
                  v-for="(category, index) in categories"
                  v-bind:value="category.value"
                >
                  {{ category.name }}
                </option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <router-link to="/add-equipment">
        <div class="inline-block w-full px-8 sm:w-1/2 xl:w-1/4">
          <a class="bg-stone-700 text-white font-bold py-2 px-4 rounded">
            Tạo mới thiết bị
          </a>
        </div>
      </router-link>
    </div>
    <div class="flex flex-wrap -mx-6">
      <div class="w-full px-6 sm:w-1/2 xl:w-1/4">
        <div class="flex items-center px-5 py-6 bg-white rounded-md shadow-sm">
          <div class="mx-5">
            <h4 class="text-2xl font-semibold text-gray-700">
              {{ this.sumOfEquipments }}
            </h4>
            <div class="text-gray-500">Tổng thiết bị</div>
          </div>
        </div>
      </div>
      <div class="w-full px-6 sm:w-1/2 xl:w-1/4">
        <div class="flex items-center px-5 py-6 bg-white rounded-md shadow-sm">
          <div class="mx-5">
            <h4 class="text-2xl font-semibold text-gray-700">
              {{ this.sumOfTakeOverEquipment }}
            </h4>
            <div class="text-gray-500">Tổng bản giao</div>
          </div>
        </div>
      </div>
      <div class="w-full px-6 sm:w-1/2 xl:w-1/4">
        <div class="flex items-center px-5 py-6 bg-white rounded-md shadow-sm">
          <div class="mx-5">
            <h4 class="text-2xl font-semibold text-gray-700">
              {{ this.sumOfInventoryEquipment }}
            </h4>
            <div class="text-gray-500">Tổng tồn kho</div>
          </div>
        </div>
      </div>
      <div class="w-full px-6 sm:w-1/2 xl:w-1/4">
        <div class="flex items-center px-5 py-6 bg-white rounded-md shadow-sm">
          <div class="mx-5">
            <h4 class="text-2xl font-semibold text-gray-700">
              {{ this.sumOfDamagedEquipment }}
            </h4>
            <div class="text-gray-500">Tổng hư hỏng</div>
          </div>
        </div>
      </div>
    </div>
    <div class="flex flex-col mt-8">
      <div class="py-2 -my-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
        <div
          class="inline-block min-w-full overflow-hidden align-middle border-b border-gray-200 shadow sm:rounded-lg"
        >
          <table class="-w-full">
            <thead>
              <tr>
                <th
                  class="w-18 py-3 text-xs font-medium leading-4 tracking-wider text-center text-gray-500 uppercase border-b border-gray-200 bg-gray-50"
                >
                  Mã thiết bị
                </th>
                <th
                  class="w-18 py-3 text-xs font-medium leading-4 tracking-wider text-center text-gray-500 uppercase border-b border-gray-200 bg-gray-50"
                >
                  Tên thiết bị
                </th>
                <th
                  class="py-3 text-xs font-medium leading-4 tracking-wider text-center text-gray-500 uppercase border-b border-gray-200 bg-gray-50"
                >
                  Trạng thái bàn giao
                </th>
                <th
                  class="w-36 py-3 text-xs font-medium leading-4 tracking-wider text-center text-gray-500 uppercase border-b border-gray-200 bg-gray-50"
                >
                  Người sử dụng
                </th>
                <th
                  class="px-6 py-3 text-xs font-medium leading-4 tracking-wider text-center text-gray-500 uppercase border-b border-gray-200 bg-gray-50"
                >
                  Người tạo
                </th>
                <th
                  class="px-6 py-3 text-xs font-medium leading-4 tracking-wider text-center text-gray-500 uppercase border-b border-gray-200 bg-gray-50"
                >
                  Thời gian nhận
                </th>
                <th
                  class="w-48 py-3 text-xs font-medium leading-4 tracking-wider text-center text-gray-500 uppercase border-b border-gray-200 bg-gray-50"
                >
                  Thời gian cập nhật
                </th>
                <th
                  class="px-6 py-3 text-xs font-medium leading-4 tracking-wider text-left text-gray-500 uppercase border-b border-gray-200 bg-gray-50"
                >
                  Người cập nhật
                </th>
                <th
                  class="w-48 py-3 text-xs font-medium leading-4 tracking-wider text-center text-gray-500 uppercase border-b border-gray-200 bg-gray-50"
                >
                  Trạng thái thiết bị
                </th>
                <th
                  class="px-6 py-3 text-xs font-medium leading-4 tracking-wider text-center text-gray-500 uppercase border-b border-gray-200 bg-gray-50"
                ></th>
              </tr>
            </thead>

            <tbody class="bg-white">
              <tr
                class="list_equipments"
                v-for="(equipment, index) in equipments"
                :key="index"
              >
                <td
                  class="px-6 py-4 border-b border-gray-200 whitespace-nowrap"
                >
                  <div class="flex items-center">
                    <div class="ml-4">
                      <div class="text-left text-sm leading-5 text-gray-500">
                        {{ equipment.device_id }}
                      </div>
                    </div>
                  </div>
                </td>
                <td
                  class="px-6 py-4 border-b border-gray-200 whitespace-nowrap"
                >
                  <div class="flex items-center">
                    <div class="ml-4">
                      <div class="text-sm leading-5 text-gray-500">
                        {{ equipment.name }}
                      </div>
                    </div>
                  </div>
                </td>
                <td
                  class="px-6 py-4 text-center border-b border-gray-200 whitespace-nowrap"
                >
                  <div class="flex items-center text-center">
                    <div class="ml-4">
                      <div class="text-sm leading-5 text-center text-gray-500">
                        {{
                          equipment.take_over_status == "1"
                            ? "Bàn giao"
                            : "Tồn kho"
                        }}
                      </div>
                    </div>
                  </div>
                </td>
                <td
                  class="px-6 py-4 border-b border-gray-200 whitespace-nowrap"
                >
                  <div class="flex items-center">
                    <div class="ml-4">
                      <div class="text-sm leading-5 text-gray-500">
                        {{ equipment.take_over_person_name }}
                      </div>
                    </div>
                  </div>
                </td>
                <td
                  class="px-6 py-4 border-b border-gray-200 whitespace-nowrap"
                >
                  <div class="flex items-center">
                    <div class="ml-4">
                      <div class="text-sm leading-5 text-gray-500">
                        {{ equipment.created_by }}
                      </div>
                    </div>
                  </div>
                </td>
                <td
                  class="px-6 py-4 border-b border-gray-200 whitespace-nowrap"
                >
                  <div class="flex items-center">
                    <div class="ml-4">
                      <div class="text-sm leading-5 text-gray-500">
                        {{ equipment.import_date }}
                      </div>
                    </div>
                  </div>
                </td>
                <td
                  class="px-6 py-4 border-b border-gray-200 whitespace-nowrap"
                >
                  <div class="flex items-center">
                    <div class="ml-4">
                      <div class="text-sm leading-5 text-gray-500">
                        {{ equipment.updated_time }}
                      </div>
                    </div>
                  </div>
                </td>
                <td
                  class="px-6 py-4 border-b border-gray-200 whitespace-nowrap"
                >
                  <div class="flex items-center">
                    <div class="ml-4">
                      <div class="text-sm leading-5 text-gray-500">
                        {{ equipment.updated_by }}
                      </div>
                    </div>
                  </div>
                </td>
                <td
                  class="px-6 py-4 border-b border-gray-200 whitespace-nowrap"
                >
                  <div class="flex items-center">
                    <div class="ml-4">
                      <div class="text-sm leading-5 text-gray-500">
                        <div v-if="equipment.device_status == '0'">Bị mất</div>
                        <div v-else-if="equipment.device_status == '1'">
                          Sử dụng được
                        </div>
                        <div v-else-if="equipment.device_status == '2'">
                          Bị hỏng
                        </div>
                        <div v-else>Not A/B/C</div>
                      </div>
                    </div>
                  </div>
                </td>
                <td>
                  <div class="flex justify-around w-48">
                    <span class="flex justify-center">
                      <button class="w-20 bg-sky-500">Bàn giao</button>

                      <a
                        class="mx-2 px-2 rounded-md"
                        @click.prevent="editEquipment(equipment.id)"
                      >
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          class="h-5 w-5 text-green-700"
                          viewBox="0 0 20 20"
                          fill="currentColor"
                        >
                          <path
                            d="M17.414 2.586a2 2 0 00-2.828 0L7 10.172V13h2.828l7.586-7.586a2 2 0 000-2.828z"
                          />
                          <path
                            fill-rule="evenodd"
                            d="M2 6a2 2 0 012-2h4a1 1 0 010 2H4v10h10v-4a1 1 0 112 0v4a2 2 0 01-2 2H4a2 2 0 01-2-2V6z"
                            clip-rule="evenodd"
                          />
                        </svg>
                      </a>

                      <button
                        class="mx-2 px-2 rounded-md"
                        @click="deleteEquipment(equipment.id)"
                      >
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          class="h-5 w-5 text-red-700"
                          viewBox="0 0 20 20"
                          fill="currentColor"
                        >
                          <path
                            fill-rule="evenodd"
                            d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z"
                            clip-rule="evenodd"
                          />
                        </svg>
                      </button>
                    </span>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <nav class="flex justify-center">
            <ul class="flex -space-x-px inline-block">
              <li>
                <a
                  type="button"
                  @click="onClickFirstPage"
                  class="py-2 px-3 ml-0 leading-tight text-gray-500 bg-white rounded-l-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  >First</a
                >
              </li>
              <li>
                <a
                  type="button"
                  @click="onClickPreviousPage"
                  class="py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  >Preivous</a
                >
              </li>

              <li>
                <a
                  type="button"
                  aria-current="page"
                  class="py-2 px-3 text-blue-600 bg-blue-50 border border-gray-300 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white"
                  >{{ currentPage }}</a
                >
              </li>
              <li>
                <a
                  type="button"
                  @click="onClickNextPage"
                  class="py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  >Next</a
                >
              </li>

              <li>
                <a
                  type="button"
                  @click="onClickLastPage"
                  class="py-2 px-3 leading-tight text-gray-500 bg-white rounded-r-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  >Last</a
                >
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Equipment from "@/types/Equipment";
import EquipmentDataService from "../services/equipments/EquipmentDataService";
import { Vue, Options } from "vue-property-decorator";
import Pagination from "./Pagination.vue";

@Options({
  components: {
    Pagination,
  },
})
export default class Dashboard extends Vue {
  public categories = [
    { value: 1, name: "Máy tính" },
    { value: 2, name: "Màn hình" },
    { value: 3, name: "Phụ kiện" },
  ];

  public equipments: Equipment[] = [];
  public sumOfTakeOverEquipment: number | undefined;
  public sumOfEquipments: number = 0;
  public sumOfInventoryEquipment: number = 0;
  public sumOfDamagedEquipment: number = 0;

  public currentPage: number = 1;
  public currentLimit: number = 5;
  public currentCategoryId: number | null = null;
  public keyword: string | null = null;
  public takeOverPerson: string | null = null;

  public totalPages: number = 0;
  public valueCategory: number = 1;
  public queryParams: any;
  async mounted() {
    this.retrieveEquipments(this.getQueryParams());
    EquipmentDataService.getCountTotal().then((res) => {
      this.sumOfEquipments = res.data.total_equipments;
      this.sumOfDamagedEquipment = res.data.total_damaged_equipments;
      this.sumOfInventoryEquipment = res.data.total_inventory_equipments;
      this.sumOfTakeOverEquipment = res.data.total_take_over_equipments;
    });
  }
  async retrieveEquipments(queryParams: string) {
    EquipmentDataService.getAllEquipments(queryParams)
      .then((res: any) => {
        console.log(res.data);
        this.totalPages = res.data.n_pages;
        this.equipments = res.data.equipments;
      })
      .then(() => {
        for (let i = 0; i < this.equipments.length; i++) {
          if (this.equipments[i].updated_time != null) {
            this.equipments[i].updated_time = this.handleImportDate(
              this.equipments[i].updated_time
            );
          }
          if (this.equipments[i].import_date != null) {
            this.equipments[i].import_date = this.handleImportDate(
              this.equipments[i].import_date
            );
          }

          this.equipments[i].category_name = this.handleCategoryEquipment(
            this.equipments[i].category_id
          );
        }
      });
  }

  getQueryParams() {
    this.queryParams = {
      page: this.currentPage,
      limit: this.currentLimit,
      category_id: this.currentCategoryId,
      keyword: this.keyword,
      take_over_person: this.takeOverPerson,
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
  deleteEquipment(id: String) {
    let queryParams = `page=1`;
    if (confirm("Bạn có chắc chắn muốn xóa thiết bị này ?")) {
      EquipmentDataService.deleteEquipment(id)
        .then((res) => console.log("Delete Successfully !!"))
        .then(() => this.retrieveEquipments(this.getQueryParams()))
        .catch((err) => console.log(err));
    }
  }
  async filterCategory(categoryId: number) {
    if (categoryId == 0 || categoryId == null) {
      this.currentCategoryId = null;

      this.currentPage = 1;
      this.retrieveEquipments(this.getQueryParams());
    } else {
      this.currentPage = 1;
      this.currentCategoryId = categoryId;
      this.retrieveEquipments(this.getQueryParams());
    }
  }
  async onClickFirstPage() {
    this.currentPage = 1;
    this.retrieveEquipments(this.getQueryParams());
  }
  onClickNextPage() {
    if (this.currentPage != this.totalPages) {
      this.currentPage++;
      this.retrieveEquipments(this.getQueryParams());
    }
  }
  onClickPreviousPage() {
    if (this.currentPage != 1) {
      this.currentPage--;
      this.retrieveEquipments(this.getQueryParams());
    }
  }
  onClickLastPage() {
    this.currentPage = this.totalPages;
    this.retrieveEquipments(this.getQueryParams());
  }

  handleDeviceStatus(device_status: String) {
    if (device_status == "0") {
      return "Bị mất";
    } else if (device_status == "1") {
      return "Sử dụng được";
    } else if (device_status == "2") {
      return "Bị hư hỏng";
    }
  }
  handleCategoryEquipment(category_id: string) {
    if (category_id == "1") {
      return "Máy tính";
    } else if (category_id == "2") {
      return "Màn hình";
    } else if (category_id == "3") {
      return "Phụ kiện";
    }
    return "null";
  }
  handleImportDate(data: string) {
    var d = new Date(parseInt(data));
    return d.toLocaleDateString();
  }

  searchEquipments() {
    if (this.keyword == "") {
      this.keyword = null;
    }
    this.retrieveEquipments(this.getQueryParams())
      .then((res) => {})
      .catch((err) => alert("Lỗi tìm kiếm"));
  }

  searchTakeOverPerson() {
    if (this.takeOverPerson == "") {
      this.takeOverPerson = null;
    }
    this.retrieveEquipments(this.getQueryParams())
      .then((res) => {})
      .catch((err) => alert("Lỗi tìm kiếm"));
  }
}
</script>
