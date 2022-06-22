<template>
  <div
    class="
      absolute
      h-screen
      top-0
      right-0
      w-5/12
      shadow-2xl
      border-l-2 border-indigo-300
      rounded-none
    "
  >
    <div
      class="
        grid grid-cols-4
        text-start
        border-b-2 border-indigo-300
        w-auto
        font-semibold
        text-base
        self-start
        text-black
        bg-indigo-500
      "
    >
      <h1
        class="px-2 pt-2 pb-1 col-span-3 text-lg font-medium text-white w-auto"
      >
        Chỉnh sửa thông tin bàn giao
      </h1>
      <button
        class="
          place-self-end
          bg-indigo-500
          hover:bg-indigo-200
          m-2
          transition-colors
          w-auto
          text-white
          rounded-md
          focus:outline-none
        "
        v-on:click="changeShow(false)"
      >
        <fa icon="xmark" class="px-2 py-2"></fa>
      </button>
    </div>

    <div class="relative overflow-x-hidden overflow-auto bg-indigo-100 h-4/5">
      <div class="pl-3 text-sm grid grid-cols-3 grid-flow-row mt-2 mb-3.5">
        <div class="p-1 font-medium text-gray-700">Mã thiết bị</div>
        <div class="p-1 col-span-2 font-medium text-gray-700">Tên thiết bị</div>

        <div>
          <div
            class="
              mx-1
              px-2
              py-1.5
              border
              bg-gray-200
              focus:ring-gray-500
              w-5/6
              hover:border-gray-900
              lg:text-base
              sm:text-sm
              border-gray-300
              rounded
              focus:outline-none
              text-gray-700
            "
          >
            {{ record.device_id }}
          </div>
        </div>
        <div class="col-span-2">
          <div
            class="
              mx-1
              px-2
              py-1.5
              border
              bg-gray-200
              focus:ring-gray-500
              w-5/6
              hover:border-gray-900
              lg:text-base
              sm:text-sm
              border-gray-300
              rounded
              focus:outline-none
              text-gray-700
            "
          >
            {{ record.name }}
          </div>
        </div>
        <div class="p-1 font-medium text-gray-700">Chi phí</div>
        <div class="p-1 col-span-2 font-medium text-gray-700">
          Thời gian bàn giao
        </div>

        <div>
          <input
            type="number"
            class="
              mx-1
              px-2
              py-1.5
              border
              focus:ring-gray-500
              w-5/6
              hover:border-gray-900
              lg:text-base
              sm:text-sm
              border-gray-300
              rounded
              focus:outline-none
              text-black
            "
            placeholder=""
            v-model="record.cost"
          />
        </div>
        <div class="flex flex-row col-span-2 w-5/6">
          <input
            type="text"
            class="
              mx-1
              w-5/6
              px-2
              py-1.5
              border
              focus:ring-gray-500
              hover:border-gray-900
              lg:text-base
              sm:text-sm
              border-gray-300
              rounded
              focus:outline-none
              text-black
            "
            placeholder=""
            v-model="record.take_over_time"
          />
          <DatePicker
            class="ml-1.5 w-min inline-block"
            v-model="record.take_over_time"
          />
        </div>

        <div class="p-1 font-medium text-gray-700">Người bàn giao</div>
        <div class="pl-1 col-span-2 font-medium text-gray-700">
          Loại bàn giao
        </div>
        <div>
          <!-- <input
                type="text"
                class=" mx-1  px-2 py-1.5 border focus:ring-gray-500 w-5/6 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded focus:outline-none text-black"
                placeholder=""
                v-model="record.take_over_person"
              /> -->
          <v-select
            class="
              mx-1
              bg-white
              border
              focus:ring-gray-500
              w-5/6
              hover:border-gray-900
              lg:text-base
              sm:text-sm
              border-gray-300
              rounded
              focus:outline-none
              text-black
            "
            :options="['Canada', 'United States']"
            
            
          ></v-select>
        </div>
        <div class="col-span-2">
          <select
            v-model="record.type"
            id="type"
            name="type"
            autocomplete="type-name"
            class="
              mx-1
              px-2
              py-1.5
              border
              focus:ring-gray-500
              w-5/6
              hover:border-gray-900
              lg:text-base
              sm:text-sm
              border-gray-300
              rounded-md
              focus:outline-none
              text-black
            "
          >
            <option value="0">Bàn giao thiết bị mới</option>
            <option value="1">Bàn giao thiết bị sau khi sửa chữa</option>
          </select>
        </div>
        <div class="p-1 font-medium text-gray-700">Người nhận thiết bị</div>
        <div class="p-1 col-span-2 font-medium text-gray-700">
          Người xác nhận
        </div>

        <div>
          <input
            type="text"
            class="
              mx-1
              px-2
              py-1.5
              border
              focus:ring-gray-500
              w-5/6
              hover:border-gray-900
              lg:text-base
              sm:text-sm
              border-gray-300
              rounded
              focus:outline-none
              text-black
            "
            placeholder=""
            v-model="record.username"
          />
        </div>
        <div>
          <input
            type="text"
            class="
              mx-1
              px-2
              py-1.5
              border
              focus:ring-gray-500
              w-5/6
              hover:border-gray-900
              lg:text-base
              sm:text-sm
              border-gray-300
              rounded
              focus:outline-none
              text-black
            "
            placeholder=""
            v-model="record.verifier"
          />
        </div>
      </div>
      <div
        class="
          px-3
          pt-2
          bg-indigo-100
          grid grid-cols-1
          text-sm
          w-full
          grid-flow-row
          border-t border-gray-300
        "
      >
        <div class="pl-1 font-medium text-gray-700">Message</div>
        <textarea
          id="w3review"
          name="w3review"
          rows="3"
          cols="50"
          class="mx-1 my-2 px-2 py-1.5 border rounded"
          v-model="record.message"
        >
        </textarea>
        <div class="pl-1 font-medium text-gray-700">Tệp đính kèm</div>
        <!-- <div class="mx-2 bg-gray-50 w-full h-24 my-2"> 

       </div> -->
        <div class="mx-1 mt-2 mb-3">
          <div class="row">
            <div class="col-8">
              <label class="btn btn-default p-0">
                <input
                  type="file"
                  accept="image/*"
                  ref="file"
                  @change="selectImage"
                />
              </label>
            </div>
          </div>
          <div v-if="currentImage" class="progress">
            <div
              class="progress-bar progress-bar-info"
              role="progressbar"
              :aria-valuenow="progress"
              aria-valuemin="0"
              aria-valuemax="100"
              :style="{ width: progress + '%' }"
            >
              {{ progress }}%
            </div>
          </div>

          <div v-if="message" class="alert alert-secondary" role="alert">
            {{ message }}
          </div>
          <div class="card mt-3">
            <div class="card-header">List of Images</div>
            <ul class="list-group list-group-flush">
              <div v-for="(image, index) in allImageCurrentURL">
                <div>
                  <div class="img-wrap">
                    <span class="close" @click="deleteImage(index)"
                      >&times;</span
                    >
                    <img class="w-64 preview my-3" :src="image" alt="" />
                  </div>
                </div>
              </div>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="bg-indigo-100 w-full border-t border-gray-300">
      <!-- <div class="p-1 m-2 text-base  font-medium text-gray-700">Thông tin</div> -->
      <div class="flex flex-row gap">
        <button
          class="
            bg-sky-500
            hover:bg-sky-600
            m-3.5
            transition-colors
            text-base
            w-auto
            text-white
            p-2
            rounded-md
            focus:outline-none
          "
        >
          <fa icon="pen-to-square" class="px-1"></fa>
          Cập nhật
        </button>
        <button
          class="
            bg-red-500
            hover:bg-red-600
            m-3.5
            transition-colors
            w-auto
            text-white
            p-2
            rounded-md
            focus:outline-none
          "
          v-on:click="changeShow(false)"
        >
          <fa icon="xmark" class="px-1"></fa>
          Hủy
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import DatePicker from "./DatePicker.vue";
import UploadService from "../services/equipments/UploadFilesService";
import TakeOverService from "@/services/takeover/TakeOverService";
import TakeOverRecord from "@/types/TakeOverRecord";
import { Vue, Options, Prop, Emit, Ref } from "vue-property-decorator";
import "vue-select/dist/vue-select.css";
import Fuse from 'fuse.js';
import User from "@/types/User"

@Options({
  components: {
    DatePicker,
  },
})
export default class EditTakeOver extends Vue {
  record: TakeOverRecord = {
    id: "",
    equipment_id: "",
    username: "",
    take_over_time: "",
    status: "",
    verifier: "",
    take_over_person: "",
    type: "",
    message: "",
    cost: "",
    created_by: "",
    created_time: "",
    updated_by: "",
    updated_time: "",
    metadata_info: "",
    device_id: "",
    name: "",
  };
  @Prop() id!: number;

  @Emit("changeEditTakeOverShow")
  changeShow(data: boolean) {
    return data;
  }

  @Ref("file") inpuFile!: HTMLInputElement;
  private allImageCurrentURL: String[] = [];
  private currentImage: File | null | undefined = null;
  private allImageFile: File[] = [];
  selectImage(e: InputEvent) {
    const value = e!.target as HTMLInputElement;
    this.currentImage = value?.files?.item(0);
    if (this.currentImage != null) {
      const temp = URL.createObjectURL(this.currentImage);
      this.allImageFile.push(this.currentImage);
      this.allImageCurrentURL.push(temp);
    }
  }
  deleteImage(index: number) {
    this.allImageCurrentURL.splice(index, 1);
    this.allImageFile.splice(index, 1);
  }
  async getImageFile() {
    let obj = {};
    for (let i = 0; i < this.allImageFile.length; i++) {
      await UploadService.upload(this.allImageFile[i]).then((response) => {
        obj = Object.assign(response.data, obj);
      });
    }
    return obj;
  }

  async created() {
    console.log("edit");
    console.log(this.id);
    this.retrieveRecord();
  }

  async retrieveRecord() {
    await TakeOverService.getRecordById(this.id)
      .then((res) => {
        this.record = res.data;
        console.log(this.record);
        this.record.take_over_time = this.handleDate(
          this.record.take_over_time
        );
      })
      .catch((err) => {
        alert(err.response.data);
      });
  }
  handleDate(data: string | undefined) {
    if (data === undefined) return "";
    var d = new Date(Number(data));
    return d.toLocaleString();
  }

  user = [
    {
      username : 'tatthanh',
      fullname : 'Nguyễn Tất Thành'
    },
   {
      username : 'tatthanh@rever.vn',
      fullname : 'Nguyễn Tất Thành'
    },
    {
      username : 'tuongvy',
      fullname : 'Phạm Nguyễn Tường Vy'
    },
    {
      username : 'tuongvy@rever.vn',
      fullname : 'Phạm Nguyễn Tường Vy'
    },
  ]
  
  options: Fuse.IFuseOptions<User>  = {
    keys: ['username','fullname'],
};

  // fuseSearch(options :Fuse.IFuseOptions<User> , search) {
  //     const fuse = new Fuse(options, {
  //       keys: ['title', 'author.firstName', 'author.lastName'],
  //       shouldSort: true,
  //     })
  //     return search.length
  //       ? fuse.search(search).map(({ item }) => item)
  //       : fuse.list
  //   }
}
</script>

<style>
</style>