import sys
import json
import urllib,httplib
import getpass
import codecs
import os
import arcpy
import time

mypath=r"C:\Users\LinZiXiang\Desktop\OGC\mxd\nyc_roads.mxd"

mxd = arcpy.mapping.MapDocument(mypath)

df = arcpy.mapping.ListDataFrames(mxd, "Layers")[0]

theShape = r"C:\Users\LinZiXiang\Desktop\OGC\nyc_roads.shp"

addLayer = arcpy.mapping.Layer(theShape)

arcpy.mapping.AddLayer(df, addLayer, "AUTO_ARRANGE")

arcpy.RefreshActiveView()

arcpy.RefreshTOC()

mxd.save()

x=os.startfile(mypath)

print mxd

print b
