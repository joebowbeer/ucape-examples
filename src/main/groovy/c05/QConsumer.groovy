package c05

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.CSProcess
import org.jcsp.lang.CSTimer
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class QConsumer implements CSProcess {

  def ChannelOutput get
  def ChannelInput receive
  def long delay = 0

  void run () {
    def timer = new CSTimer()
    println  "QConsumer has started"
    def running = true

    while (running) {
      get.write(1)
      def v = receive.read()
      println "QConsumer has read $v"
      timer.sleep (delay)
      if ( v == null ) running = false
    }
  }
}
