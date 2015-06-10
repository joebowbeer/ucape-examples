package c23.MontecarloPi

import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList
import groovyx.gpars.csp.PAR
import org.jcsp.lang.Channel

import c23.loaderObjects.WorkerInterface

class McPiWorker implements WorkerInterface {

  def ChannelInputList inChannels
  def ChannelOutputList outChannels
  def cores = 1

  def connect(inChannels, outChannels){
    this.inChannels = inChannels
    this.outChannels = outChannels
  }

  void run(){
    println "running McPiWorker"
    def M2C = Channel.one2oneArray(cores)
    def C2M = Channel.one2oneArray(cores)
    def toCores = new ChannelOutputList(M2C)
    def fromCores = new ChannelInputList(C2M)
    def coreNetwork = (0 ..< cores).collect{ c ->
      return new McPiCore( inChannel: M2C[c].in(),
        outChannel: C2M[c].out() )
    }
    def manager = new McPiManager (
      inChannel: inChannels[0],
      outChannel: outChannels[0],
      toCores: toCores,
      fromCores: fromCores)
    new PAR(coreNetwork + [manager]).run()
  }
}
