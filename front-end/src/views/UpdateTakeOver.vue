<style scoped>
.img-wrap {
  position: relative;
}

.hasImage:hover section {
  background-color: rgba(5, 5, 5, 0.4);
}

.hasImage:hover button:hover {
  background: rgba(5, 5, 5, 0.45);
}

#overlay p,
i {
  opacity: 0;
}

#overlay.draggedover {
  background-color: rgba(255, 255, 255, 0.7);
}

#overlay.draggedover p,
#overlay.draggedover i {
  opacity: 1;
}

.group:hover .group-hover\:text-blue-800 {
  color: #2b6cb0;
}

.img-wrap .close {
  position: absolute;
}
</style>
<template>
  <div class="absolute h-screen top-0 right-0 w-1/2 shadow-2xl border-l-2 border-indigo-300 rounded-none">
    <div
      class="grid grid-cols-4 text-start border-b-2 border-indigo-300 w-auto font-semibold text-base self-start text-black bg-indigo-500">
      <h1 class="px-2 pt-2 pb-1 col-span-3 text-lg font-medium text-white w-auto">
        Chỉnh sửa thông tin bàn giao - {{ id }}
      </h1>
      <button
        class="place-self-end bg-indigo-500 hover:bg-indigo-200 m-2 transition-colors w-auto text-white rounded-md focus:outline-none"
        v-on:click="changeShow(false)">
        <fa icon="xmark" class="px-2 py-2"></fa>
      </button>
    </div>

    <div class="relative overflow-x-hidden overflow-auto bg-indigo-100 h-4/5">
      <div class="pl-3 text-sm grid grid-cols-3 grid-flow-row mt-2 mb-3.5">
        <div class="p-1 font-medium text-gray-700">Mã thiết bị</div>
        <div class="p-1 col-span-2 font-medium text-gray-700">Tên thiết bị</div>

        <div>
          <div
            class="mx-1 px-2 py-1.5 border bg-gray-200 focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded focus:outline-none text-gray-700">
            {{ record.device_id }}
          </div>
        </div>
        <div class="col-span-2">
          <div
            class="mx-1 px-2 py-1.5 border bg-gray-200 focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded focus:outline-none text-gray-700">
            {{ record.equipment_name }}
          </div>
        </div>
        <div class="p-1 font-medium text-gray-700">Chi phí</div>
        <div class="p-1 col-span-2 font-medium text-gray-700">
          Thời gian bàn giao
        </div>

        <div>
          <input type="number"
            class="mx-1 px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded focus:outline-none text-black"
            placeholder="" v-model="record.cost" />
        </div>
        <div class="flex flex-row col-span-2 w-11/12">
          <Datepicker
            class="mx-1 w-11/12 border focus:ring-gray-500 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded focus:outline-none text-black"
            v-model="editDate" :format="format" />
        </div>

        <div class="p-1 font-medium text-gray-700">Người bàn giao</div>
        <div class="pl-1 col-span-2 font-medium text-gray-700">
          Loại bàn giao
        </div>
        <div>
          <!-- <input
                type="text"
                class=" mx-1  px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded focus:outline-none text-black"
                placeholder=""
                v-model="record.take_over_person"
              /> -->
          <v-select
            class="mx-1 bg-white border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-sm sm:text-sm border-gray-300 rounded focus:outline-none text-black"
            :options="options" v-model="take_over_person" :get-option-label="(option) => option.username"
            :dropdown-should-open="dropdownShouldOpen">
            <template #search="{ attributes, events }">
              <input class="vs__search bg-white lg:text-base sm:text-sm focus:outline-none text-black"
                v-bind="attributes" v-on="events" @input="retrieveUser" />
            </template>
            <template #option="{ username, fullname }">
              {{ fullname }}
              <br />
              <cite>{{ username }} </cite>
            </template>
          </v-select>
        </div>
        <div class="col-span-2">
          <select v-model="record.reason" id="type" name="type" autocomplete="type-name"
            class="mx-1 px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded-md focus:outline-none text-black">
            <option value="1">Bàn giao thiết bị mới</option>
            <option value="2">Bàn giao thiết bị sau khi sửa chữa</option>
          </select>
        </div>
        <div class="p-1 font-medium text-gray-700">Người nhận thiết bị</div>
        <div class="p-1 font-medium text-gray-700">Người xác nhận</div>
        <div class="p-1 font-medium text-gray-700">Trạng thái</div>
        <div>
          <v-select
            class="mx-1 bg-white border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-sm sm:text-sm border-gray-300 rounded focus:outline-none text-black"
            :options="options" v-model="user" :get-option-label="(option) => option.username"
            :dropdown-should-open="dropdownShouldOpen" @change="changeUser">
            <template #search="{ attributes, events }">
              <input class="vs__search bg-white lg:text-base sm:text-sm focus:outline-none text-black"
                v-bind="attributes" v-on="events" @input="retrieveUser" />
            </template>
            <template #option="{ username, fullname }">
              {{ fullname }}
              <br />
              <cite>{{ username }} </cite>
            </template>
          </v-select>
        </div>

        <div>
          <v-select
            class="mx-1 bg-white border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-sm sm:text-sm border-gray-300 rounded focus:outline-none text-black"
            :options="options" v-model="verifier" :get-option-label="(option) => option.username"
            :dropdown-should-open="dropdownShouldOpen">
            <template #search="{ attributes, events }">
              <input class="vs__search bg-white lg:text-base sm:text-sm focus:outline-none text-black"
                v-bind="attributes" v-on="events" @input="retrieveUser" />
            </template>
            <template #option="{ username, fullname }">
              {{ fullname }}
              <br />
              <cite>{{ username }} </cite>
            </template>
          </v-select>
        </div>
        <div>
          <select v-model="record.status" id="type" name="type" autocomplete="type-name"
            class="mx-1 px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded-md focus:outline-none text-black">
            <option value="1">Đã xác nhận</option>
            <option value="0">Chờ xác nhận</option>
          </select>
        </div>
      </div>
      <!-- <div
        class="px-3 pt-2 bg-indigo-100 grid grid-cols-1 text-sm w-full grid-flow-row border-t border-gray-300"
      >
        <div class="p-1 font-medium text-gray-700">Trạng thái</div>
        <select
          v-model="record.status"
          id="type"
          name="type"
          autocomplete="type-name"
          class="mx-1 px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded-md focus:outline-none text-black"
        >
          <option value="1">Đã xác nhận</option>
          <option value="0">Chờ xác nhận</option>
        </select>
      </div> -->
      <div class="px-3 pt-2 bg-indigo-100 grid grid-cols-1 text-sm w-full grid-flow-row border-t border-gray-300">
        <div class="pl-1 font-medium text-gray-700">Message</div>
        <textarea id="w3review" name="w3review" rows="3" cols="50" class="mx-1 my-2 px-2 py-1.5 border rounded"
          v-model="record.message">
        </textarea>
        <div>
          <div class="pl-1 font-medium text-gray-700">Tệp đính kèm</div>
          <div v-if="allFileName.length != 0">
            <div class="grid grid-rows-2 " v-for="(file, index) in allFileName" :key="index">
              <div class="bg-gray-300 w-fit h-fit border rounded flex flex-row m-2">
                <fa icon="file-arrow-up" class="px-2 py-2"></fa>
                <div class="py-1">{{ file.file_name }}</div>
                <span class="close px-2 py-1" @click="deleteSelectedFile(file.file_name, index)">&times;</span>
              </div>
              <textarea id="message"
                class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="Your message..." v-model="file.file_description"></textarea>
            </div>

          </div>
        </div>

        <div class="mx-1 mt-2 mb-3">
          <div class="row">
            <div class="col-8">
              <label class="px-2">Thêm tệp mới: </label>

              <input type="file" ref="file" @change="selectFiles" />
            </div>
          </div>
          <span v-if="msgError" class="text-red-600 pb-2 text-base" role="alert">
            *{{ msgError }}
          </span>

          <div class="bg-white h-64 overflow-y-auto border-2 border-indigo-300">
            <div class="border-b-2 border-indigo-300 text-sm text-orange-600 font-semibold flex flex-row">
              <fa icon="folder" class="px-2 py-1"></fa>
              <div class="p-1">Selected Files</div>
            </div>
            <div class="grid grid-rows-2 " v-for="(file, index) in allNewFileInfo" :key="index">
              <div class="bg-gray-300 w-fit h-fit border rounded flex flex-row m-2">
                <fa icon="file-arrow-up" class="px-2 py-2"></fa>
                <div class="py-1">{{ file.file_name }}</div>
                <span class="close px-2 py-1" @click="deleteNewSelectedFile(index)">&times;</span>
              </div>
              <textarea id="message"
                class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="Your message..." v-model="file.file_description"></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="bg-indigo-100 w-full border-t border-gray-300">
      <!-- <div class="p-1 m-2 text-base  font-medium text-gray-700">Thông tin</div> -->
      <div class="flex flex-row gap">
        <button
          class="bg-sky-500 hover:bg-sky-600 m-3.5 transition-colors text-base w-auto text-white p-2 rounded-md focus:outline-none"
          @click="updateTakeOverRecord">
          <fa icon="pen-to-square" class="px-1"></fa>
          Save
        </button>
        <button
          class="bg-red-500 hover:bg-red-600 m-3.5 transition-colors w-auto text-white p-2 rounded-md focus:outline-none"
          v-on:click="changeShow(false)">
          <fa icon="xmark" class="px-1"></fa>
          Hủy
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import Datepicker from "@vuepic/vue-datepicker";
import { ref } from "vue";
import TakeOverService from "@/services/takeover/TakeOverService";
import HistoricalService from "@/services/historical/HistoricalService";
import HistoricalRecord from "@/types/HistoricalRecord";
import { Vue, Options, Prop, Emit, Ref } from "vue-property-decorator";
import "vue-select/dist/vue-select.css";
import FileInfo from "../types/FileInfo";
import User from "@/types/User";
import UserService from "@/services/user/UserService";
import UploadService from "../services/equipments/UploadFilesService";

@Options({
  components: {
    Datepicker,
  },
})
export default class UpdateTakeOver extends Vue {
  record: HistoricalRecord = {
    id: "",
    equipment_id: "",
    user: "",
    action_time: "",
    status: "",
    verifier: "",
    performer: "",
    type_action: 1,
    message: null,
    cost: null,
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
  @Prop() id!: number;
  options: User[] = [];
  user: User = {
    username: this.record.user,
    fullname: "",
  };
  take_over_person: User = {
    username: this.record.performer,
    fullname: "",
  };
  verifier: User = {
    username: this.record.verifier,
    fullname: "",
  };
  timeOut: any;
  editDate: any = null;
  currentMetaData: any;
  currentFileName: string[] = [];
  public allFileName: FileInfo[] = [];
  public allFileInfo: any;
  public allNewFileInfo: FileInfo[] = [];

  @Emit("changeUpdateTakeOverShow")
  changeShow(data: boolean) {
    return data;
  }

  format(date: Date | null | undefined) {
    if (date === null || date === undefined) return null;
    else {
      return date.toLocaleString();
    }
  }

  @Ref("file") inpuFile!: HTMLInputElement;
  msgError: string | null | undefined = null;
  allFiles: File[] = [];
  selectFiles(e: InputEvent) {
    const value = e!.target as HTMLInputElement;
    this.allFiles = [];
    console.log(value.files);
    if (value.files != null) {
      console.log(value.files.length);
      for (let i = 0; i < value.files.length; i++) {
        const currentFile = value.files.item(i);
        if (currentFile != null && currentFile?.size > 5000000) {
          this.msgError = "Chọn file <= 5MB";
          this.allFiles = [];

          return;
        }
        if (currentFile != null) {
          console.log(currentFile);
          this.allFiles.push(currentFile);
          this.allNewFileInfo.push({ file_url: "", file_description: "", file_extention: "", file_name: currentFile.name, size: "" })
        }
      }
      this.msgError = null;
    }
  }
  deleteSelectedFile(file_name: string, index: number) {
    console.log(this.allFileInfo);
    delete this.allFileInfo[`${file_name}`];
    this.allFileName.splice(index, 1);
  }
  deleteNewSelectedFile(index: number) {
    this.allNewFileInfo.splice(index, 1);
    this.allFiles.splice(index, 1);
  }
  async getUploadFile() {
    let obj = {};
    let temp;
    for (let i = 0; i < this.allFiles.length; i++) {
      await UploadService.uploadFile(this.allFiles[i])
        .then((response) => {
          const key = Object.keys(response.data);
          response.data[key[0]].file_description = this.allNewFileInfo[i].file_description;
          console.log(response.data);
          obj = Object.assign(response.data, obj);
        })
        .catch((error) => {
          temp = 0;
        });
    }
    if (temp == 0) {
      return 0;
    }
    return obj;
  }
  changeUser() {
    this.verifier = this.user;
  }

  async created() {
    await this.retrieveRecord();
    this.options.push(this.user);
    this.options.push(this.take_over_person);
    this.options.push(this.verifier);
  }

  deleteFiles: string[] = [];




  dropdownShouldOpen(VueSelect: any) {
    console.log(this.editDate);
    return VueSelect.search.length !== 0;
  }

  async retrieveRecord() {
    await HistoricalService.getRecordById(this.id, 1)
      .then((res) => {
        console.log(res.data);
        this.record = res.data;
        console.log(res.data);
        // this.record.take_over_time = this.handleDate(
        //   this.record.take_over_time
        // );
        if (this.record.cost != null)
          this.record.cost = parseFloat(this.record.cost).toString();
        this.editDate = ref(new Date(Number(this.record.action_time)));
        this.allFileInfo = res.data.metadata_info;
        this.allFileName = Object.values(res.data.metadata_info);
        console.log(this.allFileInfo);

        this.user = {
          username: this.record.user,
          fullname: "",
        };
        this.take_over_person = {
          username: this.record.performer,
          fullname: "",
        };
        this.verifier = {
          username: this.record.verifier,
          fullname: "",
        };
      })
      .catch((err) => {
        alert(err.response.data);
      });
  }
  handleDate(event: Event) {
    if (
      (event.target as HTMLInputElement).value === undefined ||
      (event.target as HTMLInputElement).value === null
    )
      return "";
    var d = new Date((event.target as HTMLInputElement).value);
    this.record.action_time = d.toLocaleString();
  }

  async updateTakeOverRecord() {
    if (this.user == null) {
      alert("Hãy chọn người nhận thiết bị!");
    } else if (this.take_over_person == null) {
      alert("Hãy chọn người bàn giao!");
    } else if (this.verifier == null) {
      alert("Hãy chọn người xác nhận !");
    } else if (this.editDate === null || this.editDate === undefined) {
      alert("Hãy nhập thời gian bàn giao");
    } else if (
      this.record.reason == null ||
      this.record.reason == ""
    ) {
      alert("Hãy chọn loại bàn giao");
    }
    // else if (this.msgError!=null){
    //   alert("Chọn file <= 5MB");
    // }
    else {
      const newFileUpload = await this.getUploadFile();
      this.record.metadata_info = Object.assign(this.allFileInfo, newFileUpload);
      const data = {
        id: this.record.id,
        equipment_id: this.record.equipment_id,
        username: this.user?.username,
        take_over_time: this.editDate.getTime(),
        verifier: this.verifier?.username,
        take_over_person: this.take_over_person?.username,
        type_take_over: this.record.reason,
        message: this.record.message,
        cost: this.record.cost,
        updated_by: "tatthanh",
        metadata_info: this.record.metadata_info,
        status: this.record.status,
      };
      if (this.record.cost == "") data.cost = null;

      console.log(data);
      TakeOverService.update(data)
        .then(() => {
          this.$emit('changeData');
          alert("Cập nhật thông tin bàn giao thành công !");
          this.changeShow(false);
        })
        .catch((err) => {
          const errors = err.response.data.errors[0];
          console.log(errors);
          let temp = "";
          Object.values(errors).forEach((error) => {
            temp = temp + error + "\n";
          });
          alert(errors);
        });
    }
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
}
</script>

<style>
</style>
