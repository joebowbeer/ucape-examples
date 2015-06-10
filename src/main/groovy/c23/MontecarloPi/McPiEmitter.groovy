package c23.MontecarloPi

import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList

import c23.loaderObjects.WorkerInterface

class McPiEmitter implements WorkerInterface {

  def ChannelInputList inChannels
  def ChannelOutputList outChannels
  def workers = 1
  def iterations = 192

  def connect(inChannels, outChannels){
    this.inChannels = inChannels
    this.outChannels = outChannels
  }

  void run(){
    println "running McPiEmitter"
    for ( w in 0 ..< workers) outChannels[w].write(iterations / workers)
  }
}
