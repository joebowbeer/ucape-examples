package c19.groupLocationService

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput
import org.jcsp.net2.NetChannel
import org.jcsp.net2.NetChannelLocation

class GLcapability implements CSProcess, Serializable {
	
	def ChannelInput nameChannel
	def ChannelInput locationChannel
	def ChannelOutput label1Config
	def ChannelOutput label2Config
	def NetChannelLocation requestLocation
	
	void run(){
		def responseChannel = NetChannel.net2one()
		def responseLocation = responseChannel.getLocation()
		def requestChannel = NetChannel.any2net(requestLocation)
		def groupName = nameChannel.read()
		def groupData = new GLdata( responseLocation: responseLocation,
																groupName: groupName)  
		requestChannel.write(groupData) 
		def replyData = responseChannel.read()
    
		if (replyData.location != null){
			label1Config.write("Meeting at")
			label2Config.write(replyData.location)
		}
		else {
			label1Config.write("Does not yet exist")
			label2Config.write("Type meeting location")
			def location = locationChannel.read()
			def newGroup = new GLdata( responseLocation: responseLocation,
																 groupName: groupName,
																 location: location)
			requestChannel.write(newGroup)
		}
	}// end run
}
