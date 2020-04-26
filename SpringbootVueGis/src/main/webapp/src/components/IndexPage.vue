<template>
  <div class="index">
    <div class="header">
      <Header></Header>
    </div>
    <div class="section">
      <NavigationList></NavigationList>
      <RightSide></RightSide>
    </div>
    <div class="footer">
      <Footer></Footer>
    </div>
  </div>
</template>

<script>

  import Header from "./PageParts/Header";
  import Footer from "./PageParts/Footer";
  import NavigationList from "./NavigationList";
  import RightSide from "./RightSidePart/RightSide";

    export default {
      name: "index",
      data(){
        return{
          active:0,
        }
      },
      components:{
        NavigationList,
        RightSide,
        Header,
        Footer
      },
      methods:{
        userinfo(){
          this.$router.replace({path:'/userinfo'});
        }
      }
    }
</script>

<style scoped>
  .index{
    display: flex;
    flex-direction: column;
    min-height: 100%;
    background-color: #e8e8e8;
  }
  .header{
    height: 70px;
  }
  .section{
    flex:1;
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
  }
  .footer{
    height: 22px;
    margin-bottom: 0px;
  }
  .NavigationList{
    width: 17%;
    margin-bottom: 10px;
  }
  .RightSide{
    width: 83%;
    margin-bottom: 5px;
  }

</style>
