<template>
  <div class="RightSideIndex">
    <div class="index-text">
      <div id="container">
        <div id="bd">
          <el-row class="demo-avatar-demo-basic">
            <el-col :span="2" class="icon-wrap">
              <i class="iconfont-sprvue  icon-sprvueA"></i>
              <h6 class="icon-word">{{ $t('index.desktop') }}</h6>
            </el-col>
            <el-col :span="2" class="icon-wrap">
              <i class="iconfont-sprvue  icon-sprvuegonggao"></i>
              <h6 class="icon-word">{{ $t('index.plan') }}</h6>
            </el-col>
            <el-col :span="2" class="icon-wrap">
              <i class="iconfont-sprvue  icon-sprvueProcesslog"></i>
              <h6 class="icon-word">{{ $t('index.index') }}</h6>
            </el-col>
            <el-col :span="2" class="icon-wrap">
              <i class="iconfont-sprvue  icon-sprvueshoucangjia"></i>
              <h6 class="icon-word">{{ $t('index.bookmark') }}</h6>
            </el-col>
            <el-col :span="2" class="icon-wrap">
              <i class="iconfont-sprvue  icon-sprvuetongxunlu"></i>
              <h6 class="icon-word">{{ $t('index.address') }}</h6>
            </el-col>
            <el-col :span="2" class="icon-wrap">
              <i class="iconfont-sprvue  icon-sprvuedaiban"></i>
              <h6 class="icon-word">{{ $t('index.schedule') }}</h6>
            </el-col>
            <el-col :span="2" class="icon-wrap">
              <i class="iconfont-sprvue  icon-sprvueyoujian"></i>
              <h6 class="icon-word">{{ $t('index.new') }}</h6>
            </el-col>
            <el-col :span="2" class="icon-wrap">
              <i class="iconfont-sprvue  icon-sprvueliulanqi"></i>
              <h6 class="icon-word">{{ $t('index.browser') }}</h6>
            </el-col>
          </el-row>
          <el-row class="demo-info">
            <el-col :span="8">
              <el-card shadow="never">
                <div slot="header" class="clearfix">
                  <span>{{ $t('index.system') }}</span>
                  <i class="el-icon-refresh" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-star-off" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-bell" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-switch-button" style="float: right; padding: 3px 0"></i>
                </div>
                <div>
                  <div id="myChart" :style="{width:'100%',height:'300px'}"></div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="never">
                <div slot="header" class="clearfix">
                  <span>{{ $t('index.visit') }}</span>
                  <i class="el-icon-refresh" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-star-off" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-bell" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-switch-button" style="float: right; padding: 3px 0"></i>
                </div>
                <div>
                  <div id="myChart2" :style="{width:'100%',height:'300px'}"></div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="never">
                <div slot="header" class="clearfix">
                  <span>{{ $t('index.date') }}</span>
                  <i class="el-icon-refresh" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-star-off" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-bell" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-switch-button" style="float: right; padding: 3px 0"></i>
                </div>
                <div>
                  <div id="myChart3" :style="{width:'100%',height:'300px'}"></div>
                </div>
              </el-card>
            </el-col>
          </el-row>
          <el-row class="demo-info">
            <el-col :span="8">
              <el-card shadow="never">
                <div slot="header" class="clearfix">
                  <span>{{ $t('index.system') }}</span>
                  <i class="el-icon-refresh" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-star-off" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-bell" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-switch-button" style="float: right; padding: 3px 0"></i>
                </div>
                <div>
                  <tr>{{ $t('index.message.m1') }}</tr>
                  <tr>{{ $t('index.message.m2') }}</tr>
                  <tr>{{ $t('index.message.m3') }}</tr>
                  <tr>{{ $t('index.message.m4') }}</tr>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="never">
                <div slot="header" class="clearfix">
                  <span>{{ $t('index.schedule') }}</span>
                  <i class="el-icon-refresh" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-star-off" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-bell" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-switch-button" style="float: right; padding: 3px 0"></i>
                </div>
                <div>
                  <tr>{{ $t('index.message.m1') }}</tr>
                  <tr>{{ $t('index.message.m2') }}</tr>
                  <tr>{{ $t('index.message.m3') }}</tr>
                  <tr>{{ $t('index.message.m4') }}</tr>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="never">
                <div slot="header" class="clearfix">
                  <span>{{ $t('index.date') }}</span>
                  <i class="el-icon-refresh" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-star-off" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-bell" style="float: right; padding: 3px 0"></i>
                  <i class="el-icon-switch-button" style="float: right; padding: 3px 0"></i>
                </div>
                <div>
                  <tr>{{ $t('index.message.m1') }}</tr>
                  <tr>{{ $t('index.message.m2') }}</tr>
                  <tr>{{ $t('index.message.m3') }}</tr>
                  <tr>{{ $t('index.message.m4') }}</tr>
                </div>
              </el-card>
            </el-col>
          </el-row>
          <el-row class="demo-info">
            <el-col :span="8">
              <el-card shadow="never">
                <div slot="header" class="clearfix">
                  <span>{{ $t('index.system') }}</span>
                  <el-button style="float: right; padding: 3px 0" type="text">{{ $t('index.click') }}</el-button>
                </div>
                <div>
                  <tr>{{ $t('index.message.m1') }}</tr>
                  <tr>{{ $t('index.message.m2') }}</tr>
                  <tr>{{ $t('index.message.m3') }}</tr>
                  <tr>{{ $t('index.message.m4') }}</tr>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="never">
                <div slot="header" class="clearfix">
                  <span>{{ $t('index.new') }}</span>
                  <el-button style="float: right; padding: 3px 0" type="text">{{ $t('index.click') }}</el-button>
                </div>
                <div>
                  <tr>{{ $t('index.message.m1') }}</tr>
                  <tr>{{ $t('index.message.m2') }}</tr>
                  <tr>{{ $t('index.message.m3') }}</tr>
                  <tr>{{ $t('index.message.m4') }}</tr>
                </div>
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="never">
                <div slot="header" class="clearfix">
                  <span>{{ $t('index.date') }}</span>
                  <el-button style="float: right; padding: 3px 0" type="text">{{ $t('index.click') }}</el-button>
                </div>
                <div>
                  <tr>{{ $t('index.message.m1') }}</tr>
                  <tr>{{ $t('index.message.m2') }}</tr>
                  <tr>{{ $t('index.message.m3') }}</tr>
                  <tr>{{ $t('index.message.m4') }}</tr>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

  export default {
    name: "RightSideIndex",
    data(){
      return{
        value: new Date(),
      }
    },
    mounted() {
      this.InitEcharts();
    },
    methods:{
      InitEcharts(){
          let myChart = this.$echarts.init(document.getElementById('myChart'))
          myChart.setOption({
            tooltip: {
              trigger: 'axis'
            },
            legend: {
              data: ['Email Marketing', 'Affiliate Advertising', 'Video Advertising', 'Direct Access', 'Search Engine']
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            toolbox: {
              feature: {
                saveAsImage: {}
              }
            },
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
            },
            yAxis: {
              type: 'value'
            },
            series: [
              {
                name:  'Mail Marketing',
                type: 'line',
                stack: 'Total amount',
                data: [120, 132, 101, 134, 90, 230, 210]
              },
              {
                name: 'Affiliate Advertising',
                type: 'line',
                stack: 'Total amount',
                data: [220, 182, 191, 234, 290, 330, 310]
              },
              {
                name: 'Video ad',
                type: 'line',
                stack: 'Total amount',
                data: [150, 232, 201, 154, 190, 330, 410]
              },
              {
                name: 'direct interview',
                type: 'line',
                stack: 'Total amount',
                data: [320, 332, 301, 334, 390, 330, 320]
              },
              {
                name: '搜索引擎',
                type: 'line',
                stack: 'search engine',
                data: [820, 932, 901, 934, 1290, 1330, 1320]
              }
            ]
          });
          let myChart2 = this.$echarts.init(document.getElementById('myChart2'))
          myChart2.setOption({
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
              orient: 'vertical',
              left: 'left',
              data: ['Direct Access', 'Email Marketing', 'Affiliate Advertising', 'Video Advertising', 'Search Engine']
            },
            series: [
              {
                name: 'Visit source',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                  {value: 335, name: 'direct interview'},
                  {value: 310, name: 'Email marketing'},
                  {value: 234, name: 'Affiliate ads'},
                  {value: 135, name: 'Video ad'},
                  {value: 1548, name: 'search engine'}
                ],
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          });
          let myChart3 = this.$echarts.init(document.getElementById('myChart3'))
          var data = [{
            name: 'Grandpa',
            children: [{
              name: 'Uncle Leo',
              value: 15,
              children: [{
                name: 'Cousin Jack',
                value: 2
              }, {
                name: 'Cousin Mary',
                value: 5,
                children: [{
                  name: 'Jackson',
                  value: 2
                }]
              }, {
                name: 'Cousin Ben',
                value: 4
              }]
            }, {
              name: 'Aunt Jane',
              children: [{
                name: 'Cousin Kate',
                value: 4
              }]
            }, {
              name: 'Father',
              value: 10,
              children: [{
                name: 'Me',
                value: 5,
                itemStyle: {
                  color: 'red'
                }
              }, {
                name: 'Brother Peter',
                value: 1
              }]
            }]
          }, {
            name: 'Mike',
            children: [{
              name: 'Uncle Dan',
              children: [{
                name: 'Cousin Lucy',
                value: 3
              }, {
                name: 'Cousin Luck',
                value: 4,
                children: [{
                  name: 'Nephew',
                  value: 2
                }]
              }]
            }]
          }, {
            name: 'Nancy',
            children: [{
              name: 'Uncle Nike',
              children: [{
                name: 'Cousin Betty',
                value: 1
              }, {
                name: 'Cousin Jenny',
                value: 2
              }]
            }]
          }];
          myChart3.setOption({
            visualMap: {
              type: 'continuous',
              min: 0,
              max: 10,
              inRange: {
                color: ['#2D5F73', '#538EA6', '#F2D1B3', '#F2B8A2', '#F28C8C']
              }
            },
            series: {
              type: 'sunburst',
              data: data,
              radius: [0, '90%'],
              label: {
                rotate: 'radial'
              }
            }
          });
        },
    },
  }
</script>

<style scoped>
  .demo-avatar-demo-basic{
    margin-top: 1px;
    margin-bottom: 10px;
    background-color: #FFF;
  }
  .icon-wrap{
    font-size: 70px;
    text-align: center;
    color: #666666;
    margin-left: 10px;
    margin-right: 10px;
  }
  .icon-word{
    font-size: 15px;
    margin-top: 0px;
    text-align: center;
    color: #666666;
    margin-bottom: 5px;
  }
  .demo-info{
    margin-top: 6px;
    margin-bottom: 1px;
  }
</style>
