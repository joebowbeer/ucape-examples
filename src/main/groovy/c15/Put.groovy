package c15

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelOutput

class Put implements CSProcess { 
	 
  def ChannelOutput outChannel  
  
  void run() {
    def i = 1
    while (true) {
      outChannel.write ( i )
      i = i + 1
    }
  }
}
