<template>
  <div>
    <div class="relative flex bg-gray-200">
      <div class="flex-1 container mx-auto px-6 py-2">
        <div class="p-1 w-auto h-auto mx-auto bg-gray-50 shalow-lg rounded-xl">
          <div
            class="text-center border-b-2 border-indigo-300 w-full block font-semibold text-base self-start text-black">
            <h1 class="text-2xl leading-relaxed">
              Thông tin chi tiết thiết bị
            </h1>
          </div>
          <div>
            <div class="flex flex-row">
              <div class="flex flex-col">
              </div>
              <div class="flex flex-col">
              </div>
              <div class="flex flex-col">
              </div>
            </div>
          </div>
          <div class="p-1 m-4 grid grid-rows-1 grid-flow-col">
            <div class="gap-px grid grid-cols-1 grid-flow-row place-items-center">
              <div class="gap-0.5 grid grid-rows-1 grid-flow-col">
                <button @click="handlePreviousImage"
                  class="sm:h-auto w-fit focus:outline-none transition-colors bg-gray-50 hover:bg-gray-200 text-gray-700">
                  <fa icon="angle-left" class="h-7 w-7"></fa>
                </button>
                <img class="h-72 w-72" :src="allImageCurrentURL[indexImage]" />
                <button @click="handleNextImage"
                  class="sm:h-auto w-fit transition-colors focus:outline-none bg-gray-50 hover:bg-gray-200 text-gray-700">
                  <fa icon="angle-right" class="h-7 w-7"></fa>
                </button>
              </div>
              <button
                class="sm:h-auto sm:w-auto px-4 py-1 my-1 text-center focus:outline-none transition-colors rounded-md border-b border-gray-300 bg-gray-200 hover:bg-gray-300 text-gray-700">
                <fa icon="file-circle-plus"></fa>
                Thêm ảnh mới
              </button>
            </div>

            <div class="grid grid-cols-1 grid-flow-row divide-y divide-gray-300">
              <span class="pl-2 grid grid-rows-1 grid-flow-col">
                <div>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Mã thiết bị
                  </div>
                  <p class="pl-1 text-slate-500">{{ equipment?.device_id }}</p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Danh mục
                  </div>
                  <p class="pl-1 text-slate-500">
                    {{ this.categories[equipment.category_id] }}
                  </p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Giá tiền
                  </div>
                  <p class="pl-1 text-slate-500">{{ equipment?.price }} VND</p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Trạng thái bàn giao
                  </div>
                  <p class="pl-1 text-slate-500">
                  <p v-if="equipment.take_over_status == '1'">Đã được bàn giao</p>
                  <p v-else-if="equipment.take_over_status == '0'">Đang tồn kho</p>
                  </p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Người thêm thông tin
                  </div>
                  <p class="pl-1 text-slate-500">{{ equipment?.created_by }}</p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Người cập nhật thông tin
                  </div>
                  <p class="pl-1 text-slate-500">{{ equipment?.updated_by }}</p>
                </div>
                <div>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Tên thiết bị
                  </div>
                  <p class="pl-1 text-slate-500">{{ equipment?.name }}</p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Tình trạng khi mới nhập
                  </div>
                  <p class="pl-1 text-slate-500">
                  <p v-if="equipment?.start_status == '1'">New</p>
                  <p v-else-if="equipment?.start_status == '2'">like new loại S (99,99%)</p>
                  <p v-else-if="equipment?.start_status == '3'">like new loại A (99%)</p>
                  <p v-else-if="equipment?.start_status == '4'"> like New Loại B (97-98%)</p>
                  <p v-else-if="equipment?.start_status == '5'">like New Loại C (Dưới 95%-90%)</p>
                  <p v-else-if="equipment?.start_status == '6'">Thiết bị cũ (second-hand)</p>
                  <p v-else-if="equipment?.start_status == '7'">Khác</p>
                  </p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Giá trị khấu hao
                  </div>
                  <p class="pl-1 text-slate-500">
                    {{ equipment?.depreciated_value * 100 }} %
                  </p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Người đang sử dụng
                  </div>
                  <p class="pl-1 text-slate-500">
                    {{ equipment?.take_over_person_id }}
                  </p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Thời gian thêm thông tin
                  </div>
                  <p class="pl-1 text-slate-500">
                    {{ equipment?.created_time }}
                  </p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Thời gian cập nhật thông tin
                  </div>
                  <p class="pl-1 text-slate-500">
                    {{ equipment?.updated_time }}
                  </p>
                </div>
                <div>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Trạng thái thiết bị
                  </div>
                  <p class="pl-1 text-yellow-500 italic font-semibold">
                    {{ this.deviceStatus[equipment.device_status] }}
                    <span v-if="equipment.compensation_status == '0'">chưa đền bù</span>
                    <span v-if="equipment.compensation_status == '1'">đã đền bù</span>
                  </p>

                  <div class="p-1 text-base font-medium text-gray-700">
                    Thời gian nhập thiết bị
                  </div>
                  <p class="pl-1 text-slate-500">
                    {{ equipment?.import_date }}
                  </p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Thời gian kì hạn
                  </div>
                  <p class="pl-1 text-slate-500">
                    {{ equipment?.depreciation_period }}
                    <span v-if="equipment.period_type == '1'">Tháng</span>
                    <span v-else-if="equipment.period_type == '2'">Năm</span>
                  </p>
                </div>
              </span>
              <div class="p-2 mt-4 grid grid-cols-3">
                <button
                  class="bg-green-500 hover:bg-green-600 m-2 transition-colors flex justify-center w-auto items-center text-white px-1 py-2 rounded-md focus:outline-none"
                  v-on:click="handleAddTakeOverShow(true)"
                  v-if="equipment?.take_over_status == '0' && equipment?.device_status == '1'">
                  <fa icon="rotate-right" class="pr-2"></fa>
                  Bàn giao
                </button>
                <button
                  class="bg-red-500 hover:bg-red-600 m-2 transition-colors flex justify-center w-auto items-center text-white px-1 py-2 rounded-md focus:outline-none"
                  v-if="equipment?.take_over_status == '1'" v-on:click="handleAddTakeBackShow(true)">
                  <fa icon="rotate-left" class="pr-2"></fa>
                  Thu hồi
                </button>
                <button
                  class="bg-yellow-500 hover:bg-yellow-600 m-2 transition-colors flex justify-center w-auto-2 items-center text-white px-1 py-2 rounded-md focus:outline-none"
                  v-if="equipment?.take_over_status == '0' && equipment.device_status == '2'"
                  v-on:click="handleAddFixEquipmentShow(true)">
                  <fa icon="rotate-left" class="pr-2"></fa>
                  Sửa chữa
                </button>
                <button @click="editEquipment"
                  class="bg-sky-500 hover:bg-sky-600 m-2 transition-colors flex justify-center items-center w-auto text-gray-900 px-1 py-2 rounded-md focus:outline-none">
                  <fa icon="pen-to-square" class="pr-2"></fa>
                  Cập nhật
                </button>
                <button @click="deleteEquipment"
                  class="absolute place-self-end bg-gray-50 hover:bg-gray-200 mt-2 transition-colors flex justify-center items-center w-auto text-red-500 p-2 rounded-md focus:outline-none">
                  <fa icon="ban" class="pr-2"></fa>
                  Xóa
                </button>

              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
    <Historical class="mx-3" :key="keyHistorical" v-on:changeData="handleDataUpdate" />


    <AddTakeOver v-if="isAddTakeOverShow" v-on:changeAddTakeOverShow="handleAddTakeOverShow"
      v-bind:device_id="equipment?.device_id" v-bind:equipment_name="equipment?.name"
      v-bind:equipment_id="equipment?.id" v-on:addsuccess="handleDataUpdate" />
    <AddTakeBack v-if="isAddTakeBackShow" v-on:changeAddTakeBackShow="handleAddTakeBackShow"
      v-bind:device_id="equipment?.device_id" v-bind:equipment_name="equipment?.name"
      v-bind:equipment_id="equipment?.id" v-on:addsuccess="handleDataUpdate" />
    <AddFixEquipment v-if="isAddFixEquipmentShow" v-on:changeAddFixEquipmentShow="handleAddFixEquipmentShow"
      v-bind:device_id="equipment.device_id" v-bind:equipment_name="equipment.name" v-bind:equipment_id="equipment.id"
      v-on:addsuccess="handleDataUpdate" />

  </div>
</template>

<script lang="ts">

import AddTakeOver from "./AddTakeOver.vue";
import AddTakeBack from "./AddTakeBack.vue";
import AddFixEquipment from "./AddFixEquipment.vue";
import EquipmentDataService from "../services/equipments/EquipmentDataService";
import TakeOverRecord from "@/types/TakeOverRecord";
import { Vue, Options, } from "vue-property-decorator";
import Equipment from "@/types/Equipment";

import Historical from "./Historical.vue";
@Options({
  components: {

    AddTakeOver,
    AddTakeBack,
    AddFixEquipment,
    Historical
  },
})
export default class DetailEquipment extends Vue {
  public deviceStatus = {
    0: "Bị Mất",
    1: "Sử dụng được",
    2: "Bị hư hỏng",
    3: "Đã bán cho nhân viên",
  };
  public categories = {
    1: "Máy tính",
    2: "Màn hình",
    3: "Phụ kiện",
  };
  public allImageCurrentURL: string[] = [];
  public indexImage = 0;
  equipment: Equipment = {
    category_id: null,
    compensation_status: null,
    category_name: null,
    created_by: null,
    created_time: null,
    device_id: null,
    id: null,
    import_date: null,
    name: null,
    price: null,
    start_status: null,
    take_over_person_id: null,
    take_over_status: null,
    updated_by: null,
    updated_time: null,
    device_status: null,
    depreciated_value: null,
    depreciation_period: null,
    period_type: null,
    metadata_info: null,
  };


  isAddFixEquipmentShow: Boolean = false;
  isAddTakeOverShow: Boolean = false;
  isAddTakeBackShow: Boolean = false;
  keyHistorical: number = 0;

  recordsOfEquipment: TakeOverRecord[] = [];
  currentPage: number = 1;
  currentLimit: number = 5;
  currentTakeOverStatus: string | null = null;
  currentTakeOverType: string | null = null;
  keyUser: string | null = null;
  keyTakeOverPerson: string | null = null;
  totalPages: number = 0;
  public recordTakeBackId: number = 0;
  public recordTakeOverId: number = 0;
  public recordFixEquipmentId: number = 0;
  keyTakeBack: number = 0;
  keyTakeOver: number = 0;
  keyFixEquipment: number = 0;
  currentFixEquipmentRecord: string | null = null;
  // type: any = {
  //   1: "Bàn giao thiết bị mới",
  //   2: "Bàn giao thiết bị sau khi sửa chữa",
  // };
  async created() {
    const idParams = this.$route.params.id;
    await this.retrieveDetailEquipment(idParams);

  }
  handlePreviousImage() {
    if (this.indexImage == 0) {
      return;
    } else {
      this.indexImage--;
    }
  }
  handleNextImage() {
    if (this.indexImage + 1 == this.allImageCurrentURL.length) {
      return;
    } else {
      this.indexImage++;
    }
  }

  async handleDataUpdate() {
    this.keyHistorical += 1
    const idParams = this.$route.params.id;
    await this.retrieveDetailEquipment(idParams);
  }

  async retrieveDetailEquipment(id: any) {
    const response = await EquipmentDataService.getEquipmentDetail(id)
      .then((res) => {
        console.log(res.data);
        this.equipment = res.data;
        this.equipment.price = parseFloat(this.equipment.price!).toString();
        const allImage = Object.values(res.data.metadata_info);
        let result = allImage.map((Image: any) => Image.file_url);
        result.forEach((URL, index) => {
          this.allImageCurrentURL[index] = URL;
        });
      })
      .then(() => this.handleFieldEquipment())
      .catch((err) => console.log(err));
  }
  handleImportDate(data: string) {
    var d = new Date(parseInt(data));
    return d.toLocaleString();
  }
  handleFieldEquipment() {
    if (this.equipment.import_date != null) {
      this.equipment.import_date = this.handleImportDate(
        this.equipment.import_date
      );
    }
    if (this.equipment.created_time != null) {
      this.equipment.created_time = this.handleImportDate(
        this.equipment.created_time
      );
    }
    if (this.equipment.updated_time != null) {
      this.equipment.updated_time = this.handleImportDate(
        this.equipment.updated_time
      );
    }
  }
  handleAddTakeOverShow(data: Boolean) {
    this.isAddTakeOverShow = data;

  }
  handleAddTakeBackShow(data: Boolean) {
    this.isAddTakeBackShow = data;

  }
  handleAddFixEquipmentShow(data: Boolean) {
    this.isAddFixEquipmentShow = data;

  }




  editEquipment() {
    const id: any = this.equipment.id;
    this.$router.push({ name: "update", params: { id: id } });
  }
  deleteEquipment() {
    const id = this.equipment.id;
    const takeOverStatus = this.equipment.take_over_status;
    if (takeOverStatus == "1") {
      alert("Thiết bị đang được bàn giao hiện không thể xóa !!");
    } else {
      if (confirm("Bạn có chắc chắn muốn xóa thiết bị này ?")) {
        EquipmentDataService.deleteEquipment(id!);
        this.$router.push({ name: "Home" });
      }
    }
  }

  handleDate(data: string) {
    var d = new Date(Number(data));
    return d.toLocaleString();
  }
}
</script>

<style>
</style>
