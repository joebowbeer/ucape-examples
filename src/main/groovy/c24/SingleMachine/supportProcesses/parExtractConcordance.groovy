package c24.SingleMachine.supportProcesses

import org.jcsp.lang.CSProcess

import c24.SingleMachine.methods.defs;

class parExtractConcordance implements CSProcess {

  def equalMap
  def n
  def startIndex
  def words
  def minSeqLen
  def printWriter

  void run() {
    defs.extractConcordance ( equalMap, n,
      startIndex, words,
      minSeqLen, printWriter )
    printWriter.flush()
    printWriter.close()
  }
}
