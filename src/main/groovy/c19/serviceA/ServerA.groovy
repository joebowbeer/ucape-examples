package c19.serviceA

import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

def serverIP = "127.0.0.1"
// each service is located at a different port 	
def AServerAddress = new TCPIPNodeAddress(serverIP, 4567)
Node.getInstance().init(AServerAddress)
def initialChannel = NetChannel.numberedNet2One(1)
while (true) {
	def request = initialChannel.read()
	def processSendChannel =NetChannel.one2net(request.processReceiveLocation)
	def aProcess = new Aprocess()
	processSendChannel.write(aProcess)		
}
