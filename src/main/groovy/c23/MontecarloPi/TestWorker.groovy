package c23.MontecarloPi

import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList
import groovyx.gpars.csp.PAR
import org.jcsp.lang.CSProcess
import org.jcsp.lang.Channel
import org.jcsp.lang.ChannelInput
import org.jcsp.lang.ChannelOutput

class TestWorker implements CSProcess {

  def ChannelInput inChannel
  def ChannelOutput outChannel
  def cores = 1

  void run(){
    println "running testWorker"
    def M2C = Channel.one2oneArray(cores)
    def C2M = Channel.one2oneArray(cores)
    def toCores = new ChannelOutputList(M2C)
    def fromCores = new ChannelInputList(C2M)
    def coreNetwork = (0 ..< cores).collect{ c ->
      return new McPiCore(
        inChannel: M2C[c].in(),
        outChannel: C2M[c].out() ) }
    def manager = new McPiManager (
      inChannel: inChannel,
      outChannel: outChannel,
      toCores: toCores,
      fromCores: fromCores)
    new PAR(coreNetwork + [manager]).run()
  }
}
