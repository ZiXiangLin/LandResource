//>>built
define(["dojo/_base/array","dojo/_base/lang","dojo/Stateful","./StatefulArray"],function(e,b,f,g){var d=function(a,c){return(c||d)["getStateful"+(c||d).getType(a).replace(/^[a-z]/,function(a){return a.toUpperCase()})](a)};return b.setObject("dojox.mvc.getStateful",b.mixin(d,{getType:function(a){return b.isArray(a)?"array":null!=a&&"[object Object]"=={}.toString.call(a)?"object":"value"},getStatefulArray:function(a){return new g(e.map(a,function(a){return d(a,this)},this))},getStatefulObject:function(a){var c=
new f,b;for(b in a)c[b]=d(a[b],this);return c},getStatefulValue:function(a){return a}}))});