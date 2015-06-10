package c23.MontecarloPi

import groovyx.gpars.csp.ChannelOutputList
import groovyx.gpars.csp.PAR
import org.jcsp.lang.CSProcess

class TestEmitter implements CSProcess {

  def ChannelOutputList toWorkers
  def workers = 1
  def iterations = 192

  void run(){
    println "running testEmitter"
    def emitter = new McPiEmitter(
      workers: workers,
      iterations: iterations)
    emitter.connect(null, toWorkers)
    new PAR([emitter]).run()
  }
}
