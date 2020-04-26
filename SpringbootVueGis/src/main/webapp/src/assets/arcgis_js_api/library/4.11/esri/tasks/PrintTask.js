// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.11/esri/copyright.txt for details.
//>>built
define("require exports ../core/tsSupport/assignHelper ../core/tsSupport/awaiterHelper ../core/tsSupport/declareExtendsHelper ../core/tsSupport/decorateHelper ../core/tsSupport/generatorHelper dojo/dom-construct dojox/gfx/canvas ../kernel ../request ../core/kebabDictionary ../core/lang ../core/promiseUtils ../core/screenUtils ../core/urlUtils ../core/accessorSupport/decorators ../geometry/Polygon ./Geoprocessor ./Task ./support/fileFormat ./support/layoutTemplate ./support/printTaskUtils ./support/PrintTemplate ./support/Query".split(" "),
function(W,X,I,q,J,z,A,K,C,w,L,x,D,M,E,y,v,N,O,P,Q,R,l,S,T){function B(l){return l&&(l.path||"image/svg+xml"===l.contentType)}var G={Feet:"ft",Kilometers:"km",Meters:"m",Miles:"mi"},H=new x.default({esriFeet:"Feet",esriKilometers:"Kilometers",esriMeters:"Meters",esriMiles:"Miles"}),U=new x.default({esriExecutionTypeSynchronous:"sync",esriExecutionTypeAsynchronous:"async"}),V=new T({returnGeometry:!0});return function(x){function e(a){a=x.call(this,a)||this;a._ssExtent=null;a._legendLayers=[];a._legendLayerNameMap=
{};a._gpServerUrl=null;a._cimVersion=null;a._is11xService=!1;a._gpMetadata=null;a.updateDelay=1E3;return a}J(e,x);Object.defineProperty(e.prototype,"_geoprocessor",{get:function(){return new O(this.url,{updateDelay:this.updateDelay})},enumerable:!0,configurable:!0});Object.defineProperty(e.prototype,"mode",{get:function(){return this._gpMetadata&&this._gpMetadata.executionType?U.fromJSON(this._gpMetadata.executionType):"sync"},enumerable:!0,configurable:!0});e.prototype.execute=function(a,b){var d=
this,c=this.url,m=c.lastIndexOf("/GPServer/");0<m&&(c=c.slice(0,m+9));return M.resolve().then(function(){if(d._gpServerUrl===c)return{data:d._gpMetadata};d._gpServerUrl=c;return L(c,{query:{f:"json"}})}).then(function(b){d._gpMetadata=b.data;d._cimVersion=d._gpMetadata.cimVersion;d._is11xService=!!d._cimVersion;return d._getGpPrintParams(a)}).then(function(a){return d._geoprocessor["async"===d.mode?"submitJob":"execute"](a,b).then(function(a){return"sync"===d.mode?a.results&&a.results[0]&&a.results[0].value:
d._geoprocessor.getResultData(a.jobId,"Output_File",b).then(function(a){return a.value})})})};e.prototype._createOperationalLayers=function(a,b){return q(this,void 0,void 0,function(){var d,c,m,p,u,e,n,g,f;return A(this,function(k){switch(k.label){case 0:d=[],c={layerView:null,printTemplate:b,view:a},m=0,b.preserveScale&&(m=b.outScale||a.scale),p=l.getVisibleLayerViews(a,m),u=0,e=p,k.label=1;case 1:if(!(u<e.length))return[3,24];n=e[u];g=n.layer;if(!g.loaded||l.isGroupLayer(g))return[3,23];f=void 0;
c.layerView=n;if(!l.isBingMapsLayer(g))return[3,2];f=this._createBingMapsLayerJSON(g,c);return[3,22];case 2:return l.isCSVLayer(g)?[4,this._createCSVLayerJSON(g,c)]:[3,4];case 3:return f=k.sent(),[3,22];case 4:return l.isFeatureLayer(g)?[4,this._createFeatureLayerJSON(g,c)]:[3,6];case 5:return f=k.sent(),[3,22];case 6:if(!l.isGraphicsLayer(g))return[3,7];f=this._createGraphicsLayerJSON(g,c);return[3,22];case 7:if(!l.isImageryLayer(g))return[3,8];f=this._createImageryLayerJSON(g,c);return[3,22];case 8:if(!l.isKMLLayer(g))return[3,
9];f=this._createKMLLayerJSON(g,c);return[3,22];case 9:if(!l.isMapImageLayer(g))return[3,10];f=this._createMapImageLayerJSON(g,c);return[3,22];case 10:if(!l.isMapNotesLayer(g))return[3,11];f=this._createMapNotesLayerJSON(g,c);return[3,22];case 11:if(!l.isOpenStreetMapLayer(g))return[3,12];f=this._createOpenStreetMapLayerJSON(g,c);return[3,22];case 12:return l.isStreamLayer(g)?[4,this._createStreamLayerJSON(g,c)]:[3,14];case 13:return f=k.sent(),[3,22];case 14:if(!l.isTileLayer(g))return[3,15];f=this._createTileLayerJSON(g,
c);return[3,22];case 15:return l.isVectorTileLayer(g)?[4,this._createVectorTileLayerJSON(g,c)]:[3,17];case 16:return f=k.sent(),[3,22];case 17:if(!l.isWebTileLayer(g))return[3,18];f=this._createWebTileLayerJSON(g,c);return[3,22];case 18:if(!l.isWMSLayer(g))return[3,19];f=this._createWMSLayerJSON(g,c);return[3,22];case 19:if(!l.isWMTSLayer(g))return[3,20];f=this._createWMTSLayerJSON(g,c);return[3,22];case 20:return[4,this._createScreenshotJSON(g,c)];case 21:f=k.sent(),k.label=22;case 22:f&&(Array.isArray(f)?
d.push.apply(d,f):(f.id=g.id,f.title=this._legendLayerNameMap[g.id]||g.title,f.opacity=g.opacity,f.minScale=g.minScale||0,f.maxScale=g.maxScale||0,d.push(f))),k.label=23;case 23:return u++,[3,1];case 24:return m&&d.forEach(function(a){a.minScale=0;a.maxScale=0}),a.graphics&&a.graphics.length&&(f=this._createFeatureCollectionJSON(null,a.graphics,b))&&d.push(f),[2,d]}})})};e.prototype._createBingMapsLayerJSON=function(a,b){return{culture:a.culture,key:a.key,type:"BingMaps"+("aerial"===a.style?"Aerial":
"hybrid"===a.style?"Hybrid":"Road")}};e.prototype._createCSVLayerJSON=function(a,b){var d=b.layerView,c=b.printTemplate;return q(this,void 0,void 0,function(){var b,p;return A(this,function(m){switch(m.label){case 0:this._legendLayers&&this._legendLayers.push({id:a.id});if(!this._is11xService)return[3,1];b={type:"CSV"};a.write(b,{origin:"web-map"});delete b.popupInfo;delete b.layerType;b.showLabels=c.showLabels&&a.labelsVisible;return[3,3];case 1:return[4,this._getGraphics(d)];case 2:return p=m.sent(),
[2,this._createFeatureCollectionJSON(a,p,c)];case 3:return[2,b]}})})};e.prototype._createFeatureCollectionJSON=function(a,b,d){var c=this,m=l.createPolygonLayer(),p=l.createPolylineLayer(),e=l.createPointLayer(),r=l.createMultipointLayer(),n=l.createPointLayer();n.layerDefinition.name="textLayer";delete n.layerDefinition.drawingInfo;a&&("esri.layers.FeatureLayer"===a.declaredClass||"esri.layers.StreamLayer"===a.declaredClass?m.layerDefinition.name=p.layerDefinition.name=e.layerDefinition.name=r.layerDefinition.name=
this._legendLayerNameMap[a.id]||a.get("arcgisProps.title")||a.title:"esri.layers.GraphicsLayer"===a.declaredClass&&(b=a.graphics.items));var g=a&&a.renderer&&"esri.renderer.SimpleRenderer"===a.renderer.declaredClass;if(a&&a.renderer&&"function"!==typeof a.get("renderer.field")){var f=a.renderer.toJSON();m.layerDefinition.drawingInfo.renderer=f;p.layerDefinition.drawingInfo.renderer=f;e.layerDefinition.drawingInfo.renderer=f;r.layerDefinition.drawingInfo.renderer=f}else delete m.layerDefinition.drawingInfo,
delete p.layerDefinition.drawingInfo,delete e.layerDefinition.drawingInfo,delete r.layerDefinition.drawingInfo;var f=a&&a.fields,k=a&&a.renderer,t=[];k&&"function"!==typeof a.get("renderer.field")&&("class-breaks"===k.type?(f||(f=[{name:k.field,type:"esriFieldTypeDouble"}],k.normalizationField&&f.push({name:k.normalizationField,type:"esriFieldTypeDouble"})),k.field&&t.push(k.field),k.normalizationField&&t.push(k.normalizationField)):"unique-value"===k.type&&(f||(f=[{name:k.field,type:"esriFieldTypeString"}],
k.field2&&f.push({name:k.field2,type:"esriFieldTypeString"}),k.field3&&f.push({name:k.field3,type:"esriFieldTypeString"})),k.field&&t.push(k.field),k.field2&&t.push(k.field2),k.field3&&t.push(k.field3)));f&&(m.layerDefinition.fields=f,p.layerDefinition.fields=f,e.layerDefinition.fields=f,r.layerDefinition.fields=f);for(var f=b&&b.length,h,k=function(c){c=b[c]||b.getItemAt(c);if(!1===c.visible||!c.geometry)return"continue";h=c.toJSON();h.hasOwnProperty("popupTemplate")&&delete h.popupTemplate;h.geometry&&
h.geometry.z&&delete h.geometry.z;if(h.symbol&&h.symbol.outline&&"esriCLS"===h.symbol.outline.type&&!F._is11xService)return"continue";h.symbol&&h.symbol.outline&&h.symbol.outline.color&&h.symbol.outline.color[3]&&!F._is11xService&&(h.symbol.outline.color[3]=255);if(a&&a.renderer&&!h.symbol&&("function"===typeof a.renderer.field||a.renderer.compiledFunc||a.renderer.hasVisualVariables())){var f=a.renderer,g=f.getSymbol(c);if(!g)return"continue";h.symbol=g.toJSON();f.hasVisualVariables()&&l.applyVisualVariables(h.symbol,
{renderer:f,graphic:c,symbol:g})}h.symbol&&(h.symbol.angle||delete h.symbol.angle,B(h.symbol)?h.symbol=F._convertSvgToPictureMarkerSymbolJson(h.symbol):h.symbol.text&&delete h.attributes);if(!d||!d.forceFeatureAttributes)if(a&&a.renderer&&"simple"===a.renderer.type)delete h.attributes;else if(t.length){var k={};t.forEach(function(a){h.attributes&&h.attributes.hasOwnProperty(a)&&(k[a]=h.attributes[a])});h.attributes=k}"polygon"===c.geometry.type?m.featureSet.features.push(h):"polyline"===c.geometry.type?
p.featureSet.features.push(h):"point"===c.geometry.type?h.symbol&&h.symbol.text?n.featureSet.features.push(h):e.featureSet.features.push(h):"multipoint"===c.geometry.type?r.featureSet.features.push(h):"extent"===c.geometry.type&&(h.geometry=N.fromExtent(c.geometry).toJSON(),m.featureSet.features.push(h))},F=this,q=0;q<f;q++)k(q);f=[m,p,r,e,n].filter(function(a){return 0<a.featureSet.features.length});f.forEach(function(a){var b=a.featureSet.features.every(function(a){return a.symbol});!b&&!g||d&&
d.forceFeatureAttributes||a.featureSet.features.forEach(function(a){delete a.attributes});b&&delete a.layerDefinition.drawingInfo;a.layerDefinition.drawingInfo&&a.layerDefinition.drawingInfo.renderer&&c._convertSvgRenderer(a.layerDefinition.drawingInfo.renderer)});return f.length?{featureCollection:{layers:f}}:null};e.prototype._createFeatureLayerJSON=function(a,b){return q(this,void 0,void 0,function(){var d,c,m,p,e,r,n,g,f,k;return A(this,function(l){switch(l.label){case 0:this._legendLayers&&this._legendLayers.push({id:a.id});
if((c=a.renderer)&&"dot-density"===c.type)return[2,this._createScreenshotJSON(a,b)];m=b.layerView;p=b.printTemplate;e=b.view;r=c&&("valueExpression"in c&&c.valueExpression||"hasVisualVariables"in c&&c.hasVisualVariables());n=a.source&&"feature-layer"!==a.source.type;if(!this._is11xService&&r||a.featureReduction||n||!c||"field"in c&&!(null==c.field||"string"===typeof c.field&&a.getField(c.field)))return[3,1];d={};this._setURLandToken(d,a);a.write(d,{origin:"web-map"});delete d.layerType;delete d.popupInfo;
delete d.visibility;d.showLabels=p.showLabels&&a.labelsVisible;d.layerDefinition&&d.layerDefinition.drawingInfo&&d.layerDefinition.drawingInfo.renderer&&(this._convertSvgRenderer(d.layerDefinition.drawingInfo.renderer),"visualVariables"in c&&c.visualVariables&&c.visualVariables[0]&&(g=c.visualVariables[0],"size"===g.type&&g.maxSize&&"number"!==typeof g.maxSize&&g.minSize&&"number"!==typeof g.minSize&&(f=c.getSizeRangeAtScale(g,e.scale),d.layerDefinition.drawingInfo.renderer.visualVariables[0].minSize=
f.minSize,d.layerDefinition.drawingInfo.renderer.visualVariables[0].maxSize=f.maxSize)));return[3,3];case 1:return[4,this._getGraphics(m)];case 2:k=l.sent(),d=this._createFeatureCollectionJSON(a,k,p),l.label=3;case 3:return[2,d]}})})};e.prototype._createGraphicsLayerJSON=function(a,b){return this._createFeatureCollectionJSON(a,null,b.printTemplate)};e.prototype._createImageryLayerJSON=function(a,b){this._legendLayers&&this._legendLayers.push({id:a.id});b={bandIds:a.bandIds,compressionQuality:a.compressionQuality,
format:a.format,interpolation:a.interpolation};a.mosaicRule&&(b.mosaicRule=a.mosaicRule.toJSON());a.renderingRule&&(b.renderingRule=a.renderingRule.toJSON());this._setURLandToken(b,a);return b};e.prototype._createKMLLayerJSON=function(a,b){var d=b.printTemplate;if(this._is11xService)return b={type:"kml"},a.write(b,{origin:"web-map"}),delete b.layerType,b.url=y.normalize(a.url),b.showLabels=d.showLabels&&a.labelsVisible,b;var c=[];b=b.layerView;b.allVisibleMapImages.forEach(function(b,d){d={id:a.id+
"_image"+d,type:"image",title:a.id,minScale:a.minScale||0,maxScale:a.maxScale||0,opacity:a.opacity,extent:b.extent};"data:image/png;base64,"===b.href.substr(0,22)?d.imageData=b.href.substr(22):d.url=b.href;c.push(d)});b=b.allVisiblePoints.items.concat(b.allVisiblePolylines.items,b.allVisiblePolygons.items);d=I({id:a.id},this._createFeatureCollectionJSON(null,b,d));c.push(d);return c};e.prototype._createMapImageLayerJSON=function(a,b){var d,c={id:a.id,subLayerIds:[]},m=[],p=b.view.scale,e=function(a){var b=
0===p,d=0===a.minScale||p<=a.minScale,f=0===a.maxScale||p>=a.maxScale;a.visible&&(b||d&&f)&&(a.sublayers?a.sublayers.forEach(e):(b=a.toExportImageJSON().drawingInfo,d=a.toJSON(),d.layerDefinition.drawingInfo=b,m.unshift(d),c.subLayerIds.push(a.id)))};a.sublayers&&a.sublayers.forEach(e);m.length&&(d={layers:m,visibleLayers:c.subLayerIds},this._setURLandToken(d,a),this._legendLayers.push(c));return d};e.prototype._createMapNotesLayerJSON=function(a,b){var d=this,c=b.printTemplate,m=[];b.layerView.graphicsViews.forEach(function(a){(a=
d._createFeatureCollectionJSON(a,a.graphics,c))&&m.push.apply(m,a.featureCollection.layers)});return{featureCollection:{layers:m}}};e.prototype._createOpenStreetMapLayerJSON=function(a,b){return{type:"OpenStreetMap"}};e.prototype._createScreenshotJSON=function(a,b){var d=b.printTemplate,c=b.view;return q(this,void 0,void 0,function(){var b,e,l,r,n,g,f,k,t,h;return A(this,function(m){switch(m.label){case 0:return b={type:"image"},e={format:"png",layers:[a],rotation:0},l=this._ssExtent||c.extent.clone(),
r=96,g=n=!0,d.exportOptions&&(f=d.exportOptions,0<f.dpi&&(r=f.dpi),0<f.width&&(n=f.width%2===c.width%2),0<f.height&&(g=f.height%2===c.height%2)),"map-only"!==d.layout||!d.preserveScale||d.outScale&&d.outScale!==c.scale||96!==r||n&&g||(e.area={x:0,y:0,width:c.width,height:c.height},n||--e.area.width,g||--e.area.height,this._ssExtent||(k=c.toMap(E.createScreenPoint(e.area.width,e.area.height)),l.ymin=k.y,l.xmax=k.x,this._ssExtent=l)),b.extent=l.clone()._normalize(!0).toJSON(),[4,c.takeScreenshot(e)];
case 1:return t=m.sent(),h=y.dataComponents(t.dataUrl),b.imageData=h.data,[2,b]}})})};e.prototype._createStreamLayerJSON=function(a,b){var d=b.layerView,c=b.printTemplate;return q(this,void 0,void 0,function(){var b;return A(this,function(e){switch(e.label){case 0:return this._legendLayers&&this._legendLayers.push({id:a.id}),[4,this._getGraphics(d)];case 1:return b=e.sent(),[2,this._createFeatureCollectionJSON(a,b,c)]}})})};e.prototype._createTileLayerJSON=function(a,b){b={};this._setURLandToken(b,
a);return b};e.prototype._createVectorTileLayerJSON=function(a,b){return q(this,void 0,void 0,function(){var d,c,e;return A(this,function(m){return this._is11xService&&a.serviceUrl&&a.styleUrl&&(d=w.id&&w.id.findCredential(a.styleUrl),c=w.id&&w.id.findCredential(a.serviceUrl),!d&&!c||"2.1.0"!==this._cimVersion)?(e={type:"VectorTileLayer"},e.styleUrl=y.normalize(a.styleUrl),d&&(e.token=d.token),c&&c.token!==e.token&&(e.additionalTokens=[{url:a.serviceUrl,token:c.token}]),[2,e]):[2,this._createScreenshotJSON(a,
b)]})})};e.prototype._createWebTileLayerJSON=function(a,b){b={type:"WebTiledLayer",urlTemplate:a.urlTemplate.replace(/\${/g,"{"),credits:a.copyright};a.subDomains&&0<a.subDomains.length&&(b.subDomains=a.subDomains);return b};e.prototype._createWMSLayerJSON=function(a,b){var d,c=[],e=function(a){a.visible&&(a.sublayers?a.sublayers.forEach(e):a.name&&c.unshift(a.name))};a.sublayers&&a.sublayers.forEach(e);c.length&&(d={type:"wms",transparentBackground:a.imageTransparency,visibleLayers:c,url:y.normalize(a.url),
version:a.version});return d};e.prototype._createWMTSLayerJSON=function(a,b){b=a.activeLayer;return{type:"wmts",format:b.imageFormat,layer:b.id,style:b.styleId,tileMatrixSet:b.tileMatrixSetId,url:y.normalize(a.url)}};e.prototype._setURLandToken=function(a,b){b.url&&(a.url=y.normalize(b.url),b=w.id&&w.id.findCredential(b.url))&&(a.token=b.token)};e.prototype._convertSvgToPictureMarkerSymbolJson=function(a){this._canvasParent?(this._canvasSurface.clear(),this._canvasSurface.setDimensions(1024,1024)):
(this._canvasParent=K.create("div"),this._canvasSurface=C.createSurface(this._canvasParent,1024,1024));var b;b="image/svg+xml"===a.contentType?this._canvasSurface.createObject(C.Image,{src:"data:image/svg+xml;base64,"+a.imageData,width:E.pt2px(a.width),height:E.pt2px(a.height),x:0,y:0}):this._canvasSurface.createObject(C.Path,a.path).setFill(a.color).setStroke(a.outline);"pendingRender"in this._canvasSurface&&this._canvasSurface._render(!0);var d=this._canvasSurface.rawNode.getContext("2d");b=b.getBoundingBox();
var c=Math.ceil(b.width+b.x),e=Math.ceil(b.height+b.y),l=d.getImageData(b.x,b.y,c,e);d.canvas.width=c;d.canvas.height=e;d.putImageData(l,0,0);return{type:"esriPMS",imageData:d.canvas.toDataURL("image/png").substr(22),angle:a.angle,contentType:"image/png",height:a.size?a.size:e-b.y,width:a.size?a.size:c-b.x,xoffset:a.xoffset,yoffset:a.yoffset}};e.prototype._convertSvgRenderer=function(a){var b=this,d=a.type;if("simple"===d&&B(a.symbol))a.symbol=this._convertSvgToPictureMarkerSymbolJson(a.symbol);else if("unique-value"===
d||"class-breaks"===d)B(a.defaultSymbol)&&(a.defaultSymbol=this._convertSvgToPictureMarkerSymbolJson(a.defaultSymbol)),(a=a["unique-value"===d?"uniqueValueInfos":"classBreakInfos"])&&a.forEach(function(a){B(a.symbol)&&(a.symbol=b._convertSvgToPictureMarkerSymbolJson(a.symbol))})};e.prototype._getGraphics=function(a){return a.queryFeatures(V).then(function(a){return a.features})};e.prototype._getPrintDefinition=function(a,b){return q(this,void 0,void 0,function(){var d,c,e,l,u;return A(this,function(m){switch(m.label){case 0:return d=
a.view,c=d.spatialReference,l={},[4,this._createOperationalLayers(d,b)];case 1:return e=(l.operationalLayers=m.sent(),l),u=this._ssExtent||a.extent||d.extent,c&&c.isWrappable&&(u=u.clone()._normalize(!0),c=u.spatialReference),e.mapOptions={extent:u&&u.toJSON(),spatialReference:c&&c.toJSON(),showAttribution:b.attributionVisible},this._ssExtent=null,d.rotation&&(e.mapOptions.rotation=-d.rotation),b.preserveScale&&(e.mapOptions.scale=b.outScale||d.scale),[2,e]}})})};e.prototype._getGpPrintParams=function(a){return q(this,
void 0,void 0,function(){var b,d,c,e,l,u,r,n,g,f,k,t,h,q,w,y,v,z,x,B=this;return A(this,function(m){switch(m.label){case 0:b=a.template||new S;null==b.showLabels&&(b.showLabels=!0);d=b.exportOptions;e=R.toJSON(b.layout);d&&(l=d.dpi,c={dpi:l},"map_only"===e.toLowerCase()||""===e)&&(u=d.width,r=d.height,c.outputSize=[u,r]);if(n=b.layoutOptions){k=f=void 0;if("Miles"===n.scalebarUnit||"Kilometers"===n.scalebarUnit)f="Kilometers",k="Miles";else if("Meters"===n.scalebarUnit||"Feet"===n.scalebarUnit)f=
"Meters",k="Feet";g={titleText:n.titleText,authorText:n.authorText,copyrightText:n.copyrightText,customTextElements:n.customTextElements,scaleBarOptions:{metricUnit:H.toJSON(f),metricLabel:G[f],nonMetricUnit:H.toJSON(k),nonMetricLabel:G[k]}}}t=null;n&&n.legendLayers&&(t=n.legendLayers.map(function(a){B._legendLayerNameMap[a.layerId]=a.title;var b={id:a.layerId};a.subLayerIds&&(b.subLayerIds=a.subLayerIds);return b}));return[4,this._getPrintDefinition(a,b)];case 1:return h=m.sent(),h.operationalLayers&&
(w=/[\u4E00-\u9FFF\u0E00-\u0E7F\u0900-\u097F\u3040-\u309F\u30A0-\u30FF\u31F0-\u31FF]/,y=/[\u0600-\u06FF]/,v=function(a){var b=a.text,c=(a=a.font)&&a.family&&a.family.toLowerCase();b&&a&&("arial"===c||"arial unicode ms"===c)&&(a.family=w.test(b)?"Arial Unicode MS":"Arial","normal"!==a.style&&y.test(b)&&(a.family="Arial Unicode MS"))},h.operationalLayers.forEach(function(a){a.featureCollection&&a.featureCollection.layers&&a.featureCollection.layers.forEach(function(a){a.layerDefinition&&a.layerDefinition.drawingInfo&&
a.layerDefinition.drawingInfo.renderer&&a.layerDefinition.drawingInfo.renderer.symbol&&(q=a.layerDefinition.drawingInfo.renderer,"esriTS"===q.symbol.type&&v(q.symbol));a.featureSet&&a.featureSet.features&&a.featureSet.features.forEach(function(a){a.symbol&&"esriTS"===a.symbol.type&&v(a.symbol)})})})),a.outSpatialReference&&(h.mapOptions.spatialReference=a.outSpatialReference.toJSON()),D.mixin(h,{exportOptions:c,layoutOptions:g}),D.mixin(h.layoutOptions,{legendOptions:{operationalLayers:null!=t?t:
this._legendLayers.slice()}}),this._legendLayers.length=0,z=JSON.stringify(h),x={Web_Map_as_JSON:z,Format:Q.toJSON(b.format),Layout_Template:e},a.extraParameters&&D.mixin(x,a.extraParameters),[2,x]}})})};z([v.property({dependsOn:["url","updateDelay"]})],e.prototype,"_geoprocessor",null);z([v.property()],e.prototype,"_gpMetadata",void 0);z([v.property({dependsOn:["_gpMetadata"],readOnly:!0})],e.prototype,"mode",null);z([v.property()],e.prototype,"updateDelay",void 0);return e=z([v.subclass("esri.tasks.PrintTask")],
e)}(v.declared(P))});