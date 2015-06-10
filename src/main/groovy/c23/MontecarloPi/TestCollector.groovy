package c23.MontecarloPi

import groovyx.gpars.csp.ChannelInputList
import groovyx.gpars.csp.PAR
import org.jcsp.lang.CSProcess

class TestCollector implements CSProcess {

  def ChannelInputList fromWorkers
  def workers = 1
  def iterations = 192

  void run(){
    println "running testCollector"
    def collector = new McPiCollector (
      workers: workers,
      iterations: iterations)
    collector.connect(fromWorkers, null)
    new PAR ([collector]).run()
  }
}
