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
                    class="text-gray-400 px-2 py-1"
                  ></fa>
                  <input
                    class="text-base bg-gray-50 w-5/6 focus:outline-none"
                    type="text"
                    placeholder="Tên người sử dụng"
                    v-model="keyUser"
                    @input="retrieveRecordsBySearch"
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
                    @input="retrieveRecordsBySearch"
                  />
                </div>
              </span>
            </div>
            <div class="p-2 flex justify-end w-auto">
              <select
                v-model="currentTakeOverStatus"
                @change="retrieveRecordsBySearch"
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
                @change="retrieveRecordsBySearch"
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
            <div class="min-w-full align-middle flex justify-center">
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
                        {{type[record.type]}}
                      </div>
                        
                    </td>
                    <td>
                      <div class="p-1 text-sm text-center ">
                       <div v-if="record.status == '1'" class="text-green-500 italic font-semibold">Đã xác nhận </div>
                        <div v-else-if="record.status == '0'" class="text-blue-500 italic font-semibold">
                          Chờ xác nhận
                        </div>
                        
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
                              disabled:cursor-not-allowed
                              disabled:opacity-50
                            "
                            v-on:click.stop="
                              (recordId = parseInt(record.id)),
                                handleEditTakeOverShow(true)
                            "
                            :disabled="record.status == '1'"
                          >
                            <fa icon="pen-to-square"></fa>
                          </button>
                          <button
                            :disabled="record.status == '1'"
                            class="
                              disabled:cursor-not-allowed
                              disabled:opacity-50
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
                              deleteRecord(parseInt(record.id))
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
          <nav class="flex justify-center ">
            <ul class="flex  -space-x-px ">
              <li>
                <button
                  @click="onClickFirstPage"
                  class="py-2 px-3  ml-0 disabled:cursor-not-allowed leading-tight text-gray-500 bg-white rounded-l-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  :disabled='currentPage==1'
                  
                  >First</button
                >
              </li>
              <li>
                <button
                  @click="onClickPreviousPage"
                  class="py-2 px-3 leading-tight disabled:cursor-not-allowed text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  :disabled='currentPage==1'
                  >Preivous</button
                >
              </li>

              <li>
                <a
                  
                  
                  class="pb-3 leading-tight px-3 mt-4 disable text-white  bg-blue-500 border border-gray-300  dark:border-gray-700 dark:bg-gray-700 dark:text-white"
                  >{{currentPage}}</a
                >
              </li>
              <li>
                <button
                  @click="onClickNextPage"
                  class="py-2 px-3 leading-tight text-gray-500 disabled:cursor-not-allowed bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  :disabled='currentPage==totalPages'
                  >Next</button
                >
              </li>

              <li>
                <button
                  @click="onClickLastPage"
                  class="py-2 px-3 leading-tight disabled:cursor-not-allowed text-gray-500 bg-white rounded-r-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  :disabled='currentPage==totalPages'
                  >Last</button
                >
              </li>
            </ul>
            <ul class="flex  -space-x-px text-gray-500 m-2 text-sm ">
              Tổng số trang: {{totalPages}}
            </ul>
          </nav>
        </div>
      </div>
    </div>
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
  public records: TakeOverRecord[] = [];
  public  currentPage: number = 1;
  public currentLimit: number = 4;
  public currentTakeOverStatus: string | null = null;
  public currentTakeOverType: string | null = null;
  public keyUser: string | null = null;
  keyTakeOverPerson: string | null = null;
  totalPages: number = 0;
  public recordId :number = 0;
  type:any = {
    1 : 'Bàn giao thiết bị mới',
    2 : 'Bàn giao thiết bị sau khi sửa chữa'
  }

  handleDetailTakeOverShow(data: Boolean) {
    this.isDetailTakeOverShow = data;
    this.isAddTakeOverShow = false;
    this.isEditTakeOverShow = false;
  }

  handleEditTakeOverShow(data: Boolean) {
    this.retrieveRecords(this.getQueryParams());
    this.isDetailTakeOverShow = false;
    this.isAddTakeOverShow = false;
    this.isEditTakeOverShow = data;
  }
  async created(){
    this.retrieveRecords(this.getQueryParams())
  }
  

  async retrieveRecords(params:String){
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

  retrieveRecordsBySearch() {
    if (this.currentTakeOverStatus == "-1") this.currentTakeOverStatus = null;
    if (this.currentTakeOverType == "-1") this.currentTakeOverType = null;
    if (this.keyTakeOverPerson == "") this.keyTakeOverPerson = null;
    if (this.keyUser == "0") this.keyUser = null;
    this.currentPage = 1;
    this.retrieveRecords(this.getQueryParams());
  }

  getQueryParams(){
    const queryParams:any = {
      page: this.currentPage,
      limit: this.currentLimit,
      username: this.keyUser,
      take_over_person: this.keyTakeOverPerson,
      type_take_over: this.currentTakeOverType,
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
  async deleteRecord(id: number) {
   
    if (confirm("Bạn có chắc chắn muốn xóa bản ghi bàn giao này ?")) {
     await TakeOverService.deleteById(id)
        .then((res) => alert("Delete Successfully !!"))
        .then(() => this.retrieveRecords(this.getQueryParams()))
        .catch((err) => alert(err.response.data));
    }
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

<style>
</style>