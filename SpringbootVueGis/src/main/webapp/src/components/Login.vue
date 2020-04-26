<template>
  <body id="poster">
  <div class="header">
    <el-row class="header-el-row">
      <el-col :span="4" class="header-el-row-col" style="text-align: center;">
        <div class="header-item-logo-box" style="text-align: center;">
          <img class="header-item-logo" src="../assets/header.png">
        </div>
      </el-col>
    </el-row>
  </div>
  <div class="section">
    <mu-container align-content="center" class="mu-con-login">
      <mu-form ref="form" :model="validateForm" class="mu-demo-form"  >
        <div class="logo-box-span">
          <div class="logo-box">
            <img class="logo" src="../assets/logo.png">
          </div>
          <h2 class="login_title">系统登录</h2>
        </div>
        <mu-form-item  prop="username" :rules="usernameRules" icon="account_circle">
          <mu-text-field v-model="validateForm.username" prop="username" placeholder="用户名"></mu-text-field>
        </mu-form-item>
        <mu-form-item prop="password" :rules="passwordRules" icon="locked">
          <mu-text-field type="password" v-model="validateForm.password" prop="password" placeholder="密码"></mu-text-field>
        </mu-form-item>
        <mu-form-item class="btn-box">
          <mu-button class="btn-login" color="primary" @click="login">提交</mu-button>
          <mu-button class="btn-reg" @click="register">注册</mu-button>
        </mu-form-item>
      </mu-form>
    </mu-container>
  </div>
  <div class="footer">
  </div>
  </body>
</template>

<script>

export default {
  name: 'Login',
  data () {
    return {
      alert: false,
      usernameRules: [
        { validate: (val) => !!val, message: '必须填写用户名'},
        { validate: (val) => val.length >= 1, message: '用户名长度大于1'}
      ],
      passwordRules: [
        { validate: (val) => !!val, message: '必须填写密码'},
        { validate: (val) => val.length >= 3 && val.length <= 10, message: '密码长度大于3小于10'}
      ],
      argeeRules: [{ validate: (val) => !!val, message: '必须同意用户协议'}],
      validateForm: {
        username: '',
        password: '',
        isAgree: false
      },
      responseResult: []
    }
  },
  methods: {
    login () {
      var _this = this
      //console.log(this.$store.state)
      this.$axios.post('/login', {
          username: this.validateForm.username,
          password: this.validateForm.password
        }).then(successResponse => {
          if (successResponse.data.code === 200) {
            _this.$store.commit('login', _this.validateForm)
            var path = this.$route.query.redirect
            this.$router.replace({path: path === '/' || path === undefined ? '/index' : path})
          }else if (successResponse.data.code === 400){
            alert("登录失败");
          }
        }).catch(failResponse => {})
    },
    register(){
      this.$router.replace({path:'/register'})
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>
<style>
  #poster {
    background:url("../assets/nasa.jpg") no-repeat;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
    display: flex;
    flex-direction: column;
    min-height: 100%;
  }
  body{
    margin: 0px;
  }
  .header{
    flex: 0;
    height: 70px;
    background:url("../assets/topbg.png") repeat-x;
  }
  .section{
    flex:1;
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    margin-top: 5px;
  }
  .footer{
    flex:0;
    background-color: rgb(21,101,192);
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

  .logo-box{
    width: 150px;
    height: 90px;
    margin: 0 auto;
  }
  .logo{
    max-width: 100%;
  }
  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
  .mu-demo-form {
    width: 50%;
    margin: 0 auto;
  }
  .mu-con-login{
    max-width: 600px;
    max-height: 500px;
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    margin-top: 120px;
    width: 60%;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }


  .btn-reg{
    width: 100%;
    margin: 0 auto;
  }
  .btn-login{
    width: 100%;
    margin: 0 auto;
  }
  .btn-box{
    margin-top: 50px;
  }


</style>
