<template>
  <div class="test">
    <mu-divider></mu-divider>
    <span>STEP0 TEST BUTTON INTERFACE</span>
    <mu-divider></mu-divider>
    <span>STEP1 TEST WCS URL AND RELATED OPERATION</span>
    <div style="margin-top: 5px">
      <p>Output </p>
      <p>====> wcs url with arcgis server
        WCS url::::http://localhost:6080/arcgis/services/WCS/MODEV1D.20160517.CN.EVI.V2.TIF20200311232622603/ImageServer/WCSServer
        Process (Arcgis Server)WCS getcapabilities: http://localhost:6080/arcgis/services/WCS/MODEV1D.20160517.CN.EVI.V2.TIF20200311232622603/ImageServer/WCSServer?SERVICE=WCS&VERSION=1.1.1&REQUEST=getcapabilities
        Process (Arcgis Server)WCS describecoverage: http://localhost:6080/arcgis/services/WCS/MODEV1D.20160517.CN.EVI.V2.TIF20200311232622603/ImageServer/WCSServer?SERVICE=WCS&VERSION=1.1.1&REQUEST=describecoverage&identifiers=1
        通过 wcs.getCoverage 得到的数据地址::::  http://localhost:6080/arcgis/services/WCS/MODEV1D.20160517.CN.EVI.V2.TIF20200311232622603/ImageServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=1&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBOX=73.396000000000001,3.7996000000000052,134.8032,53.601500000000001&amp;width=10407&amp;height=8440</p>
    </div>
    <mu-divider></mu-divider>
    <span>STEP2 TEST WPS EXECUTE</span>
    <div style="margin-top: 5px">
      <p>Output</p>
      <p>urlgetcabilities::::http://localhost:6080/arcgis/services/MySystem/Slope/GPServer/WPSServer?SERVICE=WPS&VERSIONS=1.0.0&REQUEST=getcapabilities
        document::::org.dom4j.tree.DefaultDocument@614a543b [Document: name http://localhost:6080/arcgis/services/MySystem/Slope/GPServer/WPSServer?SERVICE=WPS&VERSIONS=1.0.0&REQUEST=getcapabilities]
        urlDescribeProcesshttp://localhost:6080/arcgis/services/MySystem/Slope/GPServer/WPSServer?SERVICE=WPS&VERSIONS=1.0.0&REQUEST=DescribeProcess&identifier=Slope
        identifier::::[Slope, in_raster, output_measurement, z_factor, output_spatial_reference, out_raster]
        identifier::::Slope+in_raster+output_measurement
        requestXML:::(wps:DataInputs)(wps:Input)(ows:Identifier)in_raster(/ows:Identifier)(wps:Reference xlink:href="http://localhost:6080/arcgis/services/WCS/MODEV1D.20160517.CN.EVI.V2.TIF20200311232622603/ImageServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=1&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBOX=73.396000000000001,3.7996000000000052,134.8032,53.601500000000001&amp;width=10407&amp;height=8440"/)(/wps:Input)(wps:Input)(ows:Identifier)output_measurement(/ows:Identifier)(wps:Data)(wps:LiteralData)"urn:ogc:def:crs:EPSG::4326"(/wps:LiteralData)(/wps:Data)(/wps:Input)(/wps:DataInputs)(wps:ResponseForm)(wps:ResponseDocument storeExecuteResponse="true" lineage="true" status="true")(wps:Output asReference="true")(ows:Identifier)z_factor(/ows:Identifier)(/wps:Output)(/wps:ResponseDocument)(/wps:ResponseForm>
        sb::::(?xml version="1.0" encoding="utf-8" ?)(wps:Execute xmlns:wps="http://www.opengis.net/wps/1.0.0" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ows="http://www.opengis.net/ows/1.1" service="WPS" version="1.0.0" xsi:schemaLocation="http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_request.xsd">(ows:Identifier)Slope(/ows:Identifier)(wps:DataInputs)(wps:Input)(ows:Identifier)in_raster(/ows:Identifier)(wps:Reference xlink:href="http://localhost:6080/arcgis/services/WCS/MODEV1D.20160517.CN.EVI.V2.TIF20200311232622603/ImageServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=1&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBOX=73.396000000000001,3.7996000000000052,134.8032,53.601500000000001&amp;width=10407&amp;height=8440"/>(/wps:Input)(wps:Input)(ows:Identifier)output_measurement(/ows:Identifier)(wps:Data)(wps:LiteralData)"urn:ogc:def:crs:EPSG::4326"(/wps:LiteralData)(/wps:Data)(/wps:Input)(/wps:DataInputs)(wps:ResponseForm)(wps:ResponseDocument storeExecuteResponse="true" lineage="true" status="true")(wps:Output asReference="true")(ows:Identifier)z_factor(/ows:Identifier)(/wps:Output)(/wps:ResponseDocument)(/wps:ResponseForm)(/wps:Execute>
        res::::(ows:ExceptionReport version='1.1.0' language='en' xmlns:ows='http://www.opengis.net/ows'>(ows:Exception exceptionCode='NoApplicableCode'>(ows:ExceptionText>Wrong Execute request.(/ows:ExceptionText>(/ows:Exception>(/ows:ExceptionReport>
        wps executeceptionReport version='1.1.0' language='en' xmlns:ows='http://www.opengis.net/ows'>(ows:Exception exceptionCode='NoApplicableCode
      </p>
    </div>
    <el-button @click="test">Click ME!</el-button>
  </div>
</template>

<script>

    export default {
        name: "test",
        data(){
          return{
            file:'',
          }
        },
        methods:{
          getFile(){
            this.file = event.target.files[0];
            console.log(this.file);
          },
          importData(){
            event.preventDefault();
            let formData = new FormData();
            formData.append("file",this.file);
            this.$axios.post('/Uploadfile',formData,{timeout:900000})
              .then(function (response) {
                alert(response.data);
                console.log(response);
              })
              .catch((function (error) {
                alert("fail");
                console.log(error);
              }))
          },
          test(){
            this.$axios({
              method:'post',
               url:'/Test2',
            }).then(successResponse =>{
              console.log(successResponse.data)
            })
          },
        },
        mounted() {
        }
    }
</script>

<style scoped>
  .test {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    position: relative;
    padding-left: 200px;
  }
</style>
