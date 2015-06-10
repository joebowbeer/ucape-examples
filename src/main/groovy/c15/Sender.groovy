package c15

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.CSProcess
import org.jcsp.lang.CSTimer
import org.jcsp.lang.ChannelOutput

class Sender implements CSProcess {
  
  def ChannelOutput outChannel
  def String id
  
  void run() {
    def timer = new CSTimer()
    while (true) {
      timer.sleep(10000)
      outChannel.write ( id )
    }
  }
}

      
  
