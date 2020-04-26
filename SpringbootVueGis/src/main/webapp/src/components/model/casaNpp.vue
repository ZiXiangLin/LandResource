<template>
  <div class="casaNpp">
    <div class="table">
      <div class="title">
        <span>模型&nbsp;&nbsp;&nbsp;名称:</span>
        <span>NPP模型模型</span>
        <div class="line"></div>
      </div>
      <div class="head">
        <span>参数名:</span>
        <span>参数类型</span>
        <span>参数值</span>
        <div class="line"></div>
      </div>
      <div class="content">
        <ul>
          <li><span>NDVI数据</span><span>WCS_Url</span>
            <el-autocomplete
              v-model="ndvi"
              :fetch-suggestions="queryDataSearchAsync"
              placeholder="请输入内容"
              @select="handleSelect"
            ></el-autocomplete></li>
          <li><span>SOL数据</span><span>WCS_Url</span>
            <el-autocomplete
              v-model="sol"
              placeholder="请输入内容"
            ></el-autocomplete></li>
          <li><span>T数据</span><span>WCS_Url</span>
            <el-autocomplete
              v-model="t"
              placeholder="请输入内容"
            ></el-autocomplete></li>
          <li><span>T_OPT数据</span><span>String</span>
            <el-autocomplete
              v-model="tOpt"
              placeholder="请输入内容"
            ></el-autocomplete></li>
          <li><span>ET数据</span><span>WCS_Url</span>
            <el-autocomplete
              v-model="et"
              placeholder="请输入内容"
            ></el-autocomplete></li>
          <li><span>PET数据</span><span>WCS_Url</span>
            <el-autocomplete
              v-model="pet"
              placeholder="请输入内容"
            ></el-autocomplete></li>
          <li><span>Calculate_SR</span><span>WPS_Url</span>
            <el-autocomplete
              v-model="sr"
              :fetch-suggestions="queryToolSearchAsync"
              placeholder="请输入内容"
              @select="handleSelect"
            ></el-autocomplete></li>
          <li><span>Calculate_FPAR</span><span>WPS_Url</span>
            <el-autocomplete
              v-model="fpar"
              :fetch-suggestions="queryToolSearchAsync"
              placeholder="请输入内容"
              @select="handleSelect"
            ></el-autocomplete></li>
          <li><span>Calculate_APAR</span><span>WPS_Url</span>
            <el-autocomplete
              v-model="apar"
              :fetch-suggestions="queryToolSearchAsync"
              placeholder="请输入内容"
              @select="handleSelect"
            ></el-autocomplete></li>
          <li><span>Calculate_T1</span><span>WPS_Url</span>
            <el-autocomplete
              v-model="t1"
              :fetch-suggestions="queryToolSearchAsync"
              placeholder="请输入内容"
              @select="handleSelect"
            ></el-autocomplete></li>
          <li><span>Calculate_T2</span><span>WPS_Url</span>
            <el-autocomplete
              v-model="t2"
              :fetch-suggestions="queryToolSearchAsync"
              placeholder="请输入内容"
              @select="handleSelect"
            ></el-autocomplete></li>
          <li><span>Calculate_W</span><span>WPS_Url</span>
            <el-autocomplete
              v-model="w"
              :fetch-suggestions="queryToolSearchAsync"
              placeholder="请输入内容"
              @select="handleSelect"
            ></el-autocomplete></li>
          <li><span>Calculate_e</span><span>WPS_Url</span>
            <el-autocomplete
              v-model="e"
              :fetch-suggestions="queryToolSearchAsync"
              placeholder="请输入内容"
              @select="handleSelect"
            ></el-autocomplete></li>
          <li><span>Calculate_NPP</span><span>WPS_Url</span>
            <el-autocomplete
              v-model="npp"
              :fetch-suggestions="queryToolSearchAsync"
              placeholder="请输入内容"
              @select="handleSelect"
            ></el-autocomplete></li>
        </ul>
      </div>
      <div class="tail">
        <button id="execute" @click="executeCASANPP">执行</button>
        <button id="cancel" @click="close">取消</button>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "casaNpp",
    data(){
      return{
        //模型参数
        ndvi:'',
        sol:'',
        t:'',
        tOpt:'',
        et:'',
        pet:'',
        sr:'',
        fpar:'',
        apar:'',
        t1:'',
        t2:'',
        w:'',
        e:'',
        npp:'',
        //AutoComplete 获得Service 表中的Url
        dataUrl:[],
        tooUrl:[],
        data:'',
        tool:'',
        timeout: null
     }
    },
    mounted(){
      this.dataUrl = this.loadAllData();
      this.toolUrl = this.loadAllTool();
    },
    methods:{
      executeCASANPP(){
        this.$axios
          .post('/CASANPP',{
            ndvi: this.ndvi,
            sol: this.sol,
            t: this.t,
            tOpt: this.tOpt,
            et: this.et,
            pet: this.pet,
            sr: this.sr,
            fpar: this.fpar,
            apar: this.apar,
            t1: this.t1,
            t2: this.t2,
            w: this.w,
            e: this.e,
            npp: this.npp,
          })
          .then(successResponse =>{
            console.log(successResponse.data);
            let result = ''+ successResponse.data;
            console.log(result);
            //window.open("/MySystem/page/processing/servermap_admin.jsp?url="+result)
          })
          .catch(failResponse =>{
          })
        console.log(this.closeValue);
      },
      close(){
        this.ndvi='';
        this.sol='';
        this.t='';
        this.tOpt='';
        this.et='';
        this.per='';
        this.sr='';
        this.fpar='';
        this.apat='';
        this.t1='';
        this.t2='';
        this.w='';
        this.e='';
        this.npp='';
        this.$emit('closeCasaNpp','close CASA NPP')
      },
      loadAllData(){
        let dataTemp= {};
        let datas = [];
        this.$axios
          .post('/getDataUrl')
          .then(successResponse =>{
            let responseData = successResponse.data;
            for(let i=0;i < responseData.length;i++){
              dataTemp["value"] = responseData[i].url;
              dataTemp["cnname"] = responseData[i].cnname;
              datas.push(dataTemp);
            }
          })
          .catch(failResponse =>{
          })
        return datas;
      },
      loadAllTool(){
        let dataTemp= {};
        let datas = [];
        this.$axios
          .post('/getToolUrl')
          .then(successResponse =>{
            let responseData = successResponse.data;
            for(let i=0;i < responseData.length;i++){
              dataTemp["value"] = responseData[i].url;
              dataTemp["cnname"] = responseData[i].cnname;
              datas.push(dataTemp);
            }
          })
          .catch(failResponse =>{
          })
        return datas;
      },
      queryDataSearchAsync(queryString, cb) {
        var dataUrl = this.dataUrl;
        var results = queryString ? dataUrl.filter(this.createStateFilterDataUrl(queryString)) : dataUrl;
        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(results);
        }, 3000 * Math.random());
      },
      queryToolSearchAsync(queryString,cb) {
        var dataUrl = this.toolUrl;
        var results = queryString ? dataUrl.filter(this.createStateFilterToolUrl(queryString)) : dataUrl;
        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(results);
        }, 3000 * Math.random());
      },
      createStateFilterDataUrl(queryString) {
        return (data) => {
          return (data.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      createStateFilterToolUrl(queryString) {
        return (tool) => {
          return (tool.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      handleSelect(item) {
        console.log(item);
      }
    }
  }
</script>

<style scoped>
  .title,.head{
    height: 50px;
  }
  .tail{
    height: 60px;
  }
  .content{
    height: auto;
  }
  span{
    display: inline-table;
    width: 240px;
    height: 30px;
    overflow: hidden;
    text-align: left;
  }
  #execute{
    position: relative;
    left: 150px;
  }
  #cancel{
    position: relative;
    left: 330px;
  }

</style>
