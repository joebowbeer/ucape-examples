package c24.SingleMachine.supportProcesses

import org.jcsp.lang.CSProcess

import c24.SingleMachine.methods.defs;

class parFindEqualKeys implements CSProcess {

  def words
  def startIndex
  def inList
  def outMap

  void run(){
    defs.extractEqualValues(words, startIndex, inList, outMap)
  }
}
