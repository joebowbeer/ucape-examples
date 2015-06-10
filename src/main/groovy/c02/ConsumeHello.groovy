package c02

import org.jcsp.lang.ChannelInput
import org.jcsp.lang.CSProcess

class ConsumeHello implements CSProcess {
	
  def ChannelInput inChannel

  void run() {
    def first = inChannel.read()
    def second = inChannel.read()
    println "$first $second!"
  }
}
