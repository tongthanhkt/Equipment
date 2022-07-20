<template>
  <div class="mx-60">
    <button class="bg-stone-700 text-white font-bold py-2 px-4 rounded" @click="isAddCategory = !isAddCategory">
      Tạo mới danh mục
    </button>
    <div class="flex flex-col mt-8">
      <div class="py-2 -my-2 overflow-x-auto sm:-mx-6 sm:px-6 lg:-mx-8 lg:px-8">
        <div class="inline-block min-w-full overflow-hidden align-middle border-b border-gray-200 shadow sm:rounded-lg">
          <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
            <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
              <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                  <th scope="col" class="px-6 py-3">Mã danh mục</th>
                  <th scope="col" class="px-6 py-3">Tên danh mục</th>
                  <th scope="col" class="px-6 py-3">Trạng thái danh mục</th>
                  <th scope="col" class="px-6 py-3">Người tạo</th>
                  <th scope="col" class="px-6 py-3">Thời gian tạo</th>
                  <th scope="col" class="px-6 py-3">Người cập nhật</th>
                  <th scope="col" class="px-6 py-3">Thời gian cập nhật</th>
                  <th scope="col" class="px-6 py-3"></th>
                </tr>
              </thead>
              <tbody>
                <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700"
                  v-for="(category, index) in categories" :key="index">
                  <td scope="row"
                    class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap action-hover">
                    {{ category.id }}
                  </td>
                  <td class="px-6 py-4">{{ category.name }}</td>
                  <td class="px-6 py-4 font-bold">
                    <p v-if="category.status == '-1'" class="text-orange-700">
                      Deleted
                    </p>
                    <p v-else-if="category.status == '0'" class="text-orange-700">
                      Disable
                    </p>
                    <p v-else-if="category.status == '1'" class="text-green-500">
                      Available
                    </p>
                  </td>
                  <td class="px-6 py-4">{{ category.created_by }}</td>
                  <td class="px-6 py-4">{{ category.created_time }}</td>
                  <td class="px-6 py-4">{{ category.updated_by }}</td>
                  <td class="px-6 py-4">{{ category.updated_time }}</td>

                  <td class="px-6 py-4 text-right">
                    <div>
                      <span class="flex justify-center">
                        <button class="mx-2 px-2 rounded-md list_equipments" @click="
                          (isUpdateCategory = !isUpdateCategory),
                          getCategoryId(category.id)
                        ">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-green-700" viewBox="0 0 20 20"
                            fill="currentColor">
                            <path d="M17.414 2.586a2 2 0 00-2.828 0L7 10.172V13h2.828l7.586-7.586a2 2 0 000-2.828z" />
                            <path fill-rule="evenodd"
                              d="M2 6a2 2 0 012-2h4a1 1 0 010 2H4v10h10v-4a1 1 0 112 0v4a2 2 0 01-2 2H4a2 2 0 01-2-2V6z"
                              clip-rule="evenodd" />
                          </svg>
                        </button>

                        <button class="mx-2 px-2 rounded-md list_equipments" @click="deleteCategory(category.id)">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-red-700" viewBox="0 0 20 20"
                            fill="currentColor">
                            <path fill-rule="evenodd"
                              d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z"
                              clip-rule="evenodd" />
                          </svg>
                        </button>
                      </span>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
  <AddCategory v-if="isAddCategory" v-on:changeAddCategoryShow="changeAddCategoryShow" />
  <UpdateCategory v-if="isUpdateCategory" v-on:changeUpdateCategoryShow="changeUpdateCategoryShow" :id="categoryId" />
</template>
<script lang="ts">
import Category from "@/types/Category";
import CategoryService from "../services/category/categoryService";
import { Vue, Options } from "vue-property-decorator";
import AddCategory from "./AddCategory.vue";
import UpdateCategory from "./UpdateCategory.vue";
@Options({
  components: { AddCategory, UpdateCategory },
})
export default class CategoryList extends Vue {
  public isAddCategory: Boolean = false;
  public isUpdateCategory: Boolean = false;
  public categoryId: number | null = null;
  public categories: Category[] = [];
  public queryParams: any;
  public name: string | null = null;
  public status: number | null = null;
  async mounted() {
    await this.retrieveCategories();
  }
  getCategoryId(id: number | null) {
    this.categoryId = id;
  }
  changeUpdateCategoryShow() {
    this.isUpdateCategory = !this.isUpdateCategory;
  }
  changeAddCategoryShow() {
    this.isAddCategory = !this.isAddCategory;
  }
  getQueryParams() {
    this.queryParams = {
      name: this.name,
      status: this.status,
    };
    Object.keys(this.queryParams).forEach((key) => {
      if (
        this.queryParams[key] === null ||
        this.queryParams[key] === undefined
      ) {
        delete this.queryParams[key];
      }
    });
    this.$router.push({ name: "search", query: this.queryParams });
    let queryParams = "";
    const temp = Object.entries(this.queryParams);
    for (let i = 0; i < temp.length; i++) {
      if (i < temp.length - 1) {
        let param = temp[i][0] + "=" + temp[i][1] + "&";
        queryParams += param;
      } else {
        let param = temp[i][0] + "=" + temp[i][1];
        queryParams += param;
      }
    }
    return queryParams;
  }
  async retrieveCategories() {
    CategoryService.getAllCategories(this.queryParams)
      .then((res: any) => {
        this.categories = res.data.categories;
      })
      .then(() => this.handleFieldCategory());
  }
  handleImportDate(data: string) {
    var d = new Date(parseInt(data));
    return d.toLocaleString();
  }
  deleteCategory(id: number) {
    if (confirm("Bạn có chắc chắn muốn xóa danh mục này ?")) {
      CategoryService.deleteCategory(id)
        .then((res) => alert("Delete Successfully !!"))
        .then(() => this.retrieveCategories())
        .catch((err) =>
          alert("Không thể xóa danh mục này vì đang có thiết bị")
        );
    }
  }
  handleFieldCategory() {
    for (let i = 0; i < this.categories.length; i++) {
      if (this.categories[i].updated_time != null) {
        this.categories[i].updated_time = this.handleImportDate(
          this.categories[i].updated_time!
        );
      }
      if (this.categories[i].created_time != null) {
        this.categories[i].created_time = this.handleImportDate(
          this.categories[i].created_time!
        );
      }
    }
  }
}
</script>
