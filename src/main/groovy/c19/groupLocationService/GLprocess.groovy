package c19.groupLocationService

import groovyx.gpars.csp.PAR
import org.jcsp.lang.Channel
import org.jcsp.lang.CSProcess
import org.jcsp.net2.NetChannelLocation

class GLprocess implements CSProcess, Serializable{

  def NetChannelLocation requestLocation

  void run() {
    def nameChannel = Channel.one2one()
    def locationChannel = Channel.one2one()
    def label1Config = Channel.one2one()
    def label2Config = Channel.one2one()
    new PAR([ new GLinterface( nameChannel: nameChannel.out(),
          locationChannel: locationChannel.out(),
          label1Config: label1Config.in(),
          label2Config: label2Config.in() ),
        new GLcapability(nameChannel: nameChannel.in(),
          locationChannel: locationChannel.in(),
          label1Config: label1Config.out(),
          label2Config: label2Config.out(),
          requestLocation: requestLocation)]).run()
  }
}
