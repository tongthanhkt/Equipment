<template>
  <div class="flex flex-col mx-64 my-16">
    <div class="flex flex-wrap justify-end">
      <p class="pt-2 font-medium text-3xl">NĂM</p>
      <div class="p-1 font-medium text-gray-700">
        <select
          @change="handleYear()"
          id="year"
          name="type"
          autocomplete="type-name"
          v-model="year"
          class="mx-1 px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded-md focus:outline-none text-black"
        >
          <option value="2022" selected disabled hidden>2022</option>
          <option v-for="item in years" :value="item.id">
            {{ item.name }}
          </option>
        </select>
      </div>
      <div class="p-1 font-medium text-gray-700">
        <select
          @change="handleQuater()"
          v-model="quater"
          id="quater"
          name="type"
          autocomplete="type-name"
          class="mx-1 px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded-md focus:outline-none text-black"
        >
          <option value="0" selected disabled hidden>Quý</option>
          <option v-for="item in quaters" :value="item.id">
            {{ item.name }}
          </option>
        </select>
      </div>
      <div class="p-1 font-medium text-gray-700">
        <select
          @change="handleMonth()"
          v-model="month"
          id="month"
          name="type"
          autocomplete="type-name"
          class="mx-1 px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded-md focus:outline-none text-black"
        >
          <option value="0" selected disabled hidden>Tháng</option>
          <option v-for="item in months" :value="item.id">
            {{ item.name }}
          </option>
        </select>
      </div>
    </div>
    <div class="py-2 -my-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
      <div
        class="inline-block min-w-full overflow-hidden align-middle border-b border-gray-200 shadow sm:rounded-lg"
      >
        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
          <table
            class="w-full text-sm text-left text-gray-500 dark:text-gray-400"
          >
            <thead
              class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400"
            >
              <tr>
                <th scope="col" class="px-6 py-3">Số thứ tự</th>
                <th scope="col" class="px-6 py-3">Danh mục</th>
                <th scope="col" class="px-6 py-3">Nhập mới</th>
                <th scope="col" class="px-6 py-3">Đã sửa chữa</th>
                <th scope="col" class="px-6 py-3">Được bàn giao</th>
                <th scope="col" class="px-6 py-3">Được thu hồi</th>
                <th scope="col" class="px-6 py-3">
                  Tổng tiền nhập mới thiết bị
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
                v-for="(statisticRecord, index) in statisticRecords"
                :key="index"
              >
                <td
                  scope="row"
                  class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap action-hover"
                >
                  {{ statisticRecord.id }}
                </td>
                <td class="px-6 py-4">{{ statisticRecord.name }}</td>
                <td class="px-6 py-4 font-bold">
                  {{ statisticRecord.total_import_equipments }}
                </td>
                <td class="px-6 py-4">
                  {{ statisticRecord.total_fixed_equipment }}
                </td>
                <td class="px-6 py-4">
                  {{ statisticRecord.total_take_over_equipments }}
                </td>
                <td class="px-6 py-4">
                  {{ statisticRecord.total_take_back_equipments }}
                </td>
                <td class="px-6 py-4">
                  {{ statisticRecord.total_import_equipments_cost }} VND
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import { Vue, Options } from "vue-property-decorator";
import StatisticCategoryService from "../services/StatisticCategoryService";
import StatisticCategoryRecord from "../types/StatisticCategoryRecord";
import Category from "@/types/Category";
import CategoryService from "@/services/category/categoryService";
export default class StasticCategory extends Vue {
  public year: number = 2022;
  public quater: number = 0;
  public month: number = 0;
  public years: any = [
    { id: 2017, name: " 2017" },
    { id: 2018, name: " 2018" },
    { id: 2019, name: " 2019" },
    { id: 2020, name: " 2020" },
    { id: 2021, name: " 2021" },
    { id: 2022, name: " 2022" },
    { id: 2023, name: " 2023" },
  ];
  public quaters: any = [
    { id: 1, name: " 1" },
    { id: 2, name: " 2" },
    { id: 3, name: " 3" },
    { id: 4, name: " 4" },
  ];
  public months: any = [
    { id: 1, name: " 1" },
    { id: 2, name: " 2" },
    { id: 3, name: " 3" },
    { id: 4, name: " 4" },
    { id: 5, name: " 5" },
    { id: 6, name: " 6" },
    { id: 7, name: " 7" },
    { id: 8, name: " 8" },
    { id: 9, name: " 9" },
    { id: 10, name: " 10" },
    { id: 11, name: " 11" },
    { id: 12, name: " 12" },
  ];
  public statisticRecords: StatisticCategoryRecord[] = [];
  public statisticTempRecords: StatisticCategoryRecord[] = [];
  public fromDate = 1;
  public toDate = 1638205200000;
  private queryParams: any;
  public categories: Category[] = [];
  mounted() {
    this.retrieveStatisticCategoryRecords();
  }
  handleYear() {
    this.quater = 0;
    this.month = 0;
    console.log(this.year);
    this.fromDate = new Date(`1/1/${this.year}`).getTime();
    this.toDate = new Date(`1/1/${this.year + 1}`).getTime();
    this.retrieveStatisticCategoryRecords();
  }

  handleQuater() {
    if (this.year == 0) {
      this.quater = 0;
      console.log("Vui long chon nam");
    } else {
      this.fromDate = new Date(
        `${1 + 3 * (this.quater - 1)}/1/${this.year}`
      ).getTime();
      if (this.quater == 4) {
        this.toDate = new Date(`12/31/${this.year}`).getTime();
      } else this.toDate = new Date(`12/31/${this.year}`).getTime();
      this.retrieveStatisticCategoryRecords();
    }
  }
  handleMonth() {
    if (this.year == 0) {
      console.log("Vui long chon nam");
    } else {
      this.quater = 0;
      this.fromDate = new Date(`${this.month}/1/${this.year}`).getTime();
      this.toDate = new Date(`${this.month + 1}/1/${this.year}`).getTime();
      this.retrieveStatisticCategoryRecords();
    }
  }
  getQueryParams() {
    this.queryParams = {
      from_date: this.fromDate,
      to_date: this.toDate,
    };

    Object.keys(this.queryParams).forEach((key) => {
      if (
        this.queryParams[key] === null ||
        this.queryParams[key] === undefined
      ) {
        delete this.queryParams[key];
      }
    });
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
  async retrieveStatisticCategoryRecords() {
    await StatisticCategoryService.getAllStatisticCategory(
      this.getQueryParams()
    ).then((res) => {
      this.statisticRecords = res.data.records;

      console.log(this.statisticRecords);
    });

    await CategoryService.getAllCategories(null).then(
      (res) => (this.categories = res.data.categories)
    );
    console.log(this.categories);
    for (let i = 0; i < this.categories.length; i++) {
      this.statisticRecords[this.categories[i].id].id = this.categories[i].id;
      this.statisticRecords[this.categories[i].id].name =
        this.categories[i].name;
    }
    console.log(this.statisticRecords);
  }
}
</script>
