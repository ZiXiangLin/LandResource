<template>
  <div class="RightSideMapScan">
    <div id="viewDivMapScan"></div>
  </div>

</template>

<script>
  import { loadModules } from 'esri-loader';
  import messageBus from "../../bus/messageBus";

  export default {
    name: 'web-map',
    data(){
      return{

      }
    },
    computed:{
      mapUrl:{
        get(){
          return this.$store.state.mapUrl
        },
        set(val){
          this.$store.commit('changeMapUrl',val)
        }
      }
    },
    methods:{
      InitNullMap(){
        loadModules([
          'esri/Map',
          'esri/views/MapView',
          'esri/layers/ImageryLayer',
          'esri/layers/FeatureLayer',
          'esri/layers/MapImageLayer',
          'esri/widgets/Compass',
          'esri/widgets/DistanceMeasurement2D',
          'esri/widgets/AreaMeasurement2D'
        ], { css: true })
          .then(([ArcGISMap, MapView,ImageryLayer,FeatureLayer,MapImageLayer,Compass,DistanceMeasurement2D,AreaMeasurement2D]) => {
              // 无Url数据情况下 默认MapScan
              const map = new ArcGISMap({
                basemap: 'topo-vector'
              });
              this.view = new MapView({
                container: "viewDivMapScan",
                map: map,
                zoom: 16,
                center: [114.35347491666666, 30.532201055555554],//未解决地图自动缩放到所添加的图层中
                popup: {
                  dockEnabled: true,
                  dockOptions: {
                    buttonEnabled: false,
                    breakpoint: false
                  }
                }
              });
          }).catch(err =>{
          console.log(err)
        });
      },
      InitMap(){
        loadModules([
          'esri/Map',
          'esri/views/MapView',
          'esri/layers/ImageryLayer',
          'esri/layers/FeatureLayer',
          'esri/layers/MapImageLayer',
          'esri/widgets/Compass',
          'esri/widgets/DistanceMeasurement2D',
          'esri/widgets/AreaMeasurement2D'
        ], { css: true })
          .then(([ArcGISMap, MapView,ImageryLayer,FeatureLayer,MapImageLayer,Compass,DistanceMeasurement2D,AreaMeasurement2D]) => {

              var layer = new ImageryLayer({
                url:this.mapUrl,
                format:"jpgpng"
              });
              var map = new ArcGISMap({
                basemap:'gray',
                layers:[layer]
              });
              this.view = new MapView({
                container:"viewDivMapScan",
                map:map,
                zoom: 5,
                center: [114.35347491666666, 30.532201055555554],
              });
          }).catch(err =>{
          console.log(err)
        });
      },
      listenMapUrlResult(event, callback){
        let self = this;
        messageBus.$on('showMapUrlResult',(prop)=>{
          console.log("process result:"+prop);
          //将处理得到的结果上传store
          this.$store.commit("changeMapUrl",prop);
          //切换MapScan视图
          this.InitMap()
        })
      },
    },
    mounted() {
      if(this.mapUrl == ""){
        console.log("init null map")
        this.InitNullMap();
      }else {
        console.log("init map")
        this.InitMap();
      }
      this.listenMapUrlResult();
    },
    beforeDestroy() {
      if (this.view) {
        // destroy the map view
        this.view.container = null;
      }
    },
  };

</script>

<style scoped>
  .RightSideMapScan{
    height: 800px;
  }
  #viewDivMapScan{
    padding: 0;
    margin: 0;
    height: 100%;
  }
</style>
