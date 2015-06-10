package c15

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput

class Receiver implements CSProcess {
  
  def ChannelInput inChannel
  
  void run() {
    while (true) {
      def v = inChannel.read()
      println "$v"
    }
  }
}
