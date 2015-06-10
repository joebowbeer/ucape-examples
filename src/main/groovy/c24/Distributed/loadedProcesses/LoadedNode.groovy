package c24.Distributed.loadedProcesses

import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList
import groovyx.gpars.csp.PAR

import c24.Distributed.loaderObjects.WorkerInterface
import c24.Distributed.processes.Node

class LoadedNode implements WorkerInterface {

  def ChannelInputList inChannels       // one input channel from Reader
  def ChannelOutputList outChannels     // N output channels to Mergers

  def N = 0
  def sourceList
  def runs
  def node
  def timeRoot = '/Concordance/OutputFiles/Distributed/Times/'
  def runId  = 'SM'

  def connect(inChannels, outChannels){
    this.inChannels = inChannels
    this.outChannels = outChannels
  }

  void run(){
    def timeFileName = timeRoot + runId + "_N_" + node + "_times.txt"
    def timeHandle = new File(timeFileName)
    if (timeHandle.exists()) timeHandle.delete()
    def timeWriter = timeHandle.newPrintWriter()
    def node = new Node(
      nodeInChannel: inChannels[0],
      node: node,
      sourceList: sourceList,
      runs: runs,
      N: N,
      toMergers: outChannels,
      timeWriter: timeWriter)
    new PAR([node]).run()
  }
}
