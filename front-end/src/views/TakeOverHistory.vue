<template>
<div>
 
  <div class="relative flex bg-gray-200 ">
  
  <div class ="flex-1 container mx-auto p-2">
  
  
<div class="p-1 mt-8 w-auto h-auto  mx-auto bg-gray-50 shalow-lg rounded-xl ">
  <div class=" text-center p-2 border-b-2 border-indigo-300 w-full block  font-semibold  text-base self-start text-black">
          <h1 class="text-2xl leading-relaxed">Lịch sử bàn giao</h1>
  </div>
  <div class="grid grid-flow-col grid-rows-1">
  <div class="p-2 flex place-items-end w-auto">
    <span class="flex justify-start">
      <div class="px-1 py-2 m-2 text-base grid grid-rows-1 grid-flow-col rounded-md h-fit w-fit border-2 border-blue-400 focus:border-blue-600 focus:ring focus:ring-opacity-40 focus:ring-indigo-500">
        <fa icon = "magnifying-glass" class="text-gray-400 px-2 py-1" ></fa>
        <input
            class="text-base bg-gray-50 w-5/6 focus:outline-none"
            type="text"
            placeholder="Tên người sử dụng"
            
          />
      </div>
       <div class="px-1 py-2 m-2 text-base grid grid-rows-1 grid-flow-col rounded-md h-fit w-fit border-2 border-blue-400 focus:border-blue-600 focus:ring focus:ring-opacity-40 focus:ring-indigo-500">
        <fa icon = "magnifying-glass" class="text-gray-400 px-2 py-1" ></fa>
        <input
            class="text-base bg-gray-50 w-5/6 focus:outline-none"
            type="text"
            placeholder="Tên người bàn giao"
            
          />
      </div>
      
      
    

    </span>
    
     </div>
     <div class="p-2 flex justify-end w-auto">
     
     <select name="takeover_status" id="takeover_status" class ="bg-blue-500 m-2 text-white   p-2 rounded w-auto">
    <option  disabled selected hidden>Trạng thái bàn giao</option>
    <option value="0" class="bg-white text-black hover:bg-blue-700">Chưa xác nhận</option>
    <option value="1" class="bg-white text-black hover:bg-blue-700" >Đã xác nhận</option>
    </select>
     <select name="takeover_status" id="takeover_status" class ="bg-blue-500 m-2 text-white   p-2 rounded w-auto">
    <option disabled selected hidden>Loại bàn giao</option>
    <option value="0" class="bg-white text-black hover:bg-blue-700">Bàn giao thiết bị mới</option>
    <option value="1" class="bg-white text-black hover:bg-blue-700" >Bàn giao thiết bị sau khi sửa</option>
    </select>
</div>
     
</div>
 
  <div class="p-1 mx-1 my-2 ">
    <div
      class=" min-w-full  align-middle flex justify-items-center  "
    >
      <table class="hover:border-collapse min -w-full bg-gray-100 rounded-xl place-content-center ">
        <thead>
          <tr class = " border-b  border-gray-500 ">
             <th
              class="p-2 text-sm  font-medium   text-left text-gray-700   "
            >
              Mã
            </th>
            <th
              class="p-2 text-sm  font-medium   text-left text-gray-700   "
            >
              Mã thiết bị
            </th>
             <th
              class="p-2 text-sm  font-medium   text-left text-gray-700   "
            >
              Tên thiết bị
            </th>
            <th
              class="p-2 text-sm  font-medium   text-left text-gray-700   "
            >
              Người sử dụng
            </th>
            <th
              class="p-2 text-sm  font-medium   text-left text-gray-700   "
            >
              Người bàn giao
            </th>
           
            <th
              class="p-2 text-sm  font-medium   text-left text-gray-700   "
            >
              Loại bàn giao
            </th>
            <th
              class="p-2 text-sm  font-medium   text-left text-gray-700   "
            >
              Trạng thái bàn giao
            </th>
            <th
             class="p-2 text-sm  font-medium   text-left text-gray-700   "
            >
              Người tạo
            </th>
            <th
             class="p-2 text-sm  font-medium   text-left text-gray-700   "
            >
              Thời gian tạo
            </th>
            
            <th
              
            >
              
            </th>
          </tr>
        </thead>

        <tbody >
          <tr class=" hover:bg-gray-200 transition-colors border-b border-gray-200"
           v-on:click="handleDetailTakeOverShow(true)"
          >
            
            <td >
            <div class="p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td >
            <div class="p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            
            <td>
            <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
             
            >
            <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
              
            >
             <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
              
            >
             
              <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
             
            >
            <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
             
            >
             <div class="p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
              
            >
              <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td>
              <div class="flex justify-around w-auto">
                <span class="flex justify-center">
                  <button
                    class="bg-gray-100 hover:bg-gray-300 m-1 transition-colors  flex justify-center items-center w-auto text-blue-500 px-3.5 py-2 rounded-md "
                     v-on:click.stop="handleEditTakeOverShow(true)"
                  >
                    <fa icon="pen-to-square"   ></fa>               
                  </button>
                  <button
                    class="bg-gray-100 hover:bg-gray-300 m-1 transition-colors  flex justify-center items-center w-auto text-red-500 px-3.5 py-2 rounded-md "
                  >
                    <fa icon="trash-can"   ></fa>               
                  </button>
                </span>
              </div>
            </td>
          </tr>
          <tr class="hover:bg-gray-200 transition-colors border-b border-gray-200"
            v-on:click="handleDetailTakeOverShow(true)"
          >
            
            
            <td
              
            >
            <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td >
            <div class="p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td >
            <div class="p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
             
            >
            <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
              
            >
             <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
              
            >
             
              <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
             
            >
            <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
             
            >
             <div class="p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td
              
            >
              <div class=" p-1 text-sm text-center text-gray-500">
                Phạm Nguyen Tuong Vy
              </div>
            </td>
            <td>
              <div class="flex justify-around w-auto" @click.prevent.self>
                <span class="flex justify-center">
                  <button
                    class= "block bg-gray-100 hover:bg-gray-300 m-1 transition-colors   justify-center items-center w-auto text-blue-500 px-3.5 py-2 rounded-md "
                     v-on:click.stop="handleEditTakeOverShow(true)"
                  >
                    <fa icon="pen-to-square"   ></fa>               
                  </button>
                  <button
                    class="block bg-gray-100 hover:bg-gray-300 m-1 transition-colors  justify-center items-center w-auto text-red-500 px-3.5 py-2 rounded-md "
                    
                  >
                    <fa icon="trash-can"   ></fa>               
                  </button>
                </span>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      
    </div>
  </div>
  <nav aria-label="Page navigation example">
            <ul class="inline-flex -space-x-px">
              <li>
                <a
                  type="button"
                 
                  class="py-2 px-3 ml-0 leading-tight text-gray-500 bg-white rounded-l-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  >First</a
                >
              </li>
              <li>
                <a
                  type="button"
                  
                  class="py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  >Preivous</a
                >
              </li>

              <li>
                <a
                  type="button"
                  aria-current="page"
                  class="py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  >1</a
                >
              </li>
              <li>
                <a
                  type="button"
                 
                  class="py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  >Next</a
                >
              </li>

              <li>
                <a
                  type="button"
                  
                  class="py-2 px-3 leading-tight text-gray-500 bg-white rounded-r-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  >Last</a
                >
              </li>
            </ul>
          </nav>
</div>
</div>

 

</div>
<DetailTakeOver v-if="isDetailTakeOverShow"  v-on:changeDetailTakeOverShow="handleDetailTakeOverShow" v-on:changeEditTakeOverShow="handleEditTakeOverShow"/>

<EditTakeOver v-if="isEditTakeOverShow"  v-on:changeEditTakeOverShow="handleEditTakeOverShow"/>
</div>



</template>

<script lang="ts">
import DetailTakeOver from "./DetailTakeOver.vue"
import EditTakeOver from "./EditTakeOver.vue";

import { Vue, Options } from "vue-property-decorator";


@Options({
  components: {
    DetailTakeOver,
    EditTakeOver
  },
})
export default class TakeOverHistory extends Vue {
   isDetailTakeOverShow :Boolean = false;
   isAddTakeOverShow: Boolean = false;
   isEditTakeOverShow: Boolean = false;
  handleDetailTakeOverShow(data:Boolean) {
    this.isDetailTakeOverShow = data
    this.isAddTakeOverShow=false
    this.isEditTakeOverShow=false
  }
  
  handleEditTakeOverShow(data:Boolean) {
    this.isDetailTakeOverShow=false
    this.isAddTakeOverShow=false
    console.log("handle edit ne")
    this.isEditTakeOverShow = data
  }
  

}
</script>

<style>

</style>