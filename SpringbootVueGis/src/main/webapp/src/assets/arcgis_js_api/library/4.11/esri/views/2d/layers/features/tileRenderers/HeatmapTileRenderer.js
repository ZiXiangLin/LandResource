// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.11/esri/copyright.txt for details.
//>>built
require({cache:{"esri/renderers/support/heatmapUtils":function(){define(["require","exports","../../core/global","../../views/3d/support/mathUtils"],function(k,b,p,g){function n(c,a,d,q,f){c=new Uint32Array(c*c);a="buffer"in a?a:new Float64Array(a);d="buffer"in d?new Uint32Array(d.buffer):new Uint32Array((new Uint8Array(d)).buffer);f=d.length/(f-q);for(var b=0;b<a.length;b++)c[b]=d[g.clamp(Math.floor((a[b]-q)*f),0,d.length-1)];return c.buffer}function r(c){for(var a=Math.round(3*c),d=2*c*c,b=new Float64Array(2*
a+1),f=0;f<=b.length;f++)b[f]=Math.exp(-Math.pow(f-a,2)/d)/Math.sqrt(2*Math.PI)*(c/2);return b}function l(c,a){return"function"===typeof c?c:c?"string"===typeof a?function(a){return-1*+a[c]}:function(d){return+d[c]+a}:function(a){return 1}}Object.defineProperty(b,"__esModule",{value:!0});b.generateGradient=function(){if(!("document"in p))return function(a){return null};var c=document.createElement("canvas"),a=c.getContext("2d");c.height=512;c.width=1;return function(d){var b=a.createLinearGradient(0,
0,0,c.height),f=0;for(d=d.colorStops;f<d.length;f++){var g=d[f],h=g.color;b.addColorStop(g.ratio,"rgba("+h[0]+", "+h[1]+", "+h[2]+", "+h[3]+")")}a.fillStyle=b;a.fillRect(0,0,1,c.height);return a.getImageData(0,0,1,c.height).data}}();b.calculateHeatmapIntensityInfo=function(c,a,d,b){var f=a.blurRadius,g=a.fieldOffset,h=a.field;a=new Float64Array(d*b);for(var q=r(f),f=Math.round(3*f),k=Number.NEGATIVE_INFINITY,e,g=l(h,g),h=0;h<c.length;h++){var m=c[h],t=m.geometry,x=t.x-f,n=t.y-f,z=Math.max(0,x);e=
Math.max(0,n);for(var p=Math.min(b,t.y+f),t=Math.min(d,t.x+f),m=+g(m.attributes),u=e;u<p;u++)for(var A=q[u-n],v=z;v<t;v++)e=a[u*d+v]+=A*q[v-x]*m,e>k&&(k=e)}return{matrix:a.buffer,max:k}};b.drawHeatmap=function(c,a,d,b,f,g){c.canvas.width=c.canvas.height=a;c.clearRect(0,0,a,a);var h=c.getImageData(0,0,a,a);d&&b&&h.data.set(new Uint8ClampedArray(n(a,d,b,f,g)));c.putImageData(h,0,0)};b.createHeatmapImageData=n;b.createKernel=r;b.createValueFunction=l})},"esri/views/2d/layers/features/tileRenderers/BaseTileRenderer":function(){define("require exports ../../../../../core/tsSupport/declareExtendsHelper ../../../../../core/tsSupport/decorateHelper ../../../../../core/Accessor ../../../../../core/HandleOwner ../../../../../core/accessorSupport/decorators".split(" "),
function(k,b,p,g,n,r,l){Object.defineProperty(b,"__esModule",{value:!0});k=function(c){function a(a){a=c.call(this)||this;a.tiles=new Map;a.layer=null;return a}p(a,c);a.prototype.destroy=function(){this.tiles.clear();this.layer=this.layerView=this.tileInfoView=this.configuration=this.tiles=null};Object.defineProperty(a.prototype,"updating",{get:function(){return this.isUpdating()},enumerable:!0,configurable:!0});a.prototype.acquireTile=function(a){var c=this,d=this.createTile(a);d.once("isReady",
function(){return c.notifyChange("updating")});this.tiles.set(a.id,d);return d};a.prototype.forEachTile=function(a){this.tiles.forEach(a)};a.prototype.releaseTile=function(a){this.tiles.delete(a.key.id);this.disposeTile(a)};a.prototype.isUpdating=function(){var a=!0;this.tiles.forEach(function(c){a=a&&c.isReady});return!a};a.prototype.requestUpdate=function(){this.layerView.requestUpdate()};g([l.property()],a.prototype,"configuration",void 0);g([l.property()],a.prototype,"layer",void 0);g([l.property()],
a.prototype,"layerView",void 0);g([l.property()],a.prototype,"tileInfoView",void 0);g([l.property()],a.prototype,"updating",null);return a=g([l.subclass()],a)}(l.declared(n,r));b.default=k})},"esri/views/2d/layers/features/tileRenderers/support/HeatmapSource":function(){define(["require","exports","../../../../../../renderers/support/heatmapUtils"],function(k,b,p){Object.defineProperty(b,"__esModule",{value:!0});k=function(){function b(){this.gradient=null;this.width=this.height=512}b.prototype.render=
function(b){p.drawHeatmap(b,512,this.intensities,this.gradient,this.minPixelIntensity,this.maxPixelIntensity)};return b}();b.HeatmapSource=k})},"*noref":1}});
define("require exports ../../../../../core/tsSupport/declareExtendsHelper ../../../../../core/tsSupport/decorateHelper ../../../../../renderers ../../../../../core/Accessor ../../../../../core/maybe ../../../../../core/promiseUtils ../../../../../core/accessorSupport/decorators ../../../../../renderers/support/diffUtils ../../../../../renderers/support/heatmapUtils ../../../engine/BitmapContainer ../../../engine/BitmapTile ./BaseTileRenderer ./support/HeatmapSource".split(" "),function(k,b,p,g,n,
r,l,c,a,d,q,f,w,h,y){Object.defineProperty(b,"__esModule",{value:!0});k=function(b){function e(a){a=b.call(this,a)||this;a.container=new f.BitmapContainer;return a}p(e,b);Object.defineProperty(e.prototype,"gradient",{get:function(){return q.generateGradient(this.configuration.renderer)},enumerable:!0,configurable:!0});e.prototype.createTile=function(a){var m=w.BitmapTile.pool.acquire();m.key.set(a);this.tileInfoView.getTileCoords(m,a);m.resolution=this.tileInfoView.getTileResolution(a);return m};
e.prototype.applyConfiguration=function(a){this.configuration=a;a=a.renderer;var m=a.minPixelIntensity,b=a.maxPixelIntensity,c=this.gradient;this.tiles.forEach(function(a){var d=a.source;d&&(d.minPixelIntensity=m,d.maxPixelIntensity=b,d.gradient=c,a.invalidateTexture())})};e.prototype.clear=function(){this.tiles.forEach(function(a){if(a=a.source)a.intensities=null})};e.prototype.getProcessorConfiguration=function(){var a=this.layer,b=this.layerView;return{type:"heatmap",renderer:a.renderer.toJSON(),
definitionExpression:a.definitionExpression,availableFields:b.availableFields,gdbVersion:a.gdbVersion,historicMoment:a.historicMoment&&a.historicMoment.getTime()}};e.prototype.hitTest=function(a,b){return c.resolve([])};e.prototype.highlight=function(a){return{remove:function(){},pause:function(){},resume:function(){}}};e.prototype.install=function(a){a.addChild(this.container)};e.prototype.uninstall=function(a){this.container.removeAllChildren();a.removeChild(this.container)};e.prototype.needsProcessorReconfiguration=
function(a){var b=this.configuration;return this.wouldClear(a)||b.definitionExpression!==a.definitionExpression};e.prototype.wouldClear=function(a){var b=this.configuration;if(!b||b.availableFields.join()!==a.availableFields.join())return!0;b=this.configuration&&n.fromJSON(this.configuration.renderer)||null;a=a&&n.fromJSON(a.renderer)||null;a=d.diff(b,a);if(l.isNone(a))return!1;switch(a.type){case "complete":return!0;case "partial":for(var c in a.diff)if("colorStops"!==c&&"minPixelIntensity"!==c&&
"maxPixelIntensity"!==c)return!0}return!1};e.prototype.disposeTile=function(a){this.container.removeChild(a);w.BitmapTile.pool.release(a)};e.prototype.supportsRenderer=function(a){return a&&"heatmap"===a.type};e.prototype.onTileData=function(a){var b=this.tiles.get(a.tileKey);if(b){a=a.intensityInfo;var c=this.configuration.renderer,d=c.minPixelIntensity,c=c.maxPixelIntensity,e=b.source||new y.HeatmapSource;e.intensities=a&&a.matrix||null;e.minPixelIntensity=d;e.maxPixelIntensity=c;e.gradient=this.gradient;
b.source=e;this.container.addChild(b);this.requestUpdate()}};e.prototype.onTileError=function(a){console.error(a)};e.prototype.setFilterFlags=function(a,b,c,d){};g([a.property({readOnly:!0,dependsOn:["configuration"]})],e.prototype,"gradient",null);return e=g([a.subclass()],e)}(a.declared(r,h.default));b.default=k});