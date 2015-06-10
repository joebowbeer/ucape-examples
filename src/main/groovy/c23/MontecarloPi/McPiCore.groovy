package c23.MontecarloPi

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class McPiCore implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput outChannel

  void run() {
    def iterations = inChannel.read()
    def rng = new Random()
    def int inQuadrant = 0
    1.upto(iterations) {
      def randomX = rng.nextFloat()
      def randomY = rng.nextFloat()
      if ( ((randomX * randomX)+(randomY * randomY)) < 1.0) inQuadrant = inQuadrant + 1
    }
    outChannel.write(inQuadrant)
  }
}
