  GNU nano 4.8                                    main.py                                              from flask import Flask, request
import json, os, signal
import threading
from package.fileManagement import FileManagement
from package.udpReceiver import UdpReceiver
from package.client import UdpClient

receiverList = []
# creating the udp port forwarding to a list of client ports depending on file configuration
def run_job():
        # need to change this method to be flexible to find comments and ignore comments
        fileManager = FileManagement()
        arr = fileManager.readFromFile("ports.conf")
        if len(arr) == 0:
            # create file if not exist or is empty
            fileManager.clearFileContent()
            return
        else:
            # need to check if first character is char #
            index = 0
            print("Length of array is: " + str(len(arr)))
            while index < len(arr):
                print('index: ' + str(index))
                if arr[index].strip() == '':
                    index += 1
                    continue
                if arr[index][0] == '#':
                    print("found #")
                    index += 1
                    continue
                # extract input and output ports
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
                receiverList.append(receiver)
                receiver.start()
                index += 1
run_job()
fileManager = FileManagement()
pid = str(os.getpid())
fileManager.writeToFile('w', 'pid', pid)
