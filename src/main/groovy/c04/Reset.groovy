package c04

import groovyx.gpars.csp.ALT
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class Reset implements CSProcess {

  def ChannelInput inChannel
  def ChannelInput resetChannel
  def ChannelOutput outChannel

  void run () {
    def guards = [resetChannel, inChannel]
    def alt = new ALT(guards)
    while (true) {
      def index = alt.priSelect()
      def inputValue = inChannel.read()
      outChannel.write(index == 0 ? resetChannel.read() : inputValue)
    }
  }
}
