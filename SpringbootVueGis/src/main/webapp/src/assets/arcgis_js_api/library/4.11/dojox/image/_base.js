//>>built
define(["dojo","dojox"],function(a,b){a.getObject("image",!0,b);var c;b.image.preload=function(b){c||(c=a.create("div",{style:{position:"absolute",top:"-9999px",height:"1px",overflow:"hidden"}},a.body()));return a.map(b,function(b){return a.create("img",{src:b},c)})};a.config.preloadImages&&a.addOnLoad(function(){b.image.preload(a.config.preloadImages)})});