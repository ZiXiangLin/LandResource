<template>
  <div class="wcslist">
    <el-table
      ref="wcsTable"
      :data="wcsInfo"
      highlight-current-row
      @current-change="handleCurrentChange"
      stripe
      style="width: 100%">
      <el-table-column
        prop="id"
        label="ID"
        width="140">
      </el-table-column>
      <el-table-column
        prop="wcsurl"
        label="WCSUrl">
      </el-table-column>
    </el-table>
    <div style="margin-top: 20px">
      <el-button @click="emitCurrent">查看WCS</el-button>
      <el-button @click="setCurrent()">取消选择</el-button>
      <el-button @click="close">关闭</el-button>
    </div>
  </div>
</template>

<script>
    import messageBus from "../../bus/messageBus";

    export default {
      name: "wcslist",
      data(){
        return{
          wcsInfo:[{}],
          currentUrl:'',
        }
      },
      mounted() {
        this.getWcsInfo()
      },
      methods:{
        getWcsInfo(){
          this.$axios.post('/findWcsList')
            .then(successResponse =>{
              this.wcsInfo = successResponse.data
            })
            .catch(failResponse =>{
            })
        },
        handleCurrentChange(val){
          if(val.wcsurl!=null) this.currentUrl = val.wcsurl
          else this.currentUrl=""
        },
        setCurrent(row) {
          this.$refs.wcsTable.setCurrentRow(row);
        },
        emitCurrent(){
          var name = ''
          if(this.currentUrl.search("WCSServices")== -1){
            name = this.currentUrl.substring(this.currentUrl.indexOf("WCS/")+4, this.currentUrl.indexOf("/ImageServer"))
          }else {
            name = this.currentUrl.substring(this.currentUrl.indexOf("WCSServices/")+12, this.currentUrl.indexOf("/ImageServer"))
          }
          console.log(name)
          var result = "http://localhost:6080/arcgis/rest/services/WCS/" + name +"/ImageServer";
          messageBus.$emit('showMapUrlResult',result);
          this.close()
        },
        close(){
          this.$emit('closeWcsList','close WcsList')
        }
      }
    }
</script>

<style scoped>

</style>
