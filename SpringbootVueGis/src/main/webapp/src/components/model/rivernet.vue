<template>
    <div class="rivernet">
      <h2>河网提取</h2>
      <div id="wizard">
        <div>
          <div class="row">
            <div style="margin-left: 5%;margin-right: 5%;">
              <h3>河网提取</h3>
              <blockquote>
                <p>根据DEM利用水文分析工具提取地表水流径流模型的水流方向、汇流累积量、水流长度、
                  河流网络（包括河流网络的分级等）以及对研究区的流域进行分割等。</p>
              </blockquote>
            </div>
            <div class="col-4" style="margin-left: 5%; width: 600px;">
              <form id="uploadForm" action="file" enctype="multipart/form-data"
                    method="post">
                <h4>输入数据:</h4>
                <div class="form-group">
                  <input type="file" ref="myfile" @change="getFile($event)">
                </div>
                <h4>阈值设定:</h4>
                <div class="form-group">
                  <input type="number" v-model="number" id="input02" class="number" >
                  <input type="text" v-model="url"  id="input03" class="url" style="visibility: hidden;" >
                </div>
                <el-button @click="importData" type="success" size="mini" icon="el-icon-upload2">上传与处理</el-button>
                <el-button @click="preview" id="btnUpload" size="mini">结果预览</el-button>
              </form>
            </div>
          </div>
        </div>
<!--        <div>-->
<!--          <div style=" margin-left: 5%;">-->
<!--            <button style="width: 80px; height: 40px;">预览</button>-->
<!--          </div>-->
<!--        </div>-->
      </div>
    </div>
</template>

<script>

    import messageBus from "../../bus/messageBus";

    export default {
        name: "rivernet",
        data(){
          return{
            file:'',
            url:'',
            number:0,
          };
        },
        methods:{
          getFile(){
            this.file = event.target.files[0];
            console.log(this.file);
          },
          preview(){
            if(this.url=""){
              alert("severmap.jsp?url="+this.url);
            }else {
              alert("url is null!");
            }
          },
          importData(){
            event.preventDefault();
            let formData = new FormData();
            formData.append("file",this.file);
            formData.append("username",this.$store.state.user.username);
            formData.append("number",this.number);
            this.$axios.post('/upload',formData,{
                timeout: 900000,
              })
              .then(function (response) {
                alert(response.data);
              })
              .catch((function (error) {
                alert("error");
                console.log(error);
              }))

          }
        },
        mounted() {
        }
    }
</script>

<style scoped>

</style>
