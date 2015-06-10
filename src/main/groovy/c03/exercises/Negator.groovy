package c03.exercises

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class Negator implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput outChannel

  void run () {
    while (true) {
      outChannel.write(-inChannel.read())
    }
  }
}
