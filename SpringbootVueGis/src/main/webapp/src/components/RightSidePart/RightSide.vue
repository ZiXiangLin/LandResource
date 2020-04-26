<template>
    <div class="RightSide">
      <vue-tabs></vue-tabs>
    </div>
</template>

<script>

  import RightSideIndex from "./RightSideIndex";
  import RightSideMapScan from "./RightSideMapScan";
  import RightSideMap from "./RightSideMap";
  import messageBus from "../../bus/messageBus";
    export default {
      name: "RightSide",
      components:{
        RightSideMap,
        RightSideMapScan,
        RightSideIndex
      },
      data(){
        return{
        };
      },
      mounted() {
        this.$taber.open({
          name:'RightSideMap'
        })
        this.$taber.open({
          name:'RightSideMapScan'
        })
        this.$taber.open({
          name:'RightSideSearch'
        })
        this.$taber.open({
          name:'RightSideIndex'
        })


      },
      created() {

      },
      methods:{

      }


    }
</script>

<style scoped>
  .RightSide{
    background-color: #f9f9f9;
  }
</style>
