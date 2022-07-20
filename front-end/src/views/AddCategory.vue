<template>
  <div class="absolute h-screen top-0 right-0 w-1/2 shadow-2xl border-l-2 border-indigo-300 rounded-none">
    <div
      class="grid grid-cols-4 text-start border-b-2 border-indigo-300 w-auto font-semibold text-base self-start text-black bg-indigo-500">
      <h1 class="px-2 pt-2 pb-1 col-span-3 text-lg font-medium text-white w-auto">
        Tạo mới danh mục
      </h1>
      <button
        class="place-self-end bg-indigo-500 hover:bg-indigo-200 m-2 transition-colors w-auto text-white rounded-md focus:outline-none"
        v-on:click="changeShow(false)">
        <fa icon="xmark" class="px-2 py-2"></fa>
      </button>
    </div>

    <div class="relative overflow-x-hidden overflow-auto bg-indigo-100 h-4/5">
      <div class="grid-rows-3">
        <div class="p-1 font-medium text-gray-700">
          Tên danh mục:<input v-model="category.name"
            class="mx-1 px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded focus:outline-none text-black" />
        </div>
        <div class="p-1 font-medium text-gray-700">
          Trạng thái:
          <select v-model="this.category.status" id="type" name="type" autocomplete="type-name"
            class="mx-1 px-2 py-1.5 border focus:ring-gray-500 w-11/12 hover:border-gray-900 lg:text-base sm:text-sm border-gray-300 rounded-md focus:outline-none text-black">
            <option value="0">Vô hiệu hóa</option>
            <option value="1">Hoạt động</option>
          </select>
        </div>
      </div>
    </div>
    <div class="bg-indigo-100 w-full border-t border-gray-300">
      <!-- <div class="p-1 m-2 text-base  font-medium text-gray-700">Thông tin</div> -->
      <div class="flex flex-row gap">
        <button
          class="bg-green-500 hover:bg-green-600 m-3.5 transition-colors text-base w-auto text-white p-2 rounded-md focus:outline-none"
          @click="insertCategory">
          <fa icon="rotate-right" class="px-1"></fa>
          Thêm danh mục
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
import { Vue, Options, Emit, Ref, Prop } from "vue-property-decorator";
import CategoryService from "@/services/category/categoryService";
import Category from "../types/Category";
@Options({})
export default class AddCategory extends Vue {
  public category: Category = {
    id: 0,
    name: null,
    status: null,
    created_by: "tatthanh",
    created_time: null,
    updated_by: null,
    updated_time: null,
  };
  @Emit("changeAddCategoryShow")
  changeShow() { }

  async insertCategory() {
    if (this.category.name == null) {
      alert("Vui lòng nhập tên danh mục");
    } else if (this.category.status == null) {
      alert("Vui lòng nhập trạng thái");
    } else {
      CategoryService.addCategory(this.category)
        .then(() => {
          alert("Thêm danh mục thành công !");
        })
        .catch((error) => alert("Thêm thất bại"));
    }
  }
}
</script>

<style>
</style>
