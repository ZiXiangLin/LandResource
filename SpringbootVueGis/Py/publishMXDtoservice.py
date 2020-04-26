# -*- coding: utf-8 -*-
import os as OS
import arcpy
import time
# createGISServerConnectionFile,define local variable  
"""
wrkpc = r"C:\Users\LinZiXiang\Desktop\OGC\tmp" #定义一个文件路径
con_Filename = r"E:\tmp\arcgis on pc-20160509aapl.server.com_6443 (publisher).ags"  
maxfilename = "balnk.mxd"
"""
"""#代码创建ags连接文件，但是url总是出错所以手动创建此文件
out_folder_path = wrkpc  
server_url = r"https://pc-20160509aapl.server.com:6443/arcgis/manager/#" #ArcGIS Server站点的URL https://pc-20160509aapl.server.com:6443/arcgis/ 
staging_folder_path = wrkpc  
username = "xj199608" #站点用户名  
password = "x19960817j" #密码  

arcpy.mapping.CreateGISServerConnectionFile("PUBLISH_GIS_SERVICES",  
                                            out_folder_path,  
                                            con_Filename,  
                                            server_url,  
                                            "ARCGIS_SERVER",  
                                            False,  
                                            staging_folder_path,  
                                            username,  
                                            password,  
                                            "SAVE_USERNAME")"""

def publishService(argv):
    #获得命令行参数,各参数均为字符串，用空格隔开E:\RSplatform\wjc\Py>python publishMXDtoservice.py "E:\tmp" "E:\tmp\myags.ags" "E:\tmp\20170921125721974map.mxd"
    wrkpc = argv[0]  #工作空间"E:\tmp"
    con_Filename = argv[1] #ags文件目录"E:\tmp\arcgis on pc-20160509aapl.server.com_6443 (publisher).ags"
    maxfilename = argv[2]  #mxd文件名createMXD.py执行后的结果

#def publishService(wrkpc,con_Filename,maxfilename):
    # define local variables  
    #mxdpath = OS.path.join(wrkpc,maxfilename)  #指定MXD所在的路径 	
    mapDoc = arcpy.mapping.MapDocument(maxfilename)  
    
    timeStr =  time.strftime('%Y%m%d%H%M%S',time.localtime(time.time()))
    #servicename = "GeoRiver"+ timeStr #发布后的服务名称
    servicename = argv[3]
    sddraft = OS.path.join(wrkpc,timeStr + "Test.sddraft") #指定.sddraft文件所在的路径  
    
    sd = OS.path.join(wrkpc,timeStr + "Test.sd")  
    connectionfile = con_Filename
    summary = "this is a test"  
    tags = "this is a test"  
      
    # creste service definition draft  
    analysis = arcpy.mapping.CreateMapSDDraft(mapDoc,  
                                              sddraft,  
                                              servicename,  
                                              "ARCGIS_SERVER",  
                                              connectionfile,  
                                              False,  
                                              "WFS",  
                                              summary,tags)#WP_MapService指向server上的文件夹
    #stage and upload the service if the sddraft analysis didn't contain errors  
    if analysis['errors'] == {}:  
        # excute StageService  
        arcpy.StageService_server(sddraft,sd)  
        # excute UploadServiceDfinition  
        arcpy.UploadServiceDefinition_server(sd,connectionfile)
        print "Service successfully published"	
    else:  
        # if the sddraft analysis contained errors,display them  
        print analysis['errors']
if __name__ == "__main__":
     sys.exit(publishService(sys.argv[1:]))
     #sys.exit(publishService("E:\\tmp","E:\\tmp\\myags.ags","E:\\tmp\\20170921125721974map.mxd"))