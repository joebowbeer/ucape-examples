package c17.counted

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.CSProcess
import org.jcsp.lang.CSTimer
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class CountedSampledNetwork implements CSProcess {

  def ChannelOutput outChannel
  def ChannelInput inChannel

  void run() {
    def timer = new CSTimer()
    while (true){
      def v = inChannel.read()
      timer.sleep(250)
      outChannel.write (v*2)
    }
  }
}
