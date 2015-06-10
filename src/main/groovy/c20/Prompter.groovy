package c20

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class Prompter implements CSProcess{
  
  def ChannelOutput toQueue
  def ChannelInput fromQueue
  def ChannelOutput toReceiver

  void run() {
    while (true) {
      toQueue.write(1)
      toReceiver.write ( fromQueue.read() )
    }    
  }
}
