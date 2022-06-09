<template>
  <div class="relative bg-white shadow rounded-3xl sm:p-10 w-3/6 ml-80">
    <div>
      <div class="flex items-center space-x-5">
        <div class="block pl-2 font-semibold text-xl self-start text-gray-700">
          <h1 class="text-2xl leading-relaxed">Thêm mới thiết bị</h1>
        </div>
      </div>
      <div class="divide-y divide-gray-200">
        <div
          class="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7"
        >
          <div class="flex flex-row">
            <div class="flex flex-col w-48">
              <label class="leading-loose">Mã thiết bị</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.device_id"
              />
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Tên thiết bị</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-64 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.name"
              />
            </div>
          </div>
          <div class="flex flex-col">
            <label class="leading-loose">Danh mục</label>
            <select
              v-model="equipment.category_id"
              id="country"
              name="country"
              autocomplete="country-name"
              class="mt-1 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            >
              <option value="1">Máy tính</option>
              <option value="2">Bàn phím PC</option>
            </select>
          </div>
          <div class="flex flex-row">
            <div class="flex flex-col w-48">
              <label class="leading-loose">Tình trạng mới nhập</label>
              <select
                id="country"
                name="country"
                autocomplete="country-name"
                class="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                v-model="equipment.start_status"
              >
                <option value="1">New</option>
                <option value="2">Like New loại S (99%)</option>
                <option value="3">Like New loại A (99%)</option>
                <option value="4">Like New loại B (97%-98%)</option>
                <option value="5">Like New loại C (90%-95%)</option>
                <option value="6">Thiết bị cũ ( second hand)</option>
              </select>
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Trạng thái thiết bị</label>
              <select
                v-model="equipment.device_status"
                id="country"
                name="country"
                autocomplete="country-name"
                class="w-75px mt-1 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              >
                <option value="0">Bị mất</option>
                <option value="1">Sử dụng được</option>
                <option value="2">Bị hư hỏng</option>
              </select>
            </div>
          </div>
          <div class="flex flex-row">
            <div class="flex flex-col">
              <label class="leading-loose">Giá tiền</label>
              <input
                type="text"
                class="w-32 px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.price"
              />
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Tháng / Năm</label>
              <select
                v-model="equipment.period_type"
                id="country"
                name="country"
                autocomplete="country-name"
                class="w-24 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              >
                <option value="0">Tháng</option>
                <option value="1">Năm</option>
              </select>
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Thời gian khấu hao</label>
              <input
                type="text"
                class="w-32 px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.depreciation_period"
              />
            </div>
          </div>
          <div class="flex flex-row w-36">
            <div class="flex flex-col">
              <label class="leading-loose">Thời gian nhập thiết bị</label>
              <DatePicker class="w-64" />
            </div>
            <div class="flex flex-col ml-14">
              <label class="leading-loose">Giá trị khấu hao</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-64 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.depreciated_value"
              />
            </div>
          </div>
          <div class="flex flex-row">
            <div class="flex flex-col">
              <label class="leading-loose">Tệp đính kèm </label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
              />
            </div>
          </div>
        </div>

        <div class="pt-4 flex items-center space-x-4">
          <button
            @click="saveEquipment"
            class="bg-blue-500 font-bold flex justify-center w-48 items-center text-white px-4 py-3 rounded-md focus:outline-none"
          >
            Thêm thiết bị
          </button>
          <button
            class="bg-red-500 font-bold flex justify-center items-center w-48 text-gray-900 px-4 py-3 rounded-md focus:outline-none"
          >
            <svg
              class="w-6 h-6 mr-3"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              ></path>
            </svg>
            Hủy
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import DatePicker from "./DatePicker.vue";
import Equipment from "../types/Equipment";
import { Vue, Options } from "vue-property-decorator";
import EquipmentDataService from "../services/equipments/EquipmentDataService";
@Options({
  components: {
    DatePicker,
  },
})
export default class AddEquipment extends Vue {
  private equipment: Equipment = {
    device_id: "",
    name: "",
    start_status: "",
    price: "",
    depreciated_value: "",
    depreciation_period: "",
    period_type: "",
    import_date: "07062022",
    take_over_status: "1",
    category_id: "1",
    device_status: "1",
    created_by: "tatthanh@rever.vn",
    create_time: "07062022",
    updated_by: "",
    updated_time: "",
    take_over_person_id: "",
    take_over_person_name: "",

    id: "",
  };

  saveEquipment() {
    const data = {
      device_id: this.equipment.device_id,
      name: this.equipment.name,
      start_status: this.equipment.start_status,
      price: this.equipment.price,
      depreciation_period: this.equipment.depreciation_period,
      period_type: this.equipment.period_type,
      depreciated_value: this.equipment.depreciated_value,
      import_date: this.equipment.import_date,
      take_over_status: this.equipment.take_over_status,
      category_id: this.equipment.category_id,
      created_by: this.equipment.created_by,
      created_time: this.equipment.create_time,
      device_status: this.equipment.device_status,
    };
    console.log(data);
    EquipmentDataService.addData(data)
      .then((res) => alert("Thêm thiết bị thành công"))
      .catch((err) => console.log(err));
  }
}
</script>
