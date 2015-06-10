package c23

import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList

import c23.loaderObjects.Sentinel
import c23.loaderObjects.WorkerInterface

class Emitter implements WorkerInterface  {

  def ChannelInputList inChannels
  def ChannelOutputList outChannels

  def connect(inChannels, outChannels){
    this.inChannels = inChannels
    this.outChannels = outChannels
  }

  void run(){
    //println "Emitter starting "
    def v = 1
    for ( i in 1 .. 10){
      def dObj = new DataObject (value: v)
      outChannels[0].write(dObj)
      v = v + 1
    }
    outChannels[0].write(new Sentinel())
    //println "Emitter Ending"
  }
}
