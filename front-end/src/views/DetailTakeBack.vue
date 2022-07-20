<template>
  <div class="absolute h-screen top-0 right-1 w-5/12 drop-shadow-lg">
    <div class="
        grid grid-cols-4
        text-start
        border-b-2 border-indigo-300
        w-auto
        font-semibold
        text-base
        self-start
        text-black
        bg-indigo-500
      ">
      <h1 class="px-2 pt-2 pb-1 col-span-3 text-lg font-medium text-white w-auto">
        Xem thông tin chi tiết thu hồi - {{ record?.id }}
      </h1>
      <button class="
          place-self-end
          bg-indigo-500
          hover:bg-indigo-200
          m-2
          transition-colors
          w-auto
          text-white
          rounded-md
          focus:outline-none
        " v-on:click="changeShow(false)">
        <fa icon="xmark" class="px-2 py-1"></fa>
      </button>
    </div>

    <div class="overflow-auto overflow-x-hidden bg-indigo-100 h-4/5">
      <div class="pl-3 text-sm grid grid-cols-3 grid-flow-row">
        <div class="p-1 font-medium text-gray-700">Mã thiết bị</div>
        <div class="p-1 col-span-2 font-medium text-gray-700">Tên thiết bị</div>

        <p class="pl-1 text-slate-500">{{ record?.device_id }}</p>
        <p class="pl-1 col-span-2 text-slate-500">{{ record?.equipment_name }}</p>
        <div class="p-1 font-medium text-gray-700">Chi phí</div>
        <div class="p-1 col-span-2 font-medium text-gray-700">
          Thời gian thu hồi
        </div>

        <p class="pl-1 text-slate-500">{{ record?.cost }}</p>
        <p class="pl-1 col-span-2 text-slate-500">
          {{ handleDate(record?.action_time) }}
        </p>

        <div class="p-1 font-medium text-gray-700">Người trả thiết bị</div>
        <div class="pl-1 col-span-2 font-medium text-gray-700">
          Loại thu hồi
        </div>
        <p class="pl-1 text-slate-500">{{ record?.user }}</p>
        <p class="pl-1 col-span-2 text-slate-500">
          {{ type[record.reason] }}
        </p>
        <div class="p-1 font-medium text-gray-700">Người thu hồi</div>
        <div class="p-1 font-medium text-gray-700">Người xác nhận</div>
        <div class="p-1 font-medium text-gray-700">Trạng thái</div>
        <p class="pl-1 pb-2 text-slate-500">{{ record?.performer }}</p>
        <p class="pl-1 pb-2 text-slate-500">{{ record?.verifier }}</p>

        <div class="pl-1 pb-2 text-slate-500">
          <div v-if="record?.status == '1'" class="text-green-500 italic font-semibold">
            Đã xác nhận
          </div>
          <div v-else-if="record?.status == '0'" class="text-blue-500 italic font-semibold">
            Chờ xác nhận
          </div>
        </div>
      </div>
      <div class="
          px-3
          pt-2
          bg-indigo-100
          grid grid-cols-1
          text-sm
          w-full
          grid-flow-row
          border-t border-gray-300
        ">
        <div class="pl-1 font-medium text-gray-700">Message</div>
        <p class="pl-1 text-slate-500">{{ record?.message }}</p>
        <div class="pl-1 font-medium text-gray-700">Tệp đính kèm</div>
        <div v-if="allFileInfo.length != 0">
          <div class="grid grid-rows-2 " v-for="(file, index) in allFileInfo" :key="index">
            <div class="bg-gray-300 w-fit h-fit border rounded flex flex-row m-2">
              <fa icon="file-arrow-up" class="px-2 py-2"></fa>
              <div class="py-1">{{ file.file_name }}</div>
              <span class="close px-2 py-1">&times;</span>
            </div>
            <textarea id=" message" disabled
              class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Your message..." v-model="file.file_description"></textarea>
          </div>

        </div>
      </div>
      <div class="px-3 pt-2 bg-indigo-100 w-full border-t border-gray-300">
        <!-- <div class="p-1 m-2 text-base  font-medium text-gray-700">Thông tin</div> -->
        <div class="grid-flow-row grid grid-cols-3 text-sm">
          <div class="p-1 font-medium text-gray-700">Thời gian tạo</div>
          <div class="p-1 col-span-2 font-medium text-gray-700">Người tạo</div>

          <p class="pl-1 text-slate-500">
            {{ handleDate(record?.created_time) }}
          </p>
          <p class="pl-1 col-span-2 text-slate-500">{{ record?.created_by }}</p>
          <div class="p-1 font-medium text-gray-700">Thời gian cập nhật</div>
          <div class="p-1 col-span-2 font-medium text-gray-700">
            Người cập nhật
          </div>

          <p class="pl-1 text-slate-500">
            {{ handleDate(record?.updated_time) }}
          </p>
          <p class="pl-1 pb-2 col-span-2 text-slate-500">
            {{ record?.updated_by }}
          </p>
        </div>
      </div>
    </div>
    <div class="bg-indigo-100 w-full border-t border-gray-300">
      <!-- <div class="p-1 m-2 text-base  font-medium text-gray-700">Thông tin</div> -->
      <div class="grid grid-cols-2">
        <button class="
            justify-self-start
            bg-sky-500
            hover:bg-sky-600
            m-3.5
            transition-colors
            text-base
            w-auto
            text-gray-900
            px-1
            py-2
            rounded-md
            focus:outline-none
            disabled:cursor-not-allowed
            disabled:opacity-50
          " :disabled="record.status == '1'" v-on:click="showUpdateTakeBack">
          <fa icon="pen-to-square" class="px-2"></fa>
          Cập nhật
        </button>
        <button class="
            justify-self-end
            bg-indigo-10
            hover:bg-gray-300
            m-3.5
            transition-colors
            w-auto
            text-red-500
            p-2
            rounded-md
            focus:outline-none
            disabled:cursor-not-allowed
            disabled:opacity-50
          " :disabled="record.status == '1'" v-on:click.stop="
  changeShow(false);
deleteDetailRecord(id);
          ">
          <fa icon="ban" class="px-2"></fa>
          Xóa
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import UploadFilesService from "@/services/equipments/UploadFilesService";
import HistoricalService from "@/services/historical/HistoricalService";
import HistoricalRecord from "@/types/HistoricalRecord";
import { Vue, Options, Prop, Emit, Ref } from "vue-property-decorator";
import FileInfo from "../types/FileInfo";
export default class DetailTakeBack extends Vue {
  record: HistoricalRecord = {
    id: "",
    equipment_id: "",
    user: "",
    action_time: "",
    status: "",
    verifier: "",
    performer: "",
    type_action: 1,
    message: "",
    cost: "",
    created_by: "",
    created_time: "",
    updated_by: "",
    updated_time: "",
    metadata_info: "",
    device_id: "",
    equipment_name: "",
    take_over_status: "",
    reason: ""
  };
  type: any = {
    "1": "Hoàn trả thiết bị khi nghỉ việc",
    "2": "Thu hồi thiết bị hư hỏng để sửa chữa",
    "3": "Đền bù thiết bị sử dụng bị mất",
    "4": "Nhân viên bù tiền mua thiết bị"
  };

  public allFileInfo: FileInfo[] = [];
  currentMetaData: any;
  currentFileName: string[] = [];

  @Prop() id!: number;

  @Emit("changeDetailTakeBackShow")
  changeShow(data: boolean) {
    return data;
  }

  @Emit("changeUpdateTakeBackShow")
  showUpdateTakeBack() {
    return true;
  }

  async created() {
    console.log(this.id);
    this.retrieveRecord();
  }

  async retrieveRecord() {
    await HistoricalService.getRecordById(this.id, 2)
      .then((res) => {
        console.log(res.data);
        this.record = res.data;
        if (this.record.cost != null)
          this.record.cost = parseFloat(this.record.cost).toString()
        this.allFileInfo = res.data.metadata_info;
      })
      .catch((err) => {
        alert(err.response.data);
      });
  }
  handleDate(data: string | undefined) {
    if (data === undefined) return null;
    var d = new Date(Number(data));
    return d.toLocaleString();
  }
  @Emit("deleteRecord")
  deleteDetailRecord(id: number) {

    return id;
  }

  downloadfile(index: number) {
    UploadFilesService.getFile(this.currentMetaData[index][1].file_url)
      .then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', this.currentMetaData[index][1].file_name);
        document.body.appendChild(link);
        link.click();
      });

  }

}
</script>

<style>
</style>