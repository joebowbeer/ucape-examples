package c18

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.MobileAgent
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput
import org.jcsp.net2.NetChannel
import org.jcsp.net2.NetChannelLocation

class BackAgent implements MobileAgent {

  def ChannelOutput toLocal
  def ChannelInput fromLocal
  def NetChannelLocation backChannel

  def results = [ ]

  def connect ( List c ) {
    this.toLocal = c[0]
    this.fromLocal = c[1]
  }

  def disconnect (){
    toLocal = null
    fromLocal = null
  }

  void run() {
    def toRoot = NetChannel.one2net (backChannel)
    toLocal.write (results)
    results = fromLocal.read()
    def last = results.size - 1
    toRoot.write(results[last])
  }
}
