package c17.test

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.CSProcess
import org.jcsp.lang.ChannelInput

import c05.ScaledData

class CollectNumbers implements CSProcess {

  def ChannelInput inChannel
  def collectedList = []
  def scaledList = []
  def iterations = 20

  void run() {
    println "Collector Started"
    for ( i in 1 .. iterations) {
      def result = (ScaledData) inChannel.read()
      collectedList << result.original
      scaledList << result.scaled
    }
    println "Collector Finished"
    println "Original: ${collectedList}"
    println "Scaled  : ${scaledList}"
  }
}
