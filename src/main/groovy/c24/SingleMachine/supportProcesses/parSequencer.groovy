package c24.SingleMachine.supportProcesses

import org.jcsp.lang.CSProcess

import c24.SingleMachine.methods.defs;

class parSequencer implements CSProcess {

  def n // the value of n for this sequence
  def inList
  def outList

  void run(){
    defs.sequencer(n, inList, outList)
  }
}
