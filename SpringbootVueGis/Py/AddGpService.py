import sys
import arcpy
import os
import httplib, urllib, json
import time

def AddGp(argv):
    # Get a token and connect
    username = argv[0]
    password = argv[1]
    host = argv[2]
    Port = argv[3]
    workspace = argv[4]
    serviceName = argv[5]
    inp = argv[6]
    arcpy.env.workspace = argv[7]
    dis = argv[8]
     
    token = getToken(username, password, host, Port)
    if token == "":
        print "Cannot get token"
        sys.exit(1)

    serverURL="http://"+host+":"+str(Port)+"/arcgis/admin"
    try:
        arcpy.mapping.CreateGISServerConnectionFile("PUBLISH_GIS_SERVICES",workspace+'/',host+".ags",serverURL,"ARCGIS_SERVER",username=username,password=password)
    except Exception, e:
        print e.message
        return e.message

    agscon = os.path.join(workspace+'/', host+".ags")
    if not os.path.isfile(agscon):
        print "Unable to connect to ArcGIS Server -- exiting"
        return ("Unable to connect to ArcGIS Server -- exiting")
        sys.exit(1)
        
    connPath = workspace+'/'+host+'.ags'
    sddraft = workspace +  serviceName + '.sddraft'
    timeStr =  time.strftime('%Y_%m_%d_%H_%M_%S',time.localtime(time.time()))
    sd = workspace + serviceName + timeStr + '.sd'
    res = workspace+"/" + serviceName + timeStr+".shp"
    #res = workspace+"/" + serviceName +".shp"
     #connPath = "D:/ArcSerTest/Server/Usa/localhost.ags"
     #sddraft = "D:/ArcSerTest/ExtractionDraft.sddraft"
     #sd = "D:/ArcSerTest/AnalysisDraft.sd"
     #serviceName = "DataExtractor"
     #arcpy.env.workspace = "D:/ArcSerTest/Server/Usa/USA.gdb"

     # create layers which will be available as input
     #arcpy.MakeFeatureLayer_management('USA.gdb/Cities', 'pt')

     # run the extract data task and assign it to the 'result' variable
     # only the cityhall layer was used as input, but the airport and firestation layers will be used in the service creation


    print "Try buffering..."
     #result = arcpy.Buffer_analysis(inp, res, "10 kilometers");
    result = arcpy.Buffer_analysis(inp, res, dis)
     #arcpy.ExtractDataTask_server("Counties", aoi, "File Geodatabase - GDB - .gdb", "ESRI GRID - GRID", os.path.join(arcpy.env.scratchFolder, "output.zip"))

     # make sure the folder is registered with the server, if not, add it to the datastore
    if arcpy.env.workspace not in [i[2] for i in arcpy.ListDataStoreItems(connPath, 'FOLDER')]:
          # both the client and server paths are the same
        dsStatus = arcpy.AddDataStoreItem(connPath, "FOLDER", "CityData", arcpy.env.workspace, arcpy.env.workspace)
        print "Data store : " + str(dsStatus)


     # create service definition draft
    print "Creating sddraft file..." 
    arcpy.CreateGPSDDraft(
        result, sddraft, serviceName, server_type="ARCGIS_SERVER",
        connection_file_path=connPath, copy_data_to_server=False, 
        folder_name=None, summary="Buffer", tags="Buffer data")
   

     # analyze the service definition draft
    print "Analyzing..."
    analyzeMessages = arcpy.mapping.AnalyzeForSD(sddraft)

     # stage and upload the service if the sddraft analysis did not contain errors
    if analyzeMessages['errors'] == {}:
         # Execute StageService
        print "Creating sd file..."
        arcpy.StageService_server(sddraft, sd)
         # Execute UploadServiceDefinition
        print "Uploading..."
        upStatus = arcpy.UploadServiceDefinition_server(sd, connPath)
        print "Completed upload"
    else: 
         # if the sddraft analysis contained errors, display them
        print analysis['errors']
         
# A function that checks that the JSON response received from the server does not contain an error   
def assertJsonSuccess(data):
    obj = json.loads(data)
    if 'status' in obj and obj['status'] == "error":
        return False
    else:
        return True

def getToken(username, password, serverName, serverPort):

    tokenURL = "/arcgis/admin/generateToken"
    params = urllib.urlencode({'username': username, 'password': password,'client': 'requestip', 'f': 'json'})
    
    response, data = postToServer(serverName, serverPort, tokenURL, params)
        
    if (response.status != 200 or not assertJsonSuccess(data)):
        print "Error while fetching tokens from admin URL. Please check if the server is running and ensure that the username/password provided are correct"
        print str(data)
        return
    else: 
        # Extract the token from it
        token = json.loads(data)   
        return token['token']
    
# A function that will post HTTP POST request to the server
def postToServer(serverName, serverPort, url, params):
    
    httpConn = httplib.HTTPConnection(serverName, serverPort)
    headers = {"Content-type": "application/x-www-form-urlencoded", "Accept": "text/plain"}

    # URL encode the resource URL
    url = urllib.quote(url.encode('utf-8'))
    
    # Build the connection to add the roles to the server
    httpConn.request("POST", url, params, headers)

    response = httpConn.getresponse()
    data = response.read()
    httpConn.close()

    return (response, data)

# Script start 
if __name__ == "__main__":
    sys.exit(AddGp(sys.argv[1:]))
