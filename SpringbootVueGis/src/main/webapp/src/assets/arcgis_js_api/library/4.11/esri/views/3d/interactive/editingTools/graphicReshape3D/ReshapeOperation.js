// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See https://js.arcgis.com/4.11/esri/copyright.txt for details.
//>>built
define("require exports ../../../../../core/tsSupport/decorateHelper ../../../../../core/tsSupport/declareExtendsHelper ../../../../../core/Accessor ../../../../../core/Evented ../../../../../core/Handles ../../../../../core/accessorSupport/decorators ../../../../../layers/graphics/dehydratedFeatures ./ReshapeMoveManipulator ./reshapeUtils ../../../webgl-engine/lib/Geometry ../../../webgl-engine/lib/GeometryUtil ../../../../interactive/Manipulator3D ../../../../interactive/manipulatorUtils".split(" "),
function(t,f,k,x,y,z,A,g,B,C,D,E,F,G,q){Object.defineProperty(f,"__esModule",{value:!0});var n=function(){return function(e,d){void 0===e&&(e=!1);void 0===d&&(d=!1);this.isDragging=e;this.fromTranslation=d;this.graphic=null;this.type="reshape-start"}}();f.ReshapeStartEvent=n;var r=function(){return function(e){this.coords=e;this.graphic=null;this.type="vertex-move"}}();f.VertexMoveEvent=r;var u=function(){return function(e,d){this.dxScreen=e;this.dyScreen=d;this.graphic=null;this.type="translate"}}();
f.TranslateEvent=u;var v=function(){return function(e){this.coords=e;this.graphic=null;this.type="vertex-add"}}();f.VertexAddEvent=v;var w=function(){return function(e){this.coords=e;this.graphic=null;this.type="vertex-remove"}}();f.VertexRemoveEvent=w;var p=function(){return function(e,d){void 0===e&&(e=!1);void 0===d&&(d=!1);this.isDragging=e;this.fromTranslation=d;this.graphic=null;this.type="reshape-stop"}}();f.ReshapeStopEvent=p;t=function(e){function d(a){a=e.call(this,a)||this;a._vertexManipulatorMaterial=
q.createManipulatorMaterial([1,.5,0],1);a._edgeManipulatorMaterial=q.createManipulatorMaterial([.5,.5,.5],1);a._selectedManipulatorMaterial=q.createManipulatorMaterial([1,1,1],1);a._manipulatorGeometry=new E(F.createSphereGeometry(1,16,16),"reshape-manipulator");a._handles=new A;a._manipulators=[];a._reshapeHelper=null;a._moveManipulator=null;a._numDragging=0;return a}x(d,e);d.prototype.destroy=function(){this._clear(null)};Object.defineProperty(d.prototype,"inputGeometry",{get:function(){return this._reshapeHelper?
this._reshapeHelper.geometry:null},set:function(a){this._recreateManipulators(this.toolViewManager,a)},enumerable:!0,configurable:!0});d.prototype.removeSelectedVertices=function(){var a=this._manipulators.filter(function(a){return a.manipulator.selected&&"vertex"===a.handle.type});this._removeVertices(a)};d.prototype._clear=function(a){this._handles.removeAll();a&&(this._manipulators.forEach(function(c){a.removeManipulator(c.manipulator)}),a.removeManipulator(this._moveManipulator));this._manipulators=
[];this._reshapeHelper=this._moveManipulator=null;this._numDragging=0};d.prototype._recreateManipulators=function(a,c){var b=this;this._clear(this.toolViewManager);this._reshapeHelper=D.createReshapeHelper(c,"global"===this.view.viewingMode);if(!this._reshapeHelper)return null;this._reshapeHelper.components.forEach(function(a){a.vertices.forEach(function(a){return b._createManipulator(a)});a.edges.forEach(function(a){return b._createManipulator(a)})});this._moveManipulator=new C.ReshapeMoveManipulator({view:this.view,
reshapeHelper:this._reshapeHelper,selectable:!1});this._handles.add(this._moveManipulator.watch("grabbing",function(a){b._manipulators.forEach(function(b){b.manipulator.interactive=!a})},!0),this._moveManipulator);this._handles.add(this._moveManipulator.watch("dragging",function(a){!0===a?(++b._numDragging,1===b._numDragging&&b.emit("reshape-operation-start",new n(!0,!0))):(--b._numDragging,0===b._numDragging&&b.emit("reshape-operation-stop",new p(!0,!0)))},!0),this._moveManipulator);this._handles.add(this._moveManipulator.watch("hovering",
function(a){b.cursor=a?"move":null},!0),this._moveManipulator);this._moveManipulator.on("drag",function(a){return b._moveManipulatorDragCallback(a)});this._moveManipulator.on("click",function(){return b.emit("click")});this.toolViewManager.addManipulator(this._moveManipulator)};d.prototype._clearManipulatorSelection=function(){this._manipulators.forEach(function(a){return a.manipulator.selected=!1})};d.prototype._createManipulator=function(a){var c=this,b=new G.Manipulator3D({view:this.view,renderObjects:[{geometry:this._manipulatorGeometry,
material:this._vertexManipulatorMaterial,stateMask:h.Vertex|4},{geometry:this._manipulatorGeometry,material:this._edgeManipulatorMaterial,stateMask:h.Edge|4},{geometry:this._manipulatorGeometry,material:this._selectedManipulatorMaterial,stateMask:8}],radius:5});b.alignment="on-the-ground";b.allowOverlap=!0;"vertex"===a.type?(b.state=h.Vertex,b.selectable=!0):(b.state=h.Edge,b.selectable=!1);var d={manipulator:b,handle:a};this._manipulators.push(d);this.toolViewManager.addManipulator(b);this._setManipulatorPosition(d);
this._handles.add(b.watch("grabbing",function(a){c._moveManipulator.interactive=!a},!0),b);this._handles.add(b.watch("dragging",function(a){!0===a?(++c._numDragging,1===c._numDragging&&c.emit("reshape-operation-start",new n(!0))):(--c._numDragging,0===c._numDragging&&c.emit("reshape-operation-stop",new p(!0)))},!0),b);this._handles.add(b.watch("hovering",function(a){c.cursor=a?"vertex"===d.handle.type?"move":"copy":null},!0),b);b.on("drag",function(){return c._manipulatorMoveCallback(d)});b.on("click",
function(a){return c._manipulatorClickCallback(a,d)});return b};d.prototype._removeManipulator=function(a){a&&(this._handles.remove(a.manipulator),this._manipulators.splice(this._manipulators.indexOf(a),1),this.toolViewManager.removeManipulator(a.manipulator))};d.prototype._getManipulatorInfoFromHandle=function(a){if(a)for(var c=0,b=this._manipulators;c<b.length;c++){var d=b[c];if(a===d.handle)return d}return null};d.prototype._setManipulatorPosition=function(a){a&&("vertex"===a.handle.type?a.manipulator.mapPoint=
this._reshapeHelper.getVertexPositionAsPoint(a.handle,l):"edge"===a.handle.type&&(a.manipulator.mapPoint=this._reshapeHelper.getEdgePositionAsPoint(a.handle,.5,l)))};d.prototype._splitEdgeManipulator=function(a){if("edge"===a.handle.type){var c=this._reshapeHelper.splitEdge(a.handle,.5);a.handle=c;a.manipulator.state=h.Vertex;a.manipulator.selectable=!0;c.left&&this.toolViewManager&&this._createManipulator(c.left);c.right&&this.toolViewManager&&this._createManipulator(c.right);return c}return null};
d.prototype._manipulatorMoveCallback=function(a){var c=this;"edge"===a.handle.type&&this._splitEdgeManipulator(a);if(a.handle&&"vertex"===a.handle.type){!1===a.manipulator.selected&&(this._clearManipulatorSelection(),a.manipulator.selected=!0);var b=a.handle.pos,d=a.manipulator.mapPoint.x-b[0],e=a.manipulator.mapPoint.y-b[1],b=this._manipulators.filter(function(a){return a.manipulator.selected&&"vertex"===a.handle.type});b.forEach(function(b){var m=b.handle;m.pos[0]+=d;m.pos[1]+=e;a!==b&&(l.x=m.pos[0],
l.y=m.pos[1],l.spatialReference=c._reshapeHelper.geometry.spatialReference,b.manipulator.mapPoint=l)});this.outputGeometry=this._reshapeHelper.commit();b.forEach(function(a){a=a.handle;c._setManipulatorPosition(c._getManipulatorInfoFromHandle(a.left));c._setManipulatorPosition(c._getManipulatorInfoFromHandle(a.right))});b=b.map(function(a){return a.handle.unnormalizedPos});b=new r(b);this.emit("vertex-move",b)}};d.prototype._removeVertices=function(a){var c=this,b=[];a.forEach(function(a){"vertex"===
a.handle.type&&c._reshapeHelper.canRemoveVertex(a.handle)&&(0===b.length&&c.emit("reshape-operation-start",new n),b.push(a.handle.unnormalizedPos),c._removeManipulator(a),c._removeManipulator(c._getManipulatorInfoFromHandle(a.handle.left)),c._removeManipulator(c._getManipulatorInfoFromHandle(a.handle.right)),(a=c._reshapeHelper.removeVertex(a.handle))&&c._createManipulator(a))});0<b.length&&(this.outputGeometry=this._reshapeHelper.commit(),a=new w(b),this.emit("vertex-remove",a),this.emit("reshape-operation-stop",
new p))};d.prototype._manipulatorClickCallback=function(a,c){"vertex"===c.handle.type&&2===a.button&&this._removeVertices([c]);"edge"===c.handle.type&&0===a.button&&(this.emit("reshape-operation-start",new n),a=this._splitEdgeManipulator(c),this.outputGeometry=this._reshapeHelper.commit(),a=new v([a.unnormalizedPos]),this.emit("vertex-add",a),this.emit("reshape-operation-stop",new p))};d.prototype._moveManipulatorDragCallback=function(a){var c=this,b=[],d=!0;this._manipulators.forEach(function(e){"vertex"===
e.handle.type&&(e.manipulator.grabbing?d=!1:"vertex"===e.handle.type&&(e.handle.pos[0]+=a.dxGeometry,e.handle.pos[1]+=a.dyGeometry,b.push(e.handle.pos),c._setManipulatorPosition(e)))});this._manipulators.forEach(function(a){"vertex"!==a.handle.type&&c._setManipulatorPosition(a)});this.outputGeometry=this._reshapeHelper.commit();if(d)this.emit("translate",new u(a.dxScreen,a.dyScreen));else{var e=new r(b);this.emit("vertex-move",e)}};k([g.property({value:null})],d.prototype,"toolViewManager",void 0);
k([g.property({value:null,nonNullable:!0})],d.prototype,"view",void 0);k([g.property({value:null})],d.prototype,"inputGeometry",null);k([g.property({value:null})],d.prototype,"cursor",void 0);k([g.property({value:null})],d.prototype,"outputGeometry",void 0);return d=k([g.subclass("esri.views.3d.interactive.editingTools.graphicReshape3D.ReshapeOperation")],d)}(g.declared(y,z));f.ReshapeOperation=t;var l=B.makeDehydratedPoint(0,0,null,null),h;(function(e){e.Vertex=16;e.Edge=32})(h||(h={}))});