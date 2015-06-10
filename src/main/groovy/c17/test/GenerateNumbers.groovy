package c17.test

// copyright 2012-13 Jon Kerridge
// Let's Do It In Parallel

import org.jcsp.lang.CSProcess
import org.jcsp.lang.CSTimer
import org.jcsp.lang.ChannelOutput

class GenerateNumbers implements CSProcess {

  def delay = 1000
  def iterations = 20
  def ChannelOutput outChannel
  def generatedList = []

  void run() {
    println "Numbers started"
    def timer = new CSTimer()
    for (i in 1 .. iterations) {
      //println "Generated ${i}"
      outChannel.write(i)
      generatedList << i
      timer.sleep(delay)
    }
    println "Numbers finished"
    //println"Generated: ${generatedList}"
  }
}
