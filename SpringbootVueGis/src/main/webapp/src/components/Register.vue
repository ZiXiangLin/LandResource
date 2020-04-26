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
      <mu-form ref="form" :model="validateForm" class="mu-form"  >
        <div class="logo-box-span">
          <div class="logo-box">
            <img class="logo" src="../assets/logo.png">
          </div>
          <h2 class="login_title">注册新用户</h2>
        </div>
        <div class="div-register">
          <mu-form-item  prop="username" :rules="usernameRules" icon="account_circle">
              <mu-text-field v-model="validateForm.username" prop="username" placeholder="用户名"></mu-text-field>
            </mu-form-item>
          <mu-form-item  prop="password" :rules="passwordRules" icon="locked">
              <mu-text-field type="password" v-model="validateForm.password" prop="password" placeholder="密码"></mu-text-field>
            </mu-form-item>
          <mu-form-item  prop="pswok" :rules="pswokRules" icon="locked">
              <mu-text-field type="pswok" v-model="validateForm.pswok" prop="pswok" placeholder="重复密码"></mu-text-field>
            </mu-form-item>
          <mu-form-item  prop="name" :rules="nameRules" icon="perm_identity">
            <mu-text-field type="name" v-model="validateForm.name" prop="name" placeholder="姓名"></mu-text-field>
          </mu-form-item>
          <mu-form-item  prop="homeAddress" :rules="homeAddressRules" icon="account_balance">
            <mu-text-field type="homeAddress" v-model="validateForm.homeAddress" prop="homeAddress" placeholder="地址"></mu-text-field>
          </mu-form-item>
          <mu-form-item  prop="phone" :rules="phoneRules" icon="settings_cell">
            <mu-text-field type="phone" v-model="validateForm.phone" prop="phone" placeholder="手机号"></mu-text-field>
          </mu-form-item>
          <mu-form-item  prop="email" :rules="emailRules" icon="tab">
            <mu-text-field type="email" v-model="validateForm.email" prop="email" placeholder="邮箱"></mu-text-field>
          </mu-form-item>
          <mu-form-item  prop="validate" :rules="validateRules" icon="feedback">
            <mu-col span="8">
              <mu-text-field type="validate" v-model="validateForm.validate" prop="validate" placeholder="邮箱验证码"></mu-text-field>
            </mu-col>
            <mu-col span="3">
              <mu-button @click="createCode" >发送</mu-button>
            </mu-col>
          </mu-form-item>
          <mu-form-item>
            <mu-button class="reg-button" color="primary" @click="DoRegister">注册</mu-button>
          </mu-form-item>
        </div>
      </mu-form>
    </mu-container>
  </div>
  <div class="footer">
  </div>
  </body>
</template>

<script>
    // platform\MySystem\WebContent\page\admin\register.jsp
    export default {
        name: "Register",
      data () {
        return {
          alert: false,
          usernameRules: [
            { validate: (val) => !!val, message: '必须填写用户名'},
            { validate: (val) => val.length >= 1, message: '用户名长度大于1'}
          ],
          passwordRules: [
            { validate: (val) => !!val, message: '必须填写密码'},
            { validate: (val) => val.length >= 6 && val.length <= 20, message: '密码长度6-20字符'}
          ],
          pswokRules:[
            { validate:(val) => !!val, message: '必须填写密码'},
            { validate:(val) => val==this.validateForm.password, message: '重复输入密码不一致'}
          ],
          nameRules:[
            { validate:(val) => !!val, message: '必须填写姓名'},
            { validate: (val) => val.length >= 2 && val.length <= 20, message: '姓名长度2-20字符'}
          ],
          homeAddressRules:[
            { validate:(val) => !!val, message: '必须填写地址'},
          ],
          phoneRules:[
            { validate:(val) => !!val, message: '必须填写电话号码'},
            { validate:(val) => !!/^1[34578]\d{9}$/.test(val), message: '电话号码格式错误' },
          ],
          emailRules:[
            { validate:(val) => !!val, message: '必须填写邮箱'},
            { validate:(val) => !!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/.test(val), message: '邮箱格式错误' },],
          validateRules:[
            { validate:(val) => !!val, message: '必须填写激活码'},
          ],
          validateForm: {
            username: '',
            password: '',
            pswok:'',
            name:'',
            homeAddress:'',
            phone:'',
            email:'',
            validate:'',
            methods:'',
          },
          responseResult: []
        }
      },
      methods: {
        createCode(){
          // unfinished 邮箱验证码功能 2020/03/03
          var reg=/^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
          //var url="/nptOfficialWebsite/apply/sendSms?mobile="+this.ruleForm.phone;
          if(this.validateForm.email==''){
            alert("请输入邮箱");
          }else if(!reg.test(this.validateForm.email)){
            alert("邮箱格式不正确");
          }else{
            this.time=60;
            this.disabled=true;
            this.timer();
            /*axios.post(url).then(
                res=>{
                this.phonedata=res.data;
            })*/
          }
        },
        DoRegister(){
          this.$router.replace({path:'/register'})
          var _this = this
          console.log(this.$store.state)
          //检查用户名是否重复
          this.$axios
            .post('/register', {
              username: this.validateForm.username,
              password: this.validateForm.password,
              name: this.validateForm.name,
              homeAddress: this.validateForm.homeAddress,
              phone: this.validateForm.phone,
              email: this.validateForm.email,
              methods:"checkUserName"
            })
            .then(successResponse => {
              if (successResponse.data.code === 200) {
                // 检查邮箱是否重复
                this.$axios
                  .post('/register', {
                    username: this.validateForm.username,
                    password: this.validateForm.password,
                    name: this.validateForm.name,
                    homeAddress: this.validateForm.homeAddress,
                    phone: this.validateForm.phone,
                    email: this.validateForm.email,
                    methods:"checkEmail"
                  })
                  .then(successResponse => {
                    if (successResponse.data.code === 200) {
                      // 用户名邮箱未重复，注册
                      this.$axios
                        .post('/register', {
                          username: this.validateForm.username,
                          password: this.validateForm.password,
                          name: this.validateForm.name,
                          homeAddress: this.validateForm.homeAddress,
                          phone: this.validateForm.phone,
                          email: this.validateForm.email,
                          methods:"other"
                        })
                        .then(successResponse => {
                          if (successResponse.data.code === 200) {
                            _this.$store.commit('login', _this.validateForm)
                            var path = this.$route.query.redirect
                            alert("注册成功");
                            // original: MySystem/home4_cn.jsp
                            this.$router.replace({path: path === '/' || path === undefined ? '/index' : path})
                          }else if (successResponse.data.code === 400){
                            alert("注册失败");
                          }
                        })
                        .catch(failResponse => {
                        })
                     }else if (successResponse.data.code === 400){
                      alert("检测邮箱重复，注册失败");
                    }
                  })
                  .catch(failResponse => {
                  })
              }else if (successResponse.data.code === 400){
                alert("检测用户名重复，注册失败");
              }
            })
            .catch(failResponse => {
            })
        },
      }
    }
</script>

<style scoped>
  .mu-form {
    width: 100%;
  }
  .mu-con-login{
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 60%;
    min-width: 500px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
  #poster {
    background:url("../assets/nasa.jpg") no-repeat;
    background-color: #eaeaf2;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
    overflow: scroll;
  }
  body{
    margin: 0px;
    display: flex;
    flex-direction: column;
    min-height: 100%;
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
  .div-register{
    width: 50%;
    margin: 0 auto;
  }
  .logo-box{
    width: 150px;
    height: 90px;
    margin: 0 auto;
  }
  .logo{
    max-width: 100%;
  }
  .login_title{
    color: #6c6e6d;
  }
  .reg-button{
    width: 100%;
    margin: 0 auto;
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
