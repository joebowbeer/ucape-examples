package c24.Distributed.processes

import org.jcsp.lang.CSProcess

import c24.SingleMachine.methods.defs;

class parMultiSequencer implements CSProcess {

  def Nmax
  def n
  def baseList
  def outList
  def lastBlock

  void run(){
    defs.multiSequencer (Nmax, n, baseList, outList, lastBlock)
  }
}
