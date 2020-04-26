// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.11/esri/copyright.txt for details.
//>>built
define("require exports ../../../core/tsSupport/declareExtendsHelper ../../../core/tsSupport/decorateHelper ../../../core/Error ../../../core/promiseUtils ../../../core/accessorSupport/decorators ../engine/BitmapContainer ../engine/BitmapTile ./LayerView2D ./support/popupUtils2D ../tiling/TileInfoView ../tiling/TileKey ../tiling/TileQueue ../tiling/TileStrategy ../../layers/TileLayerView".split(" "),function(C,D,q,m,n,r,f,t,p,u,v,w,x,y,z,A){var B=[0,0];return function(g){function c(){var a=null!==
g&&g.apply(this,arguments)||this;a._tileStrategy=null;a._tileInfoView=null;a._fetchQueue=null;a._tileRequests=new Map;a.container=new t.BitmapContainer;a.layer=null;return a}q(c,g);c.prototype.initialize=function(){var a=this,b=this.layer.tileInfo,b=b&&b.spatialReference,c;b||(c=new n("layerview:tiling-information-missing","The layer doesn't provide tiling information",{layer:this.layer}));b.equals(this.view.spatialReference)||(c=new n("layerview:spatial-reference-incompatible","The spatial reference of this layer does not meet the requirements of the view",
{layer:this.layer}));this.watch("resampling",function(b){a.refresh()});c&&this.addResolvingPromise(r.reject(c))};Object.defineProperty(c.prototype,"resampling",{get:function(){return!("resampling"in this.layer)||!1!==this.layer.resampling},enumerable:!0,configurable:!0});c.prototype.hitTest=function(a,b){return null};c.prototype.update=function(a){this._fetchQueue.pause();this._fetchQueue.state=a.state;this._tileStrategy.update(a);this._fetchQueue.resume();this.notifyChange("updating")};c.prototype.attach=
function(){var a=this;this._tileInfoView=new w(this.layer.tileInfo,this.layer.fullExtent);this._fetchQueue=new y({tileInfoView:this._tileInfoView,tileServers:"tileServers"in this.layer?this.layer.tileServers:null,concurrency:this.layer.url&&-1!==this.layer.url.indexOf("tiles.arcgis.com")?12:6,process:function(b,c){return a.fetchTile(b,c)}});this._tileStrategy=new z({cachePolicy:"keep",resampling:this.resampling,acquireTile:function(b){return a.acquireTile(b)},releaseTile:function(b){return a.releaseTile(b)},
tileInfoView:this._tileInfoView})};c.prototype.detach=function(){this._tileStrategy.destroy();this._fetchQueue.clear();this.container.removeAllChildren();this._fetchQueue=this._tileStrategy=this._tileInfoView=null};c.prototype.moveStart=function(){this.requestUpdate()};c.prototype.viewChange=function(){this.requestUpdate()};c.prototype.moveEnd=function(){this.requestUpdate()};c.prototype.createFetchPopupFeaturesQueryGeometry=function(a,b){return v.createQueryGeometry(a,b,this.view)};c.prototype.doRefresh=
function(){var a=this;this.updateRequested||this.suspended||(this._fetchQueue.reset(),this._tileStrategy.tiles.forEach(function(b){return a._enqueueTileFetch(b)}),this.notifyChange("updating"))};c.prototype.isUpdating=function(){var a=!0;this._tileRequests.forEach(function(b){a=a&&b.isFulfilled()});return!a};c.prototype.getTileBounds=function(a,b){return this._tileInfoView.getTileBounds(a,b)};c.prototype.getTileCoords=function(a,b){return this._tileInfoView.getTileCoords(a,b)};c.prototype.getTileResolution=
function(a){return this._tileInfoView.getTileResolution(a)};c.prototype.acquireTile=function(a){var b=p.BitmapTile.pool.acquire();b.key.set(a);a=this._tileInfoView.getTileCoords(B,b.key);b.x=a[0];b.y=a[1];b.resolution=this._tileInfoView.getTileResolution(b.key);a=this._tileInfoView.tileInfo.size;b.width=a[0];b.height=a[1];this._enqueueTileFetch(b);this.requestUpdate();return b};c.prototype.releaseTile=function(a){var b=this,c=this._tileRequests.get(a);c&&!c.isFulfilled()&&c.cancel();this._tileRequests.delete(a);
this.container.removeChild(a);a.once("detach",function(){p.BitmapTile.pool.release(a);b.requestUpdate()});this.requestUpdate()};c.prototype.fetchTile=function(a,b){var c=this;b="tilemapCache"in this.layer?this.layer.tilemapCache:null;if(!b)return this._fetchImage(a).catch(function(a){if(!c.resampling)return c._createBlankImage();throw a;});var d=x.pool.acquire();return b.fetchAvailabilityUpsample(a.level,a.row,a.col,d).then(function(){return d.level===a.level||c.resampling?c._fetchImage(d):c._createBlankImage()}).catch(function(b){if("AbortError"===
b.name)throw b;return c._fetchImage(a)}).then(function(b){return c.resampling&&d.level!==a.level?c._resampleImage(b,d.level,d.row,d.col,a.level,a.row,a.col):b})};c.prototype._enqueueTileFetch=function(a){var b=this;if(!this._fetchQueue.has(a.key)){var c=this._fetchQueue.push(a.key).then(function(c){a.source=c;a.width=b._tileInfoView.tileInfo.size[0];a.height=b._tileInfoView.tileInfo.size[1];a.once("attach",function(){return b.requestUpdate()});b.container.addChild(a);b.requestUpdate()});this._tileRequests.set(a,
c)}};c.prototype._fetchImage=function(a){return this.layer.fetchTile(a.level,a.row,a.col,{timestamp:this.refreshTimestamp})};c.prototype._resampleImage=function(a,b,c,d,e,f,g){var l=this._tileInfoView.tileInfo.size,h=this._tileInfoView.getTileResolution(b),k=this._tileInfoView.getTileResolution(e);e=this._tileInfoView.getLODInfoAt(e);g=e.getXForColumn(g);f=e.getYForRow(f);e=this._tileInfoView.getLODInfoAt(b);b=e.getXForColumn(d);d=e.getYForRow(c);c=Math.round((g-b)/h);b=Math.round(-(f-d)/h);d=Math.round(k/
h*l[0]);h=Math.round(k/h*l[1]);k=this._createBlankImage();k.getContext("2d").drawImage(a,c,b,d,h,0,0,l[0],l[1]);return k};c.prototype._createBlankImage=function(){var a=this._tileInfoView.tileInfo.size,b=document.createElement("canvas");b.width=a[0];b.height=a[1];return b};m([f.property({dependsOn:["layer.resampling?"]})],c.prototype,"resampling",null);return c=m([f.subclass("esri.views.2d.layers.TileLayerView2D")],c)}(f.declared(u,A))});