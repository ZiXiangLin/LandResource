// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.11/esri/copyright.txt for details.
//>>built
define(["require","exports"],function(g,h){return function(){function a(a,b,c,d,e,f){this.camera=null;this.slot=this.pass=0;this.options=null;this.renderOccludedMask=5;this.rctx=a;this.offscreenRenderingHelper=b;this.lightingData=c;this.shadowMap=d;this.ssaoHelper=e;this.sliceHelper=f}a.prototype.resetRenderOccludedMask=function(){this.renderOccludedMask=5};Object.defineProperty(a.prototype,"isHighlightPass",{get:function(){return 4===this.pass},enumerable:!0,configurable:!0});return a}()});