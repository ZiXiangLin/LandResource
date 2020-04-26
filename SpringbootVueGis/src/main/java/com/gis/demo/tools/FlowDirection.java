package com.gis.demo.tools;


public class FlowDirection {

	public static String run(String href,String flowdirectionurl){
		String requestXML = "<wps:DataInputs>"
	      		+ "<wps:Input>"
	      		+ "<ows:Identifier>InputSurfaceRaster</ows:Identifier>"
	      		+ "<ows:Title>InputSurfaceRaster</ows:Title>"
	      		//+ "<wps:Reference xlink:href=\"http://localhost:6080/arcgis/services/dem/ImageServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;Coverage=dem&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBox=-105.46238150000001,39.882790499999999,-104.8276355,40.407580500000002&amp;width=761&amp;height=629\"/>"
	      		+ "<wps:Reference xlink:href=\""
	      		+ href 
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
		      	+ "<ows:Identifier>FlowDirectionRaster</ows:Identifier>"
		      	+ "<ows:Title>FlowDirectionRaster</ows:Title>"
		      	+ "</wps:Output>"
		      	+ "</wps:ResponseDocument>"
		      	+ "</wps:ResponseForm>";
	       //String reString= WPS.execute("http://192.168.1.107:6080/arcgis/services/Tools/FlowDirection/GPServer/WPSServer", "1.0.0", "FlowDirection", requestXML);
	       //System.out.println(reString);
	       String reString= WPS.execute(flowdirectionurl, "1.0.0", StringUtils.findwpsname(flowdirectionurl), requestXML);
	       
	       return StringUtils.findResultURL(reString);
	       }
}
