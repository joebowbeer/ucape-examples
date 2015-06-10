package c19.UniversalClient

import c19.netObjects.ClientLocation

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ProcessManager
import org.jcsp.net2.NetChannel
import org.jcsp.net2.Node
import org.jcsp.net2.tcpip.TCPIPNodeAddress

class UCCapability implements CSProcess {
	
	def ChannelInput receiveNodeIdentity
	def networkBaseIP = "127.0.0."
	
	void run(){
		def clientINodeId = receiveNodeIdentity.read()
		def clientIpAddress = networkBaseIP + clientINodeId
		def nodeAddr = new TCPIPNodeAddress(clientIpAddress,1000)
		Node.getInstance().init(nodeAddr)
		// create channel on which to receive processes from server
		def processReceive = NetChannel.numberedNet2One(2)
		def processReceiveLocation = processReceive.getLocation()
		//create default channel to access server
		def accessAddress = new TCPIPNodeAddress("127.0.0.1",2345)
		def toAccess = NetChannel.any2net(accessAddress, 1)
		def receiveLocation = new ClientLocation (processReceiveLocation: processReceiveLocation)
		toAccess.write(receiveLocation)
		def accessProcess = processReceive.read()
		def pmA = new ProcessManager(accessProcess)
		pmA.start()
		def serviceProcess = processReceive.read()
		def pmS = new ProcessManager(serviceProcess)
		pmS.start()
	}
}
