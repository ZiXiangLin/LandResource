<template>
  <div class="RightSideMap">
    <div id="viewDivMap"></div>
  </div>

</template>

<script>
  import { loadModules } from 'esri-loader';

  export default {
    name: 'web-map',
    data(){
      return{

      }
    },
    mounted() {
      // lazy load the required ArcGIS API for JavaScript modules and CSS
      loadModules([
        'esri/Map',
        'esri/views/SceneView',
        'esri/layers/FeatureLayer'], { css: true })
        .then(([Map, SceneView,FeatureLayer]) => {
          var map = new Map({
            basemap: "topo-vector",
            ground: "world-elevation"
          });

          var view = new SceneView({
            container: "viewDivMap",
            map: map,
            camera: {
              position: {
                x: -118.808,
                y: 33.961,
                z: 2000 // meters
              },
              tilt: 75
            }
          });

          // Trailheads point feature layer
          var featureLayer = new FeatureLayer({
            url: "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trailheads/FeatureServer/0"
          });

          map.add(featureLayer);

          // Trails feature layer (lines)
          var trailsLayer = new FeatureLayer({
            url: "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trails/FeatureServer/0"
          });

          map.add(trailsLayer, 0);

          // Parks and open spaces (polygons)
          var parksLayer = new FeatureLayer({
            url: "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Parks_and_Open_Space/FeatureServer/0"
          });

          map.add(parksLayer, 0);


        }).catch(err =>{
          console.log(err)
      });
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
  .RightSideMap{
    height: 800px;
  }
  #viewDivMap{
    padding: 0;
    margin: 0;
    height: 100%;
  }
</style>
