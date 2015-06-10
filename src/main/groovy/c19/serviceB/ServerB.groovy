package c19.serviceB

import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

def serverIP = "127.0.0.1"
// each service is located at a different port 	
def BServerAddress = new TCPIPNodeAddress(serverIP, 5678)
Node.getInstance().init(BServerAddress)
def initialChannel = NetChannel.numberedNet2One(1)
while (true) {
	def request = initialChannel.read()
	def processSendChannel =NetChannel.one2net(request.processReceiveLocation)
	def bProcess = new Bprocess()
	processSendChannel.write(bProcess)		
}
