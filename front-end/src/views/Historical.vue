<template>
  <div>
    <div>
      <div class="relative flex bg-gray-200">
        <div class="flex-1 container mx-auto p-2">
          <div class="p-1 mt-4 w-auto h-auto mx-auto bg-gray-50 shalow-lg rounded-xl">
            <div class="
              text-center
              p-2
              border-b-2 border-indigo-300
              w-full
              block
              font-semibold
              text-base
              self-start
              text-black
            ">
              <h1 class="text-2xl leading-relaxed">Lịch sử hoạt động</h1>
            </div>
            <div class="grid grid-flow-col grid-rows-1">
              <div class="p-2  w-auto">
                <span class="flex justify-start">
                  <div class="
                    p-1
                    m-2
                    text-base
                    flex flex-row
                    rounded-md
                    h-fit
                    w-80
                    border-2 border-blue-400
                    focus:border-blue-600
                    focus:ring
                    focus:ring-opacity-40
                    focus:ring-indigo-500
                  ">
                    <fa icon="magnifying-glass" class="text-gray-400 p-2"></fa>
                    <input class="text-base bg-gray-50 w-11/12 focus:outline-none text-left p-1" type="text"
                      placeholder="Nhập username của người thực hiện" v-model="keyPerformer"
                      @input="retrieveRecordsBySearch" />
                  </div>

                </span>
              </div>
              <div class="p-2 flex justify-end w-auto">
                <select v-model="currentActionType" @change="retrieveRecordsBySearch" name="takeover_status"
                  id="takeover_status" class="bg-blue-500 m-2 text-white p-2 rounded w-auto">
                  <option value="null" disabled selected hidden>
                    Loại hoạt động
                  </option>
                  <option value="-1" class="bg-white text-black hover:bg-blue-700">
                    Tất cả
                  </option>
                  <option value="1" class="bg-white text-black hover:bg-blue-700">
                    Bàn giao
                  </option>
                  <option value="2" class="bg-white text-black hover:bg-blue-700">
                    Tồn kho
                  </option>
                  <option value="3" class="bg-white text-black hover:bg-blue-700">
                    Sửa chữa
                  </option>
                </select>

              </div>
            </div>

            <div class="p-1 m-3">
              <div class="min-w-full align-middle flex justify-center">
                <table class="
                  hover:border-collapse
                  w-full
                  bg-gray-100
                  rounded-xl
                  place-content-center
                ">
                  <thead>
                    <tr class="border-b border-gray-500">
                      <th class="p-2 text-sm font-medium text-center text-gray-700">
                        Mã
                      </th>
                      <th class="p-2 text-sm font-medium text-center text-gray-700"
                        v-if="this.$route.params.id === undefined">
                        Mã thiết bị
                      </th>
                      <th class="p-2 text-sm font-medium text-center text-gray-700"
                        v-if="this.$route.params.id === undefined">
                        Tên thiết bị
                      </th>
                      <th class="p-2 text-sm font-medium text-center text-gray-700">
                        Người sử dụng
                      </th>
                      <th class="p-2 text-sm font-medium text-center text-gray-700">
                        Người thực hiện
                      </th>

                      <th class="p-2 text-sm font-medium text-center text-gray-700">
                        Loại hoạt động
                      </th>

                      <th class="p-2 text-sm font-medium text-center text-gray-700">
                        Trạng thái
                      </th>
                      <th class="p-2 text-sm font-medium text-center text-gray-700">
                        Người tạo
                      </th>
                      <th class="p-2 text-sm font-medium text-center text-gray-700">
                        Thời gian tạo
                      </th>

                      <th></th>
                    </tr>
                  </thead>

                  <tbody>
                    <tr class="
                      hover:bg-gray-200
                      transition-colors
                      border-b border-gray-200
                    " v-on:click="handleRecordIdByType(record.type_action, parseInt(record.id)), handleDetailShowByType(record.type_action, true)"
                      v-for="(record, index) in records" :key="index">
                      <td>
                        <div class="p-1 text-sm text-center text-gray-500">
                          {{ record.id }}
                        </div>
                      </td>
                      <td v-if="this.$route.params.id === undefined">
                        <div class="p-1 text-sm text-center text-gray-500">
                          {{ record.device_id }}
                        </div>
                      </td>

                      <td v-if="this.$route.params.id === undefined">
                        <div class="p-1 text-sm text-center text-gray-500">
                          {{ record.equipment_name }}
                        </div>
                      </td>
                      <td>
                        <div class="p-1 text-sm text-center text-gray-500">
                          {{ record.user }}
                        </div>
                      </td>
                      <td>
                        <div class="p-1 text-sm text-center text-gray-500">
                          {{ record.performer }}
                        </div>
                      </td>

                      <td>
                        <div class="p-1 text-sm text-center text-gray-500">
                          <div v-if="record.type_action == 1" class="text-green-500  font-semibold">
                            Bàn giao
                          </div>
                          <div v-else-if="record.type_action == 2" class="text-red-500 font-semibold">
                            Thu hồi
                          </div>
                          <div v-else-if="record.type_action == 3" class="text-yellow-600 font-semibold">
                            Sửa chữa
                          </div>
                        </div>
                      </td>

                      <td>
                        <div class="p-1 text-sm text-center">
                          <div v-if="record.type_action == 1 || record.type_action == 2">
                            <div v-if="record.status == '1'" class="text-green-500 italic font-semibold">
                              Đã xác nhận
                            </div>
                            <div v-else-if="record.status == '0'" class="text-blue-500 italic font-semibold">
                              Chờ xác nhận
                            </div>
                          </div>
                          <div v-if="record.type_action == 3">
                            <div v-if="record.status == '1'" class="text-green-500 italic font-semibold">
                              Sửa thành công
                            </div>
                            <div v-else-if="record.status == '0'" class="text-blue-500 italic font-semibold">
                              Đang sửa
                            </div>
                            <div v-else-if="record.status == '2'" class="text-yellow-600 italic font-semibold">
                              Không sửa được
                            </div>
                          </div>
                        </div>
                      </td>
                      <td>
                        <div class="p-1 text-sm text-center text-gray-500">
                          {{ record.created_by }}
                        </div>
                      </td>
                      <td>
                        <div class="p-1 text-sm text-center text-gray-500">
                          {{ handleDate(record.created_time) }}
                        </div>
                      </td>
                      <td>
                        <div class="flex justify-around w-auto">
                          <span class="flex justify-center">
                            <button class="
                              bg-gray-100
                              hover:bg-gray-300
                              m-1
                              transition-colors
                              flex
                              justify-center
                              items-center
                              w-auto
                              text-blue-500
                              px-3.5
                              py-2
                              rounded-md
                              focus:outline-none
                              disabled:cursor-not-allowed disabled:opacity-50
                            " v-on:click.stop="
                              handleRecordIdByType(record.type_action, parseInt(record.id)), handleUpdateShowByType(record.type_action, true)
                            " :disabled="checkRecord(record)">
                              <fa icon="pen-to-square"></fa>
                            </button>
                            <button :disabled="checkRecord(record)" class="
                              disabled:cursor-not-allowed disabled:opacity-50
                              bg-gray-100
                              hover:bg-gray-300
                              m-1
                              transition-colors
                              flex
                              justify-center
                              items-center
                              w-auto
                              text-red-500
                              px-3.5
                              py-2
                              rounded-md
                              focus:outline-none
                            " v-on:click.stop="
                              deleteRecordByType(record.type_action, parseInt(record.id))
                            ">
                              <fa icon="trash-can"></fa>
                            </button>
                          </span>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            <nav class="flex justify-center">
              <ul class="flex -space-x-px">
                <li>
                  <button @click="onClickFirstPage" class="
                    py-2
                    px-3
                    ml-0
                    disabled:cursor-not-allowed
                    leading-tight
                    text-gray-500
                    bg-white
                    rounded-l-lg
                    border border-gray-300
                    hover:bg-gray-100 hover:text-gray-700
                    dark:bg-gray-800
                    dark:border-gray-700
                    dark:text-gray-400
                    dark:hover:bg-gray-700
                    dark:hover:text-white
                  " :disabled="currentPage == 1">
                    First
                  </button>
                </li>
                <li>
                  <button @click="onClickPreviousPage" class="
                    py-2
                    px-3
                    leading-tight
                    disabled:cursor-not-allowed
                    text-gray-500
                    bg-white
                    border border-gray-300
                    hover:bg-gray-100 hover:text-gray-700
                    dark:bg-gray-800
                    dark:border-gray-700
                    dark:text-gray-400
                    dark:hover:bg-gray-700
                    dark:hover:text-white
                  " :disabled="currentPage == 1">
                    Preivous
                  </button>
                </li>

                <li>
                  <a class="
                    pb-3
                    leading-tight
                    px-3
                    mt-4
                    disable
                    text-white
                    bg-blue-500
                    border border-gray-300
                    dark:border-gray-700 dark:bg-gray-700 dark:text-white
                  ">{{ currentPage }}</a>
                </li>
                <li>
                  <button @click="onClickNextPage" class="
                    py-2
                    px-3
                    leading-tight
                    text-gray-500
                    disabled:cursor-not-allowed
                    bg-white
                    border border-gray-300
                    hover:bg-gray-100 hover:text-gray-700
                    dark:bg-gray-800
                    dark:border-gray-700
                    dark:text-gray-400
                    dark:hover:bg-gray-700
                    dark:hover:text-white
                  " :disabled="currentPage == totalPages">
                    Next
                  </button>
                </li>

                <li>
                  <button @click="onClickLastPage" class="
                    py-2
                    px-3
                    leading-tight
                    disabled:cursor-not-allowed
                    text-gray-500
                    bg-white
                    rounded-r-lg
                    border border-gray-300
                    hover:bg-gray-100 hover:text-gray-700
                    dark:bg-gray-800
                    dark:border-gray-700
                    dark:text-gray-400
                    dark:hover:bg-gray-700
                    dark:hover:text-white
                  " :disabled="currentPage == totalPages">
                    Last
                  </button>
                </li>
              </ul>
              <ul class="flex -space-x-px text-gray-500 m-2 text-sm">
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

    <DetailFixEquipment v-if="isDetailFixEquipmentShow" v-on:changeDetailFixEquipmentShow="handleDetailFixEquipmentShow"
      v-on:changeUpdateFixEquipmentShow="handleUpdateFixEquipmentShow" v-bind:id="recordFixEquipmentId" />
    <UpdateFixEquipment v-if="isUpdateFixEquipmentShow" v-on:changeUpdateFixEquipmentShow="handleUpdateFixEquipmentShow"
      v-bind:id="recordFixEquipmentId" v-on:changeData="handleDataUpdate" />

    <DetailTakeBack v-if="isDetailTakeBackShow" v-on:changeDetailTakeBackShow="handleDetailTakeBackShow"
      v-on:changeUpdateTakeBackShow="handleUpdateTakeBackShow" v-on:deleteRecord="deleteTakeBackRecord"
      v-bind:id="recordTakeBackId" />

    <UpdateTakeBack v-if="isUpdateTakeBackShow" v-on:changeUpdateTakeBackShow="handleUpdateTakeBackShow"
      v-bind:id="recordTakeBackId" v-on:changeData="handleDataUpdate" />
    <DetailTakeOver v-if="isDetailTakeOverShow" v-on:changeDetailTakeOverShow="handleDetailTakeOverShow"
      v-on:changeUpdateTakeOverShow="handleUpdateTakeOverShow" v-on:deleteRecord="deleteTakeOverRecord"
      v-bind:id="recordTakeOverId" />
    <UpdateTakeOver v-if="isUpdateTakeOverShow" v-on:changeUpdateTakeOverShow="handleUpdateTakeOverShow"
      v-bind:id="recordTakeOverId" v-on:changeData="handleDataUpdate" />
  </div>
</template>

<script lang="ts">
import DetailTakeOver from "./DetailTakeOver.vue";
import UpdateTakeOver from "./UpdateTakeOver.vue";
import TakeBackHistory from "./TakeBackHistory.vue";
import TakeOverHistory from "./TakeOverHistory.vue";
import FixEquipmentHistory from "./FixEquipmentHistory.vue";
import DetailTakeBack from "./DetailTakeBack.vue";
import UpdateTakeBack from "./UpdateTakeBack.vue";
import DetailFixEquipment from "./DetailFixEquipment.vue";
import UpdateFixEquipment from "./UpdateFixEquipment.vue";
import TakeOverService from "@/services/takeover/TakeOverService";
import TakeBackService from "@/services/takeback/TakeBackService";
import { Vue, Options } from "vue-property-decorator";
import HistoricalService from "@/services/historical/HistoricalService";
import HistoricalRecord from "@/types/HistoricalRecord";
import FixEquipmentService from "@/services/fixEquipment/FixEquipmentService";

@Options({
  components: {
    DetailFixEquipment,
    UpdateFixEquipment,
    DetailTakeBack,
    DetailTakeOver,
    UpdateTakeBack,
    UpdateTakeOver,
    TakeBackHistory,
    TakeOverHistory,
    FixEquipmentHistory,
  },
})
export default class Historical extends Vue {
  isDetailTakeBackShow: Boolean = false;
  isUpdateTakeBackShow: Boolean = false;
  isDetailTakeOverShow: Boolean = false;
  isUpdateTakeOverShow: Boolean = false;
  isDetailFixEquipmentShow: Boolean = false;
  isUpdateFixEquipmentShow: Boolean = false;
  public records: HistoricalRecord[] = [];
  public currentPage: number = 1;
  public currentLimit: number = 10;
  public currentActionType: string | null = null;
  public keyPerformer: string | null = null;
  totalPages: number = 0;
  public recordTakeBackId: number = 0;
  public recordTakeOverId: number = 0;
  public recordFixEquipmentId: number = 0;
  tableKey: number = 0;


  handleDetailTakeBackShow(data: Boolean) {
    this.isDetailTakeBackShow = data;
    this.isUpdateTakeBackShow = false;
  }

  handleUpdateTakeBackShow(data: Boolean) {
    this.isDetailTakeBackShow = false;
    this.isUpdateTakeBackShow = data;
  }

  handleDetailTakeOverShow(data: Boolean) {
    this.isDetailTakeOverShow = data;
    this.isUpdateTakeOverShow = false;
  }

  handleUpdateTakeOverShow(data: Boolean) {
    this.isDetailTakeOverShow = false;
    this.isUpdateTakeOverShow = data;
  }
  handleDetailFixEquipmentShow(data: Boolean) {
    this.isDetailFixEquipmentShow = data;
    this.isUpdateFixEquipmentShow = false;
  }

  handleUpdateFixEquipmentShow(data: Boolean) {
    this.isDetailFixEquipmentShow = false;
    this.isUpdateFixEquipmentShow = data;
  }

  handleRecordTakeOverId(id: number) {
    this.recordTakeOverId = id;
  }
  handleRecordTakeBackId(id: number) {
    this.recordTakeBackId = id;
  }
  handleRecordFixEquipmentId(id: number) {
    this.recordFixEquipmentId = id;
  }

  deleteTakeBackRecord(id: number) {
    if (confirm("Bạn có chắc chắn muốn xóa bản ghi thu hồi này ?")) {
      TakeBackService.deleteById(id)
        .then(() => {
          this.handleDataUpdate();
          alert("Delete Successfully !!");
        })
        .catch((err) => alert(err.response.data));
    }
  }

  deleteTakeOverRecord(id: number) {
    if (confirm("Bạn có chắc chắn muốn xóa bản ghi bàn giao này ?")) {

      TakeOverService.deleteById(id)
        .then(() => {
          this.handleDataUpdate()
          alert("Delete Successfully !!");
        })

        .catch((err) => alert(err.response.data.errors[0]));
    }
  }

  deleteFixEquipmentRecord(id: number) {
    if (confirm("Bạn có chắc chắn muốn xóa bản ghi bàn giao này ?")) {
      FixEquipmentService.deleteById(id)
        .then(() => {
          this.handleDataUpdate()
          alert("Delete Successfully !!");
        })

        .catch((err) => alert(err.response.data.errors[0]));
    }
  }

  async created() {
    await this.retrieveRecords(this.getQueryParams());

  }



  async retrieveRecords(params: String) {
    await HistoricalService.getRecordsBySearch(params)
      .then((res) => {
        console.log(res.data);
        this.records = res.data.records;
        this.totalPages = res.data.n_pages;
      })
      .catch((err) => {
        alert(err.response.data);
      });
  }

  async retrieveRecordsBySearch() {
    console.log(this.keyPerformer);
    if (this.currentActionType == "-1") this.currentActionType = null;
    if (this.keyPerformer == "") this.keyPerformer = null;

    this.currentPage = 1;
    await this.retrieveRecords(this.getQueryParams());
  }

  getQueryParams() {
    const queryParams: any = {
      page: this.currentPage,
      size: this.currentLimit,
      performer: this.keyPerformer,
      type_action: this.currentActionType,
      equipment_id: this.$route.params.id,
    };
    Object.keys(queryParams).forEach((key) => {
      if (queryParams[key] === null || queryParams[key] === undefined) {
        delete queryParams[key];
      }
    });
    //this.$router.push({ name: "TakeOverHistory", query: queryParams });
    let params = "";
    const temp = Object.entries(queryParams);
    for (let i = 0; i < temp.length; i++) {
      if (i < temp.length - 1) {
        let param = temp[i][0] + "=" + temp[i][1] + "&";
        params += param;
      } else {
        let param = temp[i][0] + "=" + temp[i][1];
        params += param;
      }
    }
    return params;
  }
  handleDate(data: string) {
    var d = new Date(Number(data));
    return d.toLocaleString();
  }

  handleDataUpdate() {
    this.retrieveRecords(this.getQueryParams());
    this.$emit('changeData')
  }

  async onClickFirstPage() {
    this.currentPage = 1;
    this.retrieveRecords(this.getQueryParams());
  }
  onClickNextPage() {
    if (this.currentPage != this.totalPages) {
      this.currentPage++;
      this.retrieveRecords(this.getQueryParams());
    }
  }
  onClickPreviousPage() {
    if (this.currentPage != 1) {
      this.currentPage--;
      this.retrieveRecords(this.getQueryParams());
    }
  }
  onClickLastPage() {
    this.currentPage = this.totalPages;
    this.retrieveRecords(this.getQueryParams());
  }

  checkRecord(record: HistoricalRecord) {
    if (record.type_action == 1 || record.type_action == 2) {
      if (record.status == "1")
        return true
      else
        return false
    }
    else if (record.type_action == 3 && record.take_over_status == "1")
      return true
    else
      return false
  }

  handleDetailShowByType(type: number, data: boolean) {
    if (type == 1)
      this.handleDetailTakeOverShow(data)
    if (type == 2)
      this.handleDetailTakeBackShow(data)
    if (type == 3)
      this.handleDetailFixEquipmentShow(data)
  }

  handleUpdateShowByType(type: number, data: boolean) {
    if (type == 1)
      this.handleUpdateTakeOverShow(data)
    if (type == 2)
      this.handleUpdateTakeBackShow(data)
    if (type == 3)
      this.handleUpdateFixEquipmentShow(data)
  }

  deleteRecordByType(type: number, id: number) {
    console.log(type)
    if (type == 1)
      this.deleteTakeOverRecord(id)
    if (type == 2)
      this.deleteTakeBackRecord(id)
    if (type == 3)
      this.deleteFixEquipmentRecord(id)
  }

  handleRecordIdByType(type: number, id: number) {
    if (type == 1)
      this.handleRecordTakeOverId(id)
    if (type == 2)
      this.handleRecordTakeBackId(id)
    if (type == 3)
      this.handleRecordFixEquipmentId(id)
  }


}
</script>

<style>
</style>
