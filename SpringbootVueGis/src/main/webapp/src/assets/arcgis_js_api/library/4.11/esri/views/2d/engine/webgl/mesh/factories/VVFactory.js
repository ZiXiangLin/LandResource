// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.11/esri/copyright.txt for details.
//>>built
define("require exports ../../../../../../core/Error ../../../../../../core/Logger ../../definitions ../../enums ../../Utils ../../visualVariablesUtils".split(" "),function(p,g,q,u,k,l,m,r){Object.defineProperty(g,"__esModule",{value:!0});var f=u.getLogger("esri.views.2d.engine.webgl.mesh.VVFactory"),n;(function(b){b[b.NONE=0]="NONE";b[b.MISSING_DATA=1]="MISSING_DATA"})(n=g.VVError||(g.VVError={}));var t=function(){function b(){this._vvMap=new Map}b.prototype.set=function(a,b){this._vvMap.set(a,b)};
Object.defineProperty(b.prototype,"map",{get:function(){return this._vvMap},enumerable:!0,configurable:!0});b.prototype.getValue=function(a,b,c,d){return this._vvMap.has(a)?this._vvMap.get(a)(b,c,d):0};return b}();p=function(){function b(){}b.fromRenderer=function(a,e){var c=new b;switch(a.type){case "dot-density":c._createDDFunctionMap(a.attributes,e);break;case "simple":case "unique-value":case "class-breaks":c._createVVFunctionMap(a.visualVariables,e);break;default:return f.error("Cannot create factory for renderer type "+
a.type),null}return c};b.prototype.computeVV=function(a,b,c,d){var e=this;if(!this._vvMap)return n.NONE;this._vvMap.map.forEach(function(h,f){h=h(b,c,d);a[f]=e._isErrorVV(h)?1E-30:h});return n.NONE};b.prototype._isErrorVV=function(a){return null===a||isNaN(a)||Infinity===a};b.prototype._createVVFunctionMap=function(a,b){if(a&&a.length)for(var c=0;c<a.length;c++){var d=a[c],e=m.getVVType(d.type);if(d=this._createGetValueFunction(d,b))this._vvMap||(this._vvMap=new t),this._vvMap.set(e,d)}};b.prototype._createDDFunctionMap=
function(a,b){this._vvMap||(this._vvMap=new t);a.length>k.DOT_DENSITY_MAX_FIELDS&&f.warn("mapview-invalid-value","DotDensityRenderer supports a maximium of "+k.DOT_DENSITY_MAX_FIELDS+" attribtues, but found "+a.length);for(var c=0;c<Math.min(k.DOT_DENSITY_MAX_FIELDS,a.length);c++){var d=this._createNormalizedFunction(a[c],b);this._vvMap.set(c,d)}};b.prototype._createGetValueFunction=function(a,b){if(m.getVVType(a.type)===l.VVType.SIZE){var c=r.getTypeOfSizeVisualVariable(a);return c===l.WGLVVFlag.SIZE_SCALE_STOPS?
null:this._createNormalizedFunction(a,b,c===l.WGLVVFlag.SIZE_UNIT_VALUE&&function(b){return r.getVisualVariableSizeValueRepresentationRatio(b,a.valueRepresentation)})}return this._createNormalizedFunction(a,b)};b.prototype._createNormalizedFunction=function(a,b,c){var d=a.field;if(d){if("string"===typeof d){var e=a.normalizationField;return e?function(a){if(a.attributes[d]&&a.attributes[e])return a=a.attributes[d]/a.attributes[e],c?c(a):a}:c?function(a){return c(a.attributes[d])}:function(a){return a.attributes[d]}}if("function"===
typeof d)return f.error(new q("mapview-rendering:unsupported-feature","Function field types are not currently supported. Please use a valueExpression instead")),function(a){};f.error(new q("mapview-rendering:invalid-type","The field for a vv must be a string or a number, but got "+typeof d));return function(a){}}if(a.valueExpression)return m.createArcadeFunction(a.valueExpression,b,c);f.error("Unable to create a normalized function for visual variable: "+a);return function(a){}};return b}();g.default=
p});