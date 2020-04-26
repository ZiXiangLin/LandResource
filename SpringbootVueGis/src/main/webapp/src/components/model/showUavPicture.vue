<template>
    <div class="showUavPicture">
      <div id="viewDivShowUavPicture"></div>
      <div class="tail">
        <el-button id="cancel" @click="Fresh" style="margin-top: 5px">刷新</el-button>
      </div>
    </div>
</template>

<script>
  import { loadModules } from 'esri-loader';

    export default {
      name: "showUavPicture",
      data(){
          return{
            uavInfo:'',
          };
      },
      mounted() {
          // 查询uav表获取UAV数据
          this.getUavInfo();
          this.loadMap();
      },
      methods:{
        getUavInfo(){
            this.$axios.post('/findUavList')
              .then(successResponse =>{
                this.uavInfo = successResponse.data
                // Vue.$set(this.data,'uavInfo',successResponse.data)
              })
              .catch(failResponse =>{
              })
        },
        loadMap(){
            loadModules([
              'esri/Map',
              'esri/views/MapView',
              'esri/layers/ImageryLayer',
              'esri/Graphic',
              'esri/geometry/Point',
              'esri/layers/GraphicsLayer',
              'esri/symbols/PictureMarkerSymbol',
              'esri/PopupTemplate'], { css: true })
              .then(([Map, MapView, ImageryLayer,Graphic,Point,GraphicsLayer,PictureMarkerSymbol,PopupTemplate]) => {

                var map = new Map({
                  basemap: 'topo-vector',
                });

                this.view = new MapView({
                  container:'viewDivShowUavPicture',
                  map:map,
                  zoom:16,
                  center: [114.35347491666666, 30.532201055555554],
                  popup:{
                    dockEnabled:true,
                    dockOptions:{
                      buttonEnabled:false,
                      breakpoint:false
                    }
                  }
                })

                var symbol = new PictureMarkerSymbol({
                  url: require('../../assets/images/point.png'),
                  width:15,
                  height:15
                })


                var graphicsLayer = new GraphicsLayer()

                // 遍历this.uavInfo添加矢量要素点
                for(var i=0;i<this.uavInfo.length;i++){
                  var lon = this.uavInfo[i].gps_Longitude
                  var lat = this.uavInfo[i].gps_Latitude
                  var imageurl = this.uavInfo[i].uavimagefiledir
                  var id = this.uavInfo[i].id
                  var labelPoint = new Point(lon,lat)
                  var pointAtt = {
                    Latitude: lat,
                    Longitude: lon,
                  }
                  var template = new PopupTemplate({
                    title:this.uavInfo[i].uavimagefiledir,
                    content: [
                      {
                        type:"fields",
                        fieldInfos:[{
                          fieldName:"Latitude",
                          label:"Latitude",
                          format: {
                            digitSeparator: true,
                            places: 4
                          }
                        },{
                          fieldName: "Longitude",
                          label: "Longitude",
                          format:{
                            digitSeparator:true,
                            places:4
                          }
                        }]
                      },
                      {
                        type:"text",
                        text:"<p>UAV图像参数信息</p>" +
                          "<ul><li>UAV ID:"+(id)+"</li></ul>",
                      },{
                        type: "media",
                        mediaInfos:[{
                          // title:"<font color='#008000'>无人机影像",
                          type:"image",
                          value:{
                            sourceURL: require('../../assets/images/UAV/'+imageurl)
                          }
                        }]
                      }
                    ]
                  })
                  var graphic = new Graphic({
                    geometry:labelPoint,
                    popupTemplate:template,
                    symbol:symbol,
                    attributes:pointAtt,
                  })
                  graphicsLayer.add(graphic)
                }
                map.add(graphicsLayer)


              }).catch(err =>{
              console.log(err)
            });
          },
        Fresh(){
          this.loadMap();
        }
      },
    }
</script>

<style scoped>
  .showUavPicture{
    height: 100%;
  }
  #viewDivShowUavPicture{
    height: 80%;
    min-height: 750px;
  }
</style>
