package c19.accessServer

import groovyx.gpars.csp.PAR
import org.jcsp.lang.Channel
import org.jcsp.lang.CSProcess
import org.jcsp.net2.NetChannelLocation
import org.jcsp.util.OverWriteOldestBuffer

class AccessProcess implements CSProcess, Serializable {

  def NetChannelLocation processReceiveLocation
  def NetChannelLocation accessRequestLocation

  void run (){
    def buttonChannel = Channel.one2one(new OverWriteOldestBuffer(5))
    new PAR ([new AccessInterface( buttonEvents: buttonChannel.out()),
        new AccessCapability( buttonEvents: buttonChannel.in(),
          processReceiveLocation: processReceiveLocation,
          accessRequestLocation:accessRequestLocation)]).run()
  }
}
