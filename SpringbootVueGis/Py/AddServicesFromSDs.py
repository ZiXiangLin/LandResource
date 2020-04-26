# Publishes all service definitions in an operating system directory
#  (excluding subfolders)
import arcpy, os
import sys
import urllib
import httplib
import json
# Define path to SDs
#wrkspc = "C:/data"
#sdDir = wrkspc + "/SDs"
def addServices(argv):
    host = argv[0]
    Port = argv[1]
    username = argv[2]
    password = argv[3]
    serverFolder = argv[4]
    
    workspace = sys.argv[5]
    sdDir = sys.argv[6]
    token = getToken(username, password, host, Port)

    if token == "":
        print "Cannot get token..."
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
        return ("Unable to connect to ArcGIS Server -- exiting")
        sys.exit(1)

    #con = 'D:/EclipseCode/GisAccessTool/localhost.ags'

    con = workspace+'/'+host+'.ags'
	# Provide path to connection file
    # To create this file, right-click a folder in the Catalog window and
    #  click New > ArcGIS Server Connection
    #con = wrkspc + "/connections/arcgis on myserver_6080 (publisher).ags"

    # Destination folder name on ArcGIS Server
    #serverFolder = "SdPublish"

    # Loop through all items in folder
    sdList = os.listdir(sdDir)

    print "Uploading..."
    for sd in sdList:
    
        # Construct path to item
        extension = os.path.splitext(sd)[1] #Get file extension
        sdPath = os.path.join(sdDir, sd)

        # Check if item is an SD file and, if so, try to publish
        if os.path.isfile(sdPath) and extension == ".sd":    
            try:     
                arcpy.UploadServiceDefinition_server(sdPath, con, "", "", "EXISTING", serverFolder)
                print "Published " + sd + " with no errors reported."
            
            except:
                print "Could not complete publishing operation for " + sd + "."

            print arcpy.GetMessages()
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
    sys.exit(addServices(sys.argv[1:]))

