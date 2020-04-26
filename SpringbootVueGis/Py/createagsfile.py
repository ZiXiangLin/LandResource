agscon = os.path.join(workspace+'/', host+".ags")
    if not os.path.isfile(agscon):
        return ("Unable to connect to ArcGIS Server -- exiting")
        sys.exit(1)

	# Create a connection file to the server            
    serverURL="http://"+host+":"+str(Port)+"/arcgis/admin"
    print "Creating ags file..."
    try:
        arcpy.mapping.CreateGISServerConnectionFile("PUBLISH_GIS_SERVICES",workspace+'/connectionfile/',host+".ags",serverURL,"ARCGIS_SERVER",username=username,password=password)
    except Exception, e:
            return e.message   
    
    agscon = os.path.join(workspace+'/connectionfile/', host+".ags")
    if not os.path.isfile(agscon):
        return ("Unable to connect to ArcGIS Server -- exiting")
        sys.exit(1)