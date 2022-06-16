<style scoped>
.img-wrap {
  position: relative;
}
.img-wrap .close {
  position: absolute;
}
</style>
<template>
  <div class="relative bg-white shadow rounded-3xl sm:p-10 w-3/6 ml-80">
    <div>
      <div class="flex items-center space-x-5">
        <div class="block pl-2 font-semibold text-xl self-start text-gray-700">
          <h1 class="text-2xl leading-relaxed">Thêm mới thiết bị</h1>
        </div>
      </div>
      <div class="divide-y divide-gray-200">
        <div
          class="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7"
        >
          <div class="flex flex-row">
            <div class="flex flex-col w-48">
              <label class="leading-loose">Mã thiết bị</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.device_id"
              />
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Tên thiết bị</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-64 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.name"
              />
            </div>
          </div>
          <div class="flex flex-col">
            <label class="leading-loose">Danh mục</label>
            <select
              v-model="equipment.category_id"
              id="country"
              name="country"
              autocomplete="country-name"
              class="mt-1 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            >
              <option value="1">Máy tính</option>
              <option value="2">Bàn phím PC</option>
            </select>
          </div>
          <div class="flex flex-row">
            <div class="flex flex-col w-48">
              <label class="leading-loose">Tình trạng mới nhập</label>
              <select
                id="country"
                name="country"
                autocomplete="country-name"
                class="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                v-model="equipment.start_status"
              >
                <option value="1">New</option>
                <option value="2">Like New loại S (99%)</option>
                <option value="3">Like New loại A (99%)</option>
                <option value="4">Like New loại B (97%-98%)</option>
                <option value="5">Like New loại C (90%-95%)</option>
                <option value="6">Thiết bị cũ ( second hand)</option>
              </select>
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Trạng thái thiết bị</label>
              <select
                v-model="equipment.device_status"
                id="country"
                name="country"
                autocomplete="country-name"
                class="w-75px mt-1 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              >
                <option value="0">Bị mất</option>
                <option value="1">Sử dụng được</option>
                <option value="2">Bị hư hỏng</option>
              </select>
            </div>
          </div>
          <div class="flex flex-row">
            <div class="flex flex-col">
              <label class="leading-loose">Giá tiền</label>
              <input
                input
                type="number"
                class="w-32 px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                v-model="equipment.price"
              />
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Tháng / Năm</label>
              <select
                v-model="equipment.period_type"
                id="country"
                name="country"
                autocomplete="country-name"
                class="w-24 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              >
                <option value="1">Tháng</option>
                <option value="2">Năm</option>
              </select>
            </div>
            <div class="flex flex-col ml-10">
              <label class="leading-loose">Thời gian khấu hao</label>
              <input
                type="number"
                class="w-32 px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.depreciation_period"
              />
            </div>
          </div>
          <div class="flex flex-row w-36">
            <div class="flex flex-col">
              <label class="leading-loose">Thời gian nhập thiết bị</label>
              <DatePicker class="w-64" />
            </div>
            <div class="flex flex-col ml-14">
              <label class="leading-loose">Giá trị khấu hao</label>
              <input
                type="number"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-32 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.depreciated_value"
              />
            </div>
          </div>
          <div class="flex flex-row">
            <div class="flex flex-col">
              <label class="leading-loose">Tệp đính kèm </label>
              <div>
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
        </div>

        <div class="pt-4 flex items-center space-x-4">
          <button
            @click="saveEquipment"
            class="bg-blue-500 font-bold flex justify-center w-48 items-center text-white px-4 py-3 rounded-md focus:outline-none"
          >
            Thêm thiết bị
          </button>
          <button
            class="bg-red-500 font-bold flex justify-center items-center w-48 text-gray-900 px-4 py-3 rounded-md focus:outline-none"
          >
            <svg
              class="w-6 h-6 mr-3"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              ></path>
            </svg>
            Hủy
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import UploadService from "../services/equipments/UploadFilesService";
import EquipmentDataService from "../services/equipments/EquipmentDataService";
import DatePicker from "./DatePicker.vue";
import Equipment from "../types/Equipment";
import { Vue, Options, Ref } from "vue-property-decorator";
import UploadImage from "./UploadImage.vue";
@Options({
  components: {
    DatePicker,
    UploadImage,
  },
})
export default class AddEquipment extends Vue {
  private equipment: Equipment = {
    device_id: "",
    name: "",
    start_status: "",
    price: "",
    depreciated_value: "",
    depreciation_period: "",
    period_type: "",
    import_date: "07062022",
    take_over_status: "1",
    category_id: "1",
    device_status: "1",
    created_by: "tatthanh@rever.vn",
    create_time: "07062022",
    updated_by: "",
    updated_time: "",
    take_over_person_id: "",
    take_over_person_name: "",
    id: "",
    metadata_info: "",
    category_name: "",
  };

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
  upload() {
    console.log(this.allImageFile);
    console.log(this.allImageCurrentURL);
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
  async saveEquipment() {
    const data = {
      device_id: this.equipment.device_id,
      name: this.equipment.name,
      start_status: this.equipment.start_status,
      price: this.equipment.price,
      depreciation_period: this.equipment.depreciation_period,
      period_type: this.equipment.period_type,
      depreciated_value: this.equipment.depreciated_value,
      import_date: this.equipment.import_date,
      take_over_status: this.equipment.take_over_status,
      category_id: this.equipment.category_id,
      created_by: this.equipment.created_by,
      created_time: this.equipment.create_time,
      device_status: this.equipment.device_status,
      metadata_info: await this.getImageFile(),
    };
    console.log(data);
    EquipmentDataService.addData(data)
      .then(() => alert("Thêm thiết bị thành công !!"))
      .catch((err) => console.log(err.response.data.errors));
    // const a = this.allImageFile.forEach((imageFile) => {});
    // await Promise.all([a]).then((values) => console.log(values));

    // console.log(data);
    // this.upload();
    // EquipmentDataService.addData(data)
    //   .then((res) => alert("Thêm thiết bị thành công"))
    //   .catch((err) => console.log(err));
  }
}
</script>
