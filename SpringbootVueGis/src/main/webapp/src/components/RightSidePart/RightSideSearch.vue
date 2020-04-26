<template>
    <div class="RightSideSearch">
      <div v-for="item in nav">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>
              <i class="el-icon-s-cooperation"></i>
              服务名： {{item.cnname}}</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="ClickItem(item.id)" >操作按钮</el-button>
          </div>
          <div>
            <tr>
              <i class="el-icon-collection-tag"></i>
              服务描述：</tr>
            <tr>方法名：{{item.cnname}}</tr>
            <tr>方法参数说明：</tr>
            <tr>①	方法编号：{{item.id}}</tr>
            <tr>②	方法类型：{{item.category}}</tr>
            <tr>③	方法英文名: {{item.enname}}</tr>
          </div>
        </el-card>
      </div>
      <div class="button-item-part" style="margin-top: 5px">
        <el-button @click="Fresh">刷新</el-button>
        <el-button @click="PaceBack">回退</el-button>
      </div>
      <mu-divider></mu-divider>
    </div>

</template>

<script>

  import messageBus from "../../bus/messageBus";

    export default {
      name: "RightSideSearch",
      data(){
        return{
          navLevel:1,
          nav:[],
          navDefault:[
            {"id":5,"category":"菜单类","cnname":"数据服务","enname":"Data-Service"},
            {"id":6,"category":"菜单类","cnname":"处理服务","enname":"Processing-Service"},
            {"id":7,"category":"菜单类","cnname":"模型服务","enname":"Model-Service"},
            {"id":8,"category":"菜单类","cnname":"知识服务","enname":"Knowledge-Service"},
            {"id":9,"category":"菜单类","cnname":"验证服务","enname":"Validity-Service"}
          ],
          currentParentNav:0,
          paceBack:0,
        }
      },
      mounted() {
        this.nav = this.navDefault
        this.ListenMessageBusChangeMenu()
      },
      methods:{
        ClickItem(prop){
          this.paceBack = this.currentParentNav
          this.currentParentNav = prop
          this.$axios.post('/isLeaf', {
              navLevel: this.navLevel+'',
              dicId: prop
            }).then(successResponse =>{
              if(successResponse.data.isleaf == 1){
                this.OpenFunction(prop)
              }else {
                this.UpdateNav(prop)
              }
            }).catch(failResponse =>{})
        },
        OpenFunction(prop){
          //NavigationList接受通信
          messageBus.$emit('OpenFunction',prop)
        },
        UpdateNav(item){
          this.$axios.post('/getNavByDicId', {
              navLevel: this.navLevel+'',
              dicId: item,
            }).then(successResponse =>{
              this.nav = successResponse.data;
            }).catch(failResponse =>{})
        },
        Fresh: function () {
          this.nav = this.navDefault
        },
        PaceBack: function () {
          if(this.paceBack === 0){
            this.Fresh()
          }else {
            this.UpdateNav(this.paceBack)
          }
        },
        ListenMessageBusChangeMenu(){
          messageBus.$on('ChangeMenu',(prop)=>{
            this.ClickItem(prop)
          })
        },
      }

    }
</script>

<style scoped>

</style>
