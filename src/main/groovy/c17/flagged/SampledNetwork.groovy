package c17.flagged

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

class SampledNetwork implements CSProcess {  
	
  def ChannelInput inChannel
  def ChannelOutput outChannel
  
  void run() {
	  
    while (true) {
      def v = inChannel.read()
      v.c = v.a + v.b
      outChannel.write(v)
    }    
  }
}
