package c24.Distributed.loadedProcesses

import c24.Distributed.loaderObjects.WorkerInterface
import c24.Distributed.processes.Merger
import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.ChannelOutputList
import groovyx.gpars.csp.PAR

class LoadedMerger implements WorkerInterface {

  def ChannelInputList inChannels
  def ChannelOutputList outChannels

  def sourceList
  def runs
  def N
  def minSeqLen
  def outRoot
  def timeRoot
  def runId

  def connect(inChannels, outChannels){
    this.inChannels = inChannels
    this.outChannels = outChannels
  }

  void run(){
    def merger = new Merger (
      fromWorkers: inChannels,
      N: N,
      sourceList: sourceList,
      runs: runs,
      minSeqLen: minSeqLen,
      outRoot: outRoot,
      timeRoot: timeRoot,
      runId: runId)
    new PAR([merger]).run()
  }
}
