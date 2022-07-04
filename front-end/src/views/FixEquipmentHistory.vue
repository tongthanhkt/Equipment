<template>
  <div>
    <div class="relative flex bg-gray-200">
      <div class="flex-1 container mx-auto p-2">
        <div
          class="p-1 mt-8 w-auto h-auto mx-auto bg-gray-50 shalow-lg rounded-xl"
        >
          <div
            class="text-center p-2 border-b-2 border-indigo-300 w-full block font-semibold text-base self-start text-black"
          >
            <h1 class="text-2xl leading-relaxed">Lịch sử sửa chữa</h1>
          </div>
          <div class="grid grid-flow-col grid-rows-1">
            <div class="p-2 flex place-items-end w-auto">
              <span class="flex justify-start">
                <div
                  class="px-1 py-2 m-2 text-base grid grid-rows-1 grid-flow-col rounded-md h-fit w-fit border-2 border-blue-400 focus:border-blue-600 focus:ring focus:ring-opacity-40 focus:ring-indigo-500"
                >
                  <fa
                    icon="magnifying-glass"
                    class="text-gray-400 px-2 py-1"
                  ></fa>
                  <input
                    class="text-base bg-gray-50 w-5/6 focus:outline-none"
                    type="text"
                    placeholder="Tên người sửa chữa"
                    v-model="keyFixEquipmentPerson"
                    @input="retrieveRecordsBySearch"
                  />
                </div>
                <div
                  class="px-1 py-2 m-2 text-base grid grid-rows-1 grid-flow-col rounded-md h-fit w-fit border-2 border-blue-400 focus:border-blue-600 focus:ring focus:ring-opacity-40 focus:ring-indigo-500"
                >
                  <fa
                    icon="magnifying-glass"
                    class="text-gray-400 px-2 py-1"
                  ></fa>
                  <input
                    class="text-base bg-gray-50 w-5/6 focus:outline-none"
                    type="text"
                    placeholder="Mã thiết bị"
                    v-model="keyFixEquipmentDeviceId"
                    @input="retrieveRecordsBySearch"
                  />
                </div>
              </span>
            </div>
            <div class="p-2 flex justify-end w-auto">
              <select
                v-model="currentFixEquipmentStatus"
                @change="retrieveRecordsBySearch"
                name="takeback_status"
                id="takeback_status"
                class="bg-blue-500 m-2 text-white p-2 rounded w-auto"
              >
                <option value="null" disabled selected hidden>
                  Trạng thái
                </option>
                <option
                  value="-2"
                  class="bg-white text-black hover:bg-blue-700"
                >
                  Trạng thái
                </option>
                <option
                  value="-1"
                  class="bg-white text-black hover:bg-blue-700"
                >
                  Không sửa được
                </option>
                <option value="0" class="bg-white text-black hover:bg-blue-700">
                  Đang sửa
                </option>
                <option value="1" class="bg-white text-black hover:bg-blue-700">
                  Sửa thành công
                </option>
              </select>
            </div>
          </div>

          <div class="p-1 mx-1 my-2">
            <div class="min-w-full align-middle flex justify-center">
              <table
                class="hover:border-collapse min -w-full bg-gray-100 rounded-xl place-content-center"
              >
                <thead>
                  <tr class="border-b border-gray-500">
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Mã
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Mã thiết bị
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Tên thiết bị
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Người sửa chữa
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
                  </tr>
                </thead>

                <tbody>
                  <tr
                    class="hover:bg-gray-200 transition-colors border-b border-gray-200"
                    v-on:click="changeId(record.id), changeDetailShow(true)"
                    v-for="(record, index) in records"
                    :key="index"
                  >
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{ record.id }}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{ record.device_id }}
                      </div>
                    </td>

                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{ record.equipment_name }}
                      </div>
                    </td>

                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{ record.fixer }}
                      </div>
                    </td>

                    <td>
                      <div class="p-1 text-sm text-center">
                        <div
                          v-if="record.status == '-1'"
                          class="text-green-500 italic font-semibold"
                        >
                          Không sửa được
                        </div>
                        <div
                          v-else-if="record.status == '0'"
                          class="text-blue-500 italic font-semibold"
                        >
                          Đang sửa
                        </div>
                        <div
                          v-else-if="record.status == '1'"
                          class="text-blue-500 italic font-semibold"
                        >
                          Sửa thành công
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
                          <button
                            class="bg-gray-100 hover:bg-gray-300 m-1 transition-colors flex justify-center items-center w-auto text-blue-500 px-3.5 py-2 rounded-md focus:outline-none disabled:cursor-not-allowed disabled:opacity-50"
                            v-on:click.stop="
                              changeId(record.id), changeUpdateShow(true)
                            "
                            :disabled="record.take_over_status == '1'"
                          >
                            <fa icon="pen-to-square"></fa>
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
                  class="py-2 px-3 ml-0 disabled:cursor-not-allowed leading-tight text-gray-500 bg-white rounded-l-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  :disabled="currentPage == 1"
                >
                  First
                </button>
              </li>
              <li>
                <button
                  @click="onClickPreviousPage"
                  class="py-2 px-3 leading-tight disabled:cursor-not-allowed text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  :disabled="currentPage == 1"
                >
                  Preivous
                </button>
              </li>

              <li>
                <a
                  class="pb-3 leading-tight px-3 mt-4 disable text-white bg-blue-500 border border-gray-300 dark:border-gray-700 dark:bg-gray-700 dark:text-white"
                  >{{ currentPage }}</a
                >
              </li>
              <li>
                <button
                  @click="onClickNextPage"
                  class="py-2 px-3 leading-tight text-gray-500 disabled:cursor-not-allowed bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  :disabled="currentPage == totalPages"
                >
                  Next
                </button>
              </li>

              <li>
                <button
                  @click="onClickLastPage"
                  class="py-2 px-3 leading-tight disabled:cursor-not-allowed text-gray-500 bg-white rounded-r-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
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
    <!-- <DetailTakeBack
      v-if="isDetailTakeBackShow"
      v-on:changeDetailTakeBackShow="handleDetailTakeBackShow"
      v-on:changeUpdateTakeBackShow="handleUpdateTakeBackShow"
       v-on:deteteTakeBackRecord="deleteRecord"
      v-bind:id="recordId"
    />

    <UpdateTakeBack
      v-if="isUpdateTakeBackShow"
      v-on:changeUpdateTakeBackShow="handleUpdateTakeBackShow"
      v-bind:id="recordId"
    /> -->
  </div>
</template>

<script lang="ts">
import DetailFixEquipment from "./DetailFixEquipment.vue";
import UpdateFixEquipment from "./UpdateFixEquipment.vue";
import FixEquipmentService from "@/services/fixEquipment/FixEquipmentService";
import { Vue, Options, Emit, Prop } from "vue-property-decorator";
import FixEquipmentRecord from "@/types/FixEquipmentRecord";
// @Options({
//   components: {
//     DetailTakeBack,
//     UpdateTakeBack,
//   },
// })
export default class FixEquipmentHistory extends Vue {
  // isDetailTakeBackShow: Boolean = false;
  // isAddTakeBackShow: Boolean = false;
  // isUpdateTakeBackShow: Boolean = false;
  public records: FixEquipmentRecord[] = [];
  public currentPage: number = 1;
  public currentLimit: number = 10;
  public currentFixEquipmentStatus: string | null = null;
  public keyUser: string | null = null;
  keyFixEquipmentPerson: string | null = null;
  keyFixEquipmentDeviceId: string | null = null;
  totalPages: number = 0;
  public recordId: number = 0;
  @Prop() takeOverStatus!: string;
  // handleDetailTakeBackShow(data: Boolean) {
  //   this.isDetailTakeBackShow = data;
  //   this.isAddTakeBackShow = false;
  //   this.isUpdateTakeBackShow = false;
  // }

  // handleUpdateTakeBackShow(data: Boolean) {
  //   this.retrieveRecords(this.getQueryParams());
  //   this.isDetailTakeBackShow = false;
  //   this.isAddTakeBackShow = false;
  //   this.isUpdateTakeBackShow = data;
  // }
  async created() {
    this.retrieveRecords(this.getQueryParams());
  }
  async retrieveRecords(params: String) {
    await FixEquipmentService.getRecordsBySearch(params)
      .then((res) => {
        console.log(res.data);
        this.records = res.data.fix_equipment_list;
        this.totalPages = res.data.n_pages;
      })
      .catch((err) => {
        alert(err.response.data);
      });
  }

  retrieveRecordsBySearch() {
    if (this.currentFixEquipmentStatus == "-2")
      this.currentFixEquipmentStatus = null;
    if (this.keyFixEquipmentPerson == "") this.keyFixEquipmentPerson = null;
    if (this.keyUser == "0") this.keyUser = null;
    this.currentPage = 1;
    this.retrieveRecords(this.getQueryParams());
  }

  getQueryParams() {
    const queryParams: any = {
      page: this.currentPage,
      limit: this.currentLimit,
      username: this.keyUser,
      fixer: this.keyFixEquipmentPerson,
      status: this.currentFixEquipmentStatus,
      equipment_id: this.$route.params.id,
      device_id: this.keyFixEquipmentDeviceId,
    };
    Object.keys(queryParams).forEach((key) => {
      if (queryParams[key] === null || queryParams[key] === undefined) {
        delete queryParams[key];
      }
    });
    //this.$router.push({ name: "TakeBackHistory", query: queryParams });
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

  @Emit("deleteRecord")
  deleteRecordReq(id: number) {
    return id;
  }

  @Emit("changeRecordFixEquipmentId")
  changeId(id: string) {
    return parseInt(id);
  }

  @Emit("changeDetailFixEquipmentShow")
  changeDetailShow(data: boolean) {
    return data;
  }

  @Emit("changeUpdateFixEquipmentShow")
  changeUpdateShow(data: boolean) {
    return data;
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
}
</script>

<style></style>
