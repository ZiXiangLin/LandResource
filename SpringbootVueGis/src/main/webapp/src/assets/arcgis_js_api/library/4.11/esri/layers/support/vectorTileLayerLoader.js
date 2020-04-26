// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.11/esri/copyright.txt for details.
//>>built
define("require exports ../../core/tsSupport/assignHelper ../../config ../../request ../../core/promiseUtils ../../core/urlUtils ../../views/vectorTiles/style/VectorTileSource".split(" "),function(A,u,B,v,x,m,k,y){function n(a){a&&(a=k.getOrigin(a),r&&-1===r.indexOf(a)&&r.push(a))}function p(){for(var a=[],b=0;b<arguments.length;b++)a[b]=arguments[b];for(var b=void 0,c=0;c<a.length;++c)k.isProtocolRelative(a[c])?b&&(b=b.split("://")[0]+":"+a[c].trim()):b=k.isAbsolute(a[c])?a[c]:k.join(b,a[c]);return b}
function q(a,b,c,h){var g,d,e;"string"===typeof c?(c=k.normalize(c),n(c),g=k.urlToObject(c),g=x(g.path,{query:{f:"json"},responseType:"json"}),e=d=c):(g=m.resolve({data:c}),d=c.jsonUrl||null,e=h);return g.then(function(c){var f=c.data;c.ssl&&(d&&(d=d.replace(/^http:/i,"https:")),e&&(e=e.replace(/^http:/i,"https:")));return f.sources?(a.styleUrl=d||null,z(a,f,e)):f.sources?m.reject("You must specify the URL or the JSON for a service or for a style."):a.sourceUrl?w(a,f,e,!1,b):(a.sourceUrl=d||null,
w(a,f,e,!0,b))})}function z(a,b,c){var h=c?k.removeFile(c):k.appBaseUrl;a.styleBase=h;a.style=b;a.styleUrl&&n(a.styleUrl);if(!a.source){var g=[];c=null;if(b.sources.esri){var d=b.sources.esri;d.url?c=q(a,"esri",p(h,d.url)):g.push(q(a,"esri",d,h))}null===c&&(c=m.resolve());return c.then(function(){for(var c=0,d=Object.keys(b.sources);c<d.length;c++){var f=d[c];"esri"!==f&&"vector"===b.sources[f].type&&(b.sources[f].url?g.push(q(a,f,p(h,b.sources[f].url))):g.push(q(a,f,b.sources[f],h)))}return m.eachAlways(g).then(function(){})})}return m.resolve()}
function w(a,b,c,h,g){c=c?k.removeTrailingSlash(c)+"/":k.appBaseUrl;var d;if(b.hasOwnProperty("tileInfo"))d=b;else{d={xmin:-2.0037507067161843E7,ymin:-2.0037507067161843E7,xmax:2.0037507067161843E7,ymax:2.0037507067161843E7,spatialReference:{wkid:102100}};for(var e=(d.xmax-d.xmin)/512,l=[],f=b.hasOwnProperty("minzoom")?parseFloat(b.minzoom):0,t=b.hasOwnProperty("maxzoom")?parseFloat(b.maxzoom):16;f<t;f++){var r=e/Math.pow(2,f);l.push({level:f,scale:Math.floor(3779.527559055118*r),resolution:r})}e=
0;for(t=b.tiles;e<t.length;e++)n(p(c,t[e]));d={capabilities:"TilesOnly",initialExtent:d,fullExtent:d,minScale:l[0].scale,maxScale:l[l.length-1].scale,tiles:b.tiles,tileInfo:{rows:512,cols:512,dpi:96,format:"pbf",origin:{x:-2.0037508342787E7,y:2.0037508342787E7},lods:l,spatialReference:{wkid:102100}}}}l=new y(g,c,d);if(!h&&a.primarySourceName in a.sourceNameToSource){e=a.sourceNameToSource[a.primarySourceName];if(!e.isCompatibleWith(l))return m.resolve();e.fullExtent.union(l.fullExtent);e.tileInfo.lods.length<
l.tileInfo.lods.length&&(e.tileInfo=l.tileInfo)}h?(a.sourceBase=c,a.source=b,a.validatedSource=d,a.primarySourceName=g,a.sourceUrl&&n(a.sourceUrl)):n(c);a.sourceNameToSource[g]=l;return a.style?m.resolve():null==b.defaultStyles?m.reject():"string"===typeof b.defaultStyles?q(a,"",p(c,b.defaultStyles,"root.json")):q(a,"",b.defaultStyles,p(c,"root.json"))}Object.defineProperty(u,"__esModule",{value:!0});var r=v.defaults&&v.defaults.io.corsEnabledServers;u.loadMetadata=function(a){var b={source:null,
sourceBase:null,sourceUrl:null,validatedSource:null,style:null,styleBase:null,styleUrl:null,sourceNameToSource:{},primarySourceName:""},c="string"===typeof a?[a,null]:[null,a.jsonUrl],h=c[0],c=c[1],g=h?k.urlToObject(h):null;return q(b,"esri",a,c).then(function(){var a={layerDefinition:b.validatedSource,url:h,parsedUrl:g,serviceUrl:b.sourceUrl,style:b.style,styleUrl:b.styleUrl,spriteUrl:b.style.sprite&&p(b.styleBase,b.style.sprite),glyphsUrl:b.style.glyphs&&p(b.styleBase,b.style.glyphs),sourceNameToSource:b.sourceNameToSource,
primarySourceName:b.primarySourceName};n(a.spriteUrl);n(a.glyphsUrl);return a})}});