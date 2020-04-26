<template>
  <div class="manage">
    <div class="header">
      <Header></Header>
    </div>
    <div class="section">
      <div class="manage-item" style="height: 99%;width: 99%">
        <div id="pf-page" style="float: left;background-color: #FFF;height: 99%;width: 99%;margin-bottom: 3px;margin-left: 3px;margin-right: 5px;">
          <el-col :span="24"  style="margin-bottom: 5px">
            <el-tabs v-model="activeName" type="border-card" >
              <el-tab-pane label="数据库管理" name="first" style="height: 800px">
                <h2 style="margin-left: 25px">数据库管理</h2>
                <div style="margin-left: 25px">
                  <el-button @click="drawer = true" type="primary" >
                    {{this.$store.state.user.username}}
                  </el-button>
                  <el-drawer
                    title="Drawer"
                    :visible.sync="drawer"
                    :direction="direction">
                    <Drawer></Drawer>
                  </el-drawer>
                </div>
                <div style="margin-left: 25px;margin-top: 25px">
                  <el-collapse v-model="activeNameCollapse" accordion>
                    <el-collapse-item title="一致性 Consistency" name="1">
                      <div>与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；</div>
                      <div>在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。</div>
                    </el-collapse-item>
                    <el-collapse-item title="反馈 Feedback" name="2">
                      <div>控制反馈：通过界面样式和交互动效让用户可以清晰的感知自己的操作；</div>
                      <div>页面反馈：操作后，通过页面元素的变化清晰地展现当前状态。</div>
                    </el-collapse-item>
                    <el-collapse-item title="效率 Efficiency" name="3">
                      <div>简化流程：设计简洁直观的操作流程；</div>
                      <div>清晰明确：语言表达清晰且表意明确，让用户快速理解进而作出决策；</div>
                      <div>帮助用户识别：界面简单直白，让用户快速识别而非回忆，减少用户记忆负担。</div>
                    </el-collapse-item>
                    <el-collapse-item title="可控 Controllability" name="4">
                      <div>用户决策：根据场景可给予用户操作建议或安全提示，但不能代替用户进行决策；</div>
                      <div>结果可控：用户可以自由的进行操作，包括撤销、回退和终止当前操作等。</div>
                    </el-collapse-item>
                  </el-collapse>
                </div>
                <div style="margin-left: 25px;margin-top: 25px">
                  <el-row class="demo-info">
                    <el-col :span="8">
                      <el-card shadow="never">
                        <div slot="header" class="clearfix">
                          <span>{{ $t('index.system') }}</span>
                          <i class="el-icon-refresh" style="float: right; padding: 3px 0"></i>
                          <i class="el-icon-star-off" style="float: right; padding: 3px 0"></i>
                          <i class="el-icon-bell" style="float: right; padding: 3px 0"></i>
                          <i class="el-icon-switch-button" style="float: right; padding: 3px 0"></i>
                        </div>
                        <div>
                          <div id="myChart" :style="{width:'100%',height:'300px'}"></div>
                        </div>
                      </el-card>
                    </el-col>
                    <el-col :span="8">
                      <el-card shadow="never">
                        <div slot="header" class="clearfix">
                          <span>{{ $t('index.visit') }}</span>
                          <i class="el-icon-refresh" style="float: right; padding: 3px 0"></i>
                          <i class="el-icon-star-off" style="float: right; padding: 3px 0"></i>
                          <i class="el-icon-bell" style="float: right; padding: 3px 0"></i>
                          <i class="el-icon-switch-button" style="float: right; padding: 3px 0"></i>
                        </div>
                        <div>
                          <div id="myChart2" :style="{width:'100%',height:'300px'}"></div>
                        </div>
                      </el-card>
                    </el-col>
                    <el-col :span="8">
                      <el-card shadow="never">
                        <div slot="header" class="clearfix">
                          <span>{{ $t('index.date') }}</span>
                          <i class="el-icon-refresh" style="float: right; padding: 3px 0"></i>
                          <i class="el-icon-star-off" style="float: right; padding: 3px 0"></i>
                          <i class="el-icon-bell" style="float: right; padding: 3px 0"></i>
                          <i class="el-icon-switch-button" style="float: right; padding: 3px 0"></i>
                        </div>
                        <div>
                          <div id="myChart3" :style="{width:'100%',height:'300px'}"></div>
                        </div>
                      </el-card>
                    </el-col>
                  </el-row>
                </div>



              </el-tab-pane>

              <el-tab-pane label="Navigation表" name="second" style="margin-bottom: 5px">

                <el-table
                  :data="this.navList"
                  highlight-current-row
                  @current-change="handleNavCurrentChange"
                  style="float: left;width: 79%;margin-bottom: 5px">
                  <el-table-column prop="nav_ID" label="nav_ID"></el-table-column>
                  <el-table-column prop="enname" label="enname"></el-table-column>
                  <el-table-column prop="cnname" label="cnname"></el-table-column>
                  <el-table-column prop="isleaf" label="isleaf"></el-table-column>
                  <el-table-column prop="dic_ID" label="dic_ID"></el-table-column>
                  <el-table-column prop="parent_ID" label="parent_ID"></el-table-column>
                  <el-table-column prop="nav_ICON" label="nav_ICON"></el-table-column>
                  <el-table-column prop="nav_LEVEL" label="nav_LEVEL"></el-table-column>
                  <el-table-column prop="nav_URL_EN" label="nav_URL_EN"></el-table-column>
                  <el-table-column prop="nav_URL_CN" label="nav_URL_CN"></el-table-column>
                </el-table>

                <div id="nav-pf-sider" style="float: right;width: 20%">
                  <el-row>
                    <el-col :span="24">
                      <el-menu
                        default-active="2"
                        mode="vertical"
                        class="el-nav-manage-navigation"
                        @close="handleClose">
                        <el-menu-item index="1" @click="handleManageNav('addNav')">
                          <i class="el-icon-circle-plus-outline"></i>
                          <span slot="title">添加二级导航栏</span>
                        </el-menu-item>
                        <el-menu-item index="2" @click="handleManageNav('updateNav')">
                          <i class="el-icon-edit"></i>
                          <span slot="title">修改导航栏</span>
                        </el-menu-item>
                        <el-menu-item index="3" @click="handleManageNav('removeNav')">
                          <i class="el-icon-remove-outline"></i>
                          <span slot="title">删除导航栏</span>
                        </el-menu-item>
                      </el-menu>
                    </el-col>
                  </el-row>

                  <el-dialog title=this.formNav.title :visible.sync="showNavFormItem" >
                    <div class="form-item" style="background-color: #FFF; width: 99%;">
                      <el-row>
                        <el-col :span="12">
                          <el-form ref="formNav" :model="formNav" label-width="80px">
                            <el-form-item label="中文名称" :rules="[{required: true,message:'不能为空'}]">
                              <el-input v-model="formNav.cnname"></el-input>
                            </el-form-item>
                            <el-form-item label="英文名称" :rules="[{required: true,message:'不能为空'}]">
                              <el-input v-model="formNav.enname"></el-input>
                            </el-form-item>
                            <el-form-item label="图标" :rules="[{required: true,message:'不能为空'}]">
                              <el-input v-model="formNav.icon"></el-input>
                            </el-form-item>
                            <el-form-item label="父节点ID" :rules="[{required: true,message:'不能为空'}]">
                              <el-input v-model="formNav.parentId"></el-input>
                            </el-form-item>
                            <el-form-item>
                              <el-button type="primary" @click="onNavSubmit">保存</el-button>
                              <el-button>取消</el-button>
                            </el-form-item>
                          </el-form>
                        </el-col>
                        <el-col :span="12">
                          <LogoPage></LogoPage>
                        </el-col>
                      </el-row>
                    </div>
                  </el-dialog>
                </div>

              </el-tab-pane>

              <el-tab-pane label="PerCenterNav表" name="third" >

                <el-table
                  :data="this.perCenterNavList"
                  highlight-current-row
                  @current-change="handlePerCenterNavCurrentChange"
                  style="float: left;width: 79%;margin-bottom: 5px">
                  <el-table-column prop="src_URL" label="src_URL"></el-table-column>
                  <el-table-column prop="en_NAME" label="en_NAME"></el-table-column>
                  <el-table-column prop="icon" label="icon"></el-table-column>
                  <el-table-column prop="cn_NAME" label="cn_NAME"></el-table-column>
                </el-table>

                <div id="pf-sider" style="float: right;width: 20%">
                  <el-row style="">
                    <el-col :span="24">
                      <el-menu
                        default-active="2"
                        mode="vertical"
                        class="el-menu-manage-navigation"
                        @close="handleClose">
                        <el-menu-item index="1" @click="handleManagePerCenterNavigation('addPerCenterNav')">
                          <i class="el-icon-circle-plus-outline"></i>
                          <span slot="title">添加导航栏</span>
                        </el-menu-item>
                        <el-menu-item index="2" @click="handleManagePerCenterNavigation('updatePerCenterNav')">
                          <i class="el-icon-edit"></i>
                          <span slot="title">修改导航栏</span>
                        </el-menu-item>
                        <el-menu-item index="3" @click="handleManagePerCenterNavigation('removePerCenterNav')">
                          <i class="el-icon-remove-outline"></i>
                          <span slot="title">删除导航栏</span>
                        </el-menu-item>
                      </el-menu>
                    </el-col>
                  </el-row>

                  <el-dialog title=this.form.title :visible.sync="showFormItem" >
                    <div class="form-item" style="background-color: #FFF; width: 99%;">
                      <el-row>
                        <el-col :span="12">
                          <el-form ref="form" :model="form" label-width="80px">
                            <el-form-item label="cn_Name" :rules="[{required: true,message:'不能为空'}]">
                              <el-input v-model="form.cnname"></el-input>
                            </el-form-item>
                            <el-form-item label="en_Name" :rules="[{required: true,message:'不能为空'}]">
                              <el-input v-model="form.enname"></el-input>
                            </el-form-item>
                            <el-form-item label="icon" :rules="[{required: true,message:'不能为空'}]">
                              <el-input v-model="form.icon"></el-input>
                            </el-form-item>
                            <el-form-item label="src_URL" :rules="[{required: true,message:'不能为空'}]">
                              <el-input v-model="form.url"></el-input>
                            </el-form-item>
                            <el-form-item>
                              <el-button type="primary" @click="onPerCenterSubmit">保存</el-button>
                              <el-button>取消</el-button>
                            </el-form-item>
                          </el-form>
                        </el-col>
                        <el-col :span="12">
                          <LogoPage></LogoPage>
                        </el-col>
                      </el-row>
                    </div>
                  </el-dialog>

                  <el-dialog title="showDecisionItem" :visible.sync="showDecisionItem" class="form-item" style="background-color: #FFF;">
                    <el-row>
                      <el-form ref="form" :model="form" label-width="80px">
                        <el-form-item>
                          如有子项将无法删除，是否继续？
                        </el-form-item>
                        <el-form-item>
                          <el-button type="primary" @click="onPerCenterSubmit">确定</el-button>
                          <el-button>取消</el-button>
                        </el-form-item>
                      </el-form>
                    </el-row>
                  </el-dialog>

                </div>

              </el-tab-pane>

              <el-tab-pane label="Menu表" name="fourth">

                <el-table
                  :data="this.menuList"
                  highlight-current-row
                  @current-change="handleMenuCurrentChange"
                  style="float: left;width: 79%;margin-bottom: 5px">
                  <el-table-column prop="menu_ID" label="menu_ID"></el-table-column>
                  <el-table-column prop="menu_ICON" label="menu_ICON"></el-table-column>
                  <el-table-column prop="dic_ID" label="dic_ID"></el-table-column>
                  <el-table-column prop="cnname" label="cnname"></el-table-column>
                  <el-table-column prop="enname" label="enname"></el-table-column>
                </el-table>

                <div id="menu-pf-sider" style="float: right;width: 20%">
                  <el-row style="">
                    <el-col :span="24">
                      <el-menu
                        default-active="2"
                        mode="vertical"
                        class="el-menu-manage-navigation"
                        @close="handleClose">
                        <el-menu-item index="1" @click="handleManageMenu('addMenu')">
                          <i class="el-icon-circle-plus-outline"></i>
                          <span slot="title">添加菜单栏</span>
                        </el-menu-item>
                        <el-menu-item index="2" @click="handleManageMenu('updateMenu')">
                          <i class="el-icon-edit"></i>
                          <span slot="title">修改菜单栏</span>
                        </el-menu-item>
                        <el-menu-item index="3" @click="handleManageMenu('removeMenu')">
                          <i class="el-icon-remove-outline"></i>
                          <span slot="title">删除菜单栏</span>
                        </el-menu-item>
                      </el-menu>
                    </el-col>
                  </el-row>

                  <el-dialog title=this.formMenu.title :visible.sync="showMenuFormItem" >
                    <div class="form-item" style="background-color: #FFF; width: 99%;">
                      <el-row>
                        <el-col :span="12">
                          <el-form ref="formMenu" :model="formMenu" label-width="80px">
                            <el-form-item label="中文名称" :rules="[{required: true,message:'不能为空'}]">
                              <el-input v-model="formMenu.cnname"></el-input>
                            </el-form-item>
                            <el-form-item label="英文名称" :rules="[{required: true,message:'不能为空'}]">
                              <el-input v-model="formMenu.enname"></el-input>
                            </el-form-item>
                            <el-form-item label="图标" :rules="[{required: true,message:'不能为空'}]">
                              <el-input v-model="formMenu.icon"></el-input>
                            </el-form-item>
                            <el-form-item>
                              <el-button type="primary" @click="onMenuSubmit">保存</el-button>
                              <el-button>取消</el-button>
                            </el-form-item>
                          </el-form>
                        </el-col>
                        <el-col :span="12">
                          <LogoPage></LogoPage>
                        </el-col>
                      </el-row>
                    </div>
                  </el-dialog>


                </div>

              </el-tab-pane>

            </el-tabs>
          </el-col>

        </div>
      </div>
    </div>
    <div class="footer">
      <Footer></Footer>
    </div>

  </div>
</template>

<script>

  import Header from "./PageParts/Header";
  import Footer from "./PageParts/Footer";
  import Drawer from "./PageParts/Drawer";
  import NavigationList from "./NavigationList";
  import LogoPage from "./PageParts/LogoPage";
  import RightSideIndex from "./RightSidePart/RightSideIndex";
  import RightSideMap from "./RightSidePart/RightSideMap";


  import messageBus from "../bus/messageBus";

  export default {
    name: "manage",
    data(){
      return{
        activeName:'first',
        value: new Date(),

        drawer: false,
        direction: 'rtl',

        //存储Nav Menu PerNav数据
        navList:[],
        menuList:[],
        perCenterNavList:[],

        //存储Nav Menu PerNav点击项
        NavCurrentRow:null,
        MenuCurrentRow:null,
        PerCenterNavcurrentRow:null,

        //PerNav修改时的表单
        form: {
          cnname: '',
          enname: '',
          url: '',
          icon:'',
          title:''
        },

        //Menu修改时的表单
        formMenu:{
          cnname:'',
          enname:'',
          icon:'',
          title:'',
        },

        //Nav修改时的表单
        formNav:{
          parentId:'',
          cnname:'',
          enname:'',
          icon:'',
          title:'',
        },

        //PerNav修改时相关变量
        showFormItem:false,
        showDecisionItem:false,
        doWhatPerCenter:'',

        //Menu修改时相关变量
        showMenuFormItem:false,
        doWhatMenu:'',

        //Nav修改时相关变量
        showNavFormItem:false,
        doWhatNav:'',

        //ICONFONT相关变量
        ChosenIcon:'',

        //查询得到的表单
        searchOriginalNavInfo:{
          icon:'',
          cnname:'',
          enname:'',
        }
      }
    },
    computed:{
      navUrl:{
        get(){
          console.log(this.$store.state.navUrl)
          return this.$store.state.navUrl
        },
        set(val){
          this.$store.commit('changeNavUrl',val)
        }
      }
    },
    components:{
      NavigationList,
      Header,
      Footer,
      Drawer,
      LogoPage,
      RightSideIndex,
      RightSideMap,
    },
    mounted() {
      this.getMenuList();
      this.getNavList();
      this.getPerCenterNavList();
      this.listenToIconChosen();
      this.InitEcharts();
    },
    methods:{
      //ICONFONT相关操作
      listenToIconChosen(){
        messageBus.$on('chosenIconFont',(prop)=>{
          this.ChosenIcon = prop
          this.form.icon = prop
          this.formMenu.icon = prop
          this.formNav.icon = prop
        })
      },

      // PerNav CRUD操作
      handleManagePerCenterNavigation(e){
        switch (e) {
          case 'addPerCenterNav':
            this.addPerCenterNav()
            break;
          case 'updatePerCenterNav':
            this.updatePerCenterNav()
            break;
          case 'removePerCenterNav':
            this.removePerCenterNav()
            break;
          default:
            break;
        }
      },
      addPerCenterNav(){
        this.form.cnname =''
        this.form.enname =''
        this.form.url = ''
        this.form.icon = ''
        this.form.title = '添加导航栏'
        this.doWhatPerCenter = 'addPerCenterNav'
        this.showFormItem = true
      },
      updatePerCenterNav(){
        if(this.PerCenterNavcurrentRow){
          this.form.cnname =this.PerCenterNavcurrentRow.cn_NAME
          this.form.enname =this.PerCenterNavcurrentRow.en_NAME
          this.form.url = this.PerCenterNavcurrentRow.src_URL
          this.form.icon = this.PerCenterNavcurrentRow.icon
          this.form.title = '修改导航栏'
          this.doWhatPerCenter = 'updatePerCenterNav'
          this.showFormItem = true
        }else {
          alert('当前未选择导航栏')
        }
      },
      removePerCenterNav(){
        if(this.PerCenterNavcurrentRow){
          this.$axios
            .post('/ManagePersonalCenterNav', {
              method:'removeSiderNav',
              oldcnname:this.PerCenterNavcurrentRow.cn_NAME
            }).then(successResponse =>{
            console.log(successResponse.data)
            if (("" + successResponse.data) == 'true') {
              alert("操作成功")
              this.getPerCenterNavList();
            } else {
              alert("操作失败")
            }
          }).catch(failResponse =>{
          })
        }else {
          alert('当前未选择导航栏')
        }
      },
      onPerCenterSubmit(){
        switch (this.doWhatPerCenter) {
          case "addPerCenterNav":
            this.$axios
              .post('/ManagePersonalCenterNav', {
                method:'addSiderNav',
                cnname:this.form.cnname,
                enname:this.form.enname,
                icon:this.form.icon,
                url:this.form.url,
              }).then(successResponse =>{
              console.log(successResponse.data)
              if (("" + successResponse.data) == 'true') {
                alert("操作成功")
                this.getPerCenterNavList();
              } else {
                alert("操作失败")
              }
            }).catch(failResponse =>{
            })
            break;
          case "updatePerCenterNav":
            this.$axios
              .post('/ManagePersonalCenterNav', {
                method:'updateSiderNav',
                cnname:this.form.cnname,
                enname:this.form.enname,
                icon:this.form.icon,
                url:this.form.url,
                oldcnname:this.PerCenterNavcurrentRow.cn_NAME,
              }).then(successResponse =>{
              console.log(successResponse.data)
              if (("" + successResponse.data) == 'true') {
                alert("操作成功")
                this.getPerCenterNavList();
              } else {
                alert("操作失败")
              }
            }).catch(failResponse =>{
            })
            break;
        }
      },

      // Menu表 CRUD操作
      handleManageMenu(e){
        switch (e) {
          case 'addMenu':
            this.addMenu()
            break;
          case 'updateMenu':
            this.updateMenu()
            break;
          case 'removeMenu':
            this.removeMenu()
            break;
          default:
            break;
        }
      },
      addMenu(){
        this.formMenu.cnname =''
        this.formMenu.enname =''
        this.formMenu.icon = ''
        this.formMenu.title = '添加菜单栏'
        this.doWhatMenu = 'addMenu'
        this.showMenuFormItem = true
      },
      updateMenu(){
        if(this.MenuCurrentRow){
          this.formMenu.cnname =this.MenuCurrentRow.cn_NAME
          this.formMenu.enname =this.MenuCurrentRow.en_NAME
          this.formMenu.icon = this.MenuCurrentRow.icon
          this.formMenu.title = '修改菜单栏'
          this.doWhatMenu = 'updateMenu'
          this.showMenuFormItem = true
        }else {
          alert('当前未选择菜单栏')
        }
      },
      removeMenu(){
        if(this.MenuCurrentRow){
          this.$axios
            .post('/ManageMenu', {
              method:'removeMenu',
              currentMenuName:this.MenuCurrentRow.cnname,
            }).then(successResponse =>{
            console.log(successResponse.data)
            if (("" + successResponse.data) == 'true') {
              alert("操作成功")
              this.getMenuList();
            } else {
              alert("非叶结点，操作失败")
            }
          }).catch(failResponse =>{
          })
        }else {
          alert('当前未选择导航栏')
        }
      },
      onMenuSubmit(){
        switch (this.doWhatMenu) {
          case "addMenu":
            this.$axios
              .post('/ManageMenu', {
                method:'addMenu',
                newCnName:this.formMenu.cnname,
                newEnName:this.formMenu.enname,
                newIcon:this.formMenu.icon,
              }).then(successResponse =>{
              console.log(successResponse.data)
              if (("" + successResponse.data) == 'true') {
                alert("操作成功")
                this.getMenuList();
              } else {
                alert("操作失败")
              }
            }).catch(failResponse =>{
            })
            break;
          case "updateMenu":
            this.$axios
              .post('/ManageMenu', {
                method:'updateMenu',
                newCnName:this.formMenu.cnname,
                newEnName:this.formMenu.enname,
                newIcon:this.formMenu.icon,
                currentMenuName:this.MenuCurrentRow.cnname,
                currentMenuID:this.MenuCurrentRow.menu_ID
              }).then(successResponse =>{
              console.log(successResponse.data)
              if (("" + successResponse.data) == 'true') {
                alert("操作成功")
                this.getMenuList();
              } else {
                alert("操作失败")
              }
            }).catch(failResponse =>{
            })
            break;
        }
      },

      // Nav表 CRUD操作
      handleManageNav(e){
        switch (e) {
          case 'addNav':
            this.addNav()
            break;
          case 'updateNav':
            this.updateNav()
            break;
          case 'removeNav':
            this.removeNav()
            break;
          default:
            break;
        }
      },
      addNav(){
        this.formNav.cnname =''
        this.formNav.enname =''
        this.formNav.icon = ''
        this.formNav.title = '添加导航栏'
        this.doWhatNav = 'addNav'
        this.showNavFormItem = true
      },
      updateNav(){
        if(this.NavCurrentRow){
          this.formNav.cnname =this.NavCurrentRow.cnname
          this.formNav.enname =this.NavCurrentRow.enname
          this.formNav.icon = this.NavCurrentRow.nav_ICON
          this.formNav.parentId = this.NavCurrentRow.parent_ID
          this.formNav.title = '修改导航栏'
          this.doWhatNav = 'updateNav'
          this.showNavFormItem = true
        }else {
          alert('当前未选择导航栏')
        }
      },
      removeNav(){
        if(this.NavCurrentRow){
          this.$axios
            .post('/ManageMenu', {
              method:'removeSiderNav',
              current_nav_id:this.NavCurrentRow.nav_ID,
              current_nav_name:this.NavCurrentRow.cnname,
              current_nav_icon:this.NavCurrentRow.nav_ICON,
            }).then(successResponse =>{
            console.log(successResponse.data)
            if (("" + successResponse.data) == 'true') {
              alert("操作成功")
              this.getNavList();
            } else {
              alert("非叶结点，操作失败")
            }
          }).catch(failResponse =>{
          })
        }else {
          alert('当前未选择导航栏')
        }
      },
      onNavSubmit(){
        switch (this.doWhatNav) {
          case "addNav":
            this.$axios
              .post('/ManageMenu', {
                method:'addSiderNav',
                parent_id:this.formNav.parentId,
                newCnName:this.formNav.cnname,
                newEnName:this.formNav.enname,
                newIcon:this.formNav.icon,
              }).then(successResponse =>{
              console.log(successResponse.data)
              if (("" + successResponse.data) == 'true') {
                alert("操作成功")
                this.getNavList();
              } else {
                alert("操作失败")
              }
            }).catch(failResponse =>{
            })
            break;
          case "updateNav":
            this.$axios
              .post('/ManageMenu', {
                method:'updateSiderNav',
                newCnName:this.formNav.cnname,
                newEnName:this.formNav.enname,
                newIcon:this.formNav.icon,
                current_nav_icon:this.NavCurrentRow.nav_ICON,
                current_nav_id:this.NavCurrentRow.nav_ID
              }).then(successResponse =>{
              console.log(successResponse.data)
              if (("" + successResponse.data) == 'true') {
                alert("操作成功")
                this.getNavList();
              } else {
                alert("操作失败")
              }
            }).catch(failResponse =>{
            })
            break;
        }
      },

      findNavInfo(prop){
        this.$axios
          .post('/findNavInfoByDicId', {
            dic: prop,
          }).then(successResponse =>{
          console.log(successResponse.data)
          if(successResponse.data.NAV_ICON){
            this.searchOriginalNavInfo.icon = successResponse.data.NAV_ICON
          }else {
            this.searchOriginalNavInfo.icon = 'null'
          }
        }).catch(failResponse =>{
        })

        this.$axios
          .post('/findDicInfoByDicId', {
            dic: prop,
          }).then(successResponse =>{
          console.log(successResponse.data)
          this.searchOriginalNavInfo.cnname = successResponse.data.CNNAME
          this.searchOriginalNavInfo.enname = successResponse.data.ENNAME
        }).catch(failResponse =>{
        })

      },
      handleClose(){},

      //查数据库获得Nav Menu PerNav表的数据
      getNavList(){
        this.$axios({
          method:'post',
          url:'/getNavList',
        }).then(successResponse =>{
          this.navList = successResponse.data
        })
      },
      getMenuList(){
        this.$axios({
          method:'post',
          url:'/getMenuList',
        }).then(successResponse =>{
          this.menuList = successResponse.data
        })
      },
      getPerCenterNavList(){
        this.$axios({
          method:'post',
          url:'/getPerCenterNavList',
        }).then(successResponse =>{
          this.perCenterNavList = successResponse.data
        })
      },

      //追踪用户点击的Nav Menu PerNav表的ROW
      handleNavCurrentChange(val){
        this.NavCurrentRow = val;
      },
      handleMenuCurrentChange(val){
        this.MenuCurrentRow = val;
      },
      handlePerCenterNavCurrentChange(val){
        this.PerCenterNavcurrentRow = val;
      },

      InitEcharts(){
        let myChart = this.$echarts.init(document.getElementById('myChart'))
        myChart.setOption({
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['Email Marketing', 'Affiliate Advertising', 'Video Advertising', 'Direct Access', 'Search Engine']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name:  'Mail Marketing',
              type: 'line',
              stack: 'Total amount',
              data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
              name: 'Affiliate Advertising',
              type: 'line',
              stack: 'Total amount',
              data: [220, 182, 191, 234, 290, 330, 310]
            },
            {
              name: 'Video ad',
              type: 'line',
              stack: 'Total amount',
              data: [150, 232, 201, 154, 190, 330, 410]
            },
            {
              name: 'direct interview',
              type: 'line',
              stack: 'Total amount',
              data: [320, 332, 301, 334, 390, 330, 320]
            },
            {
              name: '搜索引擎',
              type: 'line',
              stack: 'search engine',
              data: [820, 932, 901, 934, 1290, 1330, 1320]
            }
          ]
        });
        let myChart2 = this.$echarts.init(document.getElementById('myChart2'))
        myChart2.setOption({
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          legend: {
            orient: 'vertical',
            left: 'left',
            data: ['Direct Access', 'Email Marketing', 'Affiliate Advertising', 'Video Advertising', 'Search Engine']
          },
          series: [
            {
              name: 'Visit source',
              type: 'pie',
              radius: '55%',
              center: ['50%', '60%'],
              data: [
                {value: 335, name: 'direct interview'},
                {value: 310, name: 'Email marketing'},
                {value: 234, name: 'Affiliate ads'},
                {value: 135, name: 'Video ad'},
                {value: 1548, name: 'search engine'}
              ],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        });
        let myChart3 = this.$echarts.init(document.getElementById('myChart3'))
        var data = [{
          name: 'Grandpa',
          children: [{
            name: 'Uncle Leo',
            value: 15,
            children: [{
              name: 'Cousin Jack',
              value: 2
            }, {
              name: 'Cousin Mary',
              value: 5,
              children: [{
                name: 'Jackson',
                value: 2
              }]
            }, {
              name: 'Cousin Ben',
              value: 4
            }]
          }, {
            name: 'Aunt Jane',
            children: [{
              name: 'Cousin Kate',
              value: 4
            }]
          }, {
            name: 'Father',
            value: 10,
            children: [{
              name: 'Me',
              value: 5,
              itemStyle: {
                color: 'red'
              }
            }, {
              name: 'Brother Peter',
              value: 1
            }]
          }]
        }, {
          name: 'Mike',
          children: [{
            name: 'Uncle Dan',
            children: [{
              name: 'Cousin Lucy',
              value: 3
            }, {
              name: 'Cousin Luck',
              value: 4,
              children: [{
                name: 'Nephew',
                value: 2
              }]
            }]
          }]
        }, {
          name: 'Nancy',
          children: [{
            name: 'Uncle Nike',
            children: [{
              name: 'Cousin Betty',
              value: 1
            }, {
              name: 'Cousin Jenny',
              value: 2
            }]
          }]
        }];
        myChart3.setOption({
          visualMap: {
            type: 'continuous',
            min: 0,
            max: 10,
            inRange: {
              color: ['#2D5F73', '#538EA6', '#F2D1B3', '#F2B8A2', '#F28C8C']
            }
          },
          series: {
            type: 'sunburst',
            data: data,
            radius: [0, '90%'],
            label: {
              rotate: 'radial'
            }
          }
        });
      },
    }
  }
</script>

<style scoped>
  .manage{
    display: flex;
    flex-direction: column;
    min-height: 100%;
    background-color: #e8e8e8;
  }
  .section{
    flex:1;
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
  }
  .NavigationList{
    width: 16%;
  }
  .manage-item{
    width: 83%;
  }
  .LogoPage{
    overflow: scroll;
    max-height: 226px;
  }
  .NavigationList{
    width: 100%;
    height: 100%;
  }
  .RightSideIndex{
    height: 100px;
    width: 100px;
  }
  .item {
    margin-top: 10px;
    margin-right: 40px;
  }
</style>
