<template>
  <div  class=" absolute h-screen top-0 right-1   w-5/12  drop-shadow-lg ">
    <div class="grid grid-cols-4 text-start border-b-2 border-indigo-300 w-auto   font-semibold  text-base self-start text-black bg-indigo-500">
          <h1 class="px-2 pt-2 pb-1 col-span-3 text-lg font-medium text-white w-auto ">Xem thông tin chi tiết bàn giao - {{record?.id}}</h1>
           <button
            class="place-self-end bg-indigo-500 hover:bg-indigo-200 m-2 transition-colors   w-auto text-white  rounded-md focus:outline-none"
             v-on:click="changeShow(false)"
         >
            <fa icon="xmark"  class="px-2 py-1" ></fa>          
          </button>
    </div>
    
      <div class="overflow-auto overflow-x-hidden bg-indigo-100  h-4/5">
        <div class=" pl-3 text-sm  grid grid-cols-3 grid-flow-row">
      <div class="p-1   font-medium text-gray-700">Mã thiết bị</div>
      <div class="p-1  col-span-2   font-medium text-gray-700">Tên thiết bị</div>
     
      <p class="pl-1   text-slate-500">{{record?.device_id}}</p>
      <p class="pl-1 col-span-2 text-slate-500">{{record?.name}}</p>
       <div class="p-1   font-medium text-gray-700">Chi phí</div>
      <div class="p-1  col-span-2   font-medium text-gray-700">Thời gian bàn giao</div>
      
      <p class="pl-1 text-slate-500">{{record?.cost}}</p>
      <p class="pl-1  col-span-2  text-slate-500 ">{{handleDate(record?.take_over_time)}}</p>
      
      
      <div class="p-1     font-medium text-gray-700">Người bàn giao</div>
      <div class="pl-1 col-span-2  font-medium text-gray-700">Loại bàn giao</div>
       <p class="pl-1 text-slate-500">{{record?.take_over_person}}</p>
      <p class="pl-1  col-span-2  text-slate-500 ">{{type[record.type]}}</p>
       <div class="p-1   font-medium text-gray-700">Người nhận thiết bị</div>
      <div class="p-1   font-medium text-gray-700">Người xác nhận</div>
      <div class="p-1     font-medium text-gray-700">Trạng thái</div>
      <p class="pl-1 pb-2 text-slate-500">{{record?.username}}</p>
      <p class="pl-1 pb-2 text-slate-500">{{record?.verifier}}</p>
     
      <div class="pl-1 pb-2   text-slate-500"><div v-if="record?.status == '1'" class="text-green-500 italic font-semibold">Đã xác nhận </div>
                        <div v-else-if="record?.status == '0'" class="text-blue-500 italic font-semibold">
                          Chờ xác nhận
                        </div></div>
      
 </div>
      <div class =" px-3 pt-2 bg-indigo-100 grid grid-cols-1 text-sm w-full grid-flow-row border-t border-gray-300" >   
      <div class="pl-1 font-medium text-gray-700">Message</div>
      <p class="pl-1 text-slate-500">{{record?.message}}</p>
       <div class="pl-1   font-medium text-gray-700">Tệp đính kèm</div>
       <div class="mx-2 bg-gray-50 w-full h-24 my-2"> 

       </div>
      </div>
      <div class =" px-3 pt-2 bg-indigo-100  w-full  border-t border-gray-300" >
         <!-- <div class="p-1 m-2 text-base  font-medium text-gray-700">Thông tin</div> -->
      <div class="grid-flow-row grid grid-cols-3 text-sm">
        <div class="p-1  font-medium text-gray-700">Thời gian tạo</div>
          <div class="p-1 col-span-2  font-medium text-gray-700">Người tạo </div>
      
      <p class="pl-1 text-slate-500">{{handleDate(record?.created_time)}}</p>
      <p class="pl-1 col-span-2  text-slate-500">{{record?.created_by}}</p>
      <div class="p-1     font-medium text-gray-700">Thời gian cập nhật</div>
       <div class="p-1 col-span-2  font-medium text-gray-700">Người cập nhật</div>
      
      <p class="pl-1 text-slate-500">{{handleDate(record?.updated_time)}}</p>
      <p class="pl-1 pb-2 col-span-2  text-slate-500">{{record?.updated_by}}</p>
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
            v-on:click.stop="changeShow(false);deleteDetailRecord(id);"
          >
            <fa icon="ban"  class="px-2 " ></fa>
            Xóa 
          </button>
      
    </div>
      </div>
      
    
   
  </div>
</template>

<script lang="ts">

import TakeOverService from "@/services/takeover/TakeOverService";
import TakeOverRecord from "@/types/TakeOverRecord";
import { Vue, Options,Prop,Emit,Ref } from "vue-property-decorator";




export default class DetailTakeOver extends Vue {
  record : TakeOverRecord ={
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
  name: ""

  }
  type:any = {
    
    1 : 'Bàn giao thiết bị mới',
    2 : 'Bàn giao thiết bị sau khi sửa chữa'
  }
  @Prop() id!: number

  @Emit('changeDetailTakeOverShow')
  changeShow(data: boolean) {
   return data
  }

  @Emit('changeEditTakeOverShow')
  showEditTakeOver() {
   return true
  }

  async created(){
    console.log(this.id)
    this.retrieveRecord()
  }

  async retrieveRecord(){
    await TakeOverService.getRecordById(this.id)
      .then(res=>{
        console.log(res.data);
        this.record = res.data
        

      })
      .catch(err=>{
        alert(err.response.data)
      })
  }
  handleDate(data: string|undefined) {
    if (data === undefined )
    return null
    var d = new Date(Number(data));
    return d.toLocaleString()
  }
  @Emit('deteteTakeOverRecord')
  deleteDetailRecord(id: number) {
    console.log("event1")
   return id
  }
}


</script>

<style>

</style>