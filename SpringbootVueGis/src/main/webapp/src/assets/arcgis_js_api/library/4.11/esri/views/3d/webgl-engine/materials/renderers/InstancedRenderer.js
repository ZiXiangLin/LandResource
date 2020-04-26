// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.11/esri/copyright.txt for details.
//>>built
define("require exports ../../../../../core/libs/gl-matrix-2/mat4f64 ../../../support/buffer/glUtil ../../lib/DefaultVertexAttributeLocations ./Instance ./utils ../../../../webgl/BufferObject ../../../../webgl/Util ../../../../webgl/VertexArrayObject".split(" "),function(y,z,q,r,t,u,h,v,n,w){function x(e,a){var b=new Map,d=function(f,a){var d=f.origin.id,c=f.data.id,g=b.get(d);g||(g={origin:f.origin.vec3,deltaByGeometry:new Map},b.set(d,g));d=g.deltaByGeometry.get(c);d||(d={renderData:f.data,toAdd:[],
toRemove:[]},g.deltaByGeometry.set(c,d));(a?d.toAdd:d.toRemove).push(f)};e.forEach(function(f){d(f,!0)});a.forEach(function(f){d(f,!1)});return b}function p(e,a,b){var d=a.elementCount(e),d=a.allocate(d);a.write({},e,null,d,0);b.setData(d.buffer)}return function(){function e(a,b,d){this._dataByOrigin=new Map;this._highlightCount=0;this._rctx=a;this._material=d;this._materialRep=b;this._glMaterials=h.acquireMaterials(this._material,this._materialRep);this._bufferWriter=d.createBufferWriter()}e.prototype.dispose=
function(){h.releaseMaterials(this._material,this._materialRep)};Object.defineProperty(e.prototype,"isEmpty",{get:function(){return 0===this._dataByOrigin.size},enumerable:!0,configurable:!0});Object.defineProperty(e.prototype,"hasHighlights",{get:function(){return 0<this._highlightCount},enumerable:!0,configurable:!0});e.prototype.renderPriority=function(){return this._material.renderPriority};e.prototype.modify=function(a){this.updateGeometries(a.toUpdate);this.addAndRemoveGeometries(a.toAdd,a.toRemove);
this.updateHighlightCount()};e.prototype.addAndRemoveGeometries=function(a,b){var d=this._rctx,f=this._bufferWriter,l=this._dataByOrigin;a=x(a,b);var e=f.vertexBufferLayout;a.forEach(function(a,b){var c=l.get(b);c||(c={origin:a.origin,highlightCount:0,dataByGeometry:new Map},l.set(b,c));a.deltaByGeometry.forEach(function(a,b){var g=c.dataByGeometry.get(b);!g&&0<a.toAdd.length&&(g={vao:new w(d,t.Default3D,{geometry:r.glLayout(e)},{geometry:v.createVertex(d,35044)}),vertexCount:0,instances:new Map,
highlightCount:0},p(a.renderData,f,g.vao.vertexBuffers.geometry),g.vertexCount=n.vertexCount(g.vao,"geometry"),c.dataByGeometry.set(b,g));var l=g.instances;a.toAdd.forEach(function(a){var f=q.mat4f64.create();h.calculateTransformRelToOrigin(a,f);var d=h.generateRenderGeometryVisibleIndexRanges(a),b=h.generateRenderGeometryHighlightRanges(a),f=new u(a.name,0,g.vertexCount,d,b,f,a.instanceParameters,a.idx,a.data.id);l.set(a.uniqueName,f);c.highlightCount=null;g.highlightCount=null});a.toRemove.forEach(function(a){l.delete(a.uniqueName)});
0===l.size&&(g.vao.dispose(),c.dataByGeometry.delete(b))});0===c.dataByGeometry.size&&l.delete(b)})};e.prototype.updateGeometries=function(a){var b=this._dataByOrigin,d=this._bufferWriter;a.forEach(function(a){var f=a.updateType;a=a.renderGeometry;var e=b.get(a.origin.id),c=e&&e.dataByGeometry.get(a.data.id),g=c&&c.instances.get(a.uniqueName);g&&(f&1&&(g.displayedIndexRange=h.generateRenderGeometryVisibleIndexRanges(a)),f&17&&(g.highlightedIndexRanges=h.generateRenderGeometryHighlightRanges(a),e.highlightCount=
null,c.highlightCount=null),f&2&&p(a.data,d,c.vao.vertexBuffers.geometry),f&4&&h.calculateTransformRelToOrigin(a,g.transformation,g.transformationNormal))})};e.prototype.updateHighlightCount=function(){var a=this;this._highlightCount=0;this._dataByOrigin.forEach(function(b){if(null==b.highlightCount){var d=0;b.dataByGeometry.forEach(function(a){if(null==a.highlightCount){var b=0;a.instances.forEach(function(a){a.highlightedIndexRanges&&++b});a.highlightCount=b}d+=a.highlightCount});b.highlightCount=
d}a._highlightCount+=b.highlightCount})};e.prototype.render=function(a,b,d,f){var e=this,h=this._rctx,c=this._glMaterials.get(b.pass),g=4===b.pass;if(!c||null!=a&&!c.beginSlot(a)||g&&0===this._highlightCount)return!1;c.bind(h,d);var k=!1;this._dataByOrigin.forEach(function(a){g&&0===a.highlightCount||(d.origin=a.origin,c.bindView(h,d),a.dataByGeometry.forEach(function(a){k=g?e.renderHighlightPass(c,a,f)||k:e.renderDefaultPass(c,a,f)||k}))});h.bindVAO(null);c.release(h,d);return k};e.prototype.renderDefaultPass=
function(a,b,d){var f=this._rctx,e=a.getProgram(),m=a.getDrawMode(),c=b.vao,g=b.vertexCount;n.assertCompatibleVertexAttributeLocations(c,e);f.bindVAO(c);var k=!1;b.instances.forEach(function(b){var c=b.displayedIndexRange;c&&0===c.length||(a.bindInstance(f,b),c?h.drawArraysFaceRange(f,c,0,m,d):h.drawArrays(f,m,0,g,d),k=!0)});return k};e.prototype.renderHighlightPass=function(a,b,d){var f=this._rctx,e=a.getProgram(),m=a.getDrawMode(),c=b.vao,g=b.vertexCount;if(0!==b.highlightCount){n.assertCompatibleVertexAttributeLocations(c,
e);f.bindVAO(c);var k=!1;b.instances.forEach(function(b){var c=b.highlightedIndexRanges;if(c&&0!==c.length)for(a.bindInstance(f,b),b=0;b<c.length;b++){var e=c[b];h.drawArrays(f,m,e.range?e.range[0]:0,e.range?e.range[1]-e.range[0]+1:g,d);k=!0}});return k}};return e}()});