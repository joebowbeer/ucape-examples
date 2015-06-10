package c24.Distributed.processes

import org.jcsp.lang.CSProcess

import c24.SingleMachine.methods.defs;

class parExtractUniqueSequences implements CSProcess{

  def equalMap
  def n
  def startIndex
  def words
  def equalWordMap

  void run(){
    defs.extractUniqueSequences ( equalMap, n, startIndex, words, equalWordMap )
  }
}
