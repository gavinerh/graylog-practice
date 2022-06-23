import socket

class UdpClient:
    def __init__(self, port, address):
        self.port = port
        self.address = address
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    def sendData(self, data):
        self.socket.sendto(data, (self.address, self.port))

    def stopClient(self):
        self.socket.close()
