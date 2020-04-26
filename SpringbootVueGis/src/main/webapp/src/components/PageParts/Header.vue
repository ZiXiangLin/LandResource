<template>
    <div class="header">
      <el-row class="header-el-row">
        <el-col :span="4" class="header-el-row-col" style="text-align: center;">
          <div class="header-item-logo-box" @click="OpenIndex()" style="text-align: center;">
            <img class="header-item-logo" src="../../assets/header.png">
          </div>
        </el-col>
        <el-col :span="8" class="header-el-row-col">
          <el-row style="height: 70px">
            <el-col :span="4" @click.native="changeMenu('5')" style="height: 70px; border-left: 1px solid #6c6e6d; border-right: 1px solid #6c6e6d; font-size: 33px; text-align: center; color: #f9f9f9">
              <i class="el-icon-menu"></i>
              <p style="font-size: 12px; margin-top: 0px; text-align: center; margin-bottom: 5px;">{{ $t('navbar.DataService') }}</p>
            </el-col>
            <el-col :span="4" @click.native="changeMenu('6')" style="height: 70px; border-right: 1px solid #6c6e6d; font-size: 33px; text-align: center; color: #f9f9f9">
              <i class="el-icon-s-custom"></i>
              <p style="font-size: 12px; margin-top: 0px; text-align: center; margin-bottom: 5px;">{{ $t('navbar.ProcessingService') }}</p>
            </el-col>
            <el-col :span="4" @click.native="changeMenu('7')" style="height: 70px; border-right: 1px solid #6c6e6d; font-size: 33px; text-align: center; color: #f9f9f9">
              <i class="el-icon-document"></i>
              <p style="font-size: 12px; margin-top: 0px; text-align: center; margin-bottom: 5px;">{{ $t('navbar.ModelService') }}</p>
            </el-col>
            <el-col :span="4" @click.native="changeMenu('8')" style="height: 70px; border-right: 1px solid #6c6e6d; font-size: 33px; text-align: center; color: #f9f9f9">
              <i class="el-icon-s-opportunity"></i>
              <p style="font-size: 12px; margin-top: 0px; text-align: center; margin-bottom: 5px;">{{ $t('navbar.KnowledgeService') }}</p>
            </el-col>
            <el-col :span="4" @click.native="changeMenu('9')" style="height: 70px; border-right: 1px solid #6c6e6d; font-size: 33px; text-align: center; color: #f9f9f9">
              <i class="el-icon-wallet"></i>
              <p style="font-size: 12px; margin-top: 0px; text-align: center; margin-bottom: 5px;">{{ $t('navbar.ValidityService') }}</p>
            </el-col>
            <el-col :span="4" style="height: 70px"></el-col>
          </el-row>
        </el-col>
        <el-col :span="7" class="header-el-row-col"></el-col>
        <el-col :span="1">
          <span class="lang" @click="changeLang" style="color:white;float: right">
            <p style="float: left;margin-top: 25px;margin-right: 5px" :class="isActive?'active':''">zh</p>
            <p style="float: right;margin-top: 25px" :class="!isActive?'active':''">en</p>
          </span>
        </el-col>
        <el-col :span="2" class="header-el-row-col">

          <div  style="text-align: center; margin-top: 15px;float: right">
            <img  src="../../assets/user.png">
          </div>
        </el-col>
        <el-col :span="1" class="header-el-row-col" >
          <el-dropdown>
            <span class="el-dropdown-link" style="font-size: 20px;color: #f9f9f9; margin-left: 10px">
              <i class="el-icon-arrow-down el-icon--right" style="margin-top: 25px"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="OpenUserInfo()" >
                <i class="el-icon-user" ></i>
                {{ $t('user.userInfo') }}
              </el-dropdown-item>
              <el-dropdown-item visible.sync="manageVisible" @click.native="OpenManage()">
                <i class="el-icon-switch-button"></i>
                {{ $t('user.manage') }}
              </el-dropdown-item>
              <el-dropdown-item @click.native="Quit()">
                <i class="el-icon-switch-button"></i>
                {{ $t('user.logOut') }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>
      </el-row>
    </div>
</template>

<script>

  import messageBus from "../../bus/messageBus";

    export default {
      name: "Header",
      data(){
        return{
          isActive:true,
          validateForm: {
            username: '',
            password: '',
            isAgree: false
          },
          manageVisible:false,
        }
      },
      created() {
        let lang = document.documentElement.lang;
        this.toActive(lang);
      },
      mounted() {
        this.WhetherAbleManage();
      },
      methods:{
        WhetherAbleManage(){
          if(this.$store.state.user.username === "dc"){
            this.manageVisible = true
          }
        },
        OpenIndex(){
          this.$router.replace({path:'/index'})
        },
        OpenUserInfo(){
          this.$router.replace({path:'/userInfo'})
        },
        OpenManage(){
          this.$router.replace({path:'/manage'})
        },
        Quit(){
          this.$store.commit('login', this.validateForm)
          this.$router.replace({path:'/'})
        },
        changeLang(e) {
          let lang = e.target.innerText;
          this.$i18n.locale = lang;
          this.toActive(lang);
          messageBus.$emit('ChangeLang',lang)
        },
        toActive(lang) {
          this.isActive = lang == "zh" ? true : false;
        },
        changeMenu(e){
          messageBus.$emit('ChangeMenu',e)
        }
      }
    }
</script>

<style scoped>
  .header{
    height: 70px;
    background:url("../../assets/topbg.png") repeat-x;
  }
  .header-el-row{
    height: 70px;
  }
  .header-el-row-col{
    height: 70px;
  }
  .header-item-logo-box{
    width: 180px;
    height: 70px;
    margin-top: 10px;
    margin-left: 35px;
  }
  .header-item-logo{
    max-width: 100%;
  }
</style>
