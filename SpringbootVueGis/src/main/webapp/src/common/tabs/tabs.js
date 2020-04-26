
//Default Page
import RightSideIndex from "../../components/RightSidePart/RightSideIndex";
import RightSideMap from "../../components/RightSidePart/RightSideMap";
import RightSideMapScan from "../../components/RightSidePart/RightSideMapScan";
import RightSideSearch from "../../components/RightSidePart/RightSideSearch";

//Model Page
import casaNpp from "../../components/model/casaNpp";
import disaster from "../../components/model/disaster";
import rivernet from "../../components/model/rivernet";
import showUavPicture from "../../components/model/showUavPicture";
import streamExtraction from "../../components/model/streamExtraction";
import wcslist from "../../components/model/wcslist";

export default [{
  name: 'RightSideIndex',
  title: 'Index',
  component: RightSideIndex
},{
  name: 'RightSideMap',
  title: 'Map',
  component: RightSideMap
},{
  name: 'RightSideMapScan',
  title: 'MapScan',
  component: RightSideMapScan
},{
  name: 'RightSideSearch',
  title: 'Search',
  component: RightSideSearch
},{
  name: 'casaNpp',
  title: 'CASA Npp Model',
  component: casaNpp
},{
  name: 'disaster',
  title: 'Rain Disaster Model',
  component: disaster
},{
  name: 'rivernet',
  title: 'River Net Model',
  component: rivernet
},{
  name: 'showUavPicture',
  title: 'Show Uav Picture',
  component: showUavPicture
},{
  name: 'streamExtraction',
  title: 'Stream Extraction Model',
  component: streamExtraction
},{
  name: 'wcslist',
  title: 'WCS List',
  component: wcslist
}]
