<template>
  <div class="streamExtraction">
    <div class="title">
      <span>Model&nbsp;&nbsp;&nbsp;Name:</span>
      <span>Raster_StreamExtraction</span>
      <div class="line"></div>
    </div>
    <div class="head">
      <span>Parameter&nbsp;&nbsp;Name:</span>
      <span>Data&nbsp;&nbsp;Type</span>
      <span>Value</span>
      <div class="line"></div>
    </div>

    <div class="content">
      <ul>
        <li><span>inputDEM</span><span>WCS_Url</span><input type="text" id="inputDEM"><button data-type="data">···</button></li>
        <li><span>threshold</span><span>int</span><input type="text" id="int"></li>
        <li><span>fill</span><span>WPS_Url</span><input type="text" id="fill"><button data-type="tool">···</button></li>
        <li><span>flowdirection</span><span>WPS_Url</span><input type="text" id="flowdirection"><button data-type="tool">···</button></li>
        <li><span>flowaccumulation</span><span>WPS_Url</span><input type="text" id="flowaccumulation"><button data-type="tool">···</button></li>
        <li><span>flowthreshold</span><span>WPS_Url</span><input type="text" id="flowthreshold"><button data-type="tool">···</button></li>
        <li><span>rester_to_vector</span><span>WPS_Url</span><input type="text" id="rester_to_vector"><button data-type="tool">···</button></li>
      </ul>
    </div>
    <div class="tail">
      <button id="execute">Execute</button>
      <button id="cancel" @click="close">Cancel</button>
    </div>
  </div>

</template>

<script>
    export default {
        name: "streamExtraction",
        methods:{
          close(){
            console.log("closeStreamExtraction");
            this.$emit('closeStreamExtraction','close StreamExtraction')
          }
        }
    }
</script>

<style scoped>

</style>
