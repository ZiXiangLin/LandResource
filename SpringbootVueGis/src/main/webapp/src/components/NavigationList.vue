<template>
  <div class="NavigationList">
    <!--中文导航栏-->
    <div v-if="lang===1">
      <el-menu
        class="el-menu-vertical-demo"
        :unique-opened="true"
        background-color="#f9f9f9"
        text-color="#333333"
        active-text-color="#0c86af">

        <!--一级菜单-->
        <template v-for="(item,index) in this.indexNav">
          <el-submenu :index="index+''">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>{{item.cnname}}</span>
            </template>

            <!--二级菜单-->
            <div v-for="(subItem,subIndex) in item.children">
              <el-submenu :index="index+'-'+subIndex">
                <template slot="title">
                  <i class="el-icon-menu"></i>
                  <span>{{subItem.cnname}}</span>
                </template>

                <!--三级菜单-->
                <div v-for="(subItemChildren,subItemChildrenIndex) in subItem.children">
                  <el-menu-item :index="index+'-'+subIndex+'-'+subItemChildrenIndex" @click="ClickNavSubItemChildren(subItemChildren.id)">
                    <template slot="title">
                      <i class="el-icon-menu"></i>
                      <span>{{subItemChildren.cnname}}</span>
                    </template>
                  </el-menu-item>
                </div>

              </el-submenu>
            </div>

          </el-submenu>
        </template>
      </el-menu>
    </div>
    <!--英文导航栏-->
    <div v-if="lang===2">
      <el-menu
        class="el-menu-vertical-demo"
        :unique-opened="true"
        background-color="#f9f9f9"
        text-color="#333333"
        active-text-color="#0c86af">

        <!--一级菜单-->
        <template v-for="(item,index) in this.indexNav">
          <el-submenu :index="index+''">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>{{item.enname}}</span>
            </template>

            <!--二级菜单-->
            <div v-for="(subItem,subIndex) in item.children">
              <el-submenu :index="index+'-'+subIndex">
                <template slot="title">
                  <i class="el-icon-menu"></i>
                  <span>{{subItem.enname}}</span>
                </template>

                <!--三级菜单-->
                <div v-for="(subItemChildren,subItemChildrenIndex) in subItem.children">
                  <el-menu-item @click="ClickNavSubItemChildren(subItemChildren.id)">
                    <template slot="title">
                      <i class="el-icon-menu"></i>
                      <span>{{subItemChildren.enname}}</span>
                    </template>
                  </el-menu-item>
                </div>

              </el-submenu>
            </div>

          </el-submenu>
        </template>
      </el-menu>
    </div>
  </div>
</template>

<script>

  import messageBus from "../bus/messageBus";

  export default {
    name: "NavigationList",
    data(){
      return{
        //lang:1时显示zh lang:2时显示en
        lang:1,
        //indexNav变量存储查询Mysql动态生成的Nav数据
        indexNav:[],
        //当前chosenItem的Nav级数
        navLevel:1,
        //当前选中的导航栏项
        selected: '',
      }
    },
    mounted() {
      // 1.调用IteratorNav动态生成indexNav
      this.$axios.post('/index',{
        }).then(successResponse =>{
          this.indexNav = successResponse.data;
          for(let i=0;i<this.indexNav.length;i++){
            this.IteratorNav(this.indexNav[i]);
          }
        }).catch(failResponse => {})

      // 2.监听MessageBUS RightSearch传来的Open Function情求
      this.ListenMessageBusOpenFunction()

      // 3.监听MessageBus Header传来的Change language请求
      this.ListenMessageBusChangeLang()

    },
    computed:{
      //Vuex 公共变量navUrl
      navUrl:{
        get(){
          return this.$store.state.navUrl
        },
        set(val){
          this.$store.commit('changeNavUrl',val)
        }
      }
    },
    methods:{
      ClickNavSubItemChildren(item){
        this.selected = item
        switch (item) {
          case 41:
            this.$taber.open({name: 'disaster'})
            break;
          case 46:
            this.$taber.open({name: 'casaNpp'})
            break;
          case 57:
            this.$taber.open({name: 'rivernet'})
            break;
          case 137:
            this.$taber.open({name: 'streamExtraction'})
            break;
          case 162:
            this.$taber.open({name: 'wcslist'})
            break;
          case 165:
            this.$taber.open({name: 'showUavPicture'})
            break;
        }
      },
      IteratorNav(item){
        this.$axios.post('/isLeaf', {
            navLevel: this.navLevel+'',
            dicId: item.id,
          }).then(successResponse =>{
          if(successResponse.data.isleaf == 1){
            //为叶结点，迭代结束返回
          }else {
            //非叶结点，添加subNav至children
            this.$axios.post('/getNavByDicId', {
              navLevel: this.navLevel+'',
              dicId: item.id,
            }).then(successResponse =>{
              let tempNav = successResponse.data;
              let temp = JSON.parse(JSON.stringify(tempNav))
              // 调用Vue.set 重渲染Nav
              typeof item.children !=='undefined' ? item.children.push(temp) : this.$set(item,'children',temp)
              //遍历新生成的children下所有叶结点 迭代查询导航栏
              for(let j=0;j<item.children.length;j++){
                this.IteratorNav(item.children[j])
              }
            }).catch(failResponse =>{})
          }
        }).catch(failResponse =>{})
      },
      ListenMessageBusOpenFunction(){
        messageBus.$on('OpenFunction',(prop)=>{
          this.ClickNavSubItemChildren(prop)
        })
      },
      ListenMessageBusChangeLang(){
        messageBus.$on('ChangeLang',(prop)=>{
          if(prop==='zh'){this.lang = 1}
          else if(prop==='en'){this.lang = 2}
        })
      },

    },
  }
</script>

<style scoped>
  .el-menu-vertical-demo{
    width: 100%;
    height: 100%;
  }
</style>
