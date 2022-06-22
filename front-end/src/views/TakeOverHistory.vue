<template>
  <div>
    <div class="relative flex bg-gray-200">
      <div class="flex-1 container mx-auto p-2">
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
                    class="text-gray-500 px-2 py-1"
                  ></fa>
                  <input
                    class="text-base bg-gray-50 w-11/12 focus:outline-none"
                    type="text"
                    placeholder="Nhập mã, tên nhân viên"
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
                    class="text-gray-500 px-2 py-1"
                  ></fa>
                  <input
                    class="text-base bg-gray-50 w-11/12 focus:outline-none"
                    type="text"
                    placeholder="Nhập mã, tên thiết bị"
                  />
                </div>
              </span>
            </div>
            <div class="p-2 flex justify-end w-auto">
              <select
                name="takeover_status"
                id="takeover_status"
                class="bg-blue-500 m-2 text-white p-2 rounded w-auto"
              >
                <option disabled selected hidden>Trạng thái bàn giao</option>
                <option value="0" class="bg-white text-black hover:bg-blue-700">
                  Chưa xác nhận
                </option>
                <option value="1" class="bg-white text-black hover:bg-blue-700">
                  Đã xác nhận
                </option>
              </select>
              <select
                name="takeover_status"
                id="takeover_status"
                class="bg-blue-500 m-2 text-white p-2 rounded w-auto"
              >
                <option disabled selected hidden>Loại bàn giao</option>
                <option value="0" class="bg-white text-black hover:bg-blue-700">
                  Bàn giao thiết bị mới
                </option>
                <option value="1" class="bg-white text-black hover:bg-blue-700">
                  Bàn giao thiết bị sau khi sửa
                </option>
              </select>
            </div>
          </div>

          <div class="p-1 mx-1 my-2">
            <div class="min-w-full align-middle flex justify-items-center">
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
                      Mã
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Mã thiết bị
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Tên thiết bị
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Người sử dụng
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Người bàn giao
                    </th>

                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Loại bàn giao
                    </th>
                    <th class="p-2 text-sm font-medium text-left text-gray-700">
                      Trạng thái bàn giao
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
                    v-on:click="recordId=parseInt(record.id),handleDetailTakeOverShow(true)"
                    v-for="(record, index) in records" :key="index"
                  >
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500" >
                       {{record.id}}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{record.device_id}}
                      </div>
                    </td>

                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{record.name}}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{record.username}}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{record.take_over_person}}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{record.type}}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{record.status}}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{record.created_by}}
                      </div>
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center text-gray-500">
                        {{handleDate(record.created_time)}}
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
                            "
                            v-on:click.stop="recordId=parseInt(record.id),handleEditTakeOverShow(true)"
                          >
                            <fa icon="pen-to-square"></fa>
                          </button>
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
                              text-red-500
                              px-3.5
                              py-2
                              rounded-md
                              focus:outline-none
                            "
                            v-on:click.stop="deleteRecord(record.id)"
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
          <nav aria-label="Page navigation example">
            <ul class="inline-flex -space-x-px">
              <li>
                <a
                  type="button"
                  class="
                    py-2
                    px-3
                    ml-0
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
                  >First</a
                >
              </li>
              <li>
                <a
                  type="button"
                  class="
                    py-2
                    px-3
                    leading-tight
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
                  >Preivous</a
                >
              </li>

              <li>
                <a
                  type="button"
                  aria-current="page"
                  class="
                    py-2
                    px-3
                    leading-tight
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
                  >1</a
                >
              </li>
              <li>
                <a
                  type="button"
                  class="
                    py-2
                    px-3
                    leading-tight
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
                  >Next</a
                >
              </li>

              <li>
                <a
                  type="button"
                  class="
                    py-2
                    px-3
                    leading-tight
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
                  >Last</a
                >
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
    <DetailTakeOver
      v-if="isDetailTakeOverShow"
      v-on:changeDetailTakeOverShow="handleDetailTakeOverShow"
      v-on:changeEditTakeOverShow="handleEditTakeOverShow"
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
import EditTakeOver from "./EditTakeOver.vue";
import TakeOverService from "@/services/takeover/TakeOverService";
import { Vue, Options } from "vue-property-decorator";
import TakeOverRecord from "@/types/TakeOverRecord";

@Options({
  components: {
    DetailTakeOver,
    EditTakeOver,
  },
})
export default class TakeOverHistory extends Vue {
  isDetailTakeOverShow: Boolean = false;
  isAddTakeOverShow: Boolean = false;
  isEditTakeOverShow: Boolean = false;
  private records: TakeOverRecord[] = [];
  currentPage: number = 1;
  currentLimit: number = 20;
  currentTakeOverStatus: number | null = null;
  currentTakeOverType: number | null = null;
  keyUser: string | null = null;
  keyTakeOverPerson: string | null = null;
  totalPages: number = 0;
  public recordId :number = 0;
  

  handleDetailTakeOverShow(data: Boolean) {
    this.isDetailTakeOverShow = data;
    this.isAddTakeOverShow = false;
    this.isEditTakeOverShow = false;
  }

  handleEditTakeOverShow(data: Boolean) {
    this.isDetailTakeOverShow = false;
    this.isAddTakeOverShow = false;
    this.isEditTakeOverShow = data;
  }
  async created(){
    this.retrieveTakeOverRecords(this.getQueryParams())
  }
  

  async retrieveTakeOverRecords(params:String){
      await TakeOverService.getRecordsBySearch(params)
      .then(res=>{
        console.log(res.data);
        this.records = res.data.take_over_list
        this.totalPages = res.data.n_pages

      })
      .catch(err=>{
        alert(err.response.data)
      })
  } 

  getQueryParams(){
    const queryParams:any = {
      page: this.currentPage,
      limit: this.currentLimit,
      username: this.keyUser,
      take_over_person: this.keyTakeOverPerson,
      type: this.currentTakeOverType,
      status: this.currentTakeOverStatus
    }
    Object.keys(queryParams).forEach((key) => {
      if (
        queryParams[key] === null ||
        queryParams[key] === undefined
      ) {
        delete queryParams[key];
      }
    });
    this.$router.push({ name: "TakeOverHistory", query: queryParams });
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
    return d.toLocaleString()
  }
  async deleteRecord(id: String) {
   
    if (confirm("Bạn có chắc chắn muốn xóa bản ghi bàn giao này ?")) {
     await TakeOverService.deleteById(id)
        .then((res) => alert("Delete Successfully !!"))
        .then(() => this.retrieveTakeOverRecords(this.getQueryParams()))
        .catch((err) => alert(err.response.data));
    }
  }
}
</script>

<style>
</style>