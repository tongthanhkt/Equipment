<style scoped>
.img-wrap {
  position: relative;
}
.img-wrap .close {
  position: absolute;
}
</style>
<template>
  <div class="flex justify-center relative">
    <div class="bg-white shadow rounded-3xl sm:p-10 mt-8">
      <h1 class="text-2xl leading-relaxed text-center font-bold">
        THÊM MỚI THIẾT BỊ
      </h1>
      <div>
        <div class="flex flex-row gap-6 mt-8">
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Mã thiết bị</p>
            <input
              type="text"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
              placeholder=""
              v-model="equipment.device_id"
            />
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Tên thiết bị</p>
            <input
              type="text"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
              placeholder=""
              v-model="equipment.name"
              required
            />
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Danh mục</p>
            <select
              v-model="equipment.category_id"
              id="country"
              name="country"
              autocomplete="country-name"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
            >
              <option
                v-for="(category, index) in categories"
                v-bind:value="category.id"
              >
                {{ category.name }}
              </option>
            </select>
          </div>
        </div>
        <div class="flex flex-row gap-6 mt-8">
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Tình trạng mới nhập</p>
            <select
              id="country"
              name="country"
              autocomplete="country-name"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
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
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Giá tiền</p>
            <input
              input
              type="number"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
              v-model="equipment.price"
            />
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Trạng thái thiết bị</p>
            <select
              @change="handleLostEquipment()"
              v-model="equipment.device_status"
              id="country"
              name="country"
              autocomplete="country-name"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
            >
              <option value="0">Bị mất</option>
              <option value="1">Sử dụng được</option>
              <option value="2">Bị hư hỏng</option>
            </select>
          </div>

          <div v-if="equipment.device_status == '0'" class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Đền bù</p>
            <select
              v-model="equipment.compensation_status"
              id="country"
              name="country"
              autocomplete="country-name"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
            >
              <option value="0">Chưa đền bù</option>
              <option value="1">Đã đền bù</option>
            </select>
          </div>
        </div>
        <div class="flex flex-row gap-6 mt-8">
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Giá trị khấu hao</p>
            <input
              type="number"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
              placeholder=""
              v-model="equipment.depreciated_value"
            />
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Thời gian khấu hao</p>
            <input
              type="number"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
              placeholder=""
              v-model="equipment.depreciation_period"
            />
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Tháng / Năm</p>
            <select
              v-model="equipment.period_type"
              id="country"
              name="country"
              autocomplete="country-name"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
            >
              <option value="1">Tháng</option>
              <option value="2">Năm</option>
            </select>
          </div>
        </div>
        <div class="flex flex-row gap-6 mt-8">
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">
              Thời gian nhập thiết bị
            </p>
            <DatePicker v-model="equipment.import_date" />
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Tệp đính kèm</p>
            <div class="row">
              <div class="col-8">
                <p class="btn btn-default p-0">
                  <input
                    name="myImage"
                    accept="image/png, image/gif, image/jpeg"
                    type="file"
                    ref="file"
                    @change="selectImage"
                  />
                </p>
              </div>
            </div>
          </div>
        </div>
        <div class="flex flex-row gap-6 mt-8 w-full">
          <div>
            <div v-if="currentImage" class="progress">
              <div
                class="progress-bar progress-bar-info w-80"
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
            <div class="card mt-3 w-80">
              <div class="card-header">List of Images</div>
              <ul class="list-group list-group-flush">
                <div v-for="(image, index) in allImageCurrentURL">
                  <div>
                    <div class="img-wrap">
                      <span class="close" @click="deleteImage(index)"
                        >&times;</span
                      >
                      <img class="preview my-3 w-80" :src="image" alt="" />
                    </div>
                  </div>
                </div>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <div class="flex flex-row gap-6 mt-8 justify-center">
        <button
          @click="saveEquipment"
          class="bg-blue-500 font-bold flex justify-center w-48 items-center text-white px-4 py-3 rounded-md focus:outline-none"
        >
          Thêm thiết bị
        </button>
        <a href="/equipment">
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
          </button></a
        >
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import UploadService from "../services/equipments/UploadFilesService";
import EquipmentDataService from "../services/equipments/EquipmentDataService";
import CategoryService from "../services/category/categoryService";
import DatePicker from "./DatePicker.vue";
import Equipment from "../types/Equipment";
import { Vue, Options, Ref } from "vue-property-decorator";
import UploadImage from "./UploadImage.vue";
import { numberLiteral } from "@babel/types";
@Options({
  components: {
    DatePicker,
    UploadImage,
  },
})
export default class AddEquipment extends Vue {
  private equipment: Equipment = {
    device_id: "",
    compensation_status: "",
    name: "",
    start_status: "",
    price: "",
    depreciated_value: null,
    depreciation_period: "",
    period_type: "",
    import_date: "",
    take_over_status: "0",
    category_id: "",
    device_status: "",
    created_by: "tatthanh@rever.vn",
    created_time: "1655372446944",
    updated_by: "",
    updated_time: "",
    take_over_person_id: "",
    take_over_person_name: "",
    id: "",
    metadata_info: "",
    category_name: "",
  };
  public categories = [];
  private errors: string[] = [];
  private importDate: string = "";
  @Ref("file") inpuFile!: HTMLInputElement;
  private allImageCurrentURL: String[] = [];
  private currentImage: File | null | undefined = null;
  private allImageFile: File[] = [];
  mounted() {
    this.retrieveCategories();
  }
  async retrieveCategories() {
    CategoryService.getAllCategories("").then((res: any) => {
      this.categories = res.data.categories;
    });
  }
  selectImage(e: InputEvent) {
    const value = e!.target as HTMLInputElement;
    this.currentImage = value?.files?.item(0);
    if (this.currentImage != null) {
      const temp = URL.createObjectURL(this.currentImage);
      this.allImageFile.push(this.currentImage);
      this.allImageCurrentURL.push(temp);
    }
  }
  handleLostEquipment() {}
  deleteImage(index: number) {
    this.allImageCurrentURL.splice(index, 1);
    this.allImageFile.splice(index, 1);
  }
  async getImageFile() {
    let obj = {};
    for (let i = 0; i < this.allImageFile.length; i++) {
      await UploadService.upload(this.allImageFile[i])
        .then((response) => {
          obj = Object.assign(response.data, obj);
        })
        .catch((error) => alert(error.message));
    }
    return obj;
  }
  checkValidateForm() {
    if (this.equipment.name?.length == 0) {
      this.errors?.push("Name's device required");
    }
    if (this.equipment.category_id?.length == 0) {
      this.errors?.push("Category's device required");
    }
    if (this.equipment.start_status?.length == 0) {
      this.errors?.push("Start status device required");
    }
    if (this.equipment.device_status?.length == 0) {
      this.errors?.push("Device status required");
    }
    if (this.equipment.price?.length == 0) {
      this.errors?.push("Price required");
    }
    if (this.equipment.depreciated_value == null) {
      this.errors?.push("Depreciated value id required");
    } else if (
      this.equipment.depreciated_value < 0 ||
      this.equipment.depreciated_value > 1
    ) {
      this.errors?.push("Depreciated value id must be >0 and <1");
    }
    if (this.equipment.depreciation_period?.length == 0) {
      this.errors?.push("Depreciated period id required");
    }
    if (
      this.equipment.import_date?.length == 0 ||
      this.equipment.import_date == null
    ) {
      this.errors?.push(" Import date required");
    }
    if (
      this.equipment.device_status == "0" &&
      this.equipment.compensation_status?.length == 0
    ) {
      this.errors?.push("Compensation status required");
    }
  }
  async saveEquipment() {
    let errors = "";
    this.checkValidateForm();
    if (this.errors.length == 0) {
      var temp = new Date(this.equipment.import_date!);
      var milliseconds = temp.getTime().toString();
      const data = {
        device_id: this.equipment.device_id,
        name: this.equipment.name,
        start_status: this.equipment.start_status,
        price: this.equipment.price,
        depreciation_period: this.equipment.depreciation_period,
        period_type: this.equipment.period_type,
        depreciated_value: this.equipment.depreciated_value,
        import_date: milliseconds,
        take_over_status: this.equipment.take_over_status,
        category_id: this.equipment.category_id,
        created_by: this.equipment.created_by,
        created_time: this.equipment.created_time,
        device_status: this.equipment.device_status,
        compensation_status: this.equipment.compensation_status,
        metadata_info: await this.getImageFile(),
      };
      EquipmentDataService.addData(data)
        .then(() => alert("Thêm thiết bị thành công"))
        .catch((err) => {
          const errors = err.response.data.errors[0];
          console.log(errors);
          let temp = "";
          Object.values(errors).forEach((error) => {
            temp = temp + error + "\n";
          });
          alert(temp);
        });
      const a = this.allImageFile.forEach((imageFile) => {});
      await Promise.all([a]);
    } else {
      for (let i = 0; i < this.errors.length; i++) {
        errors = errors + this.errors[i] + "\n";
      }
      alert(errors);
    }
    this.errors = [];
  }
}
</script>
