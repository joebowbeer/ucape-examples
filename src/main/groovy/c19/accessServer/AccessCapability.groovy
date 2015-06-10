package c19.accessServer

import c19.netObjects.ClientRequestData
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.net2.NetChannel
import org.jcsp.net2.NetChannelLocation

class AccessCapability implements CSProcess {

	def ChannelInput buttonEvents
	def NetChannelLocation processReceiveLocation
	def NetChannelLocation accessRequestLocation
	
	void run (){
		def serviceRequired = buttonEvents.read()
		def clientRequest  = new ClientRequestData(processReceiveLocation: processReceiveLocation,
																							 serviceRequired: serviceRequired)
		def toAccess = NetChannel.any2net(accessRequestLocation)
		toAccess.write(clientRequest)
	}
}
