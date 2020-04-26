<template>
  <div class="disaster" >
    <div style="height: 80px"></div>
    <div class="title" >
      <span>模型名称:</span>
      <span>暴雨洪水灾害模型</span>
      <mu-divider style="width: 800px"></mu-divider>
    </div>
    <div class="head">
      <span>参数名:</span>
      <span>参数类型</span>
      <span>参数值</span>
      <mu-divider style="width: 800px"></mu-divider>
    </div>
    <div class="content">
      <ul>
        <li><span>研究区DEM数据</span><span>WCS_Url</span>
          <el-autocomplete
            v-model="inputDEM"
            :fetch-suggestions="queryDataSearchAsync"
            placeholder="请输入内容"
          ></el-autocomplete>
        </li>
        <li><span>DEM数据精度</span><span>accuracy</span>
          <el-input
            class="dem_accuracy"
            v-model="int"
            type="int"
            :rules ="noNegativeIntegerRules"
            placeholder="请输入内容"
            style="width: 202px"
          ></el-input>
        </li>
        <li><span>错误与误差修正</span><span>WPS_Url</span>
          <el-autocomplete
            v-model="error"
            :fetch-suggestions="queryToolSearchAsync"
            placeholder="请输入内容"
          ></el-autocomplete>
        </li>
        <li><span>填充汇</span><span>WPS_Url</span>
          <el-autocomplete
            v-model="fillpits"
            :fetch-suggestions="queryToolSearchAsync"
            placeholder="请输入内容"
          ></el-autocomplete>
        </li>
        <li><span>栅格相减</span><span>WPS_Url</span>
          <el-autocomplete
            v-model="minus"
            :fetch-suggestions="queryToolSearchAsync"
            placeholder="请输入内容"
          ></el-autocomplete>
        </li>
        <li><span>条件函数提取洼地</span><span>WPS_Url</span>
          <el-autocomplete
            v-model="condition"
            :fetch-suggestions="queryToolSearchAsync"
            placeholder="请输入内容"
          ></el-autocomplete>
        </li>
      </ul>
    </div>
    <div class="tail">
      <mu-divider style="width: 800px;margin-bottom: 10px"></mu-divider>
      <el-button id="execute" @click="executeDisaster">执行</el-button>
      <el-button id="cancel" @click="close">取消</el-button>
    </div>
  </div>
</template>

<script>
  import messageBus from "../../bus/messageBus";

  export default {
      name: "disaster",
      props:['closeValue'],
      data(){
        return{
          //模型参数
          inputDEM:'',
          int: '0',
          error:'',
          fillpits:'',
          minus:'',
          condition:'',
          //Autocomplete 获得Service表中的Url
          dataUrl:[],
          toolUrl:[],
          data: '',
          tool:'',
          timeout:  null,
          noNegativeIntegerRules: [
            { validate: (val) => !!val, message: '必须填写'},
            { validate:(val) => !!/^\d+$/.test(val), message: '必须为正值' },
          ],
        }
      },
      //监听MySQL数据库中service 表内URL
      mounted() {
        this.dataUrl = this.loadAllData();
        this.toolUrl = this.loadAllTool();
      },
      methods:{
        //执行模型
        executeDisaster(){
          this.$axios
            .post('/RainDisaster',{
              inputDem: this.inputDEM,
              threshold: this.int,
              deleteError: this.error,
              fill: this.fillpits,
              minus: this.minus,
              condition: this.condition
            })
            .then(successResponse =>{
              let result = ''+ successResponse.data;
              //处理传回的结果 url arcgis api for js 打开
              alert("result: "+result);
              //通信父组件RightSide.vue 显示url：
              messageBus.$emit('showMapUrlResult',result);
              //关闭disaster: 向RightIndex.vue传送 closeDisaster事件
              this.close();
            })
            .catch(failResponse =>{
            })
          console.log(this.closeValue);
        },
        // 关闭Disaster
        close(){
          // 值清零
          this.inputDEM = '';
          this.int = 0;
          this.error = '';
          this.fillpits = '';
          this.minus = '';
          this.condition = '';
          // 向RightIndex.vue传送 closeDisaster事件
          this.$emit('closeDisaster','close Disaster')
        },
        loadAllData() {
          let dataTemp= {};
          let datas = [];
          // 查询Data Url 将查询结果 responseData 储存在 datas,返回datas
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
          // 查询Tool Url 将查询结果 responseData 储存在 datas,返回datas
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
  .dem_accuracy{
    width: 190px;
  }
  .disaster {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
    position: relative;
    padding-left: 200px;
    background-color: #f9f9f9;
  }
</style>
