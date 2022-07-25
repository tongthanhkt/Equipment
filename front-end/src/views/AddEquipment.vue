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
  <div class="flex justify-center relative">
    <div class="bg-white shadow rounded-3xl sm:p-10 mt-8">
      <h1 class="text-2xl leading-relaxed text-center font-bold">
        THÊM MỚI THIẾT BỊ
      </h1>
      <div>
        <div class="flex flex-row gap-6 mt-8">
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Mã thiết bị</p>
            <input type="text"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
              placeholder="" v-model="equipment.device_id" />
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Tên thiết bị</p>
            <input type="text"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
              placeholder="" v-model="equipment.name" required />
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Danh mục</p>
            <select v-model="equipment.category_id" id="country" name="country" autocomplete="country-name"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600">
              <option v-for="(category, index) in categories" v-bind:value="category.id">
                {{ category.name }}
              </option>
            </select>
          </div>
        </div>
        <div class="flex flex-row gap-6 mt-8">
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Tình trạng mới nhập</p>
            <select id="country" name="country" autocomplete="country-name"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
              v-model="equipment.start_status">
              <option value="1">New</option>
              <option value="2">Like New loại S (99%)</option>
              <option value="3">Like New loại A (99%)</option>
              <option value="4">Like New loại B (97%-98%)</option>
              <option value="5">Like New loại C (90%-95%)</option>
              <option value="6">Thiết bị cũ ( second hand)</option>
            </select>
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Trạng thái thiết bị</p>
            <select v-model="equipment.device_status" id="country" name="country" autocomplete="country-name"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600">
              <option value="0">Bị mất</option>
              <option value="1">Sử dụng được</option>
              <option value="2">Bị hư hỏng</option>
            </select>
          </div>
          <div v-if="equipment.device_status == '0'" class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Đền bù</p>
            <select v-model="equipment.compensation_status" id="country" name="country" autocomplete="country-name"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600">
              <option value="0">Chưa đền bù</option>
              <option value="1">Đã đền bù</option>
            </select>
          </div>
        </div>
        <div class="flex flex-row gap-6 mt-8">
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Giá tiền</p>
            <input input type="number"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
              v-model="equipment.price" />
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Thời gian khấu hao</p>
            <input type="number"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
              placeholder="" v-model="equipment.depreciation_period" />
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Tháng / Năm</p>
            <select v-model="equipment.period_type" id="country" name="country" autocomplete="country-name"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600">
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
            <DatePicker v-model="equipment.import_date" class="w-48" />
          </div>
          <div class="flex flex-col">
            <p class="leading-loose font-medium text-xl">Giá trị khấu hao</p>
            <input type="number"
              class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
              placeholder="" v-model="equipment.depreciated_value" />
          </div>
        </div>
        <div class="flex flex-row gap-6 mt-8 w-full">
          <div class="flex flex-col ">
            <p class="leading-loose font-medium text-xl">Tệp đính kèm</p>
            <div class="row">
              <div class="col-8">
                <p class="btn btn-default p-0">
                  <input name="myImage" accept="image/png, image/gif, image/jpeg" type="file" ref="file"
                    @change="selectImage" />
                </p>
              </div>
            </div>
          </div>
        </div>
        <div class="flex flex-row  mt-4">
          <div class="grid grid-cols-3 gap-6 mx-0">
            <div class="grid grid-rows-2 gap-4 list-group-flush" v-for="(image, index) in allFileInfo">
              <article tabindex="0"
                class="group hasImage w-full h-full rounded-md focus:outline-none focus:shadow-outline bg-gray-100 cursor-pointer relative text-transparent hover:text-white shadow-sm">
                <img alt="upload preview" class="img-preview w-48 h-full sticky object-cover rounded-md bg-fixed"
                  :src="image.file_url" />
                <section
                  class="flex flex-col rounded-md text-xs break-words w-full h-full z-20 absolute top-0 py-2 px-3">
                  <h1 class="flex-1"></h1>
                  <div class="flex">
                    <span class="p-1">
                      <i>
                        <svg class="fill-current w-4 h-4 ml-auto pt-" xmlns="http://www.w3.org/2000/svg" width="24"
                          height="24" viewBox="0 0 24 24">
                          <path
                            d="M5 8.5c0-.828.672-1.5 1.5-1.5s1.5.672 1.5 1.5c0 .829-.672 1.5-1.5 1.5s-1.5-.671-1.5-1.5zm9 .5l-2.519 4-2.481-1.96-4 5.96h14l-5-8zm8-4v14h-20v-14h20zm2-2h-24v18h24v-18z" />
                        </svg>
                      </i>
                    </span>

                    <p class="p-1 size text-xs"></p>
                    <button class="delete ml-auto focus:outline-none hover:bg-gray-300 p-1 rounded-md"
                      @click="deleteImage(index)">
                      <svg class="pointer-events-none fill-current w-4 h-4 ml-auto" xmlns="http://www.w3.org/2000/svg"
                        width="24" height="24" viewBox="0 0 24 24">
                        <path class="pointer-events-none"
                          d="M3 6l3 18h12l3-18h-18zm19-4v2h-20v-2h5.711c.9 0 1.631-1.099 1.631-2h5.316c0 .901.73 2 1.631 2h5.711z" />
                      </svg>
                    </button>
                  </div>
                </section>
              </article>
              <div class="h-18">
                <textarea id="message" rows="4"
                  class="block p-2.5 w-full  text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="Image description..." v-model="image.file_description"></textarea>
              </div>
            </div>
          </div>
        </div>
        <div class="flex flex-row gap-6 mt-8 justify-center">
          <button @click="saveEquipment"
            class="bg-blue-500 font-bold flex justify-center w-48 items-center text-white px-4 py-3 rounded-md focus:outline-none">
            Thêm thiết bị
          </button>
          <a href="/equipment">
            <button
              class="bg-red-500 font-bold flex justify-center items-center w-48 text-gray-900 px-4 py-3 rounded-md focus:outline-none">
              <svg class="w-6 h-6 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
              </svg>
              Hủy
            </button></a>
        </div>

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
import FileInfo from "../types/FileInfo";
import { Vue, Options, Ref } from "vue-property-decorator";
import UploadImage from "./UploadImage.vue";
import router from "@/router";
import Category from "@/types/Category";
@Options({
  components: {
    DatePicker,
    UploadImage,
  },
})
export default class AddEquipment extends Vue {
  equipment: Equipment = {
    device_id: null,
    compensation_status: null,
    name: null,
    start_status: null,
    price: null,
    depreciated_value: null,
    depreciation_period: null,
    period_type: null,
    import_date: null,
    take_over_status: "0",
    category_id: null,
    device_status: null,
    created_by: "tatthanh@rever.vn",
    created_time: null,
    updated_by: null,
    updated_time: null,
    take_over_person_id: null,
    id: null,
    metadata_info: null,
    category_name: null,
  };
  categories: Category[] = [];
  private errors: string[] = [];
  public allFileInfo: FileInfo[] = [];
  private currentImage: File | null | undefined = null;
  private allImageFile: File[] = [];
  mounted() {
    this.retrieveCategories();
  }
  async retrieveCategories() {
    CategoryService.getAllCategories("status=1").then((res: any) => {
      this.categories = res.data.categories;
    });
  }
  selectImage(e: InputEvent) {
    const value = e!.target as HTMLInputElement;
    this.currentImage = value?.files?.item(0);
    if (this.currentImage != null) {
      if (this.currentImage.type != "image/jpeg") {
        this.errors?.push("Only image file be selected ");
        return;
      }
      else if (this.currentImage.size > 5000000) {
        this.errors?.push("Size file <=5MB ");
      }
      else {
        this.allImageFile.push(this.currentImage);
        const fileUrl = URL.createObjectURL(this.currentImage);
        this.allFileInfo.push({ file_url: fileUrl, file_description: "", file_extention: "", file_name: "", size: "" })
      }

    }
  }
  deleteImage(index: number) {
    this.allFileInfo.splice(index, 1);
    this.allImageFile.splice(index, 1);
  }
  async getImageFile() {
    let obj = {};
    for (let i = 0; i < this.allImageFile.length; i++) {
      await UploadService.upload(this.allImageFile[i])
        .then((response) => {
          const key = Object.keys(response.data);
          response.data[key[0]].file_description = this.allFileInfo[i].file_description;
          obj = Object.assign(response.data, obj);
        })
        .catch((error) => {
          alert(error);
        });
    }

    return obj;
  }
  checkValidateForm() {
    if (this.equipment.name == null) {
      this.errors?.push("Name's device required");
    }
    if (this.equipment.category_id == null) {
      this.errors?.push("Category's device required");
    }
    if (this.equipment.start_status == null) {
      this.errors?.push("Start status device required");
    }
    if (this.equipment.device_status == null) {
      this.errors?.push("Device status required");
    }
    if (this.equipment.price == null) {
      this.errors?.push("Price required");
    }
    if (this.equipment.depreciated_value == null) {
      this.errors?.push("Depreciated value id required");
    } else if (
      parseInt(this.equipment.depreciated_value) < 0 ||
      parseInt(this.equipment.depreciated_value) > 1
    ) {
      this.errors?.push("Depreciated value id must be >0 and <1");
    }
    if (this.equipment.depreciation_period == null) {
      this.errors?.push("Depreciated period id required");
    }
    if (this.equipment.period_type == null) {
      this.errors?.push("Period type id required");
    }
    if (this.equipment.import_date == null) {
      this.errors?.push(" Import date required");
    }
    if (
      this.equipment.device_status == "0" &&
      this.equipment.compensation_status == null
    ) {
      this.errors?.push("Compensation status required");
    }
    if (this.equipment.device_status != "0") {
      this.equipment.compensation_status = null;
    }
  }
  async saveEquipment() {

    let errors = "";
    this.checkValidateForm();
    if (this.errors.length == 0) {
      var temp = new Date(this.equipment.import_date!);
      var milliseconds = temp.getTime().toString();
      this.equipment.metadata_info = await this.getImageFile();
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
        metadata_info: this.equipment.metadata_info,
      };
      EquipmentDataService.addData(data)
        .then(() => {
          alert("Thêm thiết bị thành công");
          router.push({ path: "/" })
        }
        )
        .catch((err) => {
          const errors = err.response.data.errors[0];
          console.log(errors);
          alert(errors);
        });


    }
    else {
      for (let i = 0; i < this.errors.length; i++) {
        errors = errors + this.errors[i] + "\n";
      }
      alert(errors);
    }
    this.errors = [];
  }
}
</script>
