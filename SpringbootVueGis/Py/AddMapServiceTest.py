import sys
import json
import urllib,httplib
import getpass
import codecs
import os
import arcpy
import time

#def AddService(argv):
def AddService(host, Port, username, password, workspace, mxdFileName, serviceName):
    """
    host = argv[0]
    Port = argv[1]
    username = argv[2]
    password = argv[3]
    workspace = argv[4]
    mxdFileName = argv[5]
    serviceName = argv[6]
    """  
    # Get a token and connect
    token = getToken(username, password, host, Port)

    if token == "":
        print "Cannot get token"
        sys.exit(1)

    # Create a connection file to the server            
    serverURL="http://"+host+":"+str(Port)+"/arcgis/admin"
    print "Creating ags file..."
    try:
        arcpy.mapping.CreateGISServerConnectionFile("PUBLISH_GIS_SERVICES",workspace+'/',host+".ags",serverURL,"ARCGIS_SERVER",username=username,password=password)
    except Exception, e:
            return e.message   
    
    agscon = os.path.join(workspace+'/', host+".ags")
    if not os.path.isfile(agscon):
        print ("Unable to connect to ArcGIS Server -- exiting")
        sys.exit(1)

    con = workspace+'/'+host+'.ags'
    
    mapDoc = arcpy.mapping.MapDocument(workspace+'/'+mxdFileName);
    sddraft = workspace +  serviceName + '.sddraft'
    timeStr =  time.strftime('%Y_%m_%d_%H_%M_%S',time.localtime(time.time()))
    sd = workspace + serviceName + timeStr + '.sd'
    summary = 'General reference map of the '+mxdFileName
    tags = serviceName
    
    # Create service definition draft
    print "Creating sddraft file..."
    arcpy.mapping.CreateMapSDDraft(mapDoc, sddraft, serviceName,'ARCGIS_SERVER', con, True, None, summary, tags)

    # Analyze the service definition draft
    print "Analyzing..."
    analysis = arcpy.mapping.AnalyzeForSD(sddraft)


    # Stage and upload the service if the sddraft analysis did not contain errors
    if analysis['errors'] == {}:
        # Execute StageService. This creates the service definition.
        print "Creating sd file..."
        arcpy.StageService_server(sddraft, sd)

        # Execute UploadServiceDefinition. This uploads the service definition and publishes the service.
        print "Uploading..."
        arcpy.UploadServiceDefinition_server(sd, con)
        print "Service successfully published"
    else: 
        print "Service could not be published because errors were found during analysis."

    return arcpy.GetMessages()

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
    #sys.exit(AddService(sys.argv[1:]))
    sys.exit(AddService("localhost",6080,"xj199608","x19960817j","E:\\RSplatform\\arcgis","blank11.mxd","blankTest"))
