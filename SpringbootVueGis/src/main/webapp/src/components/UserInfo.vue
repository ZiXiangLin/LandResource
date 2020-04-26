<template>
  <div class="userInfo">
    <div class="header">
      <Header></Header>
    </div>
    <div class="up-part">
      <div class="card-part-item" style="height: 270px;margin-top: 10px;margin-left: 30px;margin-right: 30px">
        <div class="poster-part-item" style="height: 120px"></div>
        <div class="user-info-part-item" style="background-color: #f9f9f9;height: 150px;padding: 1px">
          <div class="user-avatar-part-item" style="margin-left: 30px;height: 80px;width: 160px;margin-top: -40px">
            <div class="user-avatar" style="height: 150px;width: 160px;">
              <img class="logo" style="max-width: 100%;" src="../assets/avatar.jpg">
            </div>
          </div>
          <div class="user-introduction-part-item" style="margin-left: 220px;margin-top: -30px">
            <h2>{{this.$store.state.user.username}}</h2>
            <span>添加自我介绍 ...</span>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <el-row>
        <el-col :span="8">
          <div class="left-part">
            <el-row style="min-width: 300px">
              <el-col :span="24">
                <el-menu
                  default-active="2"
                  class="el-menu-vertical-demo"
                  @open="handleOpen"
                  @close="handleClose">
                  <el-menu-item index="1" @click="OnNavChange(1)">
                    <i class="el-icon-menu"></i>
                    <span slot="title">个人信息</span>
                  </el-menu-item>
                  <el-menu-item index="2" @click="OnNavChange(2)">
                    <i class="el-icon-menu"></i>
                    <span slot="title">修改头像</span>
                  </el-menu-item>
                  <el-menu-item index="3" @click="OnNavChange(3)">
                    <i class="el-icon-menu"></i>
                    <span slot="title">数据和个性化</span>
                  </el-menu-item>
                  <el-menu-item index="4" @click="OnNavChange(4)">
                    <i class="el-icon-menu"></i>
                    <span slot="title">安全性</span>
                  </el-menu-item>
                  <el-menu-item index="5" @click="OnNavChange(5)">
                    <i class="el-icon-menu"></i>
                    <span slot="title">用户与分享</span>
                  </el-menu-item>
                  <el-menu-item index="6" @click="OnNavChange(6)">
                    <i class="el-icon-menu"></i>
                    <span slot="title">收藏与订阅</span>
                  </el-menu-item>
                </el-menu>
              </el-col>
            </el-row>
            <el-row class="left-card-part" style="margin-top: 2px;min-width: 300px">
              <el-col :span="24" >
                <el-card :body-style="{ padding: '0px' }">
                  <img src="../assets/wuhan.jpg" class="wuhan-image">
                  <div style="padding: 14px;">
                    <span>Contact Us</span>
                    <div class="bottom clearfix">
                      <el-button type="text" class="button">Donate</el-button>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-col>
        <el-col :span="15" style="margin-bottom: 10px">
          <div class="right-section" style="width: 100%;">
            <div class="changeUserInfo" v-if="active === 1">
                <el-card class="box-card">
                  <mu-form ref="form" :model="validateForm" class="mu-demo-form">
                    <el-link icon="el-icon-edit" @click="submit">编辑</el-link>
                    <div>
                      <h3 class="login_title">个人资料</h3>
                    </div>
                    <mu-form-item label="用户名"  prop="username" :rules="usernameRules">
                      <mu-text-field v-model="validateForm.username" prop="username"></mu-text-field>
                    </mu-form-item>
                    <mu-form-item label="姓名" prop="name" :rules="nameRules">
                      <mu-text-field type="name" v-model="validateForm.name" prop="name"></mu-text-field>
                    </mu-form-item>

                  </mu-form>
                </el-card>
                <el-card class="box-card">
                  <mu-form ref="form" :model="validateForm" class="mu-demo-form">
                    <h3 class="login_title">联系信息</h3>
                    <mu-form-item label="邮箱" prop="email" :rules="emailRules">
                      <mu-text-field type="email" v-model="validateForm.email" prop="email"></mu-text-field>
                    </mu-form-item>
                    <mu-form-item label="电话" prop="phone" :rules="phoneRules">
                      <mu-text-field type="phone" v-model="validateForm.phone" prop="phone"></mu-text-field>
                    </mu-form-item>
                    <mu-form-item label="地址" prop="address" :rules="addressRules">
                      <mu-text-field type="address" v-model="validateForm.address" prop="address"></mu-text-field>
                    </mu-form-item>
                    <!--            <mu-form-item>-->
                    <!--              <mu-button color="primary" @click="submit">提交</mu-button>-->
                    <!--              <mu-button @click="clear">重置</mu-button>-->
                    <!--            </mu-form-item>-->
                  </mu-form>
                </el-card>
              </div>
            <div class="changeUserAvatar" v-if="active === 2">
              <el-row style="background-color: #f9f9f9;height: 100px">
                <h3 style="margin-left: 28px">修改头像</h3>
                <h4 style="margin-left: 28px">图片大小限制50kb</h4>
              </el-row>
              <el-row style="background-color: #e8e8e8;">
                  <el-col :span="12">
                    <div class="item_bock" style="margin-left: 20px;height: 300px;">
                      <div class="avatar-item-left-part">
                        <img :src="userInfo.avatar"/>
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="12">
                    <div class="item_bock" style="margin-left: 20px;height: 300px;">
                      <input type="file" name="image" accept="image/*" @click="handleFile"
                             class="avatar-upload-btn  avatar-item-right-part">
                      <img  alt="" :src='imgStr' class=" avatar-item-right-part">
                    </div>
                  </el-col>
                </el-row>
              <el-row style="background-color: #f9f9f9;height: 200px"></el-row>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="footer">
      <Footer></Footer>
    </div>
  </div>
</template>

<script>

  import Header from "./PageParts/Header";
  import Footer from "./PageParts/Footer";
    export default {
      name: "UserInfo",
      components:{
        Header,
        Footer
      },
      data () {
        return {
          imgStr: require('../assets/upload.png'),
          errorStr: '',
          userInfo: {
            avatar: require('../assets/avatar.jpg')
          },
          active:1,
          usernameRules: [
            { validate: (val) => !!val, message: '必须填写用户名'},
            { validate: (val) => val.length >= 3, message: '用户名长度大于3'}
          ],
          emailRules: [
            { validate: (val) => !!val, message: '必须填写邮箱'},
            { validate: (val) => val.length >= 3 && val.length <= 10, message: '密码长度大于3小于10'}
          ],
          nameRules: [
            { validate: (val) => !!val, message: '必须填写姓名'},
            { validate: (val) => val.length >= 3 && val.length <= 10, message: '密码长度大于3小于10'}
          ],
          phoneRules: [
            { validate: (val) => !!val, message: '必须填写电话'},
            { validate: (val) => val.length >= 3 && val.length <= 10, message: '密码长度大于3小于10'}
          ],
          addressRules: [
            { validate: (val) => !!val, message: '必须填写地址'},
            { validate: (val) => val.length >= 3 && val.length <= 10, message: '密码长度大于3小于10'}
          ],
          validateForm: {
            username: '',
            email:'',
            name:'',
            phone:'',
            address:''
          }
        }
      },
      methods: {
        submit () {
          this.$axios
            .post('/userInfoUpdate',{
            username: this.validateForm.username,
            phone: this.validateForm.phone,
            address: this.validateForm.address,
            email: this.validateForm.email,
            name: this.validateForm.name
            })
            .then(successResponse =>{
              if(successResponse.data.code === 200){
                alert("用户信息修改成功")
                //this.$router.replace({path:'/index'})
              }else if(successResponse.data.code === 400){
              }
            })
            .catch(failResponse =>{
            })
        },
        clear () {
          this.$refs.form.clear();
          this.validateForm = {
            email:'',
            name:'',
            phone:'',
            address:''
          };
        },
        handleOpen(key, keyPath) {
        },
        handleClose(key, keyPath) {
        },
        OnNavChange(e){
          this.active = e
        },
        // 打开图片上传
        uploadHeadImg: function () {
          this.$el.querySelector('.hiddenInput').click()
        },
        // 将头像显示
        handleFile: function (e) {
          let $target = e.target || e.srcElement
          let file = $target.files[0]
          var reader = new FileReader()
          reader.onload = (data) => {
            let res = data.target || data.srcElement
            this.userInfo.avatar = res.result
          }
          reader.readAsDataURL(file)
        },
      },
      mounted() {
          this.validateForm.username = this.$store.state.user.username;
          this.$axios
            .post('/userInfo',{
              username: this.validateForm.username
            })
            .then(successReponse =>{
              this.validateForm.email = successReponse.data.email;
              this.validateForm.phone = successReponse.data.phone;
              this.validateForm.address = successReponse.data.homeaddress;
              this.validateForm.name = successReponse.data.name;
            })
            .catch(failResponse =>{
            })
      }
    }
</script>

<style scoped>
  .userInfo{
    display: flex;
    flex-direction: column;
    min-height: 100%;
    background-color: #eaeaf2;
  }
  .header{
    height: 70px;
  }
  .section{
    flex:1;
    justify-content: space-evenly;
    margin-top: 5px;
  }
  .footer{
    height: 22px;
    margin-bottom: 0px;
  }
  .left-part{
    width: 20%;
    height: 500px;
    margin-left: 30px;
    margin-right: 30px;
  }
  .box-card{
    margin-right: 30px;
    margin-top: 3px;
  }
  .wuhan-image{
    max-width: 100%;
  }
  .mu-demo-form {
    width: 100%;
    max-width: 460px;
    margin: 0 auto;
  }
  .poster-part-item{
    background:url("../assets/nasa.jpg") no-repeat;
  }
  .item_bock {
    display: flex;
    text-align: center;
    align-items: center;
    justify-content: space-between;
    width: 300px;
    padding:0px 24px 0px 38px;
    border-bottom: 1px solid #f7f7f7;
    position: relative;
  }
  .avatar-item-left-part img{
    width:250px;
    height:250px;
    border-radius:50px
  }
  .avatar-item-right-part{
    width: 250px;
    height: 250px;
    border-radius:50px;
    display: inline-block;
  }
  .avatar-upload-btn{
    /* 通过定位把input放在img标签上面，通过不透明度隐藏 */
    position: absolute;
    left: 0;
    top: 0;
    opacity: 0;
  }

</style>
