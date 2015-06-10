package c18

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.MobileAgent
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput
import org.jcsp.net2.NetChannel

class TripAgent implements MobileAgent {

  def ChannelOutput toLocal
  def ChannelInput fromLocal
  def tripList = [ ]
  def results = [ ]
  def int pointer

  def connect ( List c ) {
    this.toLocal = c[0]
    this.fromLocal = c[1]
  }

  def disconnect (){
    toLocal = null
    fromLocal = null
  }

  void run() {
    toLocal.write (results)
    results = fromLocal.read()
    if (pointer > 0) {
      pointer = pointer - 1
      def nextChannel = NetChannel.one2net (tripList.get(pointer))
      disconnect()
      nextChannel.write(this)
    }
    else {
      println "Agent has returned to TripRoot"
    }
  }
}
