// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.11/esri/copyright.txt for details.
//>>built
define(["require","exports"],function(k,g){Object.defineProperty(g,"__esModule",{value:!0});var h=function(){function d(c){void 0===c&&(c={});this._options=c}d.prototype.toQueryParams=function(c){var d=this;if(!c)return null;var e=c.toJSON(),f={};Object.keys(e).forEach(function(a){var b=d._options[a];if(b){var c="boolean"!==typeof b&&b.name?b.name:a;a="boolean"!==typeof b&&b.getter?b.getter(e):e[a];null!=a&&(Array.isArray(a)?(b=a[0],b="number"===typeof b||"string"===typeof b):b=!1,f[c]=b?a.join(","):
"object"===typeof a?JSON.stringify(a):a)}else f[a]=e[a]},this);return f};return d}();g.createQueryParamsHelper=function(d){return new h(d)}});