<template>
  <div class=" absolute h-screen top-0 right-0   w-1/2  shadow-2xl border-l-2 border-indigo-300  rounded-none  ">
    <div
      class="grid grid-cols-4 text-start border-b-2 border-indigo-300 w-auto   font-semibold  text-base self-start text-black bg-indigo-500">
      <h1 class="px-2 pt-2 pb-1 col-span-3 text-lg font-medium text-white w-auto ">Thu hồi thiết bị</h1>
      <button
        class="place-self-end bg-indigo-500 hover:bg-indigo-200 m-2 transition-colors   w-auto text-white  rounded-md focus:outline-none"
        v-on:click="changeShow(false)">
        <fa icon="xmark" class="px-2 py-2"></fa>
      </button>
    </div>

    <div class="relative overflow-x-hidden overflow-auto bg-indigo-100  h-4/5">
      <div class=" pl-3 text-sm  grid grid-cols-3 grid-flow-row mt-2 mb-3.5">
        <div class="p-1   font-medium text-gray-700">Mã thiết bị</div>
        <div class="p-1  col-span-2   font-medium text-gray-700">Tên thiết bị</div>

        <div>
          <div
            class=" mx-1  px-2 py-1.5 border bg-gray-200 focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded focus:outline-none text-gray-700">
            {{ device_id }}</div>
        </div>
        <div class="col-span-2">
          <div
            class=" mx-1  px-2 py-1.5 border bg-gray-200 focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded focus:outline-none text-gray-700">
            {{ equipment_name }}</div>
        </div>
        <div class="p-1   font-medium text-gray-700">Chi phí</div>
        <div class="p-1  col-span-2   font-medium text-gray-700">Thời gian thu hồi</div>

        <div>
          <input type="number"
            class=" mx-1  px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded focus:outline-none text-black"
            placeholder="" v-model="record.cost" />
        </div>
        <div class="flex flex-row col-span-2 w-11/12">
          <Datepicker class="
              mx-1
              w-11/12
              border
              focus:ring-gray-500
              hover:border-gray-900
              lg:text-base
              sm:text-sm
              border-gray-300
              rounded
              focus:outline-none
              text-black
            " v-model="editDate" :format="format" />
        </div>


        <div class="p-1     font-medium text-gray-700">Người trả thiết bị</div>
        <div class="pl-1 col-span-2  font-medium text-gray-700">Loại thu hồi</div>
        <div>
          <v-select class="
              mx-1
              bg-white
              border
              focus:ring-gray-500
              w-11/12
              hover:border-gray-900
              lg:text-sm
              sm:text-sm
              border-gray-300
              rounded
              focus:outline-none
              text-black
            " :options="options" v-model="user" :get-option-label="(option) => option.username"
            :dropdown-should-open="dropdownShouldOpen">
            <template #search="{ attributes, events }">
              <input class="vs__search
              bg-white
              lg:text-base
              sm:text-sm
              focus:outline-none
              text-black" v-bind="attributes" v-on="events" @input="retrieveUser" />
            </template>
            <template #option="{ username, fullname }">
              {{ fullname }}
              <br />
              <cite>{{ username }} </cite>
            </template>
          </v-select>

        </div>
        <div class="col-span-2">

          <select v-model="record.type_take_back" id="type" name="type" autocomplete="type-name"
            class=" mx-1  px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded-md focus:outline-none text-black">
            <option value="1">Hoàn trả thiết bị khi nghỉ việc</option>
            <option value="2">Thu hồi thiết bị hư hỏng để sửa chữa</option>
            <option value="3">Đền bù thiết bị sử dụng bị mất</option>
            <option value="4">Nhân viên bù tiền mua thiết bị</option>
          </select>
        </div>
        <div class="p-1   font-medium text-gray-700">Người thu hồi</div>
        <div class="p-1  col-span-2 font-medium text-gray-700">Người xác nhận</div>

        <div>
          <v-select class="
              mx-1
              bg-white
              border
              focus:ring-gray-500
              w-11/12
              hover:border-gray-900
              lg:text-sm
              sm:text-sm
              border-gray-300
              rounded
              focus:outline-none
              text-black
            " :options="options" v-model="take_back_person" :get-option-label="(option) => option.username"
            :dropdown-should-open="dropdownShouldOpen" @change="changeTakeBackPerson">
            <template #search="{ attributes, events }">
              <input class="vs__search
              bg-white
              lg:text-base
              sm:text-sm
              focus:outline-none
              text-black" v-bind="attributes" v-on="events" @input="retrieveUser" />
            </template>
            <template #option="{ username, fullname }">
              {{ fullname }}
              <br />
              <cite>{{ username }} </cite>
            </template>
          </v-select>
        </div>
        <div>
          <v-select class="
              mx-1
              bg-white
              border
              focus:ring-gray-500
              w-11/12
              hover:border-gray-900
              lg:text-sm
              sm:text-sm
              border-gray-300
              rounded
              focus:outline-none
              text-black
            " :options="options" v-model="verifier" :get-option-label="(option) => option.username"
            :dropdown-should-open="dropdownShouldOpen">
            <template #search="{ attributes, events }">
              <input class="vs__search
              bg-white
              lg:text-base
              sm:text-sm
              focus:outline-none
              text-black" v-bind="attributes" v-on="events" @input="retrieveUser" />
            </template>
            <template #option="{ username, fullname }">
              {{ fullname }}
              <br />
              <cite>{{ username }} </cite>
            </template>
          </v-select>
        </div>

      </div>
      <div class="px-3 pt-2 bg-indigo-100 grid grid-cols-1 text-sm w-full grid-flow-row border-t border-gray-300">
        <div class="pl-1 font-medium text-gray-700">Message</div>
        <textarea id="w3review" name="w3review" rows="3" cols="50" class="mx-1 my-2 px-2 py-1.5 border rounded"
          v-model="record.message"></textarea>
        <div class="pl-1 font-medium text-gray-700">Tệp đính kèm</div>
        <!-- <div class="mx-2 bg-gray-50 w-full h-24 my-2"> 

       </div> -->
        <div class="mx-1 mt-2 mb-3">
          <div class="row">
            <div class="col-5">
              <label class="btn btn-default p-0">
                <input type="file" ref="file" @change="selectFiles" />
              </label>
            </div>
          </div>
          <span v-if="msgError" class="text-red-600 pb-2 text-base" role="alert">
            *{{ msgError }}
          </span>

          <div class="bg-white h-64 overflow-y-auto border-2 border-indigo-300">
            <div class="border-b-2 border-indigo-300 text-base text-orange-600 font-semibold flex flex-row">
              <fa icon="folder" class="px-2 py-1"></fa>
              <div>Selected Files</div>
            </div>
            <div class="grid grid-rows-2 " v-for="(file, index) in allFileInfo" :key="index">
              <div class="bg-gray-300 w-fit h-fit border rounded flex flex-row m-2">
                <fa icon="file-arrow-up" class="px-2 py-2"></fa>
                <div class="py-1">{{ file.file_name }}</div>
                <span class="close px-2 py-1" @click="deleteSelectedFile(index)">&times;</span>
              </div>
              <textarea id="message"
                class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="File description..." v-model="file.file_description"></textarea>
            </div>
          </div>
        </div>
      </div>

    </div>
    <div class="  bg-indigo-100  w-full  border-t border-gray-300">
      <!-- <div class="p-1 m-2 text-base  font-medium text-gray-700">Thông tin</div> -->
      <div class="    flex flex-row gap ">


        <button
          class=" bg-red-500 hover:bg-red-600 m-3.5 transition-colors  text-base w-auto text-white p-2 rounded-md focus:outline-none"
          @click="insertTakeBackRecord">
          <fa icon="rotate-left" class="px-1"></fa>
          Thu hồi
        </button>
        <button
          class=" bg-gray-800 hover:bg-gray-900 m-3.5 transition-colors   w-auto text-white p-2 rounded-md focus:outline-none"
          v-on:click="changeShow(false)">
          <fa icon="xmark" class="px-1 "></fa>
          Hủy
        </button>

      </div>
    </div>



  </div>
</template>

<script lang="ts">
import Datepicker from "@vuepic/vue-datepicker";
import UploadService from "../services/equipments/UploadFilesService";
import { Vue, Options, Emit, Ref, Prop } from "vue-property-decorator";
import TakeBackRecord from "@/types/TakeBackRecord";
import User from "@/types/User";
import UserService from "@/services/user/UserService";
import TakeBackService from "@/services/takeback/TakeBackService";
import FileInfo from "../types/FileInfo";
@Options({
  components: {
    Datepicker

  },
})
export default class AddTakeBack extends Vue {
  @Prop(String) device_id!: string
  @Prop(String) equipment_name!: string
  @Prop(String) equipment_id!: string

  record: TakeBackRecord = {
    equipment_id: "",
    username: null,
    take_back_time: "",
    verifier: null,
    take_back_person: null,
    type_take_back: "",
    message: null,
    cost: null,
    created_by: "tatthanh",
    metadata_info: {},
  }
  timeOut: any
  private options: User[] = [];
  public allFileInfo: FileInfo[] = [];
  user: User | null = null
  take_back_person: User | null = null
  verifier: User | null = null

  @Emit('changeAddTakeBackShow')
  changeShow(data: boolean) {
    return data
  }

  editDate: any = null;

  format(date: Date | null | undefined) {
    if (date === null || date === undefined) return null;
    else {
      return date.toLocaleString();

    }
  }

  @Ref("file") inpuFile!: HTMLInputElement;
  private msgError: string | null | undefined = null;
  private allFiles: File[] = [];
  selectFiles(e: InputEvent) {
    const value = e!.target as HTMLInputElement;
    this.allFiles = [];
    console.log(value.files);
    if (value.files != null) {
      for (let i = 0; i < value.files.length; i++) {
        const currentFile = value.files.item(i);
        if (currentFile != null && currentFile?.size > 5000000) {
          this.msgError = "Chọn file <= 5MB";
          this.allFiles = [];
          return;

        }
        if (currentFile != null) {
          this.allFiles.push(currentFile);
          this.allFileInfo.push({ file_url: "", file_description: "", file_extention: "", file_name: currentFile.name, size: "" })
        }
      }
      this.msgError = null;
    }
    console.log(this.allFileInfo);
  }
  deleteSelectedFile(index: number) {
    this.allFiles.splice(index, 1);
    this.allFileInfo.splice(index, 1)
  }
  async uploadFiles() {
    let obj = {};
    for (let i = 0; i < this.allFiles.length; i++) {
      await UploadService.uploadFile(this.allFiles[i]).then((response) => {
        const key = Object.keys(response.data);
        response.data[key[0]].file_description = this.allFileInfo[i].file_description;
        console.log(response.data);
        obj = Object.assign(response.data, obj);
      });
    }
    if (obj === null || obj === undefined) return {};
    return obj;
  }
  changeTakeBackPerson() {
    this.verifier = this.take_back_person
  }

  dropdownShouldOpen(VueSelect: any) {


    return VueSelect.search.length !== 0
  }

  async retrieveUser(event: Event) {
    //console.log(keyword);
    clearTimeout(this.timeOut);

    this.timeOut = setTimeout(() => {
      UserService.getBySearch((event.target as HTMLInputElement).value)
        .then((res) => {
          this.options = res.data.user_list;
          console.log(this.options);
        })
        .catch((err) => {
          alert(err.response.data);
        });
    }, 300);

  }

  async insertTakeBackRecord() {
    if (this.user == null) {
      alert("Hãy chọn người trả thiết bị!");

    }
    else if (this.take_back_person == null) {
      alert("Hãy chọn người thu hồi!");

    }
    else if (this.verifier == null) {
      alert("Hãy chọn người xác nhận !");

    }
    else if (this.editDate === null || this.editDate === undefined) {
      alert("Hãy nhập thời gian thu hồi");

    }
    else if (this.record.type_take_back == null || this.record.type_take_back == "") {
      alert("Hãy chọn loại thu hồi");

    }
    else {
      this.record.username = this.user.username
      this.record.take_back_person = this.take_back_person.username
      this.record.verifier = this.verifier.username
      this.record.take_back_time = this.editDate.getTime()
      this.record.metadata_info = await this.uploadFiles()
      this.record.equipment_id = this.equipment_id
      console.log(this.record)
      TakeBackService.add(this.record)
        .then(() => {
          this.$emit('addsuccess')
          alert("Thêm thông tin thu hồi cho thiết bị thành công !")
          this.changeShow(false)
        })
        .catch((err) => {
          const errors = err.response.data.errors[0];
          // console.log(errors);
          // let temp = "";
          // Object.values(errors).forEach((error) => {
          //   temp = temp + error + "\n";
          // });
          alert(errors);
        });
    }
  }

}



</script>

<style>
</style>