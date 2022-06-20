<template>
  <div  class=" absolute h-screen top-0 right-1   w-5/12  drop-shadow-lg ">
    <div class="grid grid-cols-4 text-start border-b-2 border-indigo-300 w-auto   font-semibold  text-base self-start text-black bg-indigo-500">
          <h1 class="px-2 pt-2 pb-1 col-span-3 text-lg font-medium text-white w-auto ">Xem thông tin chi tiết bàn giao</h1>
           <button
            class="place-self-end bg-indigo-500 hover:bg-indigo-200 m-2 transition-colors   w-auto text-white  rounded-md focus:outline-none"
             v-on:click="changeShow"
         >
            <fa icon="xmark"  class="px-2 py-1" ></fa>          
          </button>
    </div>
    
      <div class="overflow-auto overflow-x-hidden bg-indigo-100  h-4/5">
        <div class=" pl-3 text-sm  grid grid-cols-3 grid-flow-row">
      <div class="p-1   font-medium text-gray-700">Mã thiết bị</div>
      <div class="p-1  col-span-2   font-medium text-gray-700">Tên thiết bị</div>
     
      <p class="pl-1   text-slate-500">dsfhjfhcnakdsfcnu</p>
      <p class="pl-1 col-span-2 text-slate-500">Máy tính Dell nè nè nè</p>
       <div class="p-1   font-medium text-gray-700">Chi phí</div>
      <div class="p-1  col-span-2   font-medium text-gray-700">Thời gian bàn giao</div>
      
      <p class="pl-1 text-slate-500">100000000</p>
      <p class="pl-1  col-span-2  text-slate-500 ">26/14/12 05:00:00</p>
      
      
      <div class="p-1     font-medium text-gray-700">Người bàn giao</div>
      <div class="pl-1 col-span-2  font-medium text-gray-700">Loại bàn giao</div>
       <p class="pl-1 text-slate-500">Phạm Nguyễn Tường Vy</p>
      <p class="pl-1  col-span-2  text-slate-500 ">Bàn giao thiết bị mới</p>
       <div class="p-1   font-medium text-gray-700">Người nhận thiết bị</div>
      <div class="p-1   font-medium text-gray-700">Người xác nhận</div>
      <div class="p-1     font-medium text-gray-700">Trạng thái</div>
      <p class="pl-1 pb-2 text-slate-500">Phạm Nguyễn Tường Vy</p>
      <p class="pl-1 pb-2 text-slate-500">Phạm Nguyễn Tường Vy</p>
     
      <p class="pl-1 pb-2   text-slate-500">Chưa xác nhận</p>
      
 </div>
      <div class =" px-3 pt-2 bg-indigo-100 grid grid-cols-1 text-sm w-full grid-flow-row border-t border-gray-300" >
        
       
      
      
      <div class="pl-1 font-medium text-gray-700">Message</div>
      <p class="pl-1 text-slate-500">Khi thực hiện bàn giao thì mình sẽ có 1 bước xác nhận từ người được bàn giao
  + Trước khi người được bàn giao xác nhận đã nhận được thiết bị => Người admin có thể chỉnh sửa thông tin chi tiết bàn giao đó
  + Sau khi người được bàn giao đã xác nhận (ký nhận) => Thông tin bàn giao sẽ bị khóa lại, ko cho phép chỉnh sửa nữa</p>
       <div class="pl-1   font-medium text-gray-700">Tệp đính kèm</div>
       <div class="mx-2 bg-gray-50 w-full h-24 my-2"> 

       </div>
      </div>
      <div class =" px-3 pt-2 bg-indigo-100  w-full  border-t border-gray-300" >
         <!-- <div class="p-1 m-2 text-base  font-medium text-gray-700">Thông tin</div> -->
      <div class="grid-flow-row grid grid-cols-3 text-sm">
        <div class="p-1  font-medium text-gray-700">Thời gian tạo</div>
          <div class="p-1 col-span-2  font-medium text-gray-700">Người tạo </div>
      
      <p class="pl-1 text-slate-500">122514</p>
      <p class="pl-1 col-span-2  text-slate-500">Máy tính</p>
      <div class="p-1     font-medium text-gray-700">Thời gian cập nhật</div>
       <div class="p-1 col-span-2  font-medium text-gray-700">Người cập nhật</div>
      
      <p class="pl-1 text-slate-500">122514</p>
      <p class="pl-1 pb-2 col-span-2  text-slate-500">Máy tính</p>
      </div>
      </div>
      </div>
      <div class ="  bg-indigo-100  w-full  border-t border-gray-300" >
         <!-- <div class="p-1 m-2 text-base  font-medium text-gray-700">Thông tin</div> -->
      <div class="    grid grid-cols-2  ">
    
          
          <button
            class="justify-self-start bg-sky-500 hover:bg-sky-600 m-3.5 transition-colors  text-base w-auto text-gray-900 px-1 py-2 rounded-md focus:outline-none"
            v-on:click="showEditTakeOver"
          >
            <fa icon="pen-to-square"  class="px-2" ></fa>
            Cập nhật
          </button>
          <button
            class="justify-self-end bg-indigo-10 hover:bg-gray-300 m-3.5 transition-colors   w-auto text-red-500 p-2 rounded-md focus:outline-none"
          >
            <fa icon="ban"  class="px-2 " ></fa>
            Xóa 
          </button>
      
    </div>
      </div>
      
    
   
  </div>
</template>

<script>


import { Vue, Options,Emit,Ref } from "vue-property-decorator";




export default class EditTakeOver extends Vue {
  @Emit('changeDetailTakeOverShow')
  changeShow() {
   return false
  }

  @Emit('changeEditTakeOverShow')
  showEditTakeOver() {
   return true
  }
}


</script>

<style>

</style>