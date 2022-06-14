<style scoped>
.img-wrap {
  position: relative;
}
.img-wrap .close {
  position: absolute;
}
</style>
<template>
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
      <div class="col-4">
        <button
          class="btn btn-success btn-sm float-right"
          :disabled="!currentImage"
          @click="upload"
        >
          Upload
        </button>
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
              <span class="close" @click="deleteImage(index)">&times;</span>
              <img class="w-64 preview my-3" :src="image" alt="" />
            </div>
          </div>
        </div>
      </ul>
    </div>
  </div>
</template>
<script lang="ts">
import { Vue, Ref } from "vue-property-decorator";
import UploadService from "../services/equipments/UploadFilesService";
export default class UploadImage extends Vue {
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
    this.allImageFile.forEach((imageFile) => {
      UploadService.upload(imageFile);
    });
  }
}

// export default {
//   name: "upload-image",
//   data() {
//     return {
//       allImageCurrentURL: [],
//       currentImage: File[],
//       allImageFile: [],
//     };
//   },
//   methods: {
//     selectImage() {
//       this.currentImage = this.$refs.file.files.item(0);
//       this.allImageCurrentURL.push(URL.createObjectURL(this.currentImage));
//       this.allImageFile.push(this.currentImage);
//     },
//     deleteImage(index) {
//       this.allImageCurrentURL.splice(index, 1);
//       console.log(this.allImageCurrentURL);
//     },
//     upload() {
//       console.log(this.allImageFile);
//       // this.allImageFile.forEach((image) => {
//       //   console.log(this.currentImage);
//       // });
//     },
//   },
// };
</script>
