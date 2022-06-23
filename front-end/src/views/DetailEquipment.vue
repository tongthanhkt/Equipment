<template>
  <div>
    <div class="relative flex bg-gray-200">
      <div class="flex-1 container mx-auto px-6 py-8">
        <div class="p-1 w-auto h-auto mx-auto bg-gray-50 shalow-lg rounded-xl">
          <div
            class="
              text-center
              border-b-2 border-indigo-300
              w-full
              block
              font-semibold
              text-base
              self-start
              text-black
            "
          >
            <h1 class="text-2xl leading-relaxed">
              Thông tin chi tiết thiết bị
            </h1>
          </div>

          <div class="p-1 m-4 grid grid-rows-1 grid-flow-col">
            <div
              class="gap-px grid grid-cols-1 grid-flow-row place-items-center"
            >
              <div class="gap-0.5 grid grid-rows-1 grid-flow-col">
                <button
                  class="
                    sm:h-auto
                    w-fit
                    focus:outline-none
                    transition-colors
                    bg-gray-50
                    hover:bg-gray-200
                    text-gray-700
                  "
                >
                  <fa icon="angle-left" class="h-7 w-7"></fa>
                </button>
                <img class="h-72 w-72" src="../images/19127636.jpg" />
                <button
                  class="
                    sm:h-auto
                    w-fit
                    transition-colors
                    focus:outline-none
                    bg-gray-50
                    hover:bg-gray-200
                    text-gray-700
                  "
                >
                  <fa icon="angle-right" class="h-7 w-7"></fa>
                </button>
              </div>
              <button
                class="
                  sm:h-auto sm:w-auto
                  px-4
                  py-1
                  my-1
                  text-center
                  focus:outline-none
                  transition-colors
                  rounded-md
                  border-b border-gray-300
                  bg-gray-200
                  hover:bg-gray-300
                  text-gray-700
                "
              >
                <fa icon="file-circle-plus"></fa>
                Thêm ảnh mới
              </button>
            </div>

            <div
              class="grid grid-cols-1 grid-flow-row divide-y divide-gray-300"
            >
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
                    {{ equipment?.category_id }}
                  </p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Gía tiền
                  </div>
                  <p class="pl-1 text-slate-500">{{ equipment?.price }}</p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Trạng thái bàn giao
                  </div>
                  <p class="pl-1 text-slate-500">
                    {{ equipment?.take_over_status }}
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
                    {{ equipment?.import_date }}
                  </p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Gía trị khấu hao
                  </div>
                  <p class="pl-1 text-slate-500">
                    {{ equipment?.depreciated_value }} %
                  </p>
                  <div class="p-1 text-base font-medium text-gray-700">
                    Người đang sử dụng
                  </div>
                  <p class="pl-1 text-slate-500">
                    {{ equipment?.take_over_person_name }}
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
                  <p class="pl-1 text-slate-500">
                    {{ equipment?.device_status }}
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
                  </p>
                </div>
              </span>
              <div class="p-2 mt-4 grid grid-cols-3">
                <button
                  class="
                    bg-green-500
                    hover:bg-green-600
                    m-2
                    transition-colors
                    flex
                    justify-center
                    w-auto
                    items-center
                    text-white
                    px-1
                    py-2
                    rounded-md
                    focus:outline-none
                  "
                  v-on:click="handleAddTakeOverShow(true)"
                  v-if="equipment?.take_over_status == '0'"
                >
                  <fa icon="rotate-right" class="pr-2"></fa>
                  Bàn giao
                </button>
                <button
                  class="
                    bg-red-500
                    hover:bg-red-600
                    m-2
                    transition-colors
                    flex
                    justify-center
                    w-auto
                    items-center
                    text-white
                    px-1
                    py-2
                    rounded-md
                    focus:outline-none
                  "
                  v-if="equipment?.take_over_status == '1'"
                >
                  <fa icon="rotate-left" class="pr-2"></fa>
                  Thu hồi
                </button>
                <button
                  class="
                    bg-sky-500
                    hover:bg-sky-600
                    m-2
                    transition-colors
                    flex
                    justify-center
                    items-center
                    w-auto
                    text-gray-900
                    px-1
                    py-2
                    rounded-md
                    focus:outline-none
                  "
                >
                  <fa icon="pen-to-square" class="pr-2"></fa>
                  Cập nhật
                </button>
                <button
                  class="
                    place-self-end
                    bg-gray-50
                    hover:bg-gray-200
                    mt-2
                    transition-colors
                    flex
                    justify-center
                    items-center
                    w-auto
                    text-red-500
                    p-2
                    rounded-md
                    focus:outline-none
                  "
                >
                  <fa icon="ban" class="pr-2"></fa>
                  Xóa
                </button>
              </div>
            </div>
          </div>
        </div>
        <div
          class="p-1 mt-8 w-auto h-auto mx-auto bg-gray-50 shalow-lg rounded-xl"
        >
          <div
            class="
              text-center
              p-2
              border-b-2 border-indigo-300
              w-full
              block
              font-semibold
              text-base
              self-start
              text-black
            "
          >
            <h1 class="text-2xl leading-relaxed">Lịch sử bàn giao</h1>
          </div>
          <div class="grid grid-flow-col grid-rows-1">
            <div class="p-2 flex place-items-end w-auto">
              <span class="flex justify-start">
                <div
                  class="
                    px-1
                    py-2
                    m-2
                    text-base
                    grid grid-rows-1 grid-flow-col
                    rounded-md
                    h-fit
                    w-fit
                    border-2 border-blue-400
                    focus:border-blue-600
                    focus:ring
                    focus:ring-opacity-40
                    focus:ring-indigo-500
                  "
                >
                  <fa
                    icon="magnifying-glass"
                    class="text-gray-400 px-2 py-1"
                  ></fa>
                  <input
                    class="text-base bg-gray-50 w-5/6 focus:outline-none"
                    type="text"
                    placeholder="Tên người sử dụng"
                    v-model="keyUser"
                    @input="retrieveRecordsOfEquipmentBySearch"
                  />
                </div>
                <div
                  class="
                    px-1
                    py-2
                    m-2
                    text-base
                    grid grid-rows-1 grid-flow-col
                    rounded-md
                    h-fit
                    w-fit
                    border-2 border-blue-400
                    focus:border-blue-600
                    focus:ring
                    focus:ring-opacity-40
                    focus:ring-indigo-500
                  "
                >
                  <fa
                    icon="magnifying-glass"
                    class="text-gray-400 px-2 py-1"
                  ></fa>
                  <input
                    class="text-base bg-gray-50 w-5/6 focus:outline-none"
                    type="text"
                    placeholder="Tên người bàn giao"
                    v-model="keyTakeOverPerson"
                    @input="retrieveRecordsOfEquipmentBySearch"
                  />
                </div>
              </span>
            </div>
            <div class="p-2 flex justify-end w-auto">
              <select
                v-model="currentTakeOverStatus"
                @change="retrieveRecordsOfEquipmentBySearch"
                name="takeover_status"
                id="takeover_status"
                class="bg-blue-500 m-2 text-white p-2 rounded w-auto"
              >
                <option value=null disabled selected hidden >
                  Trạng thái
                </option>
                <option value='-1' class="bg-white text-black hover:bg-blue-700">
                  Trạng thái
                </option>
                <option value="0" class="bg-white text-black hover:bg-blue-700">
                  Chờ xác nhận
                </option>
                <option value="1" class="bg-white text-black hover:bg-blue-700">
                  Đã xác nhận
                </option>
              </select>
              <select
                v-model="currentTakeOverType"
                @change="retrieveRecordsOfEquipmentBySearch"
                name="takeover_status"
                id="takeover_status"
                class="bg-blue-500 m-2 text-white p-2 rounded w-auto"
              >
                <option value=null disabled selected hidden >
                 Loại bàn giao
                </option>
                <option value='-1' class="bg-white text-black hover:bg-blue-700">Loại bàn giao</option>
                <option value="1" class="bg-white text-black hover:bg-blue-700">
                  Bàn giao thiết bị mới
                </option>
                <option value="2" class="bg-white text-black hover:bg-blue-700">
                  Bàn giao thiết bị sau khi sửa
                </option>
              </select>
            </div>
          </div>

          <div class="p-1 mx-1 my-2">
            <div class="flex justify-center min-w-full align-middle">
              <table
                class="
                  hover:border-collapse
                  min
                  -w-full
                  bg-gray-100
                  rounded-xl
                  place-content-center
                "
              >
                <thead>
                  <tr class="border-b border-gray-500">
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Mã bàn giao
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Người sử dụng
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Người bàn giao
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Thời gian bàn giao
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Loại bàn giao
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Trạng thái
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Người tạo
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Thời gian tạo
                    </th>

                    <th></th>
                  </tr>
                </thead>

                <tbody>
                  <tr
                    class="
                      hover:bg-gray-200
                      transition-colors
                      border-b border-gray-200
                    "
                    v-on:click="
                      (recordId = parseInt(recordOfEquipment.id)),
                        handleDetailTakeOverShow(true)
                    "
                    v-for="(recordOfEquipment, index) in recordsOfEquipment"
                    :key="index"
                  >
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{ recordOfEquipment.id }}
                      </div>
                    </td>

                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{ recordOfEquipment.username }}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{ recordOfEquipment.take_over_person }}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{ handleDate(recordOfEquipment.take_over_time) }}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{ type[recordOfEquipment.type] }}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        <div
                          v-if="recordOfEquipment.status == '1'"
                          class="text-green-500 italic font-semibold"
                        >
                          Đã xác nhận
                        </div>
                        <div
                          v-else-if="recordOfEquipment.status == '0'"
                          class="text-blue-500 italic font-semibold"
                        >
                          Chờ xác nhận
                        </div>
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{ recordOfEquipment.created_by }}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{ handleDate(recordOfEquipment.created_time) }}
                      </div>
                    </td>
                    <td>
                      <div class="flex justify-around w-auto">
                        <span class="flex justify-center">
                          <button
                            class="
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
                            "
                            v-on:click.stop="
                              (recordId = parseInt(recordOfEquipment.id)),
                                handleEditTakeOverShow(true)
                            "
                            :disabled="recordOfEquipment.status == '1'"
                          >
                            <fa icon="pen-to-square"></fa>
                          </button>
                          <button
                            :disabled="recordOfEquipment.status == '1'"
                            class="
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
                            "
                            v-on:click.stop="
                              deleteRecord(parseInt(recordOfEquipment.id))
                            "
                          >
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
                <button
                  @click="onClickFirstPage"
                  class="
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
                  "
                  :disabled="currentPage == 1"
                >
                  First
                </button>
              </li>
              <li>
                <button
                  @click="onClickPreviousPage"
                  class="
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
                  "
                  :disabled="currentPage == 1"
                >
                  Preivous
                </button>
              </li>

              <li>
                <a
                  class="
                    pb-3
                    leading-tight
                    px-3
                    mt-4
                    disable
                    text-white
                    bg-blue-500
                    border border-gray-300
                    dark:border-gray-700 dark:bg-gray-700 dark:text-white
                  "
                  >{{ currentPage }}</a
                >
              </li>
              <li>
                <button
                  @click="onClickNextPage"
                  class="
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
                  "
                  :disabled="currentPage == totalPages"
                >
                  Next
                </button>
              </li>

              <li>
                <button
                  @click="onClickLastPage"
                  class="
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
                  "
                  :disabled="currentPage == totalPages"
                >
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
    <AddTakeOver
      v-if="isAddTakeOverShow"
      v-on:changeAddTakeOverShow="handleAddTakeOverShow"
      v-bind:device_id="equipment?.device_id"
      v-bind:equipment_name="equipment?.name"
    />
    <DetailTakeOver
      v-if="isDetailTakeOverShow"
      v-on:changeDetailTakeOverShow="handleDetailTakeOverShow"
      v-on:changeEditTakeOverShow="handleEditTakeOverShow"
      v-on:deteteTakeOverRecord="deleteRecord"
      v-bind:id="recordId"
    />

    <EditTakeOver
      v-if="isEditTakeOverShow"
      v-on:changeEditTakeOverShow="handleEditTakeOverShow"
      v-bind:id="recordId"
    />
  </div>
</template>

<script lang="ts">
import DetailTakeOver from "./DetailTakeOver.vue";
import AddTakeOver from "./AddTakeOver.vue";
import EditTakeOver from "./EditTakeOver.vue";
import EquipmentDataService from "../services/equipments/EquipmentDataService";
import TakeOverService from "@/services/takeover/TakeOverService";
import TakeOverRecord from "@/types/TakeOverRecord";
import { Vue, Options, Prop, Emit, Ref } from "vue-property-decorator";
import Equipment from "@/types/Equipment";
@Options({
  components: {
    DetailTakeOver,
    AddTakeOver,
    EditTakeOver,
  },
})
export default class DetailEquipment extends Vue {
  equipment: Equipment | null = null;
  isDetailTakeOverShow: Boolean = false;
  isAddTakeOverShow: Boolean = false;
  isEditTakeOverShow: Boolean = false;
  recordsOfEquipment: TakeOverRecord[] = [];
  currentPage: number = 1;
  currentLimit: number = 20;
  currentTakeOverStatus: string | null = null;
  currentTakeOverType: string | null = null;
  keyUser: string | null = null;
  keyTakeOverPerson: string | null = null;
  totalPages: number = 0;
  public recordId: number = 0;
  type: any = {
    1: "Bàn giao thiết bị mới",
    2: "Bàn giao thiết bị sau khi sửa chữa",
  };
  mounted() {
    const idParams = this.$route.params.id;
    this.retrieveDetailEquipment(idParams);
    this.retrieveRecordsOfEquipment(this.getQueryParams());
  }
  async retrieveDetailEquipment(id: any) {
    const response = await EquipmentDataService.getEquipmentDetail(id)
      .then((res) => {
        console.log(res.data);
        this.equipment = res.data;
      })
      .catch((err) => console.log(err));
  }

  handleAddTakeOverShow(data: Boolean) {
    this.isAddTakeOverShow = data;
    this.isDetailTakeOverShow = false;
    this.isEditTakeOverShow = false;
  }
  handleDetailTakeOverShow(data: Boolean) {
    this.isDetailTakeOverShow = data;
    this.isAddTakeOverShow = false;
    this.isEditTakeOverShow = false;
  }

  handleEditTakeOverShow(data: Boolean) {
    console.log("handle");
    console.log(data);
    this.isDetailTakeOverShow = false;
    this.isAddTakeOverShow = false;
    this.isEditTakeOverShow = data;
  }

  retrieveRecordsOfEquipmentBySearch() {
    if (this.currentTakeOverStatus == "-1") this.currentTakeOverStatus = null;
    if (this.currentTakeOverType == "-1") this.currentTakeOverType = null;
    if (this.keyTakeOverPerson == "") this.keyTakeOverPerson = null;
    if (this.keyUser == "0") this.keyUser = null;
    this.currentPage = 1;
    this.retrieveRecordsOfEquipment(this.getQueryParams());
  }

  async retrieveRecordsOfEquipment(params: String) {
    await TakeOverService.getRecordsBySearch(params)
      .then((res) => {
        console.log(res.data);
        console.log(params);
        this.recordsOfEquipment = res.data.take_over_list;
        this.totalPages = res.data.n_pages;
      })
      .catch((err) => {
        alert(err.response.data);
      });
  }

  getQueryParams() {
    const queryParams: any = {
      page: this.currentPage,
      limit: this.currentLimit,
      username: this.keyUser,
      take_over_person: this.keyTakeOverPerson,
      type_take_over: this.currentTakeOverType,
      status: this.currentTakeOverStatus,
      equipment_id: this.$route.params.id,
    };
    Object.keys(queryParams).forEach((key) => {
      if (queryParams[key] === null || queryParams[key] === undefined) {
        delete queryParams[key];
      }
    });
    this.$router.push({ name: "DetailEquipment", query: queryParams });
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
  deleteRecord(id: number) {
    console.log("handle event1");
    if (confirm("Bạn có chắc chắn muốn xóa bản ghi bàn giao này ?")) {
      TakeOverService.deleteById(id)
        .then((res) => alert("Delete Successfully !!"))
        .then(() => this.retrieveRecordsOfEquipment(this.getQueryParams()))
        .catch((err) => alert(err.response.data));
    }
  }

  async onClickFirstPage() {
    this.currentPage = 1;
    this.retrieveRecordsOfEquipment(this.getQueryParams());
  }
  onClickNextPage() {
    if (this.currentPage != this.totalPages) {
      this.currentPage++;
      this.retrieveRecordsOfEquipment(this.getQueryParams());
    }
  }
  onClickPreviousPage() {
    if (this.currentPage != 1) {
      this.currentPage--;
      this.retrieveRecordsOfEquipment(this.getQueryParams());
    }
  }
  onClickLastPage() {
    this.currentPage = this.totalPages;
    this.retrieveRecordsOfEquipment(this.getQueryParams());
  }
}
</script>

<style></style>
