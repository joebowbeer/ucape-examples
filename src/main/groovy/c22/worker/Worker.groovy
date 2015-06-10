package c22.worker;

import c22.universalClasses.InitObject
import c22.universalClasses.Signal
import groovyx.gpars.csp.PAR
import org.jcsp.lang.Channel
import org.jcsp.lang.CSProcess

class Worker implements CSProcess {

  def toEmitter
  def fromEmitterLoc
  def fromEmitter
  def toCollector
  def baseId

  void run(){
    toEmitter.write(new InitObject(id: baseId, channelAddress: fromEmitterLoc))
    def startWork = Channel.one2one()
    def workFinished = Channel.one2one()
    def sharedData = []
    def sync = (Signal)fromEmitter.read() //synchronisation signal
    println "Worker: $baseId initialised and about to run internal processes"
    def getter = new GetInput ( toEmitter: toEmitter,
      baseId: baseId,
      fromEmitter: fromEmitter,
      toWorker: startWork.out(),
      sharedData: sharedData )
    def worker = new DoWork ( workOn: startWork.in(),
      workCompleted: workFinished.out(),
      workerId: baseId,
      sharedData: sharedData )
    def putter = new SendOutput ( workerFinished: workFinished.in(),
      toCollector: toCollector,
      sharedData: sharedData)
    new PAR([getter, worker, putter]).run()
    println "Worker: $baseId terminated"
  }
}
