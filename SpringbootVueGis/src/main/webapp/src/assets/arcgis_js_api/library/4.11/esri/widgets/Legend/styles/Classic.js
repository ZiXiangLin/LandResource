// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.11/esri/copyright.txt for details.
//>>built
define("require exports ../../../core/tsSupport/declareExtendsHelper ../../../core/tsSupport/decorateHelper dojo/i18n!../../Legend/nls/Legend dojox/gfx ../../../core/accessorSupport/decorators ../../Widget ./support/utils ../support/styleUtils ../../support/widget".split(" "),function(z,A,v,r,t,w,q,x,y,m,f){return function(u){function e(a){a=u.call(this)||this;a.activeLayerInfos=null;a.type="classic";return a}v(e,u);e.prototype.render=function(){var a=this,c=this.activeLayerInfos,b=this.classes("esri-legend esri-widget--panel",
"esri-widget"),c=c&&c.toArray().map(function(b){return a._renderLegendForLayer(b)}).filter(function(a){return!!a});return f.tsx("div",{class:b},c&&c.length?c:f.tsx("div",{class:"esri-legend__message"},t.noLegend))};e.prototype._renderLegendForLayer=function(a){var c=this,b;if(!a.ready)return null;var d=!!a.children.length,n="esri-legend__"+a.layer.uid+"-version-"+a.version,g=a.title?f.tsx("h3",{class:this.classes("esri-widget__heading","esri-legend__service-label")},a.title):null;if(d)return b=a.children.map(function(a){return c._renderLegendForLayer(a)}).toArray(),
f.tsx("div",{key:n,class:this.classes("esri-legend__service","esri-legend__group-layer")},g,b);if((d=a.legendElements)&&!d.length)return null;d=d.map(function(b){return c._renderLegendForElement(b,a.layer)}).filter(function(a){return!!a});if(!d.length)return null;var h=(b={},b["esri-legend__group-layer-child"]=!!a.parent,b);return f.tsx("div",{key:n,class:this.classes("esri-legend__service",h)},g,f.tsx("div",{class:"esri-legend__layer"},d))};e.prototype._renderLegendForElement=function(a,c,b){var d=
this,n,g="color-ramp"===a.type,h="opacity-ramp"===a.type,e="size-ramp"===a.type,p=null;if("symbol-table"===a.type||e){var k=a.infos.map(function(b){return d._renderLegendForElementInfo(b,c,e,a.legendType)}).filter(function(a){return!!a});k.length&&(p=f.tsx("div",{class:"esri-legend__layer-body"},k))}else"color-ramp"===a.type||"opacity-ramp"===a.type||"heatmap-ramp"===a.type?p=this._renderLegendForRamp(a):"relationship-ramp"===a.type&&(p=y.renderRelationshipRamp(a,this.id));if(!p)return null;var k=
a.title,l=null;"string"===typeof k?l=k:k&&(l=m.getTitle(k,g||h),l=m.isRendererTitle(k,g||h)&&k.title?k.title+" ("+l+")":l);g=b?"esri-legend__layer-child-table":"esri-legend__layer-table";h=l?f.tsx("div",{class:"esri-legend__layer-caption"},l):null;b=(n={},n["esri-legend__layer-table--size-ramp"]=e||!b,n);return f.tsx("div",{class:this.classes(g,b)},h,p)};e.prototype._renderLegendForRamp=function(a){var c=a.infos,b="opacity-ramp"===a.type,d="heatmap-ramp"===a.type,e=c.length-1,g=document.createElement("div");
g.className="esri-legend__color-ramp "+(b?"esri-legend__opacity-ramp":"");g.style.height="75px";b=w.createSurface(g,"100%",75);try{if(d||c.forEach(function(a,b){a.offset=b/e}),b.createRect({x:0,y:0,width:"100%",height:75}).setFill({type:"linear",x1:0,y1:0,x2:0,y2:75,colors:c}).setStroke(null),"color-ramp"===a.type||"opacity-ramp"===a.type){var h=a.overlayColor;h&&0<h.a&&b.createRect({x:0,y:0,width:"100%",height:75}).setFill(h).setStroke(null)}}catch(B){b.clear(),b.destroy()}if(!b)return null;a=c.filter(function(a){return!!a.label}).map(function(a){return f.tsx("div",
{class:"esri-legend__ramp-label"},d?t[a.label]:a.label)});return f.tsx("div",{class:"esri-legend__layer-row"},f.tsx("div",{class:"esri-legend__layer-cell esri-legend__layer-cell--symbols",styles:{width:"24px"}},f.tsx("div",{class:"esri-legend__ramps",bind:g,afterCreate:m.attachToNode})),f.tsx("div",{class:"esri-legend__layer-cell esri-legend__layer-cell--info"},f.tsx("div",{class:"esri-legend__ramp-labels",styles:{height:"75px"}},a)))};e.prototype._renderLegendForElementInfo=function(a,c,b,d){var e,
g;if(a.type)return this._renderLegendForElement(a,c,!0);var h=null;d=m.isImageryStretchedLegend(c,d);a.symbol&&a.preview?h=f.tsx("div",{class:"esri-legend__symbol",bind:a.preview,afterCreate:m.attachToNode}):a.src&&(h=this._renderImage(a,c,d));if(!h)return null;c=(e={},e["esri-legend__imagery-layer-info--stretched"]=d,e);b=(g={},g["esri-legend__imagery-layer-info--stretched"]=d,g["esri-legend__size-ramp"]=!d&&b,g);return f.tsx("div",{class:"esri-legend__layer-row"},f.tsx("div",{class:this.classes("esri-legend__layer-cell esri-legend__layer-cell--symbols",
b)},h),f.tsx("div",{class:this.classes("esri-legend__layer-cell esri-legend__layer-cell--info",c)},m.getTitle(a.label,!1)||""))};e.prototype._renderImage=function(a,c,b){var d,e=a.label,g=a.src,h=a.opacity;b=(d={},d["esri-legend__imagery-layer-image--stretched"]=b,d["esri-legend__symbol"]=!b,d);c={opacity:""+(null!=h?h:c.opacity)};return f.tsx("img",{alt:e,src:g,border:0,width:a.width,height:a.height,class:this.classes(b),styles:c})};r([f.renderable(),q.property()],e.prototype,"activeLayerInfos",
void 0);r([q.property({readOnly:!0})],e.prototype,"type",void 0);return e=r([q.subclass("esri.widgets.Legend.styles.Classic")],e)}(q.declared(x))});