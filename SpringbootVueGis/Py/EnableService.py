# Demonstrates how to modify the min and max instances for a service

# For Http calls
import httplib, urllib, json

# For system tools
import sys

# For reading passwords without echoing
import getpass


# Defines the entry point into the script
def main(argv):

    # Ask for admin/publisher user name and password
    #username = raw_input("Enter user name: ")
    username = argv[0]
    #password = getpass.getpass("Enter password: ")
    password = argv[1]
    
    # Ask for server name
    #serverName = raw_input("Enter Server name: ")
    serverName = argv[2]
    serverPort = argv[3]

    print r"Enter the service name in the format <folder>/<name>.<type>."
    #service = raw_input(r"For example USA/Chicago.MapServer: ")
    service = argv[4]

    serviceType = argv[5]

    ogcType = argv[6]

    
    
    # Get a token
    token = getToken(username, password, serverName, serverPort)
    if token == "":
        print "Could not generate a token with the username and password provided."    
        return
    
    serviceURL = "/arcgis/admin/services/" + service + "." + serviceType
    
    # This request only needs the token and the response formatting parameter 
    params = urllib.urlencode({'token': token, 'f': 'json'})
    
    headers = {"Content-type": "application/x-www-form-urlencoded", "Accept": "text/plain"}
    
    # Connect to service to get its current JSON definition    
    httpConn = httplib.HTTPConnection(serverName, serverPort)
    print "Try to get props..."
    httpConn.request("POST", serviceURL, params, headers)
    
    # Read response
    response = httpConn.getresponse()
    if (response.status != 200):
        httpConn.close()
        print "Could not read service information."
        return
    else:
        data = response.read()
        
        # Check that data returned is not an error object
        if not assertJsonSuccess(data):          
            print "Error when reading service information. " + str(data)
        else:
            print "Service information read successfully. Now changing properties..."

        # Deserialize response into Python object
        #print data
        dataObj = json.loads(data)

        
        httpConn.close()


        if ogcType == "WFS":
            sertype = "WFSServer"
        if ogcType == "WMS":
            sertype = "WMSServer"
        if ogcType == "WCS":
            sertype = "WCSServer"
        if ogcType == "WPS":
            sertype = "WPSServer"
        
        # Edit desired properties of the service
        
        for obj in dataObj["extensions"]:
            if obj["typeName"] == sertype:
                obj["enabled"] = "true"

        # Serialize back into JSON
        updatedSvcJson = json.dumps(dataObj)

        # Call the edit operation on the service. Pass in modified JSON.
        editSvcURL = "/arcgis/admin/services/" + service+"."+serviceType + "/edit"
        params = urllib.urlencode({'token': token, 'f': 'json', 'service': updatedSvcJson})
        print "Uploading new props..."
        httpConn.request("POST", editSvcURL, params, headers)
        
        # Read service edit response
        editResponse = httpConn.getresponse()
        if (editResponse.status != 200):
            httpConn.close()
            print "Error while executing edit."
            return
        else:
            editData = editResponse.read()
            
            # Check that data returned is not an error object
            if not assertJsonSuccess(editData):
                print "Error returned while editing service" + str(editData)        
            else:
                print "Service edited successfully."

        httpConn.close()  

        return

# A function to generate a token given username, password and the adminURL.

def getToken(username, password, serverName, serverPort):
    # Token URL is typically http://server[:port]/arcgis/admin/generateToken
    tokenURL = "/arcgis/admin/generateToken"
    
    params = urllib.urlencode({'username': username, 'password': password, 'client': 'requestip', 'f': 'json'})
    
    headers = {"Content-type": "application/x-www-form-urlencoded", "Accept": "text/plain"}
    
    # Connect to URL and post parameters
    httpConn = httplib.HTTPConnection(serverName, serverPort)
    httpConn.request("POST", tokenURL, params, headers)
    
    # Read response
    response = httpConn.getresponse()
    if (response.status != 200):
        httpConn.close()
        print "Error while fetching tokens from admin URL. Please check the URL and try again."
        return
    else:
        data = response.read()
        httpConn.close()
        
        # Check that data returned is not an error object
        if not assertJsonSuccess(data):            
            return
        
        # Extract the token from it
        token = json.loads(data)        
        return token['token']            
        

# A function that checks that the input JSON object 
#  is not an error object.
    
def assertJsonSuccess(data):
    obj = json.loads(data)
    if 'status' in obj and obj['status'] == "error":
        print "Error: JSON object returns an error. " + str(obj)
        return False
    else:
        return True
    
        
# Script start 
if __name__ == "__main__":
    sys.exit(main(sys.argv[1:]))
