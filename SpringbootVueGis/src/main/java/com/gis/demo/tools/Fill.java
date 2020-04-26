package com.gis.demo.tools;


public class Fill {

	public static String run(String DEMhref,String fillurl){
		String requestXML = "<wps:DataInputs>"
	      		+ "<wps:Input>"
	      		+ "<ows:Identifier>InputDEM</ows:Identifier>"
	      		+ "<ows:Title>InputDEM</ows:Title>"
	      		//+ "<wps:Reference xlink:href=\"https://192.168.1.107:6443/arcgis/services/ASTGTM2_N30E114_dem/ImageServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=ASTGTM2_N30E114_dem&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBOX=114,30,114.5,30.5&amp;width=800&amp;height=800\"/>"
	      		+ "<wps:Reference xlink:href=\""
	      		+ DEMhref//"G:\\DEM\\demflt.tif"
	      		+ "?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=ASTGTM2_N30E114_dem&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBOX=114,30,114.5,30.5&amp;width=800&amp;height=800"
	      		+ "\"/>"
	      		+ "</wps:Input>"
	      		+ "<wps:Input>"
        		+ "<ows:Identifier>output_spatial_reference</ows:Identifier>"
        		+ "<ows:Title>output_spatial_reference</ows:Title>"
        		+ "<wps:Data>"
          		+ "<wps:LiteralData>\"urn:ogc:def:crs:EPSG::4326\"</wps:LiteralData>"
          		+ "</wps:Data>"
        		+ "</wps:Input>"
	      		+ "</wps:DataInputs>"
		      	+ "<wps:ResponseForm>"
		      	+ "<wps:ResponseDocument storeExecuteResponse=\"true\" lineage=\"true\" status=\"true\">"
		      	+ "<wps:Output asReference=\"true\">"
		      	+ "<ows:Identifier>Filled_DEM</ows:Identifier>"
		      	+ "<ows:Title>Filled_DEM</ows:Title>"
		      	+ "</wps:Output>"
		      	+ "</wps:ResponseDocument>"
		      	+ "</wps:ResponseForm>";
	     	//String reString= WPS.execute("http://192.168.1.107:6080/arcgis/services/Tools/Fill/GPServer/WPSServer", "1.0.0", "Fill", requestXML);
		//String reString= WPS.execute("http://192.168.1.102:6080/arcgis/services/StreamWPS/Fill/GPServer/WPSServer", "1.0.0", "Fill", requestXML);   
		String reString= WPS.execute(fillurl, "1.0.0", StringUtils.findwpsname(fillurl), requestXML);   
		//System.out.println(StringUtils.findResultURL(reString));
	       return StringUtils.findResultURL(reString);
	       
}
	
}