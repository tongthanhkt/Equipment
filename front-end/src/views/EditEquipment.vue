<template>
  <div class="relative bg-white shadow rounded-3xl sm:p-10 w-3/6 ml-80">
    <div>
      <div class="flex items-center space-x-5">
        <div class="block pl-2 font-semibold text-xl self-start text-gray-700">
          <h1 class="text-2xl leading-relaxed">Cập nhật thiết bị</h1>
        </div>
      </div>
      <div class="divide-y divide-gray-200">
        <div
          class="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7"
        >
          <div class="flex flex-row">
            <div class="flex flex-col w-64">
              <label class="leading-loose">Mã thiết bị</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.device_id"
              />
            </div>
            <div class="flex flex-col w-64">
              <label class="leading-loose">Tên thiết bị</label>
              <input
                type="text"
                class="px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.name"
              />
            </div>
          </div>
          <div class="flex flex-col w-64">
            <label class="leading-loose">Danh mục</label>
            <select
              v-model="equipment.category_id"
              id="country"
              name="country"
              autocomplete="country-name"
              class="mt-1 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            >
              <option value="1">Máy tính</option>
              <option value="2">Màn hình</option>
              <option value="3">Phụ kiện</option>
            </select>
          </div>
          <div class="flex flex-row">
            <div class="flex flex-col w-64">
              <label class="leading-loose">Tình trạng mới nhập</label>
              <select
                id="country"
                name="country"
                autocomplete="country-name"
                class="mt-1 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
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
            <div class="flex flex-col w-64">
              <label class="leading-loose">Trạng thái thiết bị</label>
              <select
                v-model="equipment.device_status"
                id="country"
                name="country"
                autocomplete="country-name"
                class="w-48 mt-1 block py-2 px-3 w-48 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
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
                class="w-36 px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-48 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
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
                class="w-32 px-4 py-2 border focus:ring-gray-500 focus:border-gray-900 w-32 sm:text-sm border-gray-300 rounded-md focus:outline-none text-gray-600"
                placeholder=""
                v-model="equipment.depreciation_period"
              />
            </div>
          </div>
          <div class="flex flex-row w-48">
            <div class="flex flex-col">
              <label class="leading-loose w-64 inline-block"
                >Thời gian nhập thiết bị</label
              >
              <div class="flex flex-row">
                <Datepicker
                  class="w-64 inline-block"
                  v-model="equipment.import_date"
                ></Datepicker>
              </div>
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
            Cập nhật thiết bị
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
import Equipment from "../types/Equipment";
import Datepicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import { Vue, Options } from "vue-property-decorator";
import EquipmentDataService from "../services/equipments/EquipmentDataService";
import UploadFilesService from "@/services/equipments/UploadFilesService";
@Options({
  components: {
    Datepicker,
  },
})
export default class AddEquipment extends Vue {
  private equipment: Equipment = {
    device_id: null,
    name: null,
    start_status: null,
    price: null,
    depreciated_value: null,
    depreciation_period: null,
    period_type: null,
    import_date: null,
    take_over_status: null,
    category_id: null,
    category_name: null,
    device_status: "1",
    created_by: null,
    created_time: null,
    updated_by: null,
    updated_time: null,
    take_over_person_id: null,
    take_over_person_name: null,
    id: null,
    metadata_info: null,
  };
  private errors: string[] = [];
  private allImageCurrentURL: string[] = []; // Địa chỉ API của hình ảnh ;
  private currentImage: File | null | undefined = null;
  private allNewImageFile: File[] = [];
  private currentMetadataInfo: any;
  private oldMetadataInfo: any;
  private importDate: string = "";
  async mounted() {
    this.retrieveEquipment();
  }
  async retrieveEquipment() {
    const idParam = this.$route.params.id;
    const response = await EquipmentDataService.getEquipmentDetail(
      idParam
    ).then((response) => {
      console.log(response.data);
      this.equipment = response.data;
      this.equipment.import_date = this.handleImportDate(
        this.equipment.import_date!
      );
      this.equipment.price = parseFloat(this.equipment.price!).toString();
      const allImage = Object.values(response.data.metadata_info);
      this.currentMetadataInfo = Object.entries(response.data.metadata_info);
      this.oldMetadataInfo = Object.entries(response.data.metadata_info);
      let result = allImage.map((Image: any) => Image.file_url);
      result.forEach((URL, index) => {
        this.allImageCurrentURL[index] = `${URL}`;
      });
    });

    //this.handleDate();
  }
  handleImportDate(data: string) {
    var d = new Date(parseInt(data));
    return d.toLocaleString();
  }
  checkValidateForm() {
    if (this.equipment.device_id?.length == 0) {
      this.errors?.push("Device id required");
    }
    if (this.equipment.name?.length == 0) {
      this.errors?.push("Name's device required");
    }
    if (this.equipment.price?.length == 0) {
      this.errors?.push("Price required");
    }
    if (this.equipment.device_id?.length == 0) {
      this.errors?.push("Device id required");
    }
    if (this.equipment.depreciated_value?.length == 0) {
      this.errors?.push("Depreciated value id required");
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
  }
  async saveEquipment() {
    let errors = "";
    this.checkValidateForm();
    if (this.errors.length != 0) {
      for (let i = 0; i < this.errors.length; i++) {
        errors = errors + this.errors[i] + "\n";
      }
      alert(errors);
      this.errors = [];
    } else {
      var temp = new Date(this.equipment.import_date!);
      var milliseconds = temp.getTime().toString();
      const data = {
        id: this.$route.params.id,
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
        created_time: "1655043885811",
        device_status: this.equipment.device_status,
        updated_time: "13062022",
        updated_by: "tatthanh",
        metadata_info: await this.getUpdatedMetaData(),
      };

      EquipmentDataService.updateEquipment(data)
        .then(() => alert("Cập nhật thành công !"))
        .catch((err) => {
          const errors = err.response.data.errors[0];
          console.log(errors);
          let temp = "";
          Object.values(errors).forEach((error) => {
            temp = temp + error + "\n";
          });
          alert(temp);
        });
    }
  }
  async getUpdatedMetaData() {
    await this.getDeletedImage();
    const newFile = await this.getNewImageFile();
    const currentFile = this.getCurrentImageFile();
    let result = Object.assign(currentFile, newFile);
    if (result == null) {
      return (result = { files: {} });
    }
    return result;
  }

  deleteImage(index: any) {
    this.allImageCurrentURL.splice(index, 1);
    this.currentMetadataInfo.splice(index, 1);
  }

  //
  async getDeletedImage() {
    for (let i = 0; i < this.oldMetadataInfo.length; i++) {
      let temp = 0;
      for (let j = 0; j < this.currentMetadataInfo.length; j++) {
        if (
          this.oldMetadataInfo[i][0] === this.currentMetadataInfo[j][0] &&
          !(this.currentMetadataInfo[i] instanceof File)
        ) {
          temp = 1;
        }
      }
      if (temp == 0) {
        await UploadFilesService.deleteFile(
          this.oldMetadataInfo[i][1].file_name
        )
          .then(() => console.log("Delete done!"))
          .catch((err) => console.log(err));
      }
    }
  }

  async getNewImageFile() {
    let obj = {};
    for (let i = 0; i < this.currentMetadataInfo.length; i++) {
      if (this.currentMetadataInfo[i] instanceof File) {
        await UploadFilesService.upload(this.currentMetadataInfo[i]).then(
          (response) => {
            obj = Object.assign(response.data, obj);
          }
        );
      }
    }
    return obj;
  }

  getCurrentImageFile() {
    let obj = Object.fromEntries(this.currentMetadataInfo);
    Object.keys(obj).forEach((key) =>
      obj[key] === undefined ? delete obj[key] : {}
    );
    return obj;
  }

  selectImage(e: InputEvent) {
    const value = e!.target as HTMLInputElement;
    this.currentImage = value?.files?.item(0);
    if (this.currentImage != null) {
      const temp = URL.createObjectURL(this.currentImage);
      this.allImageCurrentURL.push(temp);
      this.currentMetadataInfo.push(this.currentImage);
    }
  }
}
</script>
