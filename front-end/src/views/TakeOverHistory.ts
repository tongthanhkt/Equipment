
import TakeOverService from "@/services/takeover/TakeOverService";
import { Vue, Options } from "vue-property-decorator";
import TakeOverRecord from "@/types/TakeOverRecord";
export default class TakeOverHistory extends Vue {
    isDetailTakeOverShow: Boolean = false;
    isAddTakeOverShow: Boolean = false;
    isEditTakeOverShow: Boolean = false;
    public records: TakeOverRecord[] = [];
    public  currentPage: number = 1;
    public currentLimit: number = 20;
    public currentTakeOverStatus: number | null = null;
    public currentTakeOverType: number | null = null;
    public keyUser: string | null = null;
    public keyTakeOverPerson: string | null = null;
    totalPages: number = 0;
    public recordId :number = 0;
    
  
    handleDetailTakeOverShow(data: Boolean) {
      this.isDetailTakeOverShow = data;
      this.isAddTakeOverShow = false;
      this.isEditTakeOverShow = false;
    }
  
    handleEditTakeOverShow(data: Boolean) {
      this.isDetailTakeOverShow = false;
      this.isAddTakeOverShow = false;
      this.isEditTakeOverShow = data;
    }
    async created(){
      this.retrieveTakeOverRecords(this.getQueryParams())
    }
    
  
    async retrieveTakeOverRecords(params:String){
        await TakeOverService.getRecordsBySearch(params)
        .then(res=>{
          console.log(res.data);
          this.records = res.data.take_over_list
          this.totalPages = res.data.n_pages
  
        })
        .catch(err=>{
          alert(err.response.data)
        })
    } 
  
    getQueryParams(){
      const queryParams:any = {
        page: this.currentPage,
        limit: this.currentLimit,
        username: this.keyUser,
        take_over_person: this.keyTakeOverPerson,
        type: this.currentTakeOverType,
        status: this.currentTakeOverStatus
      }
      Object.keys(queryParams).forEach((key) => {
        if (
          queryParams[key] === null ||
          queryParams[key] === undefined
        ) {
          delete queryParams[key];
        }
      });
      this.$router.push({ name: "TakeOverHistory", query: queryParams });
      let params = "";
      const temp = Object.entries(queryParams);
      for (let i = 0; i < temp.length; i++) {
        if (i < temp.length - 1) {
          let param = temp[i][0] + "=" + temp[i][1] + "&";
          params += param;
        } else {
          let param = temp[i][0] + "=" + temp[i][1];
          params += param;
        }
      }
      return params;
    }
    handleDate(data: string) {
      var d = new Date(Number(data));
      return d.toLocaleString()
    }
    async deleteRecord(id: number) {
     
      if (confirm("Bạn có chắc chắn muốn xóa bản ghi bàn giao này ?")) {
       await TakeOverService.deleteById(id)
          .then((res) => alert("Delete Successfully !!"))
          .then(() => this.retrieveTakeOverRecords(this.getQueryParams()))
          .catch((err) => alert(err.response.data));
      }
    }
  }