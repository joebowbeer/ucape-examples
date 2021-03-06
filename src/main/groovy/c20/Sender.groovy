package c20

import org.jcsp.lang.CSProcess
import org.jcsp.lang.CSTimer
import org.jcsp.lang.ChannelOutput

class Sender implements CSProcess {
	
  def ChannelOutput toElement
  def int element
  def int nodes
  def int iterations
  
  def void run() {
    def timer = new CSTimer()
    for ( i in 1 .. iterations ) {
      def dest = (i % (nodes) ) + 2
      if ( dest != element ) {
        def packet = new RingPacket ( source: element, destination: dest , value: (element * 10000) + i , full: true)
        toElement.write(packet)
        timer.sleep(500)
      }
    }
    println "Sender $element has finished"
  }
}
