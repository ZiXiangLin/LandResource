# It is recommended that you set the default mosaic dataset properly before
# publishing. A connection to ArcGIS Server must be established in the
# Catalog window of ArcMap before running this script

import arcpy
import os
import sys
import time
# Define local variables:

# The folder for service definition draft and service definition files

MyWorkspace = r"C:\Users\LinZiXiang\Desktop\SpringbootVueGis\ArcPyPublishing"
Name = sys.argv[1]
InputData = sys.argv[2]
Sddraft = os.path.join(MyWorkspace, Name + ".sddraft")
Sd = os.path.join(MyWorkspace, Name + ".sd")
con = os.path.join(MyWorkspace, "arcgis on localhost_6080 (admin).ags")

# Create service definition draft
print("Start Create Service Definition Draft")
try:
    print("Creating SD draft")
    arcpy.CreateImageSDDraft(InputData, Sddraft, Name, 'ARCGIS_SERVER', con, 
                             False, "WCS", "WCS",
                             "WCS, image service")
except Exception as err:
    print(str(err))
    sys.exit("Failed to create SD draft")

print("Start Analyze the service")
# Analyze the service definition draft
analysis = arcpy.mapping.AnalyzeForSD(Sddraft)
print("The following was returned during analysis of the image service:")
for key in analysis.keys():

    print("---{}---".format(key.upper()))

    for ((message, code), layerlist) in analysis[key].iteritems():
        print("    {} (CODE {})".format(message, code))
        print("       applies to: {}".format(
            " ".join([layer.name for layer in layerlist])))

print("Start stage and upload the service")
# Stage and upload the service if the sddraft analysis did not contain errors
if analysis['errors'] == {}:
    try:
        print("Adding data path to data store to avoid copying data to server")
        arcpy.AddDataStoreItem(con, "FOLDER", "Images", MyWorkspace,
                               MyWorkspace)

        print "Staging service to create service definition"
        arcpy.StageService_server(Sddraft, Sd)

        print "Uploading the service definition and publishing image service"
        arcpy.UploadServiceDefinition_server(Sd, con)

        print "Service successfully published"
    except arcpy.ExecuteError:
       # print(arcpy.GetMessages() + "\n\n")
       # sys.exit("Failed to stage and upload service")
	   print("")

    except Exception as err:
		print("")
        # print(err[0] + "\n\n")
        #sys.exit("Failed to stage and upload service")
else:
    print("Service was not published because of errors found during analysis.")
    print(analysis['errors'])