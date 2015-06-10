package c17.sniff

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import groovyx.gpars.csp.ALT
import org.jcsp.lang.CSProcess
import org.jcsp.lang.CSTimer
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class Sniffer implements CSProcess{

  def ChannelInput fromSystemCopy
  def ChannelOutput toComparator
  def sampleInterval = 10000

  void run() {
    def TIME = 0
    def INPUT = 1
    def timer = new CSTimer()
    def snifferAlt = new ALT([timer, fromSystemCopy])
    def timeout = timer.read() + sampleInterval
    timer.setAlarm(timeout)

    while (true) {
      def index = snifferAlt.select()
      switch (index) {
      case TIME:
        toComparator.write(fromSystemCopy.read())
        timeout = timer.read() + sampleInterval
        timer.setAlarm(timeout)
        break
      case INPUT:
        fromSystemCopy.read()
        break
      }
    } // end while
  }
}
