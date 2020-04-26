import sys
def AddService(argv):
#def AddService(host, Port, username, password, workspace, mxdFileName, serviceName):

    host = argv[0]
    Port = argv[1]
    hhh = argv[2]
    print host
    print Port
    print hhh
if __name__ == "__main__":
    sys.exit(AddService(sys.argv[1:]))