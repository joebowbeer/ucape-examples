package c22.worker;

import c22.universalClasses.Sentinel
import org.jcsp.lang.CSProcess

class DoWork implements CSProcess {

  def workOn
  def workCompleted
  def workerId
  def sharedData

  void run(){
    def index = -1
    def running = true
    while (running) {
      def o = workOn.read()
      if ( o instanceof Sentinel) {
        running = false
        workCompleted.write(o)
      }
      else {
        index = (Integer)o.intValue()
        sharedData[index].manipulate(workerId)
        workCompleted.write(index)
      }
    }
  }
}
