package c02

import org.jcsp.lang.ChannelOutput
import org.jcsp.lang.CSProcess

class ProduceHW implements CSProcess {
	
  def ChannelOutput outChannel

  void run() {
    def hi = 'Hello'
    def thing = 'World'
  
    outChannel.write(hi)
    outChannel.write(thing)
  }
}
