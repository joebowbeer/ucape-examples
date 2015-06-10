package c03.exercises

import groovyx.gpars.csp.PAR
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput
import org.jcsp.plugNplay.ProcessRead

class Minus implements CSProcess {

  def ChannelInput inChannel0
  def ChannelInput inChannel1
  def ChannelOutput outChannel

  void run () {

    ProcessRead read0 = new ProcessRead ( inChannel0)
    ProcessRead read1 = new ProcessRead ( inChannel1)
    def parRead2 = new PAR ( [ read0, read1 ] )

    while (true) {
      parRead2.run()
      outChannel.write(read0.value - read1.value)
    }
  }
}
