from flask import Flask, request
import json, os, signal
import threading
from package.fileManagement import FileManagement
from package.udpReceiver import UdpReceiver
from package.client import UdpClient

receiverList = []
def run_job():
        fileManager = FileManagement()
        arr = fileManager.readFromFile("ports.conf")
        if len(arr) == 0:
            # create file if not exist or is empty
            fileManager.clearFileContent()
            return
        elif len(arr) < 3:
            # file is not empty, but contains only no ports values, just description
            return
        else:
            index = 2
            while index < len(arr):
                # extract input and output ports
                if(arr[index] == ""): break
                innerArr = arr[index].split(':')
                print(innerArr)
                # send the input and output port to create udp stream
                receiver = UdpReceiver("0.0.0.0", int(innerArr[0]))
                # get the list of output ports
                outputArr = innerArr[1].split("|")
                for i in outputArr:
                    if i == "": break;
                    clientsMap = receiver.clientsMap
                    client = UdpClient(int(i), "0.0.0.0")
                    receiver.addClient(clientsMap, client)
                index += 1
                receiverList.append(receiver)
                receiver.start()
run_job()

app = Flask(__name__)

@app.route("/", methods=["GET"])
def index():
    return "You have reached index"



@app.route("/createStream", methods=["GET"])



@app.route('/stopServer', methods=['GET'])
def stopServer():
    for i in receiverList:
        i.interruptThread()
    os.kill(os.getpid(), signal.SIGINT)
    return "Server is shutting down"

if __name__ == "__main__":
    app.run()
