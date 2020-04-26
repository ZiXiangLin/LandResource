package com.gis.demo.tools;


public class GreaterThan {
	
	public static String run(String href, String threshold,String greaterthanurl){
		String requestXML = "<wps:DataInputs>"
	      		+ "<wps:Input>"
	      		+ "<ows:Identifier>InputRaster</ows:Identifier>"
	      		+ "<ows:Title>InputRaster</ows:Title>"
	      		//+ "<wps:Reference xlink:href=\"http://localhost:6080/arcgis/services/dem/ImageServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;Coverage=dem&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBox=-105.46238150000001,39.882790499999999,-104.8276355,40.407580500000002&amp;width=761&amp;height=629\"/>"
	      		+ "<wps:Reference xlink:href=\""
	      		+ href 
	      		+ "\"/>"
	      		+ "</wps:Input>"
	      		+ "<wps:Input>"
        		+ "<ows:Identifier>InputConstantValue</ows:Identifier>"
        		+ "<ows:Title>InputConstantValue</ows:Title>"
        		+ "<wps:Data>"
          		+ "<wps:LiteralData>"
        		+threshold
          		+ "</wps:LiteralData>"
          		+ "</wps:Data>"
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
		      	+ "<ows:Identifier>OutputRaster</ows:Identifier>"
		      	+ "<ows:Title>OutputRaster</ows:Title>"
		      	+ "</wps:Output>"
		      	+ "</wps:ResponseDocument>"
		      	+ "</wps:ResponseForm>";
	       //String reString= WPS.execute("http://192.168.1.107:6080/arcgis/services/Tools/GreaterThan/GPServer/WPSServer", "1.0.0", "GreaterThan", requestXML);
	       //System.out.println(reString);
	       String reString= WPS.execute(greaterthanurl, "1.0.0", StringUtils.findwpsname(greaterthanurl), requestXML);
	       
	       return StringUtils.findResultURL(reString);
	}

}
