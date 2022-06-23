import socket
import threading
import time
import select

class UdpReceiver(threading.Thread):

    def __init__(self, IP_ADDRESS, PORT_NO):
        threading.Thread.__init__(self)
        self.address = IP_ADDRESS
        self.port = PORT_NO
        self.soc = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.soc.bind((self.address, PORT_NO))
        self.soc.setblocking(0)
        self.clientsMap = {}
        self.isOpen = True
        print("Socket created at port " + str(self.port))

    # called by the main method to add new client
    def addClient(self, oldClientMap, newClient):
        clientPort = newClient.port
        if clientPort in oldClientMap:
            # new client is not added
            return False

        self.populateClientsMap(oldClientMap, newClient)
        print("Client added")
        return True

    # send traffic from one port to another port, used to creating more than one listening clients for every udp port
    def rerouteTraffic(self):
        count = 0
        timeout_in_sec = 5
        while self.isOpen:
            ready = select.select([self.soc], [], [], timeout_in_sec)
            if ready[0]:
                data, addr = self.soc.recvfrom(10000)
                arr = self.clientsMap.keys()
                for k in arr:
                    self.clientsMap[k].sendData(data)
            else:
                continue

    # create a dictionary containing key of port numbers, and value of client object
    def populateClientsMap(self, clientList, newClient):
        # let new map reference the last map reference
        self.clientsMap = clientList
        self.clientsMap[newClient.port] = newClient

    # close the udp connection for this socket
    def closeConnection(self):
        self.soc.close()
        print("Udp Connection closed at port " + str(self.port))

    # runs the thread when the start command is executed
    def run(self):
        self.rerouteTraffic()
#        self.printOutput()

    # stop the thread from running and close the connection
    def interruptThread(self):
        self.isOpen = False
        # to synchronize the thread, make sure the printing is stopped
        self.join()
        self.closeConnection()

    # print output for testing purposes
    def printOutput(self):
        count = 0
        while count < 20:
            data, addr = self.soc.recvfrom(512)
            print(data.decode('utf-8'))
            count += 1
