package c12.fork

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.ALT
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput

class Fork implements CSProcess {

  def ChannelInput left
  def ChannelInput right

  void run () {
    def fromPhilosopher = [left, right]
    def forkAlt = new ALT ( fromPhilosopher )
    while (true) {
      def i = forkAlt.select()
      fromPhilosopher[i].read() //pick up fork i
      fromPhilosopher[i].read() //put down fork i
    }
  }
}
