<template>
  <div>
    <TakeOverHistory
      v-on:changeDetailTakeOverShow="handleDetailTakeOverShow"
      v-on:changeUpdateTakeOverShow="handleUpdateTakeOverShow"
      v-on:deleteRecord="deleteTakeOverRecord"
      v-on:changeRecordTakeOverId="handleRecordTakeOverId"
      :key="keyTakeOver"
    />
    <TakeBackHistory
      v-on:changeDetailTakeBackShow="handleDetailTakeBackShow"
      v-on:changeUpdateTakeBackShow="handleUpdateTakeBackShow"
      v-on:deleteRecord="deleteTakeBackRecord"
      v-on:changeRecordTakeBackId="handleRecordTakeBackId"
      :key="keyTakeBack"
    />
    <FixEquipmentHistory
      v-on:changeDetailFixEquipmentShow="handleDetailFixEquipmentShow"
      v-on:changeUpdateFixEquipmentShow="handleUpdateFixEquipmentShow"
      v-on:changeRecordFixEquipmentId="handleRecordFixEquipmentId"
      v-bind:key="keyFixEquipment"
    />

    <DetailFixEquipment
      v-if="isDetailFixEquipmentShow"
      v-on:changeDetailFixEquipmentShow="handleDetailFixEquipmentShow"
      v-on:changeUpdateFixEquipmentShow="handleUpdateFixEquipmentShow"
      v-bind:id="recordFixEquipmentId"
    />
    <UpdateFixEquipment
      v-if="isUpdateFixEquipmentShow"
      v-on:changeUpdateFixEquipmentShow="handleUpdateFixEquipmentShow"
      v-bind:id="recordFixEquipmentId"
       v-on:handleUpdate="handleFixingUpdate"
    />

    <DetailTakeBack
      v-if="isDetailTakeBackShow"
      v-on:changeDetailTakeBackShow="handleDetailTakeBackShow"
      v-on:changeUpdateTakeBackShow="handleUpdateTakeBackShow"
      v-on:deleteRecord="deleteTakeBackRecord"
      v-bind:id="recordTakeBackId"
    />

    <UpdateTakeBack
      v-if="isUpdateTakeBackShow"
      v-on:changeUpdateTakeBackShow="handleUpdateTakeBackShow"
      v-bind:id="recordTakeBackId"
       v-on:handleUpdate="handleTakeBackUpdate"
    />
    <DetailTakeOver
      v-if="isDetailTakeOverShow"
      v-on:changeDetailTakeOverShow="handleDetailTakeOverShow"
      v-on:changeUpdateTakeOverShow="handleUpdateTakeOverShow"
      v-on:deleteRecord="deleteTakeOverRecord"
      v-bind:id="recordTakeOverId"
    />
    <UpdateTakeOver
      v-if="isUpdateTakeOverShow"
      v-on:changeUpdateTakeOverShow="handleUpdateTakeOverShow"
      v-bind:id="recordTakeOverId"
       v-on:handleUpdate="handleTakeOverUpdate"
    />
  </div>
</template>

<script lang="ts">
import DetailTakeOver from "./DetailTakeOver.vue";
import UpdateTakeOver from "./UpdateTakeOver.vue";
import TakeBackHistory from "./TakeBackHistory.vue";
import TakeOverHistory from "./TakeOverHistory.vue";
import FixEquipmentHistory from "./FixEquipmentHistory.vue";
import DetailTakeBack from "./DetailTakeBack.vue";
import UpdateTakeBack from "./UpdateTakeBack.vue";
import DetailFixEquipment from "./DetailFixEquipment.vue";
import UpdateFixEquipment from "./UpdateFixEquipment.vue";
import TakeOverService from "@/services/takeover/TakeOverService";
import TakeBackService from "@/services/takeback/TakeBackService";
import { Vue, Options } from "vue-property-decorator";
import HistoricalService from "@/services/historical/HistoricalService";
import HistoricalRecord from "@/types/HistoricalRecord";

@Options({
  components: {
    DetailFixEquipment,
    UpdateFixEquipment,
    DetailTakeBack,
    DetailTakeOver,
    UpdateTakeBack,
    UpdateTakeOver,
    TakeBackHistory,
    TakeOverHistory,
    FixEquipmentHistory,
  },
})
export default class History extends Vue {
  isDetailTakeBackShow: Boolean = false;
  isUpdateTakeBackShow: Boolean = false;
  isDetailTakeOverShow: Boolean = false;
  isUpdateTakeOverShow: Boolean = false;
  isDetailFixEquipmentShow: Boolean = false;
  isUpdateFixEquipmentShow: Boolean = false;
  public records: HistoricalRecord[] = [];
  public currentPage: number = 1;
  public currentLimit: number = 10;
  public currentTakeBackStatus: string | null = null;
  public currentTakeBackType: string | null = null;
  public keyUser: string | null = null;
  keyTakeBackPerson: string | null = null;
  totalPages: number = 0;
  public recordTakeBackId: number = 0;
  public recordTakeOverId: number = 0;
  public recordFixEquipmentId: number = 0;
  keyTakeBack: number = 0;
  keyTakeOver: number = 0;
  keyFixEquipment: number = 0;

  handleDetailTakeBackShow(data: Boolean) {
    this.isDetailTakeBackShow = data;
    this.isUpdateTakeBackShow = false;
  }

  handleUpdateTakeBackShow(data: Boolean) {
    this.isDetailTakeBackShow = false;
    this.isUpdateTakeBackShow = data;
  }

  handleDetailTakeOverShow(data: Boolean) {
    this.isDetailTakeOverShow = data;
    this.isUpdateTakeOverShow = false;
  }

  handleUpdateTakeOverShow(data: Boolean) {
    this.isDetailTakeOverShow = false;
    this.isUpdateTakeOverShow = data;
  }
  handleDetailFixEquipmentShow(data: Boolean) {
    this.isDetailFixEquipmentShow = data;
    this.isUpdateFixEquipmentShow = false;
  }

  handleUpdateFixEquipmentShow(data: Boolean) {
    this.isDetailFixEquipmentShow = false;
    this.isUpdateFixEquipmentShow = data;
  }

  handleRecordTakeOverId(id: number) {
    this.recordTakeOverId = id;
  }
  handleRecordTakeBackId(id: number) {
    this.recordTakeBackId = id;
  }
  handleRecordFixEquipmentId(id: number) {
    this.recordFixEquipmentId = id;
  }

  deleteTakeBackRecord(id: number) {
    if (confirm("Bạn có chắc chắn muốn xóa bản ghi thu hồi này ?")) {
      TakeBackService.deleteById(id)
        .then(() => alert("Delete Successfully !!"))
        .then(() => (this.keyTakeBack += 1))
        .catch((err) => alert(err.response.data));
    }
  }

  deleteTakeOverRecord(id: number) {
    if (confirm("Bạn có chắc chắn muốn xóa bản ghi bàn giao này ?")) {
      TakeOverService.deleteById(id)
        .then(() => {
          this.keyTakeOver += 1;
          alert("Delete Successfully !!");
        })
        
        .catch((err) => alert(err.response.data.errors[0]));
    }
  }

  handleTakeOverUpdate(){
    this.keyTakeOver+=1
    
   }

   handleTakeBackUpdate(){
    this.keyTakeBack+=1
   
   }

   handleFixingUpdate(){
    this.keyFixEquipment+=1
    
   }
}
</script>

<style></style>
