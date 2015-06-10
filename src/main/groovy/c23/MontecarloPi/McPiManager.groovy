package c23.MontecarloPi

import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList
import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class McPiManager implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput outChannel
  def ChannelOutputList toCores
  def ChannelInputList fromCores

  void run() {
    def cores = fromCores.size()
    def iterations = inChannel.read()
    for ( c in 0..< cores) toCores[c].write(iterations / cores)
    def quadSum = 0
    for ( c in 0..< cores) quadSum = quadSum + fromCores[c].read()
    outChannel.write(quadSum)
  }
}
